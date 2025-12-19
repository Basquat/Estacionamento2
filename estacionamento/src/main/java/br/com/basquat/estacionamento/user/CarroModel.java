package br.com.basquat.estacionamento.user;


// Regras de dados, de acordo com as necessidades dos automoveis
public class CarroModel {

    private String placa;
    private int valor;
    private boolean MetodoPagamento;
    private boolean pago;
    private boolean Automovel;


    public CarroModel(String placa, int valor, boolean MetodoPagamento, boolean pago, boolean Automovel){
        this.placa = placa;
        this.valor = valor;
        this.MetodoPagamento = MetodoPagamento;
        this.pago = pago;
        this.Automovel = Automovel;
    }


    //GETTERS
    public String GetPlaca(){
        return placa;
    }

    public int GetValor(){
        return valor;
    }

    public boolean GetMetodoPagamento(){
        return MetodoPagamento;
    }

    public boolean GetPago(){
        return pago;
    }

    public boolean GetAutomovel(){
        return Automovel;
    }

    //SETTERS

    public void SetPlaca(String placa){
        if(placa != null && !placa.isEmpty()) { //Método if para garantir que o usuario não envie para o banco de dados um valor nulo ou vazio
            this.placa = placa;
        }
    }

    public void SetValor(int valor){
            this.valor = valor;
    }

    public void SetMetodoPagamento(boolean MetodoPagamento){
        this.MetodoPagamento = MetodoPagamento;
    }

    public void SetPago(boolean pago){
        this.pago = pago;
    }

    public void SetAutomovel(boolean Automovel){
        this.Automovel = Automovel;
    }

   
}


