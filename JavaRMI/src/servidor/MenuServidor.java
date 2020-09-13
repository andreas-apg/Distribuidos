package servidor;

import java.util.Scanner;

import common.Helpers;

public class MenuServidor {

    private ServImpl servidor;
    private static Scanner keyboard;
    private StringBuilder menuString;
    private GerenciadorDeCotacoes cotacoes;

    public MenuServidor(ServImpl servidor) {

        this.servidor = servidor;
        this.cotacoes = servidor.getGerenciadorDeCotacoes();
        buildMenuString();
        keyboard = new Scanner(System.in);
                
    }

    private void buildMenuString() {
        menuString = new StringBuilder();

        menuString.append("Menu Servidor\n");
        menuString.append("1) Imprimir lista de usuarios;\n");
        menuString.append("2) Atualizar o preco de uma acao;\n");
        menuString.append("3) Imprimir o preco das acoes;\n");
        menuString.append("0) Sair;");
    }

    public void start() throws Exception {

        while(true){                    

            System.out.println();
            System.out.println("Digite o numero da opcao desejada: ");
            System.out.println(menuString);
            System.out.println();


            System.out.print("Servidor: ");
            String option = keyboard.nextLine();
            
            if(option.isEmpty()){
                System.out.println("Opcao nao pode ser vazia");
                continue;
            }

            switch (option) {
                
                // 1) Imprimir lista de usuarios;
                case "1":
                    servidor.imprimirUsuarios();
                    break;
                
                // 2) Atualizar o preco de uma acao;
                case "2":
                    String codigoDaAcao = Helpers.obterCodigoDaAcao(keyboard);
                    Float valor = Helpers.obterValor(keyboard);
                    Cotacao novaCotacao = new Cotacao(codigoDaAcao, valor);
                    cotacoes.atualizarCotacao(novaCotacao);
                    cotacoes.imprimirCotacoes();
                    break;
                
                // 3) Imprimir o preco das acoes;
                case "3":
                    cotacoes.imprimirCotacoes();
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
