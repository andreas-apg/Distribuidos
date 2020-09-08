package servidor;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import common.Ordem;

/* classe para a transação de uma ação.
 * Para saber a cotação de uma ação,
 * pegar o objeto tipo Transacao pra
 * uma determinada acao e olha
 * o que possui a hora mais recente.
 */ 
public class Transacao extends Thread{
	String vendedor; 	// usuário
	String comprador; 	// usuário
	String acao; 		// ie PETR4
	float preco; 		// em reais
	int quantidade; 	// número inteiro 
	Date hora;
	SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	// A: usa-se assim> formataData.format(hora) 
    private Thread thread;
	
	public static ArrayList<Transacao> transacoes = new ArrayList<Transacao>();
	// A: movi as filas de compra e venda pra essa classe
	public static ArrayList<Ordem> filaDeCompra = new ArrayList<Ordem>();
	public static ArrayList<Ordem> filaDeVenda = new ArrayList<Ordem>();
	
	public Transacao(String vendedor, String comprador, String acao, float preco, int quantidade) {
		this.vendedor = vendedor;
		this.comprador = comprador;
		this.acao = acao;
		this.preco = preco;
		this.quantidade = quantidade;
		this.hora = new Date();
	}
	
	public Transacao() {};
	
    public void start() {
    	if(thread == null) {
		    thread = new Thread(this, "transaction");
		    System.out.println("Iniciando thread de Transacao...");
		    thread.start();
    	}
    }
    public void realizaCompra(Ordem compra, Ordem venda) {
    	Transacao transacao;
    	/* A: se a ordem de venda oferece maior quantidade
    	 * que a de compra, esgotará a de compra.
    	 */
    	int indiceCompra = filaDeCompra.indexOf(compra);
    	int indiceVenda = filaDeVenda.indexOf(venda);
    	if(venda.getQuantidade() > compra.getQuantidade()) {
    		transacao = new Transacao(venda.getUsuario(), compra.getUsuario(), compra.getCodigoDaAcao(), compra.getValor(), compra.getQuantidade());
    		transacoes.add(transacao);
    		venda.setQuantidade(venda.getQuantidade() - compra.getQuantidade());
    		// A: atualizando a ordem de venda na filaDeVenda
    		filaDeVenda.set(indiceVenda, venda);
    		// A: removendo a ordem de compra da filaDeCompra
    		filaDeCompra.remove(indiceCompra);    	}
    	/* A: se quantidade de compra e venda forem iguais, os dois
    	 * sairão das filas.
    	 */
    	else if(venda.getQuantidade() == compra.getQuantidade()) {
    		transacao = new Transacao(venda.getUsuario(), compra.getUsuario(), compra.getCodigoDaAcao(), compra.getValor(), compra.getQuantidade());
    		transacoes.add(transacao);
    		filaDeVenda.remove(indiceVenda);
    		filaDeCompra.remove(indiceCompra);
    	}
    	/* A: se a ordem de venda oferece menor quantidade
    	 * que a de compra, esgotará a de venda.
    	 */
    	else if(venda.getQuantidade() < compra.getQuantidade()) {
    		transacao = new Transacao(venda.getUsuario(), compra.getUsuario(), compra.getCodigoDaAcao(), compra.getValor(), venda.getQuantidade());
    		transacoes.add(transacao);
    		compra.setQuantidade(compra.getQuantidade() - venda.getQuantidade());
    		// A: atualizando a ordem de venda na filaDeCompre
    		filaDeCompra.set(indiceCompra, compra);
    		// A: removendo a ordem de venda da filaDeVenda
    		filaDeVenda.remove(indiceVenda);
    	}
    }
    public void run(){    
    	System.out.println("Thread de transacao inicializada.");
    	while(true) {
    		/* A: só executa as comparações se as duas filas não
    		* estiveram vazias.
    		*/ 
    		if(!filaDeCompra.isEmpty() && !filaDeVenda.isEmpty()) {
			    for(Ordem compra : filaDeCompra) {
			    	for(Ordem venda : filaDeVenda) {
			    		/* A: iterando pela lista de compra e venda, pra ver
			    		 * se um papel bate e se são de usuários diferentes
			    		 */
			    		if(compra.getCodigoDaAcao().equals(venda.getCodigoDaAcao()) && !compra.getUsuario().equals(venda.getUsuario())) {
			    			/* A: só será feita a compra se o preço máximo de compra
			    			* for maior ou igual ao preço mínimo de venda.
			    			*/
			    			if(compra.getValor() >= venda.getValor()) {
			    					realizaCompra(compra, venda);			   
			    			}
			    		}			    			
			    	}
			    }
			}
		}
    }	
}

