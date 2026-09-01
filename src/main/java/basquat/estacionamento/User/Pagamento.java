package basquat.estacionamento.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    // FK para auto_model.id. Continua sendo uma coluna escrita por esta
    // entidade — a associação em AutoModel é apenas leitura (ver AutoModel).
    @Column(name = "auto_id", nullable = false)
    private String autoId;

    // O front-end usa a chave "metodo"; o banco usa a coluna "metodo_pagamento".
    @Column(name = "metodo_pagamento", nullable = false)
    @JsonProperty("metodo")
    private String metodoPagamento;

    // Parte do valor total do veículo quitada por esta forma de pagamento.
    @Column(name = "valor", nullable = false)
    private double valor;

    // Troco devolvido ao cliente quando ele paga em dinheiro um valor acima
    // do devido. NÃO entra na soma que precisa bater com o valor do veículo —
    // é só registro para o caixa não ficar "sobrando" dinheiro no fim do dia.
    //
    // Coluna anulável de propósito: assim o `ddl-auto=update` consegue
    // adicioná-la mesmo numa tabela que já tenha linhas (uma coluna NOT NULL
    // sem default quebraria a migração). Linhas antigas viram troco = 0.
    @Column(name = "troco")
    private Double troco;

    @Column(name = "data", nullable = false)
    private Long data;

    @PrePersist
    @PreUpdate
    void preencherPadroes() {
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
        }
        if (data == null) {
            data = System.currentTimeMillis();
        }
        if (troco == null || troco < 0) {
            troco = 0.0;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getTroco() {
        return troco == null ? 0.0 : troco;
    }

    public void setTroco(Double troco) {
        this.troco = troco;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }
}
