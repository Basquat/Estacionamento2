package br.com.basquat.estacionamento.user;

import lombok.Data;

@Data
@Entity
public class CarroModel {
    private boolean pago;
    private boolean Automovel;
    private boolean MetodoPagamento;
    private String placa;
    private int valor;
/*
    public void CarroModel(boolean pago, boolean Automovel, boolean MetodoPagamento, String placa, int valor){
        this.Automovel = Automovel;
        this.pago = pago;
        this.MetodoPagamento = MetodoPagamento;
        this.placa = placa;
        this.valor = valor;
    }

    //GETTERS

    public boolean getAutomovel(){
        return Automovel;
    }

    public boolean getPago(){
        return pago;
    }

    public boolean getMetodoPagamento(){
        return MetodoPagamento;
    }

    public String getPlaca(){
        return placa;
    }

    public int getValor(){
        return valor;
    }

    //SETTERS

*/
}


