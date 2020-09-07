package cliente;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import interfaces.InterfaceCli;

// Classe auxiliar para obter input do usuario com validacao dos dados
public class ConstrutorDeMsg {

    private Scanner keyboard;
    private String usuario;
    private InterfaceCli cliente;

    public ConstrutorDeMsg(String usuario, Scanner keyboard, InterfaceCli cliente) {
        this.usuario = usuario;
        this.keyboard = keyboard;
        this.cliente = cliente;
    }

    public InterfaceCli getCliente() {
        return cliente;
    }

    public void setCliente(InterfaceCli cliente) {
        this.cliente = cliente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    Date obterPrazo() {
		
		String userInput;
		Date prazo;
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		
		while(true) {
			System.out.println("Digite o prazo de validade da ordem como 'hh:mm:ss' (Ex: 00:05:00)");

			userInput = keyboard.nextLine();		
			try {
				prazo = dateFormat.parse(userInput);
				break;
			} catch (ParseException e ) {			
				System.out.println("Prazo invalido");
			}
		}

		return prazo;
	}

	int obterQuantidade() {

		String userInput;
		int quantidade;
		
		while (true) {
			System.out.println("Digite a quantidade (Ex: 100)");
			userInput = keyboard.nextLine();

			try {
				quantidade = Integer.parseInt(userInput);
				break;
			} catch (NumberFormatException e) {
				System.out.println("Quantidade Invalida");
			}
		}

		return quantidade;
	}

	float obterValor() {

		String userInput;
		float valor;
		
		while(true) {
			System.out.println("Digite o valor (Ex: 22.37)");
			userInput = keyboard.nextLine();

			try {
				valor = Float.parseFloat(userInput);
				break;
			} catch (NumberFormatException e) {
				System.out.println("Valor Invalido");
			}
		}

		return valor;
	}

	public String obterCodigoDaAcao() {
		
		String userInput;
		
		while (true) {
			System.out.println("Digite o código da acao (Ex: azul4)");
			userInput = keyboard.nextLine().toLowerCase();		
			
			if (userInput.length() == 5){			
				break;
			} else {
				System.out.println("Opcao invalida");
			}
		};


		return userInput;
	}

	String obterTipoDaOrdem() {
		String userInput;
		while(true) {
			System.out.println("Digite o tipo da ordem (Ex:'comprar' ou 'vender')");
			userInput = keyboard.nextLine().toLowerCase();		

			if (userInput.equals("comprar") || userInput.equals("vender")){			
				break;
			} else {
				System.out.println("Opcao invalida");
			}
		};

		return userInput;
    }
    
    String obterTipoDaAtualizacao() {
		String userInput;
		while(true) {
			System.out.println("Digite o tipo da atualizacao (Ex:'inserir' ou 'remover')");
			userInput = keyboard.nextLine().toLowerCase();		

			if (userInput.equals("inserir") || userInput.equals("remover")){			
				break;
			} else {
				System.out.println("Opcao invalida");
			}
		};

		return userInput;
	}

	public String obterTipoDoLimite() {
		String userInput;
		while(true) {
			System.out.println("Digite o tipo do limite (Ex:'ganho' ou 'perda')");
			userInput = keyboard.nextLine().toLowerCase();		

			if (userInput.equals("ganho") || userInput.equals("perda")){			
				break;
			} else {
				System.out.println("Opcao invalida");
			}
		};

		return userInput;
	}
}
