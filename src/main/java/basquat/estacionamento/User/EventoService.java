package basquat.estacionamento.User;

import jakarta.annotation.PreDestroy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EventoService {

    // 30 min: o browser reconecta sozinho quando o servidor fecha por timeout.
    private static final long TIMEOUT_MS = 30 * 60 * 1000L;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    // O broadcast roda FORA da thread que atendeu o POST/PUT. Antes, enviar o
    // evento para cada cliente (inclusive conexões mortas, que só falham após o
    // timeout de TCP) era feito de forma síncrona dentro da requisição — a
    // resposta do "salvar" só voltava depois disso. Era a maior causa da
    // lentidão para enviar.
    private final ExecutorService broadcaster = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "sse-broadcast");
        t.setDaemon(true);
        return t;
    });

    public SseEmitter createEmitter() {
        SseEmitter emitter = new SseEmitter(TIMEOUT_MS);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            emitter.complete();
        });
        emitter.onError(e -> emitters.remove(emitter));
        emitters.add(emitter);
        broadcaster.submit(() -> enviarUm(emitter, "conectado", "ok"));
        return emitter;
    }

    /** Notifica todos os clientes que os dados mudaram (assíncrono). */
    public void notificar() {
        broadcaster.submit(() -> broadcast("atualizacao", "atualizado"));
    }

    /**
     * Mantém as conexões vivas atrás do proxy do Render e derruba as mortas
     * cedo, em vez de deixá-las acumular até o timeout.
     */
    @Scheduled(fixedRate = 25_000)
    public void heartbeat() {
        // Pelo mesmo executor do notificar(): todos os envios ficam serializados
        // numa única thread (SseEmitter.send não é seguro para envio concorrente).
        broadcaster.submit(() -> {
            if (!emitters.isEmpty()) {
                broadcast("ping", Long.toString(System.currentTimeMillis()));
            }
        });
    }

    private void broadcast(String evento, String dado) {
        for (SseEmitter emitter : emitters) {
            enviarUm(emitter, evento, dado);
        }
    }

    private void enviarUm(SseEmitter emitter, String evento, String dado) {
        try {
            emitter.send(SseEmitter.event().name(evento).data(dado).reconnectTime(3000));
        } catch (Exception e) {
            emitters.remove(emitter);
            try {
                emitter.completeWithError(e);
            } catch (Exception ignored) {
                // já estava fechado
            }
        }
    }

    @PreDestroy
    void encerrar() {
        broadcaster.shutdownNow();
        emitters.forEach(e -> {
            try {
                e.complete();
            } catch (Exception ignored) {
            }
        });
        emitters.clear();
    }
}
