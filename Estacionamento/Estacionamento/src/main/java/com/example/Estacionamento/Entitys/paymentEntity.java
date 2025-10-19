package com.Estacionamento2.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class paymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "valor", nullable = false)
    private Double valor;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPagamento status;
    
    @ManyToOne
    @JoinColumn(name = "automovel_id")
    private Automoveis automoveis;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    public paymentEntity() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    
    public StatusPagamento getStatus() { return status; }
    public void setStatus(StatusPagamento status) { this.status = status; }
    
    public Automoveis getAutomoveis() { return automoveis; }
    public void setAutomoveis(Automoveis automoveis) { this.automoveis = automoveis; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

enum StatusPagamento {
    pago, pendente
}
