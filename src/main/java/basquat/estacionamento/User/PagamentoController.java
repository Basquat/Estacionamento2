package basquat.estacionamento.User;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/Automoveis")
@CrossOrigin(origins = "*")
public class PagamentoController {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @GetMapping("/{autoId}/pagamentos")
    public List<Pagamento> getPagamentos(@PathVariable String autoId) {
        return pagamentoRepository.findByAutoId(autoId);
    }

    @PostMapping("/{autoId}/pagamentos")
    public Pagamento addPagamento(@PathVariable String autoId, @RequestBody Pagamento pagamento) {
        pagamento.setAutoId(autoId);
        pagamento.setId(java.util.UUID.randomUUID().toString());
        pagamento.setData(System.currentTimeMillis());
        return pagamentoRepository.save(pagamento);
    }

    @DeleteMapping("/{autoId}/pagamentos/{pagamentoId}")
    public void deletePagamento(@PathVariable String autoId, @PathVariable String pagamentoId) {
        pagamentoRepository.deleteById(pagamentoId);
    }

    @DeleteMapping("/{autoId}/pagamentos")
    public void deleteAllPagamentos(@PathVariable String autoId) {
        pagamentoRepository.deleteByAutoId(autoId);
    }
}
