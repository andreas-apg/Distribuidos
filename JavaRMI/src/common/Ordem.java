package common;

import java.util.ArrayList;
import java.util.Date;

/* classe para uma ordem. Um usuário
 * faz uma ordem de compra ou de venda
 * para dada ação. Há dois tipos de fila
 * de ordem: fila de compra e fila de venda,
 * ordenadas por ordem que foi feita. Se 
 * ocorrer  uma transação entre duas ordens,
 * isso é gravado em uma instância da 
 * classe transação.
 */
public class Ordem {
	private String tipoDaOrdem; // compra ou venda
	private String codigoDaAcao; // nome da ação ex PETR4
	private String usuario; // quem fez a ordem
	private float valor; // em reais
	private int quantidade;
	private Date prazo;

	// TODO: Gio: acho que isso nao pertence a essa classe, mas aa classe acima dela
	public static ArrayList<Ordem> filaDeCompra;
	public static ArrayList<Ordem> filaDeVenda;

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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCodigoDaAcao() {
		return codigoDaAcao;
	}

	public void setCodigoDaAcao(String codigoDaAcao) {
		this.codigoDaAcao = codigoDaAcao;
	}

	public void setTipoDaOrdem(String tipoDaOrdem) {
		this.tipoDaOrdem = tipoDaOrdem;
	}

}
