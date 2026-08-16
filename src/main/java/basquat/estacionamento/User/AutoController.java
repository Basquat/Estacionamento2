package basquat.estacionamento.User;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Automoveis")
@CrossOrigin(origins = "*")
public class AutoController {

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private EventoService eventoService;

    @PostMapping("/Add")
    public AutoModel addCarro(@RequestBody AutoModel model) {
        if (autoRepository.existsByPlacaIgnoreCase(model.getPlaca())) {
            throw new RuntimeException("A placa " + model.getPlaca() + " já está cadastrada.");
        }
        AutoModel saved = autoRepository.save(model);
        eventoService.notificar();
        return saved;
    }

    @DeleteMapping("/{id}")
    public void deleteAuto(@PathVariable String id) {
        autoRepository.deleteById(id);
        eventoService.notificar();
    }

    @PutMapping("/{id}")
    public AutoModel putModel(@PathVariable String id, @RequestBody AutoModel modelDetail) {
        AutoModel model = autoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Automóvel não encontrado"));

        if (!model.getPlaca().equalsIgnoreCase(modelDetail.getPlaca())
                && autoRepository.existsByPlacaIgnoreCase(modelDetail.getPlaca())) {
            throw new RuntimeException("A placa " + modelDetail.getPlaca() + " já está cadastrada.");
        }

        model.setPlaca(modelDetail.getPlaca());
        model.setTipo(modelDetail.getTipo());
        model.setValor(modelDetail.getValor());
        model.setPago(modelDetail.getPago());
        model.setEntrada(modelDetail.getEntrada());

        AutoModel saved = autoRepository.save(model);
        eventoService.notificar();
        return saved;
    }

    @GetMapping
    public List<AutoModel> getAllModels() {
        return (List<AutoModel>) autoRepository.findAll();
    }

    @GetMapping("/{id}")
    public AutoModel getModelById(@PathVariable String id) {
        return autoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Automóvel não encontrado"));
    }
}
