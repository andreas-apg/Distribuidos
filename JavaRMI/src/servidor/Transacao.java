package servidor;

import java.util.Date;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import common.Ordem;
import interfaces.InterfaceCli;

/* classe para a transação de uma ação.
 * Para saber a cotação de uma ação,
 * pegar o objeto tipo Transacao pra
 * uma determinada acao e olha
 * o que possui a hora mais recente.
 */ 
public class Transacao extends Thread{
	/* A: mudar vendedor e comprador
	* para usuário.
	*/
	String vendedor; 	// usuário
	InterfaceCli vendedorRef;
	String comprador; 	// usuário
	InterfaceCli compradorRef;
	String acao; 		// ie PETR4
	float preco; 		// em reais
	int quantidade; 	// número inteiro 
	Date hora;
	SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	// A: usa-se assim> formataData.format(hora) 
    private Thread thread;
	
	public static Vector<Transacao> transacoes = new Vector<Transacao>();
	// A: movi as filas de compra e venda pra essa classe
	public static Vector<Ordem> filaDeCompra = new Vector<Ordem>();
	public static Vector<Ordem> filaDeVenda = new Vector<Ordem>();
	
	public Transacao(String vendedor, InterfaceCli vendedorRef, String comprador, InterfaceCli compradorRef, String acao, float preco, int quantidade) {
		this.vendedor = vendedor;
		this.vendedorRef = vendedorRef;
		this.comprador = comprador;
		this.compradorRef = compradorRef;
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
    
    public synchronized static void adicionaCompra(Ordem ordem) {
    	Transacao.filaDeCompra.add(ordem);
    }
    
    public synchronized static void adicionaVenda(Ordem ordem) {
    	Transacao.filaDeCompra.add(ordem);
    }
    
    public synchronized void realizaCompra(Ordem compra, Ordem venda) throws RemoteException {
    	Transacao transacao;
    	String notificacao;
    	/* A: se a ordem de venda oferece maior quantidade
    	 * que a de compra, esgotará a de compra.
    	 */
    	int indiceCompra = filaDeCompra.indexOf(compra);
    	int indiceVenda = filaDeVenda.indexOf(venda);
    	if(venda.getQuantidade() > compra.getQuantidade()) {
    		transacao = new Transacao(venda.getUsuario(), venda.getReferenciaCliente(), compra.getUsuario(), compra.getReferenciaCliente(), compra.getCodigoDaAcao(), compra.getValor(), compra.getQuantidade());
    		transacoes.add(transacao);
    		venda.setQuantidade(venda.getQuantidade() - compra.getQuantidade());
    		// A: atualizando a ordem de venda na filaDeVenda
    		filaDeVenda.set(indiceVenda, venda);
    		// A: removendo a ordem de compra da filaDeCompra
    		filaDeCompra.remove(indiceCompra);   
    		System.out.printf("Transacao realizada: usuario %s comprou %f %s do usuario %s por %f.", venda.getUsuario(), compra.getQuantidade(), compra.getValor(), compra.getCodigoDaAcao(), venda.getUsuario());
    		notificacao = compra.getQuantidade() + " " + compra.getCodigoDaAcao() + " por " + compra.getValor();
    		compra.getReferenciaCliente().notificar("Compra realizada: " + notificacao);
    		venda.getReferenciaCliente().notificar("Venda realizada: " + notificacao);
    		}
    	/* A: se quantidade de compra e venda forem iguais, os dois
    	 * sairão das filas.
    	 */
    	else if(venda.getQuantidade() == compra.getQuantidade()) {
    		transacao = new Transacao(venda.getUsuario(), venda.getReferenciaCliente(), compra.getUsuario(), compra.getReferenciaCliente(), compra.getCodigoDaAcao(), compra.getValor(), compra.getQuantidade());
    		transacoes.add(transacao);
    		filaDeVenda.remove(indiceVenda);
    		filaDeCompra.remove(indiceCompra);
    		System.out.printf("Transacao realizada: usuario %s comprou %f %s do usuario %s por %f.", venda.getUsuario(), compra.getQuantidade(), compra.getValor(), compra.getCodigoDaAcao(), venda.getUsuario());
    		notificacao = compra.getQuantidade() + " " + compra.getCodigoDaAcao() + " por " + compra.getValor();
    		compra.getReferenciaCliente().notificar("Compra realizada: " + notificacao);
    		venda.getReferenciaCliente().notificar("Venda realizada: " + notificacao);
    	}
    	/* A: se a ordem de venda oferece menor quantidade
    	 * que a de compra, esgotará a de venda.
    	 */
    	else if(venda.getQuantidade() < compra.getQuantidade()) {
    		transacao = new Transacao(venda.getUsuario(), venda.getReferenciaCliente(), compra.getUsuario(), compra.getReferenciaCliente(), compra.getCodigoDaAcao(), compra.getValor(), venda.getQuantidade());
    		transacoes.add(transacao);
    		compra.setQuantidade(compra.getQuantidade() - venda.getQuantidade());
    		// A: atualizando a ordem de venda na filaDeCompre
    		filaDeCompra.set(indiceCompra, compra);
    		// A: removendo a ordem de venda da filaDeVenda
    		filaDeVenda.remove(indiceVenda);
    		System.out.printf("Transacao realizada: usuario %s comprou %f %s do usuario %s por %f.", venda.getUsuario(), venda.getQuantidade(), compra.getValor(), compra.getCodigoDaAcao(), venda.getUsuario());
    		notificacao = venda.getQuantidade() + " " + compra.getCodigoDaAcao() + " por " + compra.getValor();
    		compra.getReferenciaCliente().notificar("Compra realizada: " + notificacao);
    		venda.getReferenciaCliente().notificar("Venda realizada: " + notificacao);
    	}
    }
    /* A: método que varre as filas de compra e venda
     * para ver se um par de papel bate. Se bater,
     * chamará o método realizaCompra para efetuar
     * a transacao em si.
     */
    private void procuraTransacao() {
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
		    			* A: atualizei para exatamente igual porque
		    			* O pdf diz.
		    			*/
		    			if(compra.getValor() == venda.getValor()) {
		    					try {
									realizaCompra(compra, venda);
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    					return;
		    			}
		    		}			    			
		    	}
		    }
		}
    }
    
    public void run(){    
    	System.out.println("Thread de transacao inicializada.");
    	while(true) {
    		procuraTransacao();
		}
    }	
}

