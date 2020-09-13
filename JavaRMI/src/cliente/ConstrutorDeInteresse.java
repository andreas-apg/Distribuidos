package cliente;

import java.util.Scanner;

import common.Helpers;
import common.Interesse;

public class ConstrutorDeInteresse extends ConstrutorDeMsg{

	Interesse interesse;

	public ConstrutorDeInteresse(String nomeDeUsuario, CliImpl cliente, Scanner keyboard) {
		super(nomeDeUsuario, cliente, keyboard);
		interesse = new Interesse();
		
	}

	public Interesse obterInteresseDoUsuario() {

		interesse.setReferenciaCliente(cliente);
		interesse.setUsuario(nomeDeUsuario);
		interesse.setTipoDaAtualizacao(Helpers.obterTipoDaAtualizacao(keyboard));
		interesse.setCodigoDaAcao(Helpers.obterCodigoDaAcao(keyboard));

		return interesse;
	}

}
