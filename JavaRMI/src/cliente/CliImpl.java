package cliente;

import interfaces.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.Interesse;
import common.Ordem;

// Essa classe é responsável pela comunicacao com o servidor
public class CliImpl extends UnicastRemoteObject implements InterfaceCli {

    private static final long serialVersionUID = 1L;
    
    InterfaceServ referenciaServidor;

    protected CliImpl(InterfaceServ referenciaServidor) throws RemoteException {
        this.referenciaServidor = referenciaServidor;
        referenciaServidor.registrarNovoCliente("giovane");
    }


    
    @Override
    public void notificar(String texto) throws RemoteException {
        System.out.println("Notificacao recebida: " + texto);

    }

    
	public void emitirOrdemDeCompraOuVenda(Ordem ordem) {
        try {
            referenciaServidor.registrarOrdemDeCompraOuVenda(ordem);
        } catch (RemoteException e) {
            System.out.println("Erro ao emitir ordem de Compra / Venda");
            e.printStackTrace();
        }
	}


	public void obterCarteira() {
        try {
            referenciaServidor.obterCarteira(this);
        } catch (RemoteException e) {
            System.out.println("Erro ao obter carteira");
            e.printStackTrace();
        }
	}



	public void obterCotacoesDaListaDeInteresse() {
        try {
            referenciaServidor.obterCotacoesDaListaDeInteresse(this);
        } catch (RemoteException e) {
            System.out.println("Erro ao obter cotacoes da lista de interesse");
            e.printStackTrace();
        }
	}



	public void atualizarListaDeInteresse(Interesse interesse) {
        try {
            referenciaServidor.atualizarListaDeInteresse(interesse);;
        } catch (RemoteException e) {
            System.out.println("Erro ao obter cotacoes da lista de interesse");
            e.printStackTrace();
        }
	}
}
