package basquat.estacionamento.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutoRepository extends JpaRepository<AutoModel, String> {

    List<AutoModel> findByPlaca(String placa);

    Optional<AutoModel> findById(String id);

    boolean existsByPlacaIgnoreCase(String placa);
}
