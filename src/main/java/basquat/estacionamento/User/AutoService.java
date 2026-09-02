package basquat.estacionamento.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Regras de negócio dos veículos. O controller só traduz HTTP <-> chamadas
 * aqui; validação, "não encontrado" e a sincronização dos pagamentos ficam
 * concentradas neste ponto.
 */
@Service
public class AutoService {

    private final AutoRepository autoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final EventoService eventoService;

    public AutoService(AutoRepository autoRepository,
                       PagamentoRepository pagamentoRepository,
                       EventoService eventoService) {
        this.autoRepository = autoRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.eventoService = eventoService;
    }

    public List<AutoModel> listar() {
        return autoRepository.findAll();
    }

    public AutoModel buscar(String id) {
        return autoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Automóvel não encontrado"));
    }

    @Transactional
    public AutoModel criar(AutoModel model) {
        if (model.getPlaca() == null || model.getPlaca().isBlank()) {
            throw new RegraNegocioException("A placa é obrigatória.");
        }
        if (autoRepository.existsByPlacaIgnoreCase(model.getPlaca())) {
            throw new RegraNegocioException("A placa " + model.getPlaca() + " já está cadastrada.");
        }
        sincronizarPagamentos(model, model.getPagamentos());
        AutoModel salvo = autoRepository.save(model);
        eventoService.notificar();
        return salvo;
    }

    @Transactional
    public AutoModel atualizar(String id, AutoModel detalhe) {
        AutoModel model = buscar(id);

        if (!model.getPlaca().equalsIgnoreCase(detalhe.getPlaca())
                && autoRepository.existsByPlacaIgnoreCase(detalhe.getPlaca())) {
            throw new RegraNegocioException("A placa " + detalhe.getPlaca() + " já está cadastrada.");
        }

        model.setPlaca(detalhe.getPlaca());
        model.setTipo(detalhe.getTipo());
        model.setValor(detalhe.getValor());
        model.setPago(detalhe.getPago());
        model.setEntrada(detalhe.getEntrada());

        // Regrava a lista inteira de pagamentos: os antigos saem (orphanRemoval)
        // e os novos entram. Editar um veículo já pago sempre deixa o
        // detalhamento (dinheiro/pix/troco) coerente.
        model.getPagamentos().clear();
        sincronizarPagamentos(model, detalhe.getPagamentos());

        AutoModel salvo = autoRepository.save(model);
        eventoService.notificar();
        return salvo;
    }

    @Transactional
    public void remover(String id) {
        // Duas queries diretas (DELETE ... WHERE) em vez de: existsById + findById
        // + carregar pagamentos + delete de cada filho + delete do pai. Sai de ~6
        // idas ao banco para ~3 — a latência Render↔Supabase pesa em cada uma.
        pagamentoRepository.deleteByAutoId(id);
        int removidos = autoRepository.deleteByIdReturningCount(id);
        if (removidos == 0) {
            throw new RecursoNaoEncontradoException("Automóvel não encontrado");
        }
        eventoService.notificar();
    }

    // Liga cada pagamento recebido ao veículo e força id/data novos para o
    // Hibernate tratar todos como inserção.
    private void sincronizarPagamentos(AutoModel model, List<Pagamento> pagamentos) {
        if (pagamentos == null) {
            return;
        }
        for (Pagamento p : pagamentos) {
            p.setId(null);
            p.setData(null);
            p.setAutoId(model.getId());
            if (!model.getPagamentos().contains(p)) {
                model.getPagamentos().add(p);
            }
        }
    }
}
