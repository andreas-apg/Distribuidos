package cliente;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import common.*;
import interfaces.InterfaceCli;

public class OrderBuilder {

	Scanner keyboard;
	Ordem ordem;

	public OrderBuilder(String usuario, Scanner keyboard, InterfaceCli cliente) {

		this.keyboard = keyboard;

		ordem = new Ordem();
		ordem.setUsuario(usuario);
		ordem.setReferenciaCliente(cliente);
	}

	public Ordem ordemDeCompraOuVenda() {

		ordem.setTipoDaOrdem(obterTipoDaOrdem());
		ordem.setCodigoDaAcao(obterCodigoDaAcao());
		ordem.setValor(obterValor());
		ordem.setQuantidade(obterQuantidade());
		ordem.setPrazo(obterPrazo());

		return ordem;

	}

	private Date obterPrazo() {
		
		String userInput;
		Date prazo;
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		
		while(true) {
			System.out.println("Digite o prazo de validade da ordem como 'hh:mm:ss' (Ex: 00:05:00");

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

	private int obterQuantidade() {

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

	private float obterValor() {

		String userInput;
		float valor;
		
		while(true) {
			System.out.println("Digite o valor (Ex: 22.37");
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

	private String obterCodigoDaAcao() {
		
		String userInput;
		
		while (true) {
			System.out.println("Digite o código da acao (Ex: AZUL4)");
			userInput = keyboard.nextLine().toLowerCase();		
			
			if (userInput.length() == 5){			
				break;
			} else {
				System.out.println("Opcao invalida");
			}
		};


		return userInput;
	}

	private String obterTipoDaOrdem() {
		String userInput;
		while(true) {
			System.out.println("Digite o tipo da ordem (Ex:'Compra' ou 'Venda')");
			userInput = keyboard.nextLine().toUpperCase();		

			if (userInput.equals("COMPRA") || userInput.equals("VENDA")){			
				break;
			} else {
				System.out.println("Opcao invalida");
			}
		};

		return userInput;
	}

}
