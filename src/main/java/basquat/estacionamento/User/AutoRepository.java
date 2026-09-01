package basquat.estacionamento.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoRepository extends JpaRepository<AutoModel, String> {

    boolean existsByPlacaIgnoreCase(String placa);
}
