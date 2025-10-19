package com.Estacionamento2.service;

import com.Estacionamento2.model.paymentEntity;
import com.Estacionamento2.repository.paymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class paymentService {
    
    @Autowired
    private paymentRepository paymentrepository;
    
    public List<Payment> findAll() {
        return paymentrepository.findAll();
    }
    
    public Payment save(Payment payment) {
        return paymentrepository.save(payment);
    }
    
    public List<Payment> findByAutomovelId(Long automovelId) {
        return paymentrepository.findByAutomoveisId(automovelId);
    }
    
    public Double getTotalPayment() {
        Double total = paymentrepository.findTotalPaymentPago();
        return total != null ? total : 0.0;
    }
}
