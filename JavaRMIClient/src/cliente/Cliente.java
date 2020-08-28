package cliente;

import interfaces.*;
import java.rmi.registry.*;


public class Cliente {

    InterfaceServ referenciaServidor;
    InterfaceCli cliente;
    
    public void start() throws Exception {
        
        Registry referenciaServicoNomes = LocateRegistry.getRegistry();

        referenciaServidor = (InterfaceServ) referenciaServicoNomes.lookup("HomeBroker");

        cliente = new CliImpl(referenciaServidor);    
        
    }
}
