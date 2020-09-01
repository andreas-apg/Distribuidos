package cliente;

import interfaces.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CliImpl extends UnicastRemoteObject implements InterfaceCli {

    private static final long serialVersionUID = 1L;
    
    InterfaceServ referenciaServidor;

    protected CliImpl(InterfaceServ referenciaServidor) throws RemoteException {
        this.referenciaServidor = referenciaServidor;
        //referenciaServidor.registrarInteresse("Oi", this);
    }
    
    // Inicializa a Lista de Acoes que o cliente ja comeca com ela
    private void inicializarListaDeCotacoes(){

    }


    @Override
    public void notificar(String texto) throws RemoteException {
        System.out.println("Notificacao recebida: " + texto);

    }
}
