package com.example.Estacionamento.service;

import com.example.Estacionamento.Entitys.automoveis;
import com.example.Estacionamento.repository.automoveisRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class automoveisService {
  private final automoveisRepository automoveisrepository;

  public automoveisService(automoveisRepository automoveisrepository) {
    this.automoveisrepository = automoveisrepository;
  }

  public List<automoveis> findALL() {
    return automoveisrepository.findAll();
  }

  public automoveis findByID(Long id) {
    return automoveisrepository.findById(id).orElse(null);
  }
  

  public automoveis save(automoveis auto) {
    return automoveisrepository.save(auto);
  }

  public void delete(Long id) {
    automoveisrepository.deleteById(id);
  }
}
