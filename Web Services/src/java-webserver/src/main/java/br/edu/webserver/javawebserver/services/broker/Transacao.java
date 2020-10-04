package br.edu.webserver.javawebserver.services.broker;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import br.edu.webserver.javawebserver.models.Ordem;
import br.edu.webserver.javawebserver.models.Usuario;
import lombok.Data;

/* classe para a transação de uma ação.
 * Para saber a cotação de uma ação,
 * pegar o objeto tipo Transacao pra
 * uma determinada acao e olha
 * o que possui a hora mais recente.
 */
@Data
public class Transacao {
	/*
	 * A: mudar vendedor e comprador para usuário.
	 */
	String vendedor; // usuário
	String comprador; // usuário
	String acao; // ie PETR4
	float preco; // em reais
	int quantidade; // número inteiro
	static Map<String, Usuario> mapaDeUsuarios;
	String hora;
	static SimpleDateFormat formataISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	// A: Date para String: formataISO8601.format(hora)
	// A: String para Date: formataISO8601.parse(horaString)

	public static Vector<Transacao> transacoes = new Vector<Transacao>();
	// A: movi as filas de compra e venda pra essa classe
	public static Hashtable<Ordem, String> filaDeCompra = new Hashtable<Ordem, String>();
	public static Hashtable<Ordem, String> filaDeVenda = new Hashtable<Ordem, String>();

	public Transacao(String vendedor, String comprador, String acao, float preco, int quantidade) {
		this.vendedor = vendedor;
		this.comprador = comprador;
		this.acao = acao;
		this.preco = preco;
		this.quantidade = quantidade;
		this.hora = Transacao.formataISO8601.format(new Date());
	}

	public Transacao() {
	};

	public Transacao(Map<String, Usuario> mapaDeUsuarios) {
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

	private static void adicionaInteresse(String referenciaUsuario, String codigoDaAcao) {
		Usuario usuario = mapaDeUsuarios.get(referenciaUsuario);
		usuario.getListaDeInteresse().add(codigoDaAcao);
	}
    
    /* A: varre a fila de venda pra ver se tem alguma
     * com mesmo código de ação da compra nova.
     */
    private synchronized static void procuraVenda(Ordem compra) {
		if(filaDeVenda.containsValue(compra.getCodigoDaAcao()) == true) { // pode ser null, por isso da comparaçao
			Transacao transacao;
			String agora = Transacao.formataISO8601.format(new Date());
			System.out.println("filaDeVendacontém " + compra.getCodigoDaAcao());
			Iterator<Ordem> iteratorVenda = filaDeVenda.keySet().iterator();
			while ((iteratorVenda.hasNext()) && (compra.getQuantidade() > 0)) {
				Ordem venda = iteratorVenda.next();
				// A: se a ordem já tiver vencido, remove direto da lista, sem processar.
				try {
					System.out.println("Agora: " + Transacao.formataISO8601.parse(agora));
					System.out.println("Venda: " + Transacao.formataISO8601.parse(venda.getPrazo()));
					if (Transacao.formataISO8601.parse(agora).compareTo(Transacao.formataISO8601.parse(venda.getPrazo())) > 0) {
						
						System.out.println("Ordem de venda venceu em " + venda.getPrazo());
						iteratorVenda.remove();
					} else {
						if (compra.getCodigoDaAcao().equals(venda.getCodigoDaAcao())
								&& !compra.getNomeDeUsuario().equals(venda.getNomeDeUsuario())) {
							/*
							 * A: só será feita a compra se o preço for exatamente igual, como o pdf diz.
							 */
							if (compra.getValor() == venda.getValor()) {
								try {
									/*
									 * A: se a ordem de venda oferece maior quantidade que a de compra, esgotará a
									 * de compra.
									 */;
									if (venda.getQuantidade() > compra.getQuantidade()) {
										transacao = new Transacao(venda.getNomeDeUsuario(), compra.getNomeDeUsuario(),
												compra.getCodigoDaAcao(), compra.getValor(), compra.getQuantidade());
										transacoes.add(transacao);
										// A: atualizando a quantidade na ordem de venda
										venda.setQuantidade(venda.getQuantidade() - compra.getQuantidade());

										// A: removendo a ordem de compra da filaDeCompra
										filaDeCompra.remove(compra);

										// A: atualizando carteira do comprador
										adicionaNaCarteira(compra.getNomeDeUsuario(), compra.getCodigoDaAcao(),
												compra.getQuantidade());
										// A: atualizando carteira do vendedor
										tiraDaCarteira(venda.getNomeDeUsuario(), venda.getCodigoDaAcao(),
												compra.getQuantidade());

										// A: adicionando na lista de interesse do comprador
										adicionaInteresse(compra.getNomeDeUsuario(), compra.getCodigoDaAcao());

										// A: notificações
										notificaUsuarios(compra.getNomeDeUsuario(), venda.getNomeDeUsuario(),
												compra.getCodigoDaAcao(), compra.getQuantidade(), compra.getValor());

										// A: esgotando a ordem de compra pra 0. Precisa pra sair do loop.
										compra.setQuantidade(0);
									}
									/*
									 * A: se quantidade de compra e venda forem iguais, os dois sairão das filas.
									 */
									else if (venda.getQuantidade() == compra.getQuantidade()) {
										transacao = new Transacao(venda.getNomeDeUsuario(), compra.getNomeDeUsuario(),
												compra.getCodigoDaAcao(), compra.getValor(), compra.getQuantidade());
										transacoes.add(transacao);
										// A: removendo as ordens das filas
										iteratorVenda.remove();
										filaDeCompra.remove(compra);

										// A: atualizando carteira do comprador
										adicionaNaCarteira(compra.getNomeDeUsuario(), compra.getCodigoDaAcao(),
												compra.getQuantidade());

										// A: atualizando carteira do vendedor
										tiraDaCarteira(venda.getNomeDeUsuario(), venda.getCodigoDaAcao(),
												compra.getQuantidade());

										// A: adicionando na lista de interesse do comprador
										adicionaInteresse(compra.getNomeDeUsuario(), compra.getCodigoDaAcao());

										// A: notificações
										notificaUsuarios(compra.getNomeDeUsuario(), venda.getNomeDeUsuario(),
												compra.getCodigoDaAcao(), compra.getQuantidade(), compra.getValor());

										// A: esgotando as ordens de compra e venda pra 0.
										compra.setQuantidade(0);
										venda.setQuantidade(0);
									}
									/*
									 * A: se a ordem de venda oferece menor quantidade que a de compra, esgotará a
									 * de venda.
									 */
									else if (venda.getQuantidade() < compra.getQuantidade()) {
										transacao = new Transacao(venda.getNomeDeUsuario(), compra.getNomeDeUsuario(),
												compra.getCodigoDaAcao(), compra.getValor(), venda.getQuantidade());
										transacoes.add(transacao);

										// A: removendo a ordem de venda da filaDeVenda
										iteratorVenda.remove();

										// A: atualizando carteira do comprador
										adicionaNaCarteira(compra.getNomeDeUsuario(), compra.getCodigoDaAcao(),
												venda.getQuantidade());
										// A: atualizando carteira do vendedor
										tiraDaCarteira(venda.getNomeDeUsuario(), venda.getCodigoDaAcao(),
												venda.getQuantidade());

										// A: atualizando a quantidade da fila de compra
										compra.setQuantidade(compra.getQuantidade() - venda.getQuantidade());

										// A: adicionando na lista de interesse do comprador
										adicionaInteresse(compra.getNomeDeUsuario(), compra.getCodigoDaAcao());

										notificaUsuarios(compra.getNomeDeUsuario(), venda.getNomeDeUsuario(),
												compra.getCodigoDaAcao(), venda.getQuantidade(), compra.getValor());

										// A: esgotando a ordem de venda.
										venda.setQuantidade(0);
									}
								} catch (Exception e) {
									System.out.println("Exception em realizaCompra: " + e.getMessage());
								}
							}
						}
					}
				} catch (ParseException e) {
					System.out.println("Exception em realizaCompra: " + e.getMessage());

				}
			}
		}
	}
    
    /* A: varre a fila de compra pra ver se tem alguma
     * com mesmo código de ação da venda nova.
     */
    private synchronized static void procuraCompra(Ordem venda) {
		if (filaDeCompra.containsValue(venda.getCodigoDaAcao()) == true) { // pode ser null, por isso da comparaçao
			Transacao transacao;
			String agora = Transacao.formataISO8601.format(new Date());
			System.out.println("filaDeCompra contém " + venda.getCodigoDaAcao());
			Iterator<Ordem> iteratorCompra = filaDeCompra.keySet().iterator();
			while ((iteratorCompra.hasNext()) && (venda.getQuantidade() > 0)) {
				Ordem compra = iteratorCompra.next();
				// A: se a ordem já tiver vencido, remove direto da lista, sem processar.
				try {
					System.out.println("Agora: " + Transacao.formataISO8601.parse(agora));
					System.out.println("Compra: " + Transacao.formataISO8601.parse(compra.getPrazo()));
					if (Transacao.formataISO8601.parse(agora).compareTo(Transacao.formataISO8601.parse(compra.getPrazo())) > 0) {
						
						System.out.println("Ordem de compra venceu em " + compra.getPrazo());
						iteratorCompra.remove();
					} else {
						if (compra.getCodigoDaAcao().equals(venda.getCodigoDaAcao())
								&& !compra.getNomeDeUsuario().equals(venda.getNomeDeUsuario())) {
							/*
							 * A: só será feita a compra se o preço for exatamente igual, como o pdf diz.
							 */
							if (compra.getValor() == venda.getValor()) {
								try {
									/*
									 * A: se a ordem de venda oferece maior quantidade que a de compra, esgotará a
									 * de compra.
									 */;
									if (venda.getQuantidade() > compra.getQuantidade()) {
										transacao = new Transacao(venda.getNomeDeUsuario(), compra.getNomeDeUsuario(),
												compra.getCodigoDaAcao(), compra.getValor(), compra.getQuantidade());
										transacoes.add(transacao);
										// A: atualizando a quantidade na ordem de venda
										venda.setQuantidade(venda.getQuantidade() - compra.getQuantidade());

										// A: removendo a ordem de compra da filaDeCompra
										iteratorCompra.remove();

										// A: atualizando carteira do comprador
										adicionaNaCarteira(compra.getNomeDeUsuario(), compra.getCodigoDaAcao(),
												compra.getQuantidade());
										// A: atualizando carteira do vendedor
										tiraDaCarteira(venda.getNomeDeUsuario(), venda.getCodigoDaAcao(),
												compra.getQuantidade());

										// A: adicionando na lista de interesse do comprador
										adicionaInteresse(compra.getNomeDeUsuario(), compra.getCodigoDaAcao());

										// A: notificações
										notificaUsuarios(compra.getNomeDeUsuario(), venda.getNomeDeUsuario(),
												compra.getCodigoDaAcao(), compra.getQuantidade(), compra.getValor());

										// A: esgotando a ordem de compra pra 0
										compra.setQuantidade(0);
									}
									/*
									 * A: se quantidade de compra e venda forem iguais, os dois sairão das filas.
									 */
									else if (venda.getQuantidade() == compra.getQuantidade()) {
										transacao = new Transacao(venda.getNomeDeUsuario(), compra.getNomeDeUsuario(),
												compra.getCodigoDaAcao(), compra.getValor(), compra.getQuantidade());
										transacoes.add(transacao);
										// A: removendo as ordens das filas
										iteratorCompra.remove();
										filaDeVenda.remove(venda);

										// A: atualizando carteira do comprador
										adicionaNaCarteira(compra.getNomeDeUsuario(), compra.getCodigoDaAcao(),
												compra.getQuantidade());

										// A: atualizando carteira do vendedor
										tiraDaCarteira(venda.getNomeDeUsuario(), venda.getCodigoDaAcao(),
												compra.getQuantidade());

										// A: adicionando na lista de interesse do comprador
										adicionaInteresse(compra.getNomeDeUsuario(), compra.getCodigoDaAcao());

										// A: notificações
										notificaUsuarios(compra.getNomeDeUsuario(), venda.getNomeDeUsuario(),
												compra.getCodigoDaAcao(), compra.getQuantidade(), compra.getValor());

										// A: esgotando as ordens de compra e venda pra 0.
										compra.setQuantidade(0);
										venda.setQuantidade(0);
									}
									/*
									 * A: se a ordem de venda oferece menor quantidade que a de compra, esgotará a
									 * de venda.
									 */
									else if (venda.getQuantidade() < compra.getQuantidade()) {
										transacao = new Transacao(venda.getNomeDeUsuario(), compra.getNomeDeUsuario(),
												compra.getCodigoDaAcao(), compra.getValor(), venda.getQuantidade());
										transacoes.add(transacao);

										// A: removendo a ordem de venda da filaDeVenda
										filaDeVenda.remove(venda);;

										// A: atualizando carteira do comprador
										adicionaNaCarteira(compra.getNomeDeUsuario(), compra.getCodigoDaAcao(),
												venda.getQuantidade());
										// A: atualizando carteira do vendedor
										tiraDaCarteira(venda.getNomeDeUsuario(), venda.getCodigoDaAcao(),
												venda.getQuantidade());

										// A: atualizando a quantidade da fila de compra
										compra.setQuantidade(compra.getQuantidade() - venda.getQuantidade());

										// A: adicionando na lista de interesse do comprador
										adicionaInteresse(compra.getNomeDeUsuario(), compra.getCodigoDaAcao());

										notificaUsuarios(compra.getNomeDeUsuario(), venda.getNomeDeUsuario(),
												compra.getCodigoDaAcao(), venda.getQuantidade(), compra.getValor());

										// A: esgotando a ordem de venda.
										venda.setQuantidade(0);
									}
								} catch (Exception e) {
									System.out.println("Exception em realizaCompra: " + e.getMessage());
								}
							}
						}
					}
				} catch (ParseException e) {
					System.out.println("Exception em realizaCompra: " + e.getMessage());

				}
			}
		}
    }
    
    private synchronized static void notificaUsuarios(String comprador, String vendedor, String codigoDaAcao, int quantidade, float valor) throws RemoteException {
		System.out.printf("Transacao realizada: usuario %s comprou %d %s do usuario %s por %f.\n", comprador, quantidade, codigoDaAcao, vendedor, valor);
		String notificacao = quantidade + " " + codigoDaAcao + " por " + valor;

		// recupera usuarios do mapa pelo nome
		Usuario usuarioComprador = mapaDeUsuarios.get(comprador);
		Usuario usuarioVendedor = mapaDeUsuarios.get(vendedor);

		// adiciona a notificacao na fila de msgs
		usuarioComprador.getFilaDeMensagens().add("Compra realizada: " + notificacao);
		usuarioVendedor.getFilaDeMensagens().add("Venda realizada: " + notificacao);
		
	}


    private static void adicionaNaCarteira(String referenciaCliente, String codigoDaAcao, int quantidade) throws Exception {
		Usuario usuario = mapaDeUsuarios.get(referenciaCliente);
		usuario.getCarteira().adicionarAcaoNaCarteira(codigoDaAcao, quantidade);
    }

	private static void tiraDaCarteira(String referenciaCliente, String codigoDaAcao, int quantidade) throws Exception {
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
    public static synchronized void removeDasFilas(String usuarioRef) {
    	// A: criando iterador para filaDeCompra
    	Iterator<Ordem> compraIterator = filaDeCompra.keySet().iterator();
    	while(compraIterator.hasNext()) {
    		Ordem compra = compraIterator.next();
    		if(compra.getNomeDeUsuario() == usuarioRef) {
    			// A: removendo a ordem, se pertencer ao usuario
				compraIterator.remove();
    		}
    	}    	
    	// A: criando iterador para filaDeVenda
    	Iterator<Ordem> vendaIterator = filaDeVenda.keySet().iterator();
    	while(vendaIterator.hasNext()) {
    		Ordem venda = vendaIterator.next();
    		if(venda.getNomeDeUsuario() == usuarioRef) {
    			// A: removendo a ordem, se pertencer ao usuario
    			vendaIterator.remove();
    		}
    	} 
    }
    
    public static Vector<Transacao> getFilaDeTransacoes() {

		return transacoes;

        // System.out.println("Imprimindo transacoes...");

        // if (transacoes.size() == 0) {
        //     System.out.println("Lista de transacoes vazia.");
        //     return;
        // } else {
        //     StringBuilder nomes = new StringBuilder();
        //     String separador = "";

        //     for (Transacao transacao : transacoes) {
        //         nomes.append(separador);
        //         separador = "\n";
        //         nomes.append("C: " + transacao.comprador + "; acao: " + transacao.acao + "; V: " + transacao.vendedor + "; val: " + transacao.preco + "; quant: " + transacao.quantidade + " em " + formataData.format(transacao.hora));
        //     }
        //     System.out.println(nomes);
        // }
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
                nomes.append(compra.getQuantidade() + " " + compra.getCodigoDaAcao() + " por " + compra.getValor() + ". U: " + compra.getNomeDeUsuario());
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
                nomes.append(venda.getQuantidade() + " " + venda.getCodigoDaAcao() + " por " + venda.getValor() + ". U: " + venda.getNomeDeUsuario());
            }
            System.out.println(nomes);
        }
    }
    

}

