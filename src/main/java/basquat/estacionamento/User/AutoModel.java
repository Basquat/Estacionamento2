package basquat.estacionamento.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class AutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int AutomoveisID;

    private boolean pago;
    public enum Automovel {Carro, Moto;}
    enum MetodoPagamento {Pix, Dinheiro}
    private String placa;
    private int valor;

    private Automovel automovel;
    private MetodoPagamento metodoPagamento;

    @Override
    public String toString() {
        return String.format(
                "AutoModel[AutomoveisID=%s, Automovel=%s, pago=%s, MetodoPagamento=%s, placa=%s, valor=%s]",
                AutomoveisID, automovel, metodoPagamento, placa, valor
        );
    }
}