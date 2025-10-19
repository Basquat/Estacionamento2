package com.example.Estacionamento2.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class automoveisController{

  @GetMapping
  public String Test(){
    return "test";
  }
}
