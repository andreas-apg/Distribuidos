package servidor;

import java.util.Scanner;

import common.Helpers;

public class MenuServidor {

    private ServImpl servidor;
    private static Scanner keyboard;
    private StringBuilder menuString;

    public MenuServidor(ServImpl servidor) {

        this.servidor = servidor;
        buildMenuString();
        keyboard = new Scanner(System.in);
                
    }

    private void buildMenuString() {
        menuString = new StringBuilder();

        menuString.append("Menu Servidor\n");
        menuString.append("1) Imprimir lista de usuarios;\n");
        menuString.append("2) Imprimir o preco das acoes;\n");
        menuString.append("3) Atualizar o preco de uma acao;\n");
        menuString.append("0) Sair;");
    }

    public void start() throws Exception {

        while(true){                    

            System.out.println("Digite 0 para inserir ou atualizar o preco de uma acao...");

            //keyboard = new Scanner(System.in);
            String option = keyboard.nextLine();
            
            if(option.isEmpty()){
                System.out.println("Opcao nao pode ser vazia");
                continue;
            }

            switch (option) {
                
                // 1) Imprimir lista de usuarios;
                case "1":
                    // TODO 
                    throw new Exception("Not implemented yet");
                    //break;
                
                // 2) Imprimir o preco das acoes;
                case "2":
                    // TODO 
                    throw new Exception("Not implemented yet");
                    //break;
                
                // 3) Atualizar minha Lista de Interesse/Cotacoes;
                case "3":
                    String codigoDaAcao = Helpers.obterCodigoDaAcao(keyboard);
                    Float valor = Helpers.obterValor(keyboard);
                    servidor.atualizarPrecoDaAcao(codigoDaAcao, valor);
                    break;
                
                // 4) Visualizar minha carteira de acoes;   
                case "4":
                    break;
                
                // 5) Visualizar minha lista de limite de ganho/perca;    
                case "5":
                    break;
                
                // 6) Atualizar minha lista de limite de ganho/perca;
                case "6":
                    break;
            
                // 7) Sair;
                case "0":
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
