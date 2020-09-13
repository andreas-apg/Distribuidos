package servidor;

import java.util.ArrayList;
import java.util.List;


public class GerenciadorDeCotacoes {
    
    List<Cotacao> listaDeCotacoes;
    
    public GerenciadorDeCotacoes() {
        listaDeCotacoes = new ArrayList<Cotacao>();
        listaDeCotacoes.add(new Cotacao("AZUL4", (float) 22.58));
        listaDeCotacoes.add(new Cotacao("VALE3", (float) 61.95));
        listaDeCotacoes.add(new Cotacao("PETR4", (float) 21.88));
    }
    
    public void imprimirCotacoes() throws Exception {
        
    
        System.out.println("Imprimindo cotacoes...");
		
		if (listaDeCotacoes.size() == 0) {
			System.out.println("Lista de cotacoes vazia");
			return;
		}
		else {
			StringBuilder nomes = new StringBuilder();
			String separador = "";
			for (Cotacao cotacao : listaDeCotacoes) {
            
				nomes.append(separador);
  				separador = ", ";
				nomes.append(cotacao.getCodigoDaAcao() + ": " + cotacao.getValor());
			}
			
            System.out.println(nomes);
        }

    }

    public void atualizarCotacao(Cotacao novaCotacao) throws Exception {
        
        for (Cotacao cotacao : listaDeCotacoes) {
            if (cotacao.getCodigoDaAcao().equals(novaCotacao.getCodigoDaAcao())){
                cotacao.setValor(novaCotacao.getValor());
                return;
            }
        }
        
        listaDeCotacoes.add(novaCotacao);
        
    }

    

}
