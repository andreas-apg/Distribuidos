package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import interfaces.InterfaceCli;

/* classe para uma ordem. Um usuário
 * faz uma ordem de compra ou de venda
 * para dada ação. Há dois tipos de fila
 * de ordem: fila de compra e fila de venda,
 * ordenadas por ordem que foi feita. Se 
 * ocorrer  uma transação entre duas ordens,
 * isso é gravado em uma instância da 
 * classe transação.
 */
public class Ordem implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;

	private InterfaceCli referenciaCliente;
	private String tipoDaOrdem; // compra ou venda
	private String codigoDaAcao; // nome da ação ex PETR4
	private String usuario; // quem fez a ordem
	private float valor; // em reais
	private int quantidade;
	private Date prazo;

	public String getTipoDaOrdem() {
		return tipoDaOrdem;
	}

	public InterfaceCli getReferenciaCliente() {
		return referenciaCliente;
	}

	public void setReferenciaCliente(InterfaceCli referenciaCliente) {
		this.referenciaCliente = referenciaCliente;
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
