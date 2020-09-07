package cliente;
import java.util.*;

import common.Ordem;
import java.lang.management.ManagementFactory;

public class Menu {

    //cliente emite ordem de compra ou venda (usuario, codigo acao, quantidade, valor, prazo)

    private static String usuario;
    private StringBuilder menuString;
    private static Scanner keyboard;    
    private CliImpl cliente;
    ConstrutorDeMsg construtorDeMsg;

    public Menu (CliImpl cliente) {

        this.cliente = cliente;

        usuario = ManagementFactory.getRuntimeMXBean().getName();

        buildMenuString();
        
        keyboard = new Scanner(System.in);
                
    }


    public void start() throws Exception {

        System.out.println("Welcomed " + usuario + "!");
        System.out.println("Digite o numero da opcao desejada: ");
        System.out.println(menuString);

        System.out.println();

        while(true){                    

            System.out.print(usuario + ": ");
            //keyboard = new Scanner(System.in);
            String option = keyboard.nextLine();
            
            if(option.isEmpty()){
                System.out.println("Opcao nao pode ser vazia");
                continue;
            }


            switch (option) {
                
                // 1) Emitir ordem de compra ou venda;
                case "1":
                    construtorDeMsg = new ConstrutorDeMsg(usuario, keyboard, cliente);
                    ConstrutorDeOrdem construtorDeOrdem = new ConstrutorDeOrdem(construtorDeMsg);
                    Ordem ordem = construtorDeOrdem.ordemDeCompraOuVenda();
                    cliente.emitirOrdemDeCompraOuVenda(ordem);
                    break;
                
                // 2) Visualizar minha Lista de Interesse/Cotacoes;
                case "2":
                    cliente.obterCotacoesDaListaDeInteresse();
                    break;
                
                // 3) Atualizar minha Lista de Interesse/Cotacoes;
                case "3":
                    construtorDeMsg = new ConstrutorDeMsg(usuario, keyboard, cliente);
                    //cliente.atualizarListaDeInteresse();
                    break;
                
                // 4) Visualizar minha carteira de acoes;   
                case "4":
                    cliente.obterCarteira();                    
                    break;
                
                // 5) Visualizar minha lista de limite de ganho/perca;    
                case "5":
                    // cliente.obterListaDeGanhoEPerda();
                    break;
                
                // 6) Atualizar minha lista de limite de ganho/perca;
                case "6":
                    // Map ordem = orderBuilder.atualizarListaDeLimiteDeGanhoEPerda();
                    //  cliente.atualizarListaDeLimiteDeGanhoEPerda();                
                    break;
            
                // 7) Sair;
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

}
