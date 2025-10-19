package com.Estacionamento2.repository;

import com.Estacionamento2.Entitys.automoveis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface automoveisRepository extends JpaRepository<automoveis, Long> {
    Optional<automoveis> findByPlaca(String placa);
    List<automoveis> findByType(String type);
    List<automoveis> findByPago(Boolean pago);
    boolean existsByPlaca(String placa);
    
    @Query("SELECT COUNT(a), SUM(a.valor) FROM automoveis a WHERE a.pago = true AND a.formaPagamento = 'PIX'")
    Object[] getTotalPayment();
    
    @Query("SELECT COUNT(a), SUM(a.valor) FROM automoveis a WHERE a.pago = true AND a.formaPagamento = 'DINHEIRO'")
    Object[] getTotalDinheiro();
    
    @Query("SELECT a.type, COUNT(a), SUM(a.valor) FROM automoveis a WHERE a.pago = true GROUP BY a.type")
    List<Object[]> getTotaisPorTipoPagos();
    
    @Query("SELECT a.type, COUNT(a), SUM(a.valor) FROM automoveis a WHERE a.pago = false GROUP BY a.type")
    List<Object[]> getTotaisPorTipoPendentes();
}
