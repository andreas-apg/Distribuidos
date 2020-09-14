package servidor;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;

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
	static Map<InterfaceCli, Usuario> mapaDeUsuarios;
	Date hora;
	SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	// A: usa-se assim> formataData.format(hora) 
    private Thread thread;
	
	public static Vector<Transacao> transacoes = new Vector<Transacao>();
	// A: movi as filas de compra e venda pra essa classe
	public static Hashtable<Ordem, String> filaDeCompra = new Hashtable<Ordem, String>();
	public static Hashtable<Ordem, String> filaDeVenda = new Hashtable<Ordem, String>();
	
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
	
	public Transacao(Map<InterfaceCli, Usuario> mapaDeUsuarios) {
		Transacao.mapaDeUsuarios = mapaDeUsuarios;
	}
    
    public synchronized static void adicionaCompra(Ordem ordem) {
    	Transacao.filaDeCompra.put(ordem, ordem.getCodigoDaAcao());
    	procuraVenda(ordem);
    }
    
    public synchronized static void adicionaVenda(Ordem ordem) {
    	Transacao.filaDeCompra.put(ordem, ordem.getCodigoDaAcao());
    	procuraCompra(ordem);
    }
    
    public synchronized static void realizaCompra(Ordem compra, Ordem venda) throws RemoteException {
    	Transacao transacao;
    	/* A: se a ordem de venda oferece maior quantidade
    	 * que a de compra, esgotará a de compra.
    	 */;
    	try {
	    	if(venda.getQuantidade() > compra.getQuantidade()) {
	    		transacao = new Transacao(venda.getUsuario(), venda.getReferenciaCliente(), compra.getUsuario(), compra.getReferenciaCliente(), compra.getCodigoDaAcao(), compra.getValor(), compra.getQuantidade());
	    		transacoes.add(transacao);
	    		// A: atualizando a quantidade na ordem de venda
	    		venda.setQuantidade(venda.getQuantidade() - compra.getQuantidade());
	    		
	    		// A: removendo a ordem de compra da filaDeCompra
	    		filaDeCompra.remove(compra);
	    		
	    		// A: atualizando carteira do comprador
	    		adicionaNaCarteira(compra.getReferenciaCliente(), compra.getCodigoDaAcao(), compra.getQuantidade());
	    		// A: atualizando carteira do vendedor
	    		tiraDaCarteira(venda.getReferenciaCliente(), venda.getCodigoDaAcao(), compra.getQuantidade());
	    		
	    		// A: adicionando na lista de interesse do comprador
	    		adicionaInteresse(compra.getReferenciaCliente(), compra.getCodigoDaAcao());	
	    		
	    		// A: notificações
	    		notificaUsuarios(compra.getUsuario(), venda.getUsuario(), compra.getReferenciaCliente(), venda.getReferenciaCliente(), compra.getCodigoDaAcao(), compra.getQuantidade(), compra.getValor());
	    		}
	    	/* A: se quantidade de compra e venda forem iguais, os dois
	    	 * sairão das filas.
	    	 */
	    	else if(venda.getQuantidade() == compra.getQuantidade()) {
	    		transacao = new Transacao(venda.getUsuario(), venda.getReferenciaCliente(), compra.getUsuario(), compra.getReferenciaCliente(), compra.getCodigoDaAcao(), compra.getValor(), compra.getQuantidade());
	    		transacoes.add(transacao);
	    		// A: removendo as ordens das filas
	    		filaDeVenda.remove(venda);
	    		filaDeCompra.remove(compra);

	    		// A: atualizando carteira do comprador
	    		adicionaNaCarteira(compra.getReferenciaCliente(), compra.getCodigoDaAcao(), compra.getQuantidade());
	    		
	    		// A: atualizando carteira do vendedor
	    		tiraDaCarteira(venda.getReferenciaCliente(), venda.getCodigoDaAcao(), compra.getQuantidade());
	    		
	    		// A: adicionando na lista de interesse do comprador
	    		adicionaInteresse(compra.getReferenciaCliente(), compra.getCodigoDaAcao());	
	    		
	    		// A: notificações
	    		notificaUsuarios(compra.getUsuario(), venda.getUsuario(), compra.getReferenciaCliente(), venda.getReferenciaCliente(), compra.getCodigoDaAcao(), compra.getQuantidade(), compra.getValor());
	    	}
	    	/* A: se a ordem de venda oferece menor quantidade
	    	 * que a de compra, esgotará a de venda.
	    	 */
	    	else if(venda.getQuantidade() < compra.getQuantidade()) {
	    		transacao = new Transacao(venda.getUsuario(), venda.getReferenciaCliente(), compra.getUsuario(), compra.getReferenciaCliente(), compra.getCodigoDaAcao(), compra.getValor(), venda.getQuantidade());
	    		transacoes.add(transacao);
	    		// A: atualizando a quantidade da fila de compra
	    		compra.setQuantidade(compra.getQuantidade() - venda.getQuantidade());

	    		// A: removendo a ordem de venda da filaDeVenda
	    		filaDeVenda.remove(venda);
	    		
	    		// A: atualizando carteira do comprador
	    		adicionaNaCarteira(compra.getReferenciaCliente(), compra.getCodigoDaAcao(), venda.getQuantidade());
	    		// A: atualizando carteira do vendedor
	    		tiraDaCarteira(venda.getReferenciaCliente(), venda.getCodigoDaAcao(), venda.getQuantidade());
	    		
	    		// A: adicionando na lista de interesse do comprador
	    		adicionaInteresse(compra.getReferenciaCliente(), compra.getCodigoDaAcao());	
	    		
	    		notificaUsuarios(compra.getUsuario(), venda.getUsuario(), compra.getReferenciaCliente(), venda.getReferenciaCliente(), compra.getCodigoDaAcao(), venda.getQuantidade(), compra.getValor());
	    	}
		} catch (Exception e) {
			System.out.println("RemoteException: " + e.getMessage());
		}
    }
    
    private static void adicionaInteresse(InterfaceCli referenciaUsuario, String codigoDaAcao) {
    	Usuario usuario = mapaDeUsuarios.get(referenciaUsuario);
		usuario.getListaDeInteresse().add(codigoDaAcao);
    }
    
    /* A: varre a fila de venda pra ver se tem alguma
     * com mesmo código de ação da compra nova.
     */
    private static void procuraCompra(Ordem compra) {
		/* A: só executa as comparações se as duas filas não
		* estiveram vazias.
		*/ 
		if(filaDeVenda.containsValue(compra.getCodigoDaAcao()) == true) { // pode ser null, por isso da comparaçao
	    	for(Entry<Ordem, String> venda: filaDeVenda.entrySet()) {
	    		/* A: iterando pela lista de venda, pra ver
	    		 * se um papel bate e se são de usuários diferentes
	    		 */
	    		if(compra.getCodigoDaAcao().equals(venda.getValue()) && !compra.getUsuario().equals(venda.getKey().getUsuario())) {
	    			/* A: só será feita a compra se o preço for exatamente
	    			* igual, como o pdf diz.
	    			*/
	    			if(compra.getValor() == venda.getKey().getValor()) {
	    					try {
								realizaCompra(compra, venda.getKey());
							} catch (RemoteException e) {
								System.out.println("RemoteException: " + e.getMessage());
							}
	    					return;
	    			}
	    		}			    			
	    	}
	    }
    }
    
    /* A: varre a fila de compra pra ver se tem alguma
     * com mesmo código de ação da venda nova.
     */
    private static void procuraVenda(Ordem venda) {
		/* A: só executa as comparações se as duas filas não
		* estiveram vazias.
		*/ 
		if(filaDeCompra.containsValue(venda.getCodigoDaAcao()) == true) { // pode ser null, por isso da comparaçao
	    	for(Entry<Ordem, String> compra: filaDeCompra.entrySet()) {
	    		/* A: iterando pela lista de venda, pra ver
	    		 * se um papel bate e se são de usuários diferentes
	    		 */
	    		if(venda.getCodigoDaAcao().equals(compra.getValue()) && !venda.getUsuario().equals(compra.getKey().getUsuario())) {
	    			/* A: só será feita a compra se o preço for exatamente
	    			* igual, como o pdf diz.
	    			*/
	    			if(venda.getValor() == compra.getKey().getValor()) {
	    					try {
								realizaCompra(venda, compra.getKey());
							} catch (RemoteException e) {
								System.out.println("RemoteException: " + e.getMessage());
							}
	    					return;
	    			}
	    		}				    			
	    	}
	    }
    }
    
    private static void notificaUsuarios(String comprador, String vendedor, InterfaceCli compradorRef, InterfaceCli vendedorRef, String codigoDaAcao, int quantidade, float valor) throws RemoteException {
		System.out.printf("Transacao realizada: usuario %s comprou %f %s do usuario %s por %f.", vendedor, quantidade, valor, codigoDaAcao, vendedor);
		String notificacao = quantidade + " " + codigoDaAcao + " por " + valor;
		compradorRef.notificar("Compra realizada: " + notificacao);
		vendedorRef.notificar("Venda realizada: " + notificacao);
	}


    private static void adicionaNaCarteira(InterfaceCli referenciaCliente, String codigoDaAcao, int quantidade) throws Exception {
		Usuario usuario = mapaDeUsuarios.get(referenciaCliente);
		usuario.getCarteira().adicionarAcaoNaCarteira(codigoDaAcao, quantidade);
    }

	private static void tiraDaCarteira(InterfaceCli referenciaCliente, String codigoDaAcao, int quantidade) throws Exception {
		Usuario usuario = mapaDeUsuarios.get(referenciaCliente);
		usuario.getCarteira().removerAcaoDaCarteira(codigoDaAcao, quantidade);
    }
    
    public synchronized static void mataOrdem(Ordem ordem) {
		if(ordem.getTipoDaOrdem().equals("compra")) {
			filaDeCompra.remove(ordem);
		}
		else if(ordem.getTipoDaOrdem().equals("venda")) {
			filaDeVenda.remove(ordem);
		}
    }
    
    public void start() {
    	if(thread == null) {
		    thread = new Thread(this, "transaction");
		    System.out.println("Iniciando thread de Transacao...");
		    thread.start();
    	}
    }
    
    public void run(){    
    	System.out.println("Thread de transacao inicializada.");
    	while(true) {
    		//procuraTransacao();
    		/* A: tem que ter um jeito melhor do que isso
    		 * para remover ordens que venceram.
    		 
    			for(Ordem ordem : filaDeCompra) {
    				checaPrazo(ordem);
    			}
    			for(Ordem ordem : filaDeVenda) {
    				checaPrazo(ordem);   			
    		}
    		* Fazendo teste com cada Ordem ser sua thread.
    		*/
		}
    }	
}

