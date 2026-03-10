package basquat.estacionamento.User;



import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@RestController
@RequestMapping("/Automoveis")
@CrossOrigin(origins = "*")
public class AutoController {


    @Autowired
    private  AutoRepository autoRepository;

    @PostMapping("/Add")
    public  String addCarro(@RequestBody AutoModel model){
        autoRepository.save(model);
        return"a";
    }


    @DeleteMapping("{automoveisid}")
    public void deleteAuto(@PathVariable int automoveisid){
        autoRepository.deleteById(automoveisid);

    }

    @PutMapping("/{automoveisid}")
    public AutoModel PutModel(@PathVariable int automoveisid, @RequestBody AutoModel ModelDetail){
        AutoModel model = autoRepository.findById(automoveisid) .orElseThrow(() -> new RuntimeException("Automovel não encontrado"));
        model.setPlaca(ModelDetail.getPlaca());
        model.setPago(ModelDetail.getPago());
        model.setAutomovel(ModelDetail.getAutomovel());
        model.setValor(ModelDetail.getValor());
        model.setMetodopagamento(ModelDetail.getMetodopagamento());

        return autoRepository.save(model);
    }

    @GetMapping
    public List<AutoModel> getAllModels() {
        return (List<AutoModel>) autoRepository.findAll();
    }

    @GetMapping("/{automoveisid}")
    public AutoModel GetModelByID(@PathVariable int automoveisid){
        return autoRepository.findById(automoveisid).orElseThrow(() -> new RuntimeException("Automovel não encontrado"));
    }

}