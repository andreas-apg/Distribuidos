package servidor;

import java.util.HashMap;
import java.util.Map;

// Classe utilizada no Usuario para armazenar as acoes que ele possui
public class Carteira {
    
    private Map<String, Integer> listaDeAcoes ;
   
    public Carteira() {
        listaDeAcoes = new HashMap<String, Integer>();    
		listaDeAcoes.put("AZUL4", 100);
		listaDeAcoes.put("VALE3", 100);
		listaDeAcoes.put("PETR4", 100);
    }

    // retorna uma string para notificar o usario das suas acoes atuais
    public String obterCarteiraComoString() {
        
		if (listaDeAcoes.size() == 0) {
            return("Carteira vazia");
		}
		else {

            StringBuilder msg = new StringBuilder();
            msg.append("codigoDaAcao: qtde, ");
			
            String separador = "";

            for (Map.Entry<String, Integer> entry : listaDeAcoes.entrySet()) {
                String codigoDaAcao = entry.getKey();
                Integer qtde = entry.getValue();
                msg.append(separador);
                separador = ", ";
				msg.append(codigoDaAcao + ": " + qtde);
            }           
            return msg.toString();
        }
    }

    // adiciona acao na carteira do usuario
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

    // remove acao da carteira do usuario
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
        
        } else if(novaQtde == 0){
        
            listaDeAcoes.remove(codigoDaAcao);
        
        } else if(novaQtde > 0){
            
            listaDeAcoes.replace(codigoDaAcao, novaQtde);
        
        } else {

            throw new Exception("Erro Desconhecido: Nao foi possivel remover acao da carteira");
        }
        
        
    }
}
