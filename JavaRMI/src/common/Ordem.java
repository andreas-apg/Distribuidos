package common;

import java.text.SimpleDateFormat;
import java.util.Date;

import servidor.Transacao;


/* classe para uma ordem. Um usuário
 * faz uma ordem de compra ou de venda
 * para dada ação. Há dois tipos de fila
 * de ordem: fila de compra e fila de venda,
 * ordenadas por ordem que foi feita. Se 
 * ocorrer  uma transação entre duas ordens,
 * isso é gravado em uma instância da 
 * classe transação.
 */
public class Ordem extends Base {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String tipoDaOrdem; // comprar ou vender
	private float valor; // em reais
	private int quantidade;
	private Date prazo;
	private Thread thread;
	// A: usa-se assim> formataData.format(hora) 
	SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");

	public String getTipoDaOrdem() {
		return tipoDaOrdem;
	}

	public Date getPrazo() {
		return prazo;
	}

	public void setPrazo(Date prazo) {
		this.prazo = prazo;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public void setTipoDaOrdem(String tipoDaOrdem) {
		this.tipoDaOrdem = tipoDaOrdem;
	}
	
    public void start() {
    	if(thread == null) {
    		String nomeThread = this.getUsuario() + this.getCodigoDaAcao() + new Date();
		    thread = new Thread(this, nomeThread);
		    System.out.println("Iniciando thread de ordem " + this.getUsuario() + "_" + this.getTipoDaOrdem() +"_" +  this.getCodigoDaAcao() + "_" + formataData.format(new Date()));
		    thread.start();
    	}
    }
	
    /* A: thread é criada, mas dorme até estourar
     * seu prazo. Nesse momento, apenas acorda
     * para se remover da sua respectiva fila.
     */
	public void run() {
		try {
			Thread.sleep(prazo.getTime());
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		Transacao.mataOrdem(this);
	    System.out.println("Finalizando thread de ordem " + thread.getName());
		// terminando o run, a thread é finalizada.
	}
}
