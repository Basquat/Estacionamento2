package basquat.estacionamento.User;

import jakarta.persistence.*;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;




@Entity
@Table(name = "auto_model")
public class AutoModel {

    public int getAutomoveisid() {
        return automoveisid;
    }

    public void setAutomoveisid(int automoveisid) {
        this.automoveisid = automoveisid;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public automovel getAutomovel() {
        return automovel;
    }

    public void setAutomovel(automovel automovel) {
        this.automovel = automovel;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public metodopagamento getMetodopagamento() {
        return metodopagamento;
    }

    public void setMetodopagamento(metodopagamento metodopagamento) {
        this.metodopagamento = metodopagamento;
    }

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

