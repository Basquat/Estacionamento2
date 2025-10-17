package com.example.Estacionamento.Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class automoveis {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ID;

  public String placa;
  public boolean tipo;

  public automoveis() {
  }

  public automoveis(String placa, boolean tipo) {
    this.placa = placa;
    this.tipo = tipo;
  }

  public String getPlaca() {
    return placa;
  }

  public void setPlaca(String placa) {
    this.placa = placa;
  }

  public boolean getTipo() {
    return tipo;
  }

  public void setTipo(boolean tipo) {
    this.tipo = tipo;
  }
}
