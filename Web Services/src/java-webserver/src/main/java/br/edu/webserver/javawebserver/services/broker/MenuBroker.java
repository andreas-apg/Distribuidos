package br.edu.webserver.javawebserver.services.broker;

import java.util.Scanner;

import br.edu.webserver.javawebserver.services.broker.Helpers;

// Essa Classe é reponsavel por apresentar o menu do Servidor
// Para o Usuario e pegar a opcao dele. No fim,ela chama a classe
// responsavel para executar o servico.
public class MenuBroker {

    private static Scanner keyboard;
    private StringBuilder menuString;
    private ServicoBroker broker;
    // private GerenciadorDeCotacoes cotacoes;

    public MenuBroker(ServicoBroker broker) {

        this.broker = broker;
        // this.cotacoes = broker.getGerenciadorDeCotacoes();
        buildMenuString();
        keyboard = new Scanner(System.in);
                
    }

    // Constroi a string a ser impressa no menu
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

    // Apresenta o menu para o usario
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
                
                // // 1) Imprimir lista de usuarios;
                // case "1":
                //     servidor.imprimirUsuarios();
                //     break;
                
                // // 2) Atualizar o preco de uma acao;
                // case "2":
                //     String codigoDaAcao = Helpers.obterCodigoDaAcao(keyboard);
                //     Float valor = Helpers.obterValor(keyboard);
                //     Cotacao novaCotacao = new Cotacao(codigoDaAcao, valor);
                //     cotacoes.atualizarCotacao(novaCotacao);
                //     cotacoes.imprimirCotacoes();
                //     break;
                
                // // 3) Imprimir o preco das acoes;
                // case "3":
                //     cotacoes.imprimirCotacoes();
                //     break;
                
                // // 4) Imprimir a fila de transacoes;
                // case "4":
                // 	Transacao.imprimirFilaDeTransacao();
                //     break;
                
                // // 5) Imprimir a fila de compras;
                // case "5":
                // 	Transacao.imprimirFilaDeCompras();
                //     break;
                
                // // 6) Imprimir a fila de vendas;
                // case "6":
                // 	Transacao.imprimirFilaDeVendas();
                //     break;
            
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

    public MenuBroker() {
    }

    
    
}
