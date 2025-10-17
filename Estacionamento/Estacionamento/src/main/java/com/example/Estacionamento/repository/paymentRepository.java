package com.example.Estacionamento.repository;

import com.example.Estacionamento.Entitys.paymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface paymentRepository extends JpaRepository<paymentEntity, Long> {

  paymentEntity findBypix(double pix);

  paymentEntity findByDinheiro(double Dinheiro);
}

//test
//
