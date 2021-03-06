package servidor;

import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
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
	static SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
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
    	Transacao.filaDeVenda.put(ordem, ordem.getCodigoDaAcao());
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
			System.out.println("RemoteException em realizaCompra: " + e.getMessage());
		}
    }
    
    private static void adicionaInteresse(InterfaceCli referenciaUsuario, String codigoDaAcao) {
    	Usuario usuario = mapaDeUsuarios.get(referenciaUsuario);
		usuario.getListaDeInteresse().add(codigoDaAcao);
    }
    
    /* A: varre a fila de venda pra ver se tem alguma
     * com mesmo código de ação da compra nova.
     */
    private static void procuraVenda(Ordem compra) {
		if(filaDeVenda.containsValue(compra.getCodigoDaAcao()) == true) { // pode ser null, por isso da comparaçao
			System.out.println("filaDeVendacontém " + compra.getCodigoDaAcao());
			for(Entry<Ordem, String> venda: filaDeVenda.entrySet()) {
	    		/* A: iterando pela lista de venda, pra ver
	    		 * se um papel bate e se são de usuários diferentes
	    		 */
				System.out.println(compra.getCodigoDaAcao() + ", " + venda.getValue() + ", " + compra.getUsuario() + ", " + venda.getKey().getUsuario());
	    		if(compra.getCodigoDaAcao().equals(venda.getValue()) && !compra.getUsuario().equals(venda.getKey().getUsuario())) {
	    			/* A: só será feita a compra se o preço for exatamente
	    			* igual, como o pdf diz.
	    			*/
	    			if(compra.getValor() == venda.getKey().getValor()) {
	    					try {
								realizaCompra(compra, venda.getKey());
							} catch (RemoteException e) {
								System.out.println("RemoteException em procuraVenda: " + e.getMessage());
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
    private static void procuraCompra(Ordem venda) {
		if(filaDeCompra.containsValue(venda.getCodigoDaAcao()) == true) { // pode ser null, por isso da comparaçao
	    	System.out.println("filaDeCompra contém " + venda.getCodigoDaAcao());
			for(Entry<Ordem, String> compra: filaDeCompra.entrySet()) {
	    		/* A: iterando pela lista de venda, pra ver
	    		 * se um papel bate e se são de usuários diferentes
	    		 */
				System.out.println(venda.getCodigoDaAcao() + ", " + compra.getValue() + ", " + venda.getUsuario() + ", " + compra.getKey().getUsuario());
	    		if(venda.getCodigoDaAcao().equals(compra.getValue()) && !venda.getUsuario().equals(compra.getKey().getUsuario())) {
	    			/* A: só será feita a compra se o preço for exatamente
	    			* igual, como o pdf diz.
	    			*/
	    			if(venda.getValor() == compra.getKey().getValor()) {
	    					try {
								realizaCompra(compra.getKey(), venda);
							} catch (RemoteException e) {
								System.out.println("RemoteException em procuraCompra: " + e.getMessage());
							}
	    					return;
	    			}
	    		}				    			
	    	}
	    }
    }
    
    private static void notificaUsuarios(String comprador, String vendedor, InterfaceCli compradorRef, InterfaceCli vendedorRef, String codigoDaAcao, int quantidade, float valor) throws RemoteException {
		System.out.printf("Transacao realizada: usuario %s comprou %d %s do usuario %s por %f.\n", comprador, quantidade, codigoDaAcao, vendedor, valor);
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
    
    /* A: remove ordens do usuário das filas de compra e venda
     */
    public static synchronized void removeDasFilas(InterfaceCli usuarioRef) {
    	// A: criando iterador para filaDeCompra
    	Iterator<Ordem> compraIterator = filaDeCompra.keySet().iterator();
    	while(compraIterator.hasNext()) {
    		Ordem compra = compraIterator.next();
    		if(compra.getReferenciaCliente() == usuarioRef) {
    			// A: removendo a ordem, se pertencer ao usuario
    			compraIterator.remove();
    		}
    	}    	
    	// A: criando iterador para filaDeVenda
    	Iterator<Ordem> vendaIterator = filaDeVenda.keySet().iterator();
    	while(vendaIterator.hasNext()) {
    		Ordem venda = vendaIterator.next();
    		if(venda.getReferenciaCliente() == usuarioRef) {
    			// A: removendo a ordem, se pertencer ao usuario
    			vendaIterator.remove();
    		}
    	} 
    }
    
    public static void imprimirFilaDeTransacao() throws Exception {

        System.out.println("Imprimindo transacoes...");

        if (transacoes.size() == 0) {
            System.out.println("Lista de transacoes vazia.");
            return;
        } else {
            StringBuilder nomes = new StringBuilder();
            String separador = "";

            for (Transacao transacao : transacoes) {
                nomes.append(separador);
                separador = "\n";
                nomes.append("C: " + transacao.comprador + "; acao: " + transacao.acao + "; V: " + transacao.vendedor + "; val: " + transacao.preco + "; quant: " + transacao.quantidade + " em " + formataData.format(transacao.hora));
            }
            System.out.println(nomes);
        }
    }
    
    public static void imprimirFilaDeCompras() throws Exception {

        System.out.println("Imprimindo ordens de compra...");

        if (filaDeCompra.isEmpty()) {
            System.out.println("Fila de ordens de compra vazia.");
            return;
        } else {
            StringBuilder nomes = new StringBuilder();
            String separador = "";
            
            Collection<Ordem> ordensDeCompra = filaDeCompra.keySet();
            
            for (Ordem compra : ordensDeCompra) {
                nomes.append(separador);
                separador = "\n";
                nomes.append(compra.getQuantidade() + " " + compra.getCodigoDaAcao() + " por " + compra.getValor() + ". U: " + compra.getUsuario());
            }
            System.out.println(nomes);
        }
    }
    
    public static void imprimirFilaDeVendas() throws Exception {

        System.out.println("Imprimindo ordens de venda...");

        if (filaDeVenda.isEmpty()) {
            System.out.println("Fila de ordens de venda vazia.");
            return;
        } else {
            StringBuilder nomes = new StringBuilder();
            String separador = "";
            
            Collection<Ordem> ordensDeVenda = filaDeVenda.keySet();
            
            for (Ordem venda : ordensDeVenda) {
                nomes.append(separador);
                separador = "\n";
                nomes.append(venda.getQuantidade() + " " + venda.getCodigoDaAcao() + " por " + venda.getValor() + ". U: " + venda.getUsuario());
            }
            System.out.println(nomes);
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

