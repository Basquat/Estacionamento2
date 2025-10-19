package com.Estacionamento2.service;

import com.estacionamento2.model.Automoveis;
import com.estacionamento2.model.Payment;
import com.estacionamento2.repository.AutomoveisRepository;
import com.estacionamento2.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AutomoveisService {
    
    @Autowired
    private AutomoveisRepository automoveisRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    public List<Automoveis> findAll() {
        return automoveisRepository.findAll();
    }
    
    public Optional<Automoveis> findById(Long id) {
        return automoveisRepository.findById(id);
    }
    
    public Automoveis save(Automoveis automoveis) {
        return automoveisRepository.save(automoveis);
    }
    
    public void deleteById(Long id) {
        automoveisRepository.deleteById(id);
    }
    
    @Transactional
    public Automoveis togglePagamento(Long id) {
        Automoveis automoveis = automoveisRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Automóvel não encontrado"));
        
        automoveis.setPago(!automoveis.getPago());
        return automoveisRepository.save(automoveis);
    }
    
    public boolean placaExists(String placa) {
        return automoveisRepository.existsByPlaca(placa);
    }
}
