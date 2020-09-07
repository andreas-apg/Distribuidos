package cliente;

import common.Limite;

public class ConstrutorDeLimite {

	Limite limite;
	ConstrutorDeMsg construtorDeMsg;

	public ConstrutorDeLimite(ConstrutorDeMsg construtorDeMsg) {
		
		limite = new Limite();
		this.construtorDeMsg = construtorDeMsg;
	}

	public Limite obterLimiteDoUsuario() {
        
		limite.setUsuario(construtorDeMsg.getUsuario());
		limite.setReferenciaCliente(construtorDeMsg.getCliente());
        limite.setTipoDaAtualizacao(construtorDeMsg.obterTipoDaAtualizacao());
		limite.setTipoDoLimite(construtorDeMsg.obterTipoDoLimite());
        limite.setCodigoDaAcao(construtorDeMsg.obterCodigoDaAcao());
        limite.setValor(construtorDeMsg.obterValor());

		return limite;
	}

}
