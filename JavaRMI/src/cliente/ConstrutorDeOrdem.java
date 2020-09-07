package cliente;

import common.*;

public class ConstrutorDeOrdem {

	Ordem ordem;
	ConstrutorDeMsg msg;

	public ConstrutorDeOrdem(ConstrutorDeMsg msg) {


		ordem = new Ordem();
		this.msg = msg;
		
	}

	public Ordem ordemDeCompraOuVenda() {

		ordem.setUsuario(msg.getUsuario());
		ordem.setReferenciaCliente(msg.getCliente());

		ordem.setTipoDaOrdem(msg.obterTipoDaOrdem());
		ordem.setCodigoDaAcao(msg.obterCodigoDaAcao());
		ordem.setValor(msg.obterValor());
		ordem.setQuantidade(msg.obterQuantidade());
		ordem.setPrazo(msg.obterPrazo());

		return ordem;

	}

}
