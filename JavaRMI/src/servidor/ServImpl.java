package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.Interesse;
import common.Ordem;
import interfaces.InterfaceCli;
import interfaces.InterfaceServ;

public class ServImpl extends UnicastRemoteObject implements InterfaceServ {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServImpl() throws RemoteException {
		System.out.println("Executing ServIml...");

		// creates all lists and queues

	}

	@Override
	public void registrarNovoCliente(String usuario) {
		System.out.printf("usuário %s se conectou", usuario);
	}

	@Override
	public void registrarOrdemDeCompraOuVenda(Ordem ordem) throws RemoteException {
		ordem.getReferenciaCliente().notificar("Servidor recebeu a ordem");
	}

	@Override
	public void inserirNaListaDeInteresse(InterfaceCli referenciaCliente, String usuario, String codigoDaAcao)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removerDaListaDeInteresse(InterfaceCli referenciaCliente, String usuario, String codigoDaAcao)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void obterCotacoesDaListaDeInteresse(InterfaceCli referenciaCliente) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void inserirNaListaDeLimiteDePerda(InterfaceCli referenciaCliente, String usuario, String codigoDaAcao)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removerDaListaDeLimiteDePerda(InterfaceCli referenciaCliente, String usuario, String codigoDaAcao)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void inserirNaListaDeLimiteDeGanho(InterfaceCli referenciaCliente, String usuario, String codigoDaAcao)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removerDaListaDeLimiteDeGanho(InterfaceCli referenciaCliente, String usuario, String codigoDaAcao)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void obterCarteira(InterfaceCli referenciaCliente) throws RemoteException {
		// TODO Auto-generated method stub
		// Gio: da uma olhada na classe StringBuilder, eu uso ela na classe Main.

		referenciaCliente.notificar("retornando acoes");

	}

	@Override
	public void obterListaDeLimiteDeGanhoEPerda(InterfaceCli referenciaCliente) throws RemoteException {
		// TODO Auto-generated method stub

		referenciaCliente.notificar("retornando Limites");
	}

	@Override
	public void atualizarListaDeInteresse(Interesse interesse) throws RemoteException {
		// TODO Auto-generated method stub

	}

}
