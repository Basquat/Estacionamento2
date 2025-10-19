package com.estacionaplus.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pix")
public class paymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Double valor;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento status;
    
    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Construtores, Getters e Setters
}

enum StatusPagamento {
    pago, pendente
}
