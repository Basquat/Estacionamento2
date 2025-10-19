package com.estacionaplus.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "veiculos")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehiclesid")
    private Long vehiclesId;
    
    @Column(nullable = false, unique = true)
    private String placa;
    
    @Column(nullable = false)
    private Double valor;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVeiculo type;
    
    @Column(nullable = false)
    private Boolean pago = false;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private FormaPagamento formaPagamento;
    
    @Column(nullable = false)
    private LocalDateTime entrada;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Construtores, Getters e Setters
    public Veiculo() {
        this.entrada = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
    
    // ... getters e setters
}

enum TipoVeiculo {
    CARRO, MOTO
}

enum FormaPagamento {
    PIX, DINHEIRO
}
