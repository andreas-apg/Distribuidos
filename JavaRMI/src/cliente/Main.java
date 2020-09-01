package cliente;

import interfaces.*;
import java.rmi.registry.*;

public class Main {
    
    private static Menu menu;
    private static InterfaceServ referenciaServidor;
    private static InterfaceCli cliente;
    private static OrderBuilder orderBuilder;

    public static void main(String[] args) throws Exception {

        Registry referenciaServicoNomes = LocateRegistry.getRegistry();

        referenciaServidor = (InterfaceServ) referenciaServicoNomes.lookup("HomeBroker");

        cliente = new CliImpl(referenciaServidor);
        


        menu = new Menu(orderBuilder);
        menu.start();


    }
}