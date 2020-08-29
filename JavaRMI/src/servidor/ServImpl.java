package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;

import interfaces.InterfaceCli;
import interfaces.InterfaceServ;

public class ServImpl extends UnicastRemoteObject implements InterfaceServ{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServImpl() throws RemoteException{
		System.out.println("Executing ServIml...");
		
		// creates all lists and queues
		
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
	public void registrarOrdemDeCompra(InterfaceCli referenciaCliente, String usuario, String codigoDaAcao,
			int quantidade, float valor, LocalTime prazo) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarOrdemDeVenda(InterfaceCli referenciaCliente, String usuario, String codigoDaAcao,
			int quantidade, float valor, LocalTime prazo) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inserirNaListaLimiteDePerda(InterfaceCli referenciaCliente, String usuario, String codigoDaAcao)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removerDaListaLimiteDePerda(InterfaceCli referenciaCliente, String usuario, String codigoDaAcao)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inserirNaListaLimiteDeGanho(InterfaceCli referenciaCliente, String usuario, String codigoDaAcao)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removerDaListaLimiteDeGanho(InterfaceCli referenciaCliente, String usuario, String codigoDaAcao)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
