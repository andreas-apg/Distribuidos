package cliente;

import java.util.*;
import common.*;

// Classe para o usuario navegar pelo menu
public class MenuCliente {

    //cliente emite ordem de compra ou venda (usuario, codigo acao, quantidade, valor, prazo)

    private StringBuilder menuString;
    private static Scanner keyboard;
    private CliImpl cliente;

    private String nomeDeUsuario;

    public MenuCliente(CliImpl cliente) {

        this.cliente = cliente;
        nomeDeUsuario = cliente.getNomeDeUsuario();
      
        buildMenuString();
        
        keyboard = new Scanner(System.in);
                
    }

    

    public void start() throws Exception {

        System.out.println("Welcomed " + nomeDeUsuario + "!");
        
        

        while(true){                    
            
            System.out.println();
            System.out.println("Digite o numero da opcao desejada: ");
            System.out.println(menuString);

            System.out.print(nomeDeUsuario + ": ");
            //keyboard = new Scanner(System.in);
            String option = keyboard.nextLine();
            
            if(option.isEmpty()){
                System.out.println("Opcao nao pode ser vazia");
                continue;
            }


            switch (option) {
                
                // 1) Emitir ordem de compra ou venda;
                case "1":
                    ConstrutorDeOrdem construtorDeOrdem = new ConstrutorDeOrdem(nomeDeUsuario, cliente, keyboard);
                    Ordem ordem = construtorDeOrdem.obterOrdemDoUsuario();
                    cliente.emitirOrdemDeCompraOuVenda(ordem);
                    break;
                
                // 2) Visualizar minha Lista de Interesse/Cotacoes;
                case "2":
                    cliente.obterCotacoesDaListaDeInteresse();
                    break;
                
                // 3) Atualizar minha Lista de Interesse/Cotacoes;
                case "3":
                    ConstrutorDeInteresse construtorDeInteresse = new ConstrutorDeInteresse(nomeDeUsuario, cliente, keyboard);
                    Interesse interesse = construtorDeInteresse.obterInteresseDoUsuario();
                    cliente.atualizarListaDeInteresse(interesse);
                    break;
                
                // 4) Visualizar minha carteira de acoes;   
                case "4":
                    cliente.obterCarteira();                    
                    break;
                
                // 5) Visualizar minha lista de limite de ganho/perca;    
                case "5":
                    cliente.obterListaDeLimite();
                    break;
                
                // 6) Atualizar minha lista de limite de ganho/perca;
                case "6":
                    ConstrutorDeLimite construtorDeLimite = new ConstrutorDeLimite(nomeDeUsuario, cliente, keyboard);
                    Limite limite = construtorDeLimite.obterLimiteDoUsuario();
                    cliente.atualizarListaDeLimite(limite);
                    break;
            
                // 7) Sair;
                case "0":
                    cliente.sair();
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
        menuString.append("2) Visualizar minha lista de interesse de cotacao;\n");
        menuString.append("3) Atualizar minha lista de interesse de cotacao;\n");
        menuString.append("4) Visualizar minha carteira de acoes;\n");
        menuString.append("5) Visualizar minha lista de limite de ganho/perca;\n");
        menuString.append("6) Atualizar minha lista de limite de ganho/perca;\n");
        menuString.append("0) Sair;");
    }

}
