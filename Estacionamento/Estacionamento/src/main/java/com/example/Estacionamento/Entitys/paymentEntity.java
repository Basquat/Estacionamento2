package com.example.Estacionamento.Entitys;

import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class paymentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private double pix;
  private double Dinheiro;

  public paymentEntity() {
  }

  public paymentEntity(double pix, double Dinheiro) {
    this.pix = pix;
    this.Dinheiro = Dinheiro;
  }

  public double getPix() {
    return pix;
  }

  public void setPix(double pix) {
    this.pix = pix;
  }

  public double getDinheiro() {
    return Dinheiro;
  }

  public void setDinheiro(double Dinheiro) {
    this.Dinheiro = Dinheiro;
  }
}
