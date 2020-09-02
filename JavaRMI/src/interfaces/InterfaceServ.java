package interfaces;

import java.rmi.*;

import common.Ordem;

// Ex: registrarOrdemDeCompra(usuario ="5765@debian", codigoDaAcao = "AZUL4.SA",
//          quantidade = 100, valor = 22.37, prazo = 00:05:00);

public interface InterfaceServ extends Remote {
  
    public void registrarNovoCliente(String usuario) throws RemoteException;

    public void inserirNaListaDeInteresse(InterfaceCli referenciaCliente, 
        String usuario, String codigoDaAcao) throws RemoteException;
    
    public void removerDaListaDeInteresse(InterfaceCli referenciaCliente, 
        String usuario, String codigoDaAcao) throws RemoteException;
    
    public void obterCotacoesDaListaDeInteresse(InterfaceCli referenciaCliente)
        throws RemoteException;

    public void obterCarteira(InterfaceCli referenciaCliente)
        throws RemoteException;
    
    public void registrarOrdemDeCompraOuVenda(Ordem ordem)
        throws RemoteException;
    
    public void obterLimiteDeGanhoEPerda(InterfaceCli referenciaCliente)
        throws RemoteException;

    public void inserirNaListaLimiteDePerda(InterfaceCli referenciaCliente,
        String usuario, String codigoDaAcao) throws RemoteException;
    
    public void removerDaListaLimiteDePerda(InterfaceCli referenciaCliente,
        String usuario, String codigoDaAcao) throws RemoteException;    
    
    public void inserirNaListaLimiteDeGanho(InterfaceCli referenciaCliente,
        String usuario, String codigoDaAcao) throws RemoteException;
    
    public void removerDaListaLimiteDeGanho(InterfaceCli referenciaCliente,
        String usuario, String codigoDaAcao) throws RemoteException;    

}