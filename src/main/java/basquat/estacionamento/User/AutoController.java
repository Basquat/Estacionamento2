package basquat.estacionamento.User;


import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/Automoveis")
public class AutoController {


    @Autowired
    private  AutoRepository autoRepository;

    @PostMapping("/Add")
    public  String addCarro(@RequestBody AutoModel model){
        autoRepository.save(model);
        return"a";
    }


}