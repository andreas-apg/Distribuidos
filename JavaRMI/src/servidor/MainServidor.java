package servidor;

import java.rmi.RemoteException;
import java.rmi.registry.*;

import interfaces.InterfaceServ;

public class MainServidor {

    static int portaSN = 1099;

    public static void main(String[] args) {

        try {
            Registry referenciaServicoNomes = LocateRegistry.createRegistry(portaSN);
            
            InterfaceServ referenciaServidor = new ServImpl();
            
            referenciaServicoNomes.rebind("HomeBroker", referenciaServidor);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
}
