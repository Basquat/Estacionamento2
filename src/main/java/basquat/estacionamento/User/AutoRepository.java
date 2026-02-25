package basquat.estacionamento.User;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AutoRepository extends CrudRepository<AutoModel, Long> {

    List<AutoModel> findByPlaca(String placa);

    AutoModel findByid(int AutomoveisID);

}