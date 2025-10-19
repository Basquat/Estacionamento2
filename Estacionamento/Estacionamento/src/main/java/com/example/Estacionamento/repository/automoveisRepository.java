package com.estacionaplus.repository;

import com.estacionaplus.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface automoveisRepository extends JpaRepository<automoveis, Long> {
    Optional<Veiculo> findByPlaca(String placa);
    List<Veiculo> findByType(String type);
    List<Veiculo> findByPago(Boolean pago);
    boolean existsByPlaca(String placa);
    
    @Query("SELECT COUNT(v), SUM(v.valor) FROM Veiculo v WHERE v.pago = true AND v.formaPagamento = 'PIX'")
    Object[] getTotalPix();
    
    @Query("SELECT COUNT(v), SUM(v.valor) FROM Veiculo v WHERE v.pago = true AND v.formaPagamento = 'DINHEIRO'")
    Object[] getTotalDinheiro();
    
    @Query("SELECT v.type, COUNT(v), SUM(v.valor) FROM Veiculo v WHERE v.pago = true GROUP BY v.type")
    List<Object[]> getTotaisPorTipoPagos();
    
    @Query("SELECT v.type, COUNT(v), SUM(v.valor) FROM Veiculo v WHERE v.pago = false GROUP BY v.type")
    List<Object[]> getTotaisPorTipoPendentes();
}
