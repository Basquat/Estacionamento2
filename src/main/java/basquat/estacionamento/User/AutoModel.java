package basquat.estacionamento.User;

import jakarta.persistence.*;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;



@Data
@Entity
@Table(name = "auto_model")
public class AutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int automoveisid;


    private Boolean pago;

   public enum automovel {Carro, Moto;}
    public enum metodopagamento {Pix, Dinheiro}


    private String placa;


    private int valor;

    @Enumerated(EnumType.STRING)
    private automovel automovel;

    @Enumerated(EnumType.STRING)
    private metodopagamento metodopagamento;

    @Override
    public String toString() {
        return String.format(
                "AutoModel[AutomoveisID=%s, Automovel=%s, pago=%s, MetodoPagamento=%s, placa=%s, valor=%s]",
                automoveisid, automovel, metodopagamento, placa, valor
        );
    }
}

