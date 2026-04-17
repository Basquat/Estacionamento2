package basquat.estacionamento.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AutoRepository extends JpaRepository<AutoModel, Integer> {

    List<AutoModel> findByPlaca(String placa);


    String placa(String placa);
}