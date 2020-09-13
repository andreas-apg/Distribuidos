package cliente;

import java.util.Scanner;

import common.Helpers;
import common.Limite;

public class ConstrutorDeLimite extends ConstrutorDeMsg{

	Limite limite;


	public ConstrutorDeLimite(String nomeDeUsuario, CliImpl cliente, Scanner keyboard) {
		super(nomeDeUsuario, cliente, keyboard);
		limite = new Limite();
	}

	public Limite obterLimiteDoUsuario() {
		
		limite.setReferenciaCliente(cliente);
		limite.setUsuario(nomeDeUsuario);
        limite.setTipoDaAtualizacao(Helpers.obterTipoDaAtualizacao(keyboard));
		limite.setTipoDoLimite(Helpers.obterTipoDoLimite(keyboard));
        limite.setCodigoDaAcao(Helpers.obterCodigoDaAcao(keyboard));
        limite.setValor(Helpers.obterValor(keyboard));

		return limite;
	}

}
