package com.Estacionamento2.controller;

import com.Estacionamento2.Entitys.Payment;
import com.Estacionamento2.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    @GetMapping
    public List<Payment> listarTodos() {
        return paymentService.findAll();
    }
    
    @GetMapping("/automovel/{automovelId}")
    public List<Payment> listarPorAutomovel(@PathVariable Long automovelId) {
        return paymentService.findByAutomovelId(automovelId);
    }
    
    @GetMapping("/total")
    public ResponseEntity<Double> getTotalPayment() {
        Double total = paymentService.getTotalPayment();
        return ResponseEntity.ok(total);
    }
    
    @PostMapping
    public Payment criarPayment(@RequestBody Payment payment) {
        return paymentService.save(payment);
    }
}
