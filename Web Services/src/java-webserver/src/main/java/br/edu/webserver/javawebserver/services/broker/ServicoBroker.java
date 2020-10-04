package br.edu.webserver.javawebserver.services.broker;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.springframework.stereotype.Service;

import br.edu.webserver.javawebserver.models.Ordem;
import br.edu.webserver.javawebserver.models.Usuario;

@Service
public class ServicoBroker {

	private Map<String, Usuario> mapaDeUsuarios;
	
	// Classe de inicio do servidor
	// Inicializa o servidor e o menu

	public ServicoBroker() {
		System.out.println("Servico Broker executando");
		mapaDeUsuarios = new Hashtable<String, Usuario>();
		new Transacao(mapaDeUsuarios);
	}

	// public GerenciadorDeCotacoes getGerenciadorDeCotacoes() {
	// 	return gerenciadorDeCotacoes;
	// }

	public void registrarNovoCliente(String nomeDeUsuario) {
		System.out.printf("usuário %s se conectou!\n", nomeDeUsuario);
		Usuario novoUsuario = new Usuario(nomeDeUsuario);
		mapaDeUsuarios.put(nomeDeUsuario, novoUsuario);
	}

	public void registrarSaidaDeCliente(String nomeDeUsuario) {
		// A: removendo ordens do usuário das filas de compra e venda
		Transacao.removeDasFilas(nomeDeUsuario);
		Usuario usuario = mapaDeUsuarios.remove(nomeDeUsuario);
		System.out.printf("usuário %s saiu!\n", usuario.getNome());

	}

	public void notificaUsuario(String nomeDeUsuario, String msg) {
		Usuario usuario = mapaDeUsuarios.get(nomeDeUsuario);

		// adiciona a notificacao na fila de msgs
		usuario.getFilaDeMensagens().add(msg);
		
	}
	public void registrarOrdem(Ordem ordem) {
		
		notificaUsuario(ordem.getNomeDeUsuario(), "Servidor recebeu a ordem.");

		/*
		 * A: ordem.tipoDaOrdem determinará para qual fila a ordem será adicionada. O
		 * getter getTipoDaOrdem() é utilizado.
		 */
		if (ordem.getTipoDaOrdem().equals("compra")) {
			Transacao.adicionaCompra(ordem);
			
			notificaUsuario(ordem.getNomeDeUsuario(), "Ordem de compra registrada!");

		} else if (ordem.getTipoDaOrdem().equals("venda")) {
			Transacao.adicionaVenda(ordem);

			notificaUsuario(ordem.getNomeDeUsuario(), "Ordem de venda registrada!");
			
        }
        System.out.println(ordem.toString());
	}

	// Retorna o vetor de mensagens para o determinado usuario
	public Vector<String> getMensagensDoUsuario(String nomeDeUsuario) {
		return mapaDeUsuarios.get(nomeDeUsuario).getFilaDeMensagens();
	}	

	// @Override
	// public void atualizarListaDeInteresse(Interesse interesse) throws RemoteException {
	// 	try {
	// 		gerenciadorDeInteresses.atualizarListaDeInteresse(interesse);
	// 	} catch (Exception e) {
	// 		System.out.println("Servidor: erro ao atualizar lista de interesse");
	// 		e.printStackTrace();
	// 	}
	// }

	// @Override
	// public void obterCotacoesDaListaDeInteresse(InterfaceCli referenciaCliente) throws RemoteException {
	// 	gerenciadorDeInteresses.obterCotacoesDaListaDeInteresse(referenciaCliente);

	// }

	// @Override
	// public void obterCarteira(InterfaceCli referenciaCliente) throws RemoteException {
	// 	Usuario usuario = mapaDeUsuarios.get(referenciaCliente);
	// 	Carteira carteira = usuario.getCarteira();
	// 	String msg = "Servidor: imprimindo carteira do usuario: " + usuario.getNome();
	// 	System.out.println(msg);
	// 	referenciaCliente.notificar(carteira.obterCarteiraComoString());

	// }

	// @Override
	// public void obterListaDeLimite(InterfaceCli referenciaCliente) throws RemoteException {
	// 	String limites = gerenciadorDeLimites.obterListasDeLimiteComoString(referenciaCliente);
	// 	referenciaCliente.notificar(limites);
	// }

	// @Override
	// public void atualizarListaDeLimite(Limite limite) throws RemoteException {
	// 	try {
	// 		gerenciadorDeLimites.atualizarListaDeLimite(limite);
	// 	} catch (Exception e) {
	// 		limite.getReferenciaCliente().notificar("Erro ao atualizar lista de limite");
	// 		System.out.println("Erro ao atualizar lista de limite");
	// 		e.printStackTrace();
	// 	}

	// }

	// public void imprimirUsuarios() {
	// 	System.out.println("Servidor: Imprimindo lista de usuarios...");
		
	// 	if (mapaDeUsuarios.size() == 0) {
	// 		System.out.println("Servidor: Lista de usuarios vazia");
	// 		return;
	// 	}
	// 	else {
	// 		Collection<Usuario> listaDeUsuarios = mapaDeUsuarios.values();

	// 		StringBuilder nomes = new StringBuilder();
	// 		String separador = "";
	// 		for (Usuario usuario : listaDeUsuarios) {
	// 			nomes.append(separador);
  	// 			separador = ", ";
	// 			nomes.append(usuario.getNome());
	// 		}
			
	// 		System.out.println(nomes);
	// 	}
		
	// }
}
