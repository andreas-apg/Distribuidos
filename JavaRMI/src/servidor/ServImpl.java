package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import common.*;
import interfaces.*;

public class ServImpl extends UnicastRemoteObject implements InterfaceServ {

	private static final long serialVersionUID = 1L;

	Transacao transacao = new Transacao();
	private Vector<Usuario> listaDeUsuarios;
	private GerenciadorDeCotacoes gerenciadorDeCotacoes;

	public ServImpl() throws RemoteException {
		System.out.println("Executing ServImpl...");
		listaDeUsuarios = new Vector<Usuario>();
		gerenciadorDeCotacoes = new GerenciadorDeCotacoes();

		transacao.start();
		// creates all lists and queues
	}

	public GerenciadorDeCotacoes getGerenciadorDeCotacoes() {
		return gerenciadorDeCotacoes;
	}

	@Override
	public void registrarNovoCliente(String nomeDeUsuario) throws RemoteException {
		System.out.printf("usuário %s se conectou!\n", nomeDeUsuario);
		Usuario novoUsuario = new Usuario(nomeDeUsuario);
		Carteira carteira = novoUsuario.getCarteira();

		// Para facilitar os teste, iniciamos os usuarios com 3 acoes
		try {
			carteira.adicionarAcaoNaCarteira("AZUL4", 100);
			carteira.adicionarAcaoNaCarteira("VALE3", 100);
			carteira.adicionarAcaoNaCarteira("PETR4", 100);
		} catch (Exception e) {
			System.out.println("Erro ao inicializara acoes na carteira do usuario");
			e.printStackTrace();
		}


		listaDeUsuarios.add(novoUsuario);
	}

	@Override
	public void registrarSaidaDeCliente(String nomeDeUsuario) throws RemoteException {
		System.out.printf("usuário %s saiu!\n", nomeDeUsuario);
		
		// TODO: Tratar saida do usuario (remover ordens, remover da lista de usuario...)		
		
		for (Usuario usuario : listaDeUsuarios) {
			if(usuario.getNome().equals(nomeDeUsuario)){
				listaDeUsuarios.remove(usuario);
				break;
			}
		}

	}

	@Override
	public void registrarOrdem(Ordem ordem) throws RemoteException {
			ordem.getReferenciaCliente().notificar("Servidor recebeu a ordem");
			/* A: ordem.tipoDaOrdem determinará para qual fila
			 * a ordem será adicionada. O getter getTipoDaOrdem()
			 * é utilizado.
			 */
			if(ordem.getTipoDaOrdem().equals("compra")) {
				Transacao.adicionaCompra(ordem);
				ordem.getReferenciaCliente().notificar("Ordem de compra registrada!");
			}
			else if(ordem.getTipoDaOrdem().equals("venda")){
				Transacao.adicionaVenda(ordem);
				ordem.getReferenciaCliente().notificar("Ordem de venda registrada!");
			}
	}

	@Override
	public void obterCotacoesDaListaDeInteresse(InterfaceCli referenciaCliente) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void obterCarteira(InterfaceCli referenciaCliente) throws RemoteException {
		// TODO Auto-generated method stub
		// Gio: da uma olhada na classe StringBuilder, eu uso ela na classe Main.

		referenciaCliente.notificar("retornando acoes");

	}

	@Override
	public void obterListaDeLimite(InterfaceCli referenciaCliente) throws RemoteException {
		// TODO Auto-generated method stub

		referenciaCliente.notificar("retornando Limites");
	}

	@Override
	public void atualizarListaDeInteresse(Interesse interesse) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void atualizarListaDeLimite(Limite limite) throws RemoteException {
		// TODO Auto-generated method stub

	}

	public void imprimirUsuarios() {
		System.out.println("Imprimindo lista de usuarios...");
		
		if (listaDeUsuarios.size() == 0) {
			System.out.println("Lista de usuarios vazia");
			return;
		}
		else {
			StringBuilder nomes = new StringBuilder();
			String separador = "";
			for (Usuario usuario : listaDeUsuarios) {
				nomes.append(separador);
  				separador = ", ";
				nomes.append(usuario.getNome());
			}
			
			System.out.println(nomes);
		}
		
	}

}
