package servidor;

import java.rmi.RemoteException;
import java.rmi.registry.*;

public class MainServidor {

    static int portaSN = 1099;

    static MenuServidor menu;

    public static void main(String[] args) {

        try {
            Registry referenciaServicoNomes = LocateRegistry.createRegistry(portaSN);

            ServImpl referenciaServidor = new ServImpl();
            referenciaServicoNomes.rebind("HomeBroker", referenciaServidor);

            menu = new MenuServidor(referenciaServidor);
            menu.start();

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erro ao iniciar servidor");
            e.printStackTrace();
        }


    }
    
}
