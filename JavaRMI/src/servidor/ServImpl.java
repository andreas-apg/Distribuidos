package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import common.*;
import interfaces.*;

public class ServImpl extends UnicastRemoteObject implements InterfaceServ {

	private static final long serialVersionUID = 1L;

	// Todo: remover lista de Usuarios e deixar so o mapaDeusuario
	Vector<Usuario> listaDeUsuarios;
	private Map<InterfaceCli, Usuario> mapaDeUsuarios;
	Transacao transacao = new Transacao(listaDeUsuarios);
	private GerenciadorDeCotacoes gerenciadorDeCotacoes;
	private GerenciadorDeInteresses gerenciadorDeInteresses;

	public ServImpl() throws RemoteException {
		System.out.println("Executing ServImpl...");
		listaDeUsuarios = new Vector<Usuario>();
		mapaDeUsuarios = new Hashtable<InterfaceCli, Usuario>();
		gerenciadorDeCotacoes = new GerenciadorDeCotacoes();
		gerenciadorDeInteresses = new GerenciadorDeInteresses(mapaDeUsuarios, gerenciadorDeCotacoes);

		transacao.start();
		// creates all lists and queues
	}

	public GerenciadorDeCotacoes getGerenciadorDeCotacoes() {
		return gerenciadorDeCotacoes;
	}

	@Override
	public void registrarNovoCliente(String nomeDeUsuario, InterfaceCli referenciaCliente) throws RemoteException {
		System.out.printf("usuário %s se conectou!\n", nomeDeUsuario);
		Usuario novoUsuario = new Usuario(nomeDeUsuario, referenciaCliente);

		listaDeUsuarios.add(novoUsuario);
		mapaDeUsuarios.put(referenciaCliente, novoUsuario);
	}

	@Override
	public void registrarSaidaDeCliente(InterfaceCli referenciaCliente) throws RemoteException {

		// TODO: Tratar saida do usuario (remover ordens, ...)

		Usuario usuario = mapaDeUsuarios.remove(referenciaCliente);
		System.out.printf("usuário %s saiu!\n", usuario.getNome());

	}

	@Override
	public void registrarOrdem(Ordem ordem) throws RemoteException {
		ordem.getReferenciaCliente().notificar("Servidor recebeu a ordem");
		/*
		 * A: ordem.tipoDaOrdem determinará para qual fila a ordem será adicionada. O
		 * getter getTipoDaOrdem() é utilizado.
		 */
		if (ordem.getTipoDaOrdem().equals("compra")) {
			Transacao.adicionaCompra(ordem);
			ordem.getReferenciaCliente().notificar("Ordem de compra registrada!");
		} else if (ordem.getTipoDaOrdem().equals("venda")) {
			Transacao.adicionaVenda(ordem);
			ordem.getReferenciaCliente().notificar("Ordem de venda registrada!");
		}
	}

	@Override
	public void atualizarListaDeInteresse(Interesse interesse) throws RemoteException {
		try {
			gerenciadorDeInteresses.atualizarListaDeInteresse(interesse);
		} catch (Exception e) {
			System.out.println("Servidor: erro ao atualizar lista de interesse");
			e.printStackTrace();
		}
	}

	@Override
	public void obterCotacoesDaListaDeInteresse(InterfaceCli referenciaCliente) throws RemoteException {
		gerenciadorDeInteresses.obterCotacoesDaListaDeInteresse(referenciaCliente);

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
