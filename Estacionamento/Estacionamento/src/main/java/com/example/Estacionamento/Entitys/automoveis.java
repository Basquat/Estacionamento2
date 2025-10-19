package com.Estacionamento2.Entitys;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "automoveis")
public class automoveis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehiclesid")
    private Long vehiclesId;
    
    @Column(nullable = false, unique = true)
    private String placa;
    
    @Column(nullable = false)
    private Double valor;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TipoAutomoveis type;
    
    @Column(nullable = false)
    private Boolean pago = false;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private FormaPagamento formaPagamento;
    
    @Column(name = "entrada")
    private LocalDateTime entrada;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    public Automoveis() {
        this.entrada = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters e Setters
    public Long getVehiclesId() { return vehiclesId; }
    public void setVehiclesId(Long vehiclesId) { this.vehiclesId = vehiclesId; }
    
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    
    public TipoAutomoveis getType() { return type; }
    public void setType(TipoAutomoveis type) { this.type = type; }
    
    public Boolean getPago() { return pago; }
    public void setPago(Boolean pago) { this.pago = pago; }
    
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(FormaPagamento formaPagamento) { this.formaPagamento = formaPagamento; }
    
    public LocalDateTime getEntrada() { return entrada; }
    public void setEntrada(LocalDateTime entrada) { this.entrada = entrada; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

enum TipoAutomoveis {
    CARRO, MOTO
}

enum FormaPagamento {
    PIX, DINHEIRO
}
