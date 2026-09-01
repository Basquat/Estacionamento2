package basquat.estacionamento.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Faz toda exceção virar um JSON {"message": "..."} com o status HTTP certo —
 * antes qualquer RuntimeException virava 500 sem corpo e o front-end nunca
 * conseguia mostrar o motivo do erro.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> naoEncontrado(RecursoNaoEncontradoException ex) {
        return corpo(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, Object>> regraNegocio(RegraNegocioException ex) {
        return corpo(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> generico(RuntimeException ex) {
        return corpo(HttpStatus.BAD_REQUEST, ex.getMessage() != null ? ex.getMessage() : "Erro ao processar a requisição");
    }

    private ResponseEntity<Map<String, Object>> corpo(HttpStatus status, String mensagem) {
        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "message", mensagem
        ));
    }
}
