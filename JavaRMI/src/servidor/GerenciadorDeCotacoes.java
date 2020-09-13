package servidor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Classe do Servidor para ser utilizada em testes
// Permite simular o mercado atualizando manualmente o preco das acoes,
// e tambem imprimir o preco atual
public class GerenciadorDeCotacoes {

    private Map<String, Cotacao> mapaDeCotacoes;
        
    public GerenciadorDeCotacoes() {
        mapaDeCotacoes = new HashMap<String, Cotacao>();

        // Para facilitar os testes, iniciamos o servidor com 3 cotacoes 
        mapaDeCotacoes.put("AZUL4", new Cotacao("AZUL4", (float) 22.58));
        mapaDeCotacoes.put("VALE3", new Cotacao("VALE3", (float) 61.95));
        mapaDeCotacoes.put("PETR4", new Cotacao("PETR4", (float) 21.88));
    
    }
    
    public void imprimirCotacoes() throws Exception {
        
    
        System.out.println("Imprimindo cotacoes...");
		
		if (mapaDeCotacoes.size() == 0) {
			System.out.println("Lista de cotacoes vazia");
			return;
		}
		else {
			StringBuilder nomes = new StringBuilder();
            String separador = "";
            
            Collection<Cotacao> listaDeCotacoes = mapaDeCotacoes.values();

			for (Cotacao cotacao : listaDeCotacoes) {
            
				nomes.append(separador);
  				separador = ", ";
				nomes.append(cotacao.getCodigoDaAcao() + ": " + cotacao.getValor());
			}
			
            System.out.println(nomes);
        }

    }

    public void atualizarCotacao(Cotacao novaCotacao) throws Exception {
        
        Cotacao cotacao = mapaDeCotacoes.get(novaCotacao.getCodigoDaAcao());

        if (cotacao == null) {
            mapaDeCotacoes.put(novaCotacao.getCodigoDaAcao(), novaCotacao);
        }
        else {
            cotacao.setValor(novaCotacao.getValor());
        }

    }

    public Cotacao obterCotacao(String codigoDaAcao) {
        Cotacao cotacao = mapaDeCotacoes.get(codigoDaAcao);

        if (cotacao == null) {
            String msg = "Erro: Cotacao nao existente para o codigo: " + codigoDaAcao;
            throw new IllegalArgumentException(msg);
        }

        return cotacao;
    }
    

}
