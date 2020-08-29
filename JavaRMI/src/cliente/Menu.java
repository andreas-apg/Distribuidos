package cliente;
import java.util.*;

import java.lang.management.ManagementFactory;

public class Menu {

    //cliente emite ordem de compra ou venda (usuario, codigo acao, quantidade, valor, prazo)

    private static String myUserName;
    private StringBuilder menuString;
    private static Scanner keyboard;

    public Menu () {

        myUserName = ManagementFactory.getRuntimeMXBean().getName();

        buildMenuString();
        
        keyboard = new Scanner(System.in);
                
    }

    private void buildMenuString() {
        menuString = new StringBuilder();

        menuString.append("1) Emitir ordem de compra ou venda;\n");
        menuString.append("2) Visualizar minha Lista de Cotacoes;\n");
        menuString.append("3) Atualizar minha Lista de Cotacoes;\n");
        menuString.append("4) Visualizar minha carteira de acoes;\n");
        menuString.append("5) Visualizar minha lista de limite de ganho/perca;\n");
        menuString.append("6) Atualizar minha lista de limite de ganho/perca;\n");
        menuString.append("7) Sair;");
    }

    public void startMenu() throws Exception {

        System.out.println("Welcomed " + myUserName + "!");
        System.out.println("Digite o numero da opcao desejada: ");
        System.out.println(menuString);

        System.out.println();

        while(true){                    

            System.out.print(myUserName + ": ");
            //keyboard = new Scanner(System.in);
            String option = keyboard.nextLine();            
            
            if(option.isEmpty()){
                System.out.println("Opcao nao pode ser vazia");
                continue;
            }


            switch (option) {
                case "1":
                    System.out.println("hello");
                    break;
                
                case "2":
                
                    break;
                    
                case "3":
                
                    break;
                
                case "4":
                
                    break;
                    
                case "5":
                
                    break;
                    
                case "6":
                
                    break;
                    
                case "7":
                    keyboard.close();                    
                    System.exit(0);
                    break;                    

                default:
                    System.out.println("Opcao invalida");                    
                    break;
            } 

        }
        
    }
}
