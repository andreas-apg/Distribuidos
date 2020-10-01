package br.edu.webserver.javawebserver.models;

import lombok.Data;
import lombok.NoArgsConstructor;

/* classe para uma ordem. Um usuário
 * faz uma ordem de compra ou de venda
 * para dada ação. Há dois tipos de fila
 * de ordem: fila de compra e fila de venda,
 * ordenadas por ordem que foi feita. Se 
 * ocorrer  uma transação entre duas ordens,
 * isso é gravado em uma instância da 
 * classe transação.
 */


@NoArgsConstructor
@Data
public class Ordem {

	private String nomeDeUsuario;
	private String tipoDaOrdem; // comprar ou vender
	private String codigoDaAcao;
	private float valor; // em reais
	private int quantidade;
	private String prazo;

	
	@Override
	public String toString() {
		return ("Nova ordem de " + tipoDaOrdem + " do usuario " + nomeDeUsuario);
	}
	
}