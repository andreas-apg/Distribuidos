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
        menuString.append("4) Imprimir a fila de transacoes;\n");
        menuString.append("5) Imprimir a fila de compras;\n");
        menuString.append("6) Imprimir a fila de vendas;\n");
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
                
                // 4) Imprimir a fila de transacoes;
                case "4":
                	Transacao.imprimirFilaDeTransacao();
                    break;
                
                // 5) Imprimir a fila de compras;
                case "5":
                	Transacao.imprimirFilaDeCompras();
                    break;
                
                // 6) Imprimir a fila de vendas;
                case "6":
                	Transacao.imprimirFilaDeVendas();
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
