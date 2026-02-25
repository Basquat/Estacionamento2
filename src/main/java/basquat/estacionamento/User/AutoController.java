package basquat.estacionamento.User;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@RestController
@RequestMapping("/Estacionamento2")
public class AutoController {


    @Autowired
    private  AutoRepository autoRepository;

    public @ResponseBody String addCarro(
            @RequestParam boolean pago,
            @RequestParam AutoModel.Automovel automovel,
            @RequestParam AutoModel.MetodoPagamento MetodoPagamento,
            String placa,
            int valor) {
        AutoModel n = new AutoModel();

        n.setMetodoPagamento(MetodoPagamento);
        n.setPago(pago);
        n.setPlaca(placa);
        n.setValor(valor);
        n.setAutomovel(automovel);
        autoRepository.save(n);
        return "Saved";
    }
}