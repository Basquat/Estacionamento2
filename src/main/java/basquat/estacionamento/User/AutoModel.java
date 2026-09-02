package basquat.estacionamento.User;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

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

    // Formas de pagamento do veículo (dinheiro, pix, ...). Um veículo pode ser
    // quitado com várias formas ao mesmo tempo (ex.: R$ 20 dinheiro + R$ 5 pix).
    //
    // A coluna auto_id é gravada pela própria entidade Pagamento (setAutoId),
    // por isso o @JoinColumn aqui é apenas de leitura (insertable/updatable
    // false) — evita a dupla escrita da FK e o problema de INSERT com auto_id
    // nulo em coluna NOT NULL. cascade + orphanRemoval fazem os pagamentos
    // serem salvos/apagados junto com o veículo.
    // BatchSize: ao listar N veículos, o Hibernate carrega os pagamentos de
    // todos em poucas queries (WHERE auto_id IN (...)) em vez de uma por
    // veículo (N+1) — o GET /Automoveis é chamado a cada evento de sync.
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    @JoinColumn(
            name = "auto_id",
            insertable = false,
            updatable = false,
            // Não deixa o Hibernate criar a constraint de FK no banco: a tabela
            // pagamento pode ter linhas órfãs de testes antigos e a criação da
            // FK falharia a cada boot. A integridade é garantida no código.
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private List<Pagamento> pagamentos = new ArrayList<>();

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

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        // Mantém a mesma instância de coleção gerenciada pelo Hibernate.
        this.pagamentos.clear();
        if (pagamentos != null) {
            this.pagamentos.addAll(pagamentos);
        }
    }
}
