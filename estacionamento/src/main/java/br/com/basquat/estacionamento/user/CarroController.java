package br.com.basquat.estacionamento.user;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Automoveis")
public class CarroController {

    public void AddCarro(@RequestBody CarroModel carroModel){
        System.out.println();

    }
}
