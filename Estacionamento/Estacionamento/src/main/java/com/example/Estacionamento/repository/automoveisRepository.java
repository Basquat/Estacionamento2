package com.example.Estacionamento.repository;

import com.example.Estacionamento.Entitys.automoveis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface automoveisRepository extends JpaRepository<automoveis, Long> {
  automoveis findByPlaca(String Placa);

  automoveis findBytipo(String tipo);
}

