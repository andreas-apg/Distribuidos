package cliente;

import java.util.Scanner;

import interfaces.InterfaceCli;

public class ConstrutorDeMsg {

    
    protected String nomeDeUsuario;
	protected InterfaceCli cliente;
	protected Scanner keyboard;

    public ConstrutorDeMsg(String usuario, InterfaceCli cliente, Scanner keyboard) {
        this.nomeDeUsuario = usuario;
		this.cliente = cliente;
		this.keyboard = keyboard;
    }

    public InterfaceCli getCliente() {
        return cliente;
    }

    public void setCliente(InterfaceCli cliente) {
        this.cliente = cliente;
    }

    public String getUsuario() {
        return nomeDeUsuario;
    }

    public void setUsuario(String usuario) {
        this.nomeDeUsuario = usuario;
    }


}
