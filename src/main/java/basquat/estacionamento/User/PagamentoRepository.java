package basquat.estacionamento.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, String> {

    List<Pagamento> findByAutoId(String autoId);

    void deleteByAutoId(String autoId);
}
