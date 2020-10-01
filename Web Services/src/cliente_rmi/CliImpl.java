package cliente;

import interfaces.*;
import common.*;

import java.lang.management.ManagementFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


// Essa classe é responsável pela comunicacao com o servidor
public class CliImpl extends UnicastRemoteObject implements InterfaceCli {

    private static final long serialVersionUID = 1L;
    
    private String nomeDeUsuario;
    InterfaceServ referenciaServidor;

    protected CliImpl(InterfaceServ referenciaServidor) throws RemoteException {
        this.referenciaServidor = referenciaServidor;
        nomeDeUsuario = ManagementFactory.getRuntimeMXBean().getName();
        referenciaServidor.registrarNovoCliente(nomeDeUsuario, this);
    }
    
    @Override
    public void notificar(String texto) throws RemoteException {
        System.out.println();
        System.out.println("Notificacao recebida -> " + texto);

    }

    
	public void emitirOrdemDeCompraOuVenda(Ordem ordem) {
        try {
            referenciaServidor.registrarOrdem(ordem);
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
            System.out.println("Erro ao obter atualizar lista de interesse");
            e.printStackTrace();
        }
	}



	public void obterListaDeLimite() {
        try {
            referenciaServidor.obterListaDeLimite(this);
        } catch (RemoteException e) {
            System.out.println("Erro ao obter lista de limite de ganho e perda");
            e.printStackTrace();
        }
	}



	public void atualizarListaDeLimite(Limite limite) {
        try {
            referenciaServidor.atualizarListaDeLimite(limite);
        } catch (RemoteException e) {
            System.out.println("Erro ao obter atualizar lista de limite de ganho e perda");
            e.printStackTrace();
        }
    }

	public String getNomeDeUsuario() {
		return nomeDeUsuario;
	}

	public void sair() throws RemoteException {
        referenciaServidor.registrarSaidaDeCliente(this);
	}
}
