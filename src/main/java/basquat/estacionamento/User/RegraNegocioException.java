package basquat.estacionamento.User;

/** Violação de regra de negócio (ex.: placa duplicada) -> HTTP 409. */
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
