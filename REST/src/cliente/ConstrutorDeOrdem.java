package cliente;

import java.util.Scanner;

import common.*;

public class ConstrutorDeOrdem extends ConstrutorDeMsg{

	Ordem ordem;

	public ConstrutorDeOrdem(String nomeDeUsuario, CliImpl cliente, Scanner keyboard) {
		super(nomeDeUsuario, cliente, keyboard);

		ordem = new Ordem();
		
	}

	public Ordem obterOrdemDoUsuario() {

		ordem.setReferenciaCliente(cliente);
		ordem.setUsuario(nomeDeUsuario);
		ordem.setTipoDaOrdem(Helpers.obterTipoDaOrdem(keyboard));
		ordem.setCodigoDaAcao(Helpers.obterCodigoDaAcao(keyboard));
		ordem.setValor(Helpers.obterValor(keyboard));
		ordem.setQuantidade(Helpers.obterQuantidade(keyboard));
		ordem.setPrazo(Helpers.obterPrazo(keyboard));

		return ordem;

	}

}
