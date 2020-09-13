package servidor;

import java.util.HashMap;
import java.util.Map;

public class Carteira {
    
    private Map<String, Integer> listaDeAcoes ;
   
    public Carteira() {
        listaDeAcoes = new HashMap<String, Integer>();    
		listaDeAcoes.put("AZUL4", 100);
		listaDeAcoes.put("VALE3", 100);
		listaDeAcoes.put("PETR4", 100);
    }

    public void adicionarAcaoNaCarteira(String codigoDaAcao, Integer qtdeAAdicionar) throws Exception{
        
        // Validacao dos argumentos
        if (!(codigoDaAcao.length() == 5)){
            String msg = "Erro: codigo de acao invalido";
            throw new IllegalArgumentException(msg + codigoDaAcao);
        }

        if(qtdeAAdicionar <=0 ){
            String msg = "Erro: quantidade a adicionar eh invalida: ";
            throw new IllegalArgumentException(msg + qtdeAAdicionar);
        }

        
        if(listaDeAcoes.containsKey(codigoDaAcao)){
            int qtdeAtual = listaDeAcoes.get(codigoDaAcao);
            int novaQtde = qtdeAtual + qtdeAAdicionar;

            listaDeAcoes.replace(codigoDaAcao, novaQtde);
        } 
        else {
        
            listaDeAcoes.put(codigoDaAcao, qtdeAAdicionar);
        }

    }

    public void removerAcaoDaCarteira(String codigoDaAcao, Integer qtdeARemover) throws Exception {
        
        // Validacao dos argumentos
        if (!(codigoDaAcao.length() == 5)){
            String msg = "Erro: codigo de acao invalido";
            throw new IllegalArgumentException(msg + codigoDaAcao);
        }

        if(qtdeARemover <=0 ){
            String msg = "Erro: quantidade a remover invalida: ";
            throw new IllegalArgumentException(msg + qtdeARemover);
        }

        // verificar se a ação existe, se nao throw error
        if(!listaDeAcoes.containsKey(codigoDaAcao)){
            String msg = "Erro: Usuario nao possui essa acao na carteira: ";
            throw new IllegalArgumentException(msg + codigoDaAcao);
        }

        int qtdeAtual = listaDeAcoes.get(codigoDaAcao);

        int novaQtde = qtdeAtual - qtdeARemover;

        if(novaQtde < 0){
            String valores = qtdeARemover +">" + qtdeAtual;
            String msg = "Erro: Quantidade a remover eh maior que a que o usuario possui: ";
            throw new IllegalArgumentException( msg + valores);
        }


        // Atualiza a carteira

        if(novaQtde == 0){
            listaDeAcoes.remove(codigoDaAcao);
        }

        if(novaQtde > 0){
            listaDeAcoes.replace(codigoDaAcao, novaQtde);
        }
        
        throw new Exception("Erro Desconhecido: Nao foi possivel remover acao da carteira");
        
    }
}
