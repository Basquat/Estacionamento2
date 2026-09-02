package basquat.estacionamento.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PagamentoRepository extends JpaRepository<Pagamento, String> {

    /** Apaga todos os pagamentos de um veículo em uma única query (sem carregar entidades). */
    @Modifying
    @Query("delete from Pagamento p where p.autoId = :autoId")
    int deleteByAutoId(@Param("autoId") String autoId);
}
