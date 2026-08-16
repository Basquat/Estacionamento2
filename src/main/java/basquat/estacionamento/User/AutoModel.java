package basquat.estacionamento.User;

import jakarta.persistence.*;

@Entity
@Table(name = "auto_model")
public class AutoModel {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "placa", nullable = false, unique = true)
    private String placa;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "valor", nullable = false)
    private double valor;

    @Column(name = "pago", nullable = false)
    private Boolean pago;

    @Column(name = "entrada", nullable = false)
    private Long entrada;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public Long getEntrada() {
        return entrada;
    }

    public void setEntrada(Long entrada) {
        this.entrada = entrada;
    }
}
