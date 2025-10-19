package com.Estacionamento2.controller;

import com.Estacionamento2.dto.AutomoveisDTO;
import com.Estacionamento2.Entity.automoveis;
import com.Estacionamento2.Entity.TipoAutomoveis;
import com.Estacionamento2.Entity.FormaPagamento;
import com.Estacionamento2.service.AutomoveisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/automoveis")
@CrossOrigin(origins = "*")
public class automoveisController {
    
    @Autowired
    private AutomoveisService automoveisService;
    
    @GetMapping
    public List<AutomoveisDTO> listarTodos() {
        return automoveisService.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    @PostMapping
    public ResponseEntity<?> adicionarAutomoveis(@RequestBody AutomoveisDTO automoveisDTO) {
        try {
            if (automoveisService.placaExists(automoveisDTO.getPlaca())) {
                return ResponseEntity.badRequest()
                    .body("Placa " + automoveisDTO.getPlaca() + " já está cadastrada");
            }
            
            automoveis Automoveis = new Automoveis();
            Automoveis.setPlaca(automoveisDTO.getPlaca().toUpperCase());
            Automoveis.setType(TipoAutomoveis.valueOf(automoveisDTO.getType().toUpperCase()));
            Automoveis.setValor(automoveisDTO.getValor());
            Automoveis.setPago(automoveisDTO.getPago() != null ? automoveisDTO.getPago() : false);
            Automoveis.setFormaPagamento(automoveisDTO.getFormaPagamento() != null ? 
                FormaPagamento.valueOf(automoveisDTO.getFormaPagamento().toUpperCase()) : null);
            
            Automoveis automoveisSalvo = automoveisService.save(Automoveis);
            return ResponseEntity.ok(toDTO(automoveisSalvo));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Erro ao salvar automóvel: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/pago")
    public ResponseEntity<?> togglePagamento(@PathVariable Long id) {
        try {
            automoveis Automoveis = automoveisService.togglePagamento(id);
            return ResponseEntity.ok(toDTO(Automoveis));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Erro ao atualizar pagamento: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerAutomoveis(@PathVariable Long id) {
        try {
            automoveisService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Erro ao remover automóvel: " + e.getMessage());
        }
    }
    
    private AutomoveisDTO toDTO(automoveis Automoveis) {
        return new AutomoveisDTO(
            Automoveis.getVehiclesId(),
            Automoveis.getPlaca(),
            Automoveis.getType().name(),
            Automoveis.getValor(),
            Automoveis.getPago(),
            Automoveis.getFormaPagamento() != null ? Automoveis.getFormaPagamento().name() : null,
            Automoveis.getEntrada()
        );
    }
}
