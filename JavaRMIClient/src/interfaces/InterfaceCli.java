package interfaces;

import java.rmi.*;

public interface InterfaceCli extends Remote {

    public void notificar(String texto) throws RemoteException;    

}