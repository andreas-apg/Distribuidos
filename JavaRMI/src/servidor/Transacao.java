package servidor;

import java.util.Date;
import java.util.ArrayList;

import common.Ordem;

/* classe para a transação de uma ação.
 * Para saber a cotação de uma ação,
 * pegar o objeto tipo Transacao pra
 * uma determinada acao e olha
 * o que possui a hora mais recente.
 */ 
public class Transacao {
	String vendedor; 	// usuário
	String comprador; 	// usuário
	String acao; 		// ie PETR4
	float preco; 		// em reais
	int quantidade; 	// número inteiro 
	Date hora;
	
	public static ArrayList<Transacao> transacoes;
	// A: movi as filas de compra e venda pra essa classe
	public static ArrayList<Ordem> filaDeCompra;
	public static ArrayList<Ordem> filaDeVenda;
}
