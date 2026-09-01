package basquat.estacionamento.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Automoveis")
@CrossOrigin(origins = "*")
public class AutoController {

    private final AutoService autoService;

    public AutoController(AutoService autoService) {
        this.autoService = autoService;
    }

    @GetMapping
    public List<AutoModel> getAllModels() {
        return autoService.listar();
    }

    @GetMapping("/{id}")
    public AutoModel getModelById(@PathVariable String id) {
        return autoService.buscar(id);
    }

    @PostMapping("/Add")
    public AutoModel addCarro(@RequestBody AutoModel model) {
        return autoService.criar(model);
    }

    @PutMapping("/{id}")
    public AutoModel putModel(@PathVariable String id, @RequestBody AutoModel modelDetail) {
        return autoService.atualizar(id, modelDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuto(@PathVariable String id) {
        autoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
