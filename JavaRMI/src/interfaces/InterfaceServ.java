package interfaces;

import java.rmi.*;

import common.*;

// Ex: registrarOrdemDeCompra(usuario ="5765@debian", codigoDaAcao = "AZUL4.SA",
//          quantidade = 100, valor = 22.37, prazo = 00:05:00);

public interface InterfaceServ extends Remote {
  
    // Metodo chamado no servidor por cada cliente quando ele "entra" no sistema
    public void registrarNovoCliente(String usuario) throws RemoteException;

    // Lista de acoes em que o cliente tem interesse em acompanhar
    public void inserirNaListaDeInteresse(InterfaceCli referenciaCliente, 
        String usuario, String codigoDaAcao) throws RemoteException;
    
    public void removerDaListaDeInteresse(InterfaceCli referenciaCliente, 
        String usuario, String codigoDaAcao) throws RemoteException;
    
    public void atualizarListaDeInteresse(Interesse interesse)
        throws RemoteException;

    public void obterCotacoesDaListaDeInteresse(InterfaceCli referenciaCliente)
        throws RemoteException;

    // Carteira: lista de acoes que o cliente possui (comprou)
    public void obterCarteira(InterfaceCli referenciaCliente)
        throws RemoteException;
    
    public void registrarOrdem(Ordem ordem)
        throws RemoteException;
    
    // Lista de acoes que o cliente deseja ser notificado quando o preco atinge determinado valor
    public void obterListaDeLimiteDeGanhoEPerda(InterfaceCli referenciaCliente)
        throws RemoteException;

    public void inserirNaListaDeLimiteDePerda(InterfaceCli referenciaCliente,
        String usuario, String codigoDaAcao) throws RemoteException;
    
    public void removerDaListaDeLimiteDePerda(InterfaceCli referenciaCliente,
        String usuario, String codigoDaAcao) throws RemoteException;    
    
    public void inserirNaListaDeLimiteDeGanho(InterfaceCli referenciaCliente,
        String usuario, String codigoDaAcao) throws RemoteException;
    
    public void removerDaListaDeLimiteDeGanho(InterfaceCli referenciaCliente,
        String usuario, String codigoDaAcao) throws RemoteException;    

}