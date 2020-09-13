package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.*;
import interfaces.*;

public class ServImpl extends UnicastRemoteObject implements InterfaceServ {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Transacao transacao = new Transacao();
	public ServImpl() throws RemoteException {
		System.out.println("Executing ServImpl...");
		transacao.start();
		// creates all lists and queues
	}

	@Override
	public void registrarNovoCliente(String usuario) throws RemoteException {
		System.out.printf("usuário %s se conectou!\n", usuario);
	}

	@Override
	public void registrarSaidaDeCliente(String usuario) throws RemoteException {
		System.out.printf("usuário %s saiu!\n", usuario);
	}

	@Override
	public void registrarOrdem(Ordem ordem) throws RemoteException {
			ordem.getReferenciaCliente().notificar("Servidor recebeu a ordem");
			/* A: ordem.tipoDaOrdem determinará para qual fila
			 * a ordem será adicionada. O getter getTipoDaOrdem()
			 * é utilizado.
			 */
			if(ordem.getTipoDaOrdem().equals("compra")) {
				Transacao.adicionaCompra(ordem);
				ordem.getReferenciaCliente().notificar("Ordem de compra registrada!");
			}
			else if(ordem.getTipoDaOrdem().equals("venda")){
				Transacao.adicionaVenda(ordem);
				ordem.getReferenciaCliente().notificar("Ordem de venda registrada!");
			}
	}

	@Override
	public void obterCotacoesDaListaDeInteresse(InterfaceCli referenciaCliente) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void obterCarteira(InterfaceCli referenciaCliente) throws RemoteException {
		// TODO Auto-generated method stub
		// Gio: da uma olhada na classe StringBuilder, eu uso ela na classe Main.

		referenciaCliente.notificar("retornando acoes");

	}

	@Override
	public void obterListaDeLimite(InterfaceCli referenciaCliente) throws RemoteException {
		// TODO Auto-generated method stub

		referenciaCliente.notificar("retornando Limites");
	}

	@Override
	public void atualizarListaDeInteresse(Interesse interesse) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void atualizarListaDeLimite(Limite limite) throws RemoteException {
		// TODO Auto-generated method stub

	}

}
