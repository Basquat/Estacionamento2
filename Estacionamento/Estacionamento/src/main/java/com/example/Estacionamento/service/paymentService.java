package com.example.Estacionamento.service;

import com.example.Estacionamento.Entitys.paymentEntity;
import com.example.Estacionamento.repository.paymentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class paymentService {
  private final paymentRepository payment;

  public paymentService(paymentRepository payment) {
    this.payment = payment;
  }

  public List<paymentEntity> findALL() {
    return payment.findAll();
  }

  public paymentEntity findByid(Long id) {
    return payment.findById(id).orElse(null);
  }
  //

  public paymentEntity save(paymentEntity payEntity) {
    return payment.save(payEntity);
  }

  public void delete(Long id) {
    payment.deleteById(id);
  }
}
