package basquat.estacionamento.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AutoRepository extends JpaRepository<AutoModel, String> {

    boolean existsByPlacaIgnoreCase(String placa);

    /** Apaga o veículo sem o SELECT prévio do deleteById(). Retorna quantas linhas saíram. */
    @Modifying
    @Query("delete from AutoModel a where a.id = :id")
    int deleteByIdReturningCount(@Param("id") String id);
}
