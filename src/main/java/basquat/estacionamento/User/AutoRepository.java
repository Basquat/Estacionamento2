package basquat.estacionamento.User;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AutoRepository extends CrudRepository<AutoModel, Integer> {

    List<AutoModel> findByPlaca(String placa);



}