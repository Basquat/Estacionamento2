package com.Estacionamento2.service;

import com.estacionamento2.Entitys.Automoveis;
import com.estacionamento2.Entitys.Payment;
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
    private automoveisRepository automoveisrepository;
    
    @Autowired
    private paymentRepository paymentrepository;
    
    public List<Automoveis> findAll() {
        return automoveisrepository.findAll();
    }
    
    public Optional<Automoveis> findById(Long id) {
        return automoveisrepository.findById(id);
    }
    
    public Automoveis save(Automoveis automoveis) {
        return automoveisrepository.save(automoveis);
    }
    
    public void deleteById(Long id) {
        automoveisrepository.deleteById(id);
    }
    
    @Transactional
    public Automoveis togglePagamento(Long id) {
        Automoveis automoveis = automoveisrepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Automóvel não encontrado"));
        
        automoveis.setPago(!automoveis.getPago());
        return automoveisrepository.save(automoveis);
    }
    
    public boolean placaExists(String placa) {
        return automoveisrepository.existsByPlaca(placa);
    }
}
