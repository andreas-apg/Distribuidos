package servidor;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
	LocalDateTime hora;
	
	public static ArrayList<Transacao> transacoes;
}
