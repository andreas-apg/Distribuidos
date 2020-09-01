package servidor;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
	String tipo;  // compra ou venda
	String acao; // nome da ação ex PETR4
	String usuario; // quem fez a ordem
	float preco;  // em reais
	int quantidade;
	LocalDateTime hora;
	
	public static ArrayList<Ordem> filaDeCompra;
	public static ArrayList<Ordem> filaDeVenda;
}
