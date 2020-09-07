package cliente;

import common.Interesse;

public class ConstrutorDeInteresse {

	Interesse interesse;
	ConstrutorDeMsg construtorDeMsg;

	public ConstrutorDeInteresse(ConstrutorDeMsg construtorDeMsg) {
		
		interesse = new Interesse();
		this.construtorDeMsg = construtorDeMsg;
	}

	public Interesse obterInteresseDoUsuario() {
		interesse.setUsuario(construtorDeMsg.getUsuario());
		interesse.setReferenciaCliente(construtorDeMsg.getCliente());

		interesse.setTipoDaAtualizacao(construtorDeMsg.obterTipoDaAtualizacao());
		interesse.setCodigoDaAcao(construtorDeMsg.obterCodigoDaAcao());

		return interesse;
	}

}
