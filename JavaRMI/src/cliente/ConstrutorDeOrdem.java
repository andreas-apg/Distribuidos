package cliente;

import common.*;

public class ConstrutorDeOrdem {

	Ordem ordem;
	ConstrutorDeMsg construtorDeMsg;

	public ConstrutorDeOrdem(ConstrutorDeMsg construtorDeMsg) {


		ordem = new Ordem();
		this.construtorDeMsg = construtorDeMsg;
		
	}

	public Ordem obterOrdemDoUsuario() {

		ordem.setUsuario(construtorDeMsg.getUsuario());
		ordem.setReferenciaCliente(construtorDeMsg.getCliente());

		ordem.setTipoDaOrdem(construtorDeMsg.obterTipoDaOrdem());
		ordem.setCodigoDaAcao(construtorDeMsg.obterCodigoDaAcao());
		ordem.setValor(construtorDeMsg.obterValor());
		ordem.setQuantidade(construtorDeMsg.obterQuantidade());
		ordem.setPrazo(construtorDeMsg.obterPrazo());

		return ordem;

	}

}
