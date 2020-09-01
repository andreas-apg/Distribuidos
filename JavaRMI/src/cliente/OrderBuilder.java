package cliente;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class OrderBuilder {

	Scanner keyboard;
	Map<String, String> ordem;

	public OrderBuilder( String usuario, Scanner keyboard) {
		
		this.keyboard = keyboard;
		
		ordem = new LinkedHashMap<String, String>();
		ordem.put("usuario", usuario);
	}

	public Map<String, String> ordemDeCompraOuVenda() {

		ordem.put("tipoDaOrdem", obterTipoDaOrdem());
		ordem.put("codigoDaAcao", obterCodigoDaAcao());
		ordem.put("valor", obterValor());
		ordem.put("quantidade", obterQuantidade());
		ordem.put("prazo", obterPrazo());

		return ordem;

	}

	private String obterPrazo() {
		
		String prazo;
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		
		while(true) {
			System.out.println("Digite o prazo de validade da ordem como 'hh:mm:ss' (Ex: 00:05:00");

			prazo = keyboard.nextLine();		
			try {
				dateFormat.parse(prazo);
				break;
			} catch (ParseException e ) {			
				System.out.println("Prazo invalido");
			}
		}

		return prazo;
	}

	private String obterQuantidade() {

		String quantidade;
		
		while (true) {
			System.out.println("Digite a quantidade (Ex: 100)");
			quantidade = keyboard.nextLine();

			try {
				Integer.parseInt(quantidade);
				break;
			} catch (NumberFormatException e) {
				System.out.println("Quantidade Invalida");
			}
		}

		return quantidade;
	}

	private String obterValor() {

		String valor;
		
		while(true) {
			System.out.println("Digite o valor (Ex: 22.37");
			valor = keyboard.nextLine();

			try {
				Float.parseFloat(valor);
				break;
			} catch (NumberFormatException e) {
				System.out.println("Valor Invalido");
			}
		}

		return valor;
	}

	private String obterCodigoDaAcao() {
		String codigoDaAcao;
		while (true) {
			System.out.println("Digite o código da acao (Ex: AZUL4");
			codigoDaAcao = keyboard.nextLine().toLowerCase();		
			
			if (codigoDaAcao.length() == 5){			
				break;
			} else {
				System.out.println("Opcao invalida");
			}
		};


		return null;
	}

	private String obterTipoDaOrdem() {
		String tipoDaOrdem;
		while(true) {
			System.out.println("Digite o tipo da ordem (Ex:'Compra' ou 'Venda')");
			tipoDaOrdem = keyboard.nextLine().toUpperCase();		

			if (tipoDaOrdem.equals("COMPRA") || tipoDaOrdem.equals("VENDA")){			
				break;
			} else {
				System.out.println("Opcao invalida");
			}
		};

		return tipoDaOrdem;
	}

}
