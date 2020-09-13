package cliente;

import interfaces.*;
import java.rmi.registry.*;

public class MainCliente {
    
    private static Menu menu;
    private static InterfaceServ referenciaServidor;
    // private static InterfaceCli cliente;
    private static CliImpl cliente;

    public static void main(String[] args) throws Exception {

        Registry referenciaServicoNomes = LocateRegistry.getRegistry();

        referenciaServidor = (InterfaceServ) referenciaServicoNomes.lookup("HomeBroker");

        cliente = new CliImpl(referenciaServidor);

        menu = new Menu(cliente);
        menu.start();


    }
}