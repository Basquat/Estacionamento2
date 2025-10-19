package com.Estacionamento2.repository;

import com.Estacionamento2.Entitys.paymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface paymentRepository extends JpaRepository<paymentEntity, Long> {
    List<Payment> findByStatus(String status);
    
    @Query("SELECT SUM(p.valor) FROM paymentEntity p WHERE p.status = 'pago'")
    Double findTotalPaymentPago();
    
    List<Payment> findByAutomoveisId(Long automovelId);
}
