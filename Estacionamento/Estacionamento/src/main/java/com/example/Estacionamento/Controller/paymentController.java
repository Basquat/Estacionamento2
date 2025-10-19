package com.Estacionamento2.controller;

import com.Estacionamento2.Entitys.payment;
import com.Estacionamento2.service.paymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {
    
    @Autowired
    private paymentService PaymentService;
    
    @GetMapping
    public List<Payment> listarTodos() {
        return PaymentService.findAll();
    }
    
    @GetMapping("/automovel/{automovelId}")
    public List<Payment> listarPorAutomovel(@PathVariable Long automovelId) {
        return PaymentService.findByAutomovelId(automovelId);
    }
    
    @GetMapping("/total")
    public ResponseEntity<Double> getTotalPayment() {
        Double total = PaymentService.getTotalPayment();
        return ResponseEntity.ok(total);
    }
    
    @PostMapping
    public Payment criarPayment(@RequestBody payment Payment) {
        return PaymentService.save(Payment);
    }
}
