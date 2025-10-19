package com.Estacionamento2.dto;

import java.time.LocalDateTime;

public class AutomoveisDTO {
    private Long vehiclesId;
    private String placa;
    private String type;
    private Double valor;
    private Boolean pago;
    private String formaPagamento;
    private LocalDateTime entrada;
    
    public AutomoveisDTO() {}
    
    public AutomoveisDTO(Long vehiclesId, String placa, String type, Double valor, Boolean pago, String formaPagamento, LocalDateTime entrada) {
        this.vehiclesId = vehiclesId;
        this.placa = placa;
        this.type = type;
        this.valor = valor;
        this.pago = pago;
        this.formaPagamento = formaPagamento;
        this.entrada = entrada;
    }
    
    // Getters e Setters
    public Long getVehiclesId() { return vehiclesId; }
    public void setVehiclesId(Long vehiclesId) { this.vehiclesId = vehiclesId; }
    
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    
    public Boolean getPago() { return pago; }
    public void setPago(Boolean pago) { this.pago = pago; }
    
    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
    
    public LocalDateTime getEntrada() { return entrada; }
    public void setEntrada(LocalDateTime entrada) { this.entrada = entrada; }
}
