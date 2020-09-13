package common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Helpers {
    
    public static Date obterPrazo(Scanner keyboard) {
		
		String userInput;
		Date prazo;
        DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        DateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		while(true) {
			System.out.println("Digite o prazo de validade da ordem como 'hh:mm:ss' (Default: 00:05:00)");

			userInput = keyboard.nextLine();		
			try {

                if(userInput.isEmpty()){
                    userInput = "00:05:00";
                }

                prazo = timeFormat.parse(userInput);

                // incrementa a hora o prazo ao timestamp atual
                // https://mkyong.com/java/java-how-to-add-days-to-current-date/
                Date currentDate = new Date();
                System.out.println(dateTimeFormat.format(currentDate));
        
                // convert date to calendar
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);
        
                // manipulate date
                c.add(Calendar.HOUR,prazo.getHours());
                c.add(Calendar.MINUTE, prazo.getMinutes());
                c.add(Calendar.SECOND, prazo.getSeconds());
        
                // convert calendar to date
                prazo = c.getTime();
        
                System.out.println(dateTimeFormat.format(prazo));
				
				break;
			} catch (ParseException e ) {			
				System.out.println("Prazo invalido");
			}
		}

		return prazo;
	}

	public static int obterQuantidade(Scanner keyboard) {

		String userInput;
		int quantidade;
		
		while (true) {
			System.out.println("Digite a quantidade (Default: 100)");
			userInput = keyboard.nextLine();

			try {
                if(userInput.isEmpty()){
                    userInput = "100";
                }
				quantidade = Integer.parseInt(userInput);
				break;
			} catch (NumberFormatException e) {
				System.out.println("Quantidade Invalida");
			}
		}

		return quantidade;
	}

	public static float obterValor(Scanner keyboard) {

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

	public static String obterCodigoDaAcao(Scanner keyboard) {
		
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

	public static String obterTipoDaOrdem(Scanner keyboard) {
		String userInput;
		while(true) {
			System.out.println("Digite o tipo da ordem (Ex:'compra' ou 'venda')");
			userInput = keyboard.nextLine().toLowerCase();		

			if (userInput.equals("compra") || userInput.equals("venda")){			
				break;
			} else {
				System.out.println("Opcao invalida");
			}
		};
		return userInput;
    }
    
    public static String obterTipoDaAtualizacao(Scanner keyboard) {
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

	public static String obterTipoDoLimite(Scanner keyboard) {
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
