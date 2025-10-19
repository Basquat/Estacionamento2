package com.Estacionamento2.service;

import com.Estacionamento2.Entitys.automoveis;
import com.Estacionamento2.Entitys.paymentEntity;
import com.Estacionamento2.repository.automoveisRepository;
import com.Estacionamento2.repository.paymentRepository;
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
    
    public Automoveis save(automoveis Automoveis) {
        return automoveisrepository.save(Automoveis);
    }
    
    public void deleteById(Long id) {
        automoveisrepository.deleteById(id);
    }
    
    @Transactional
    public Automoveis togglePagamento(Long id) {
        automoveis Automoveis = automoveisrepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Automóvel não encontrado"));
        
        Automoveis.setPago(!automoveis.getPago());
        return automoveisrepository.save(automoveis);
    }
    
    public boolean placaExists(String placa) {
        return automoveisrepository.existsByPlaca(placa);
    }
}
