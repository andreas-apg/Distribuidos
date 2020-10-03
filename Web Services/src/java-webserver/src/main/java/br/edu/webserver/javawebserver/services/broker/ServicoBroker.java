package br.edu.webserver.javawebserver.services.broker;

import org.springframework.stereotype.Service;

import br.edu.webserver.javawebserver.models.Ordem;

@Service
public class ServicoBroker {

	// private Map<String, Usuario> mapaDeUsuarios;
	// private Transacao transacao;
	// private GerenciadorDeCotacoes gerenciadorDeCotacoes;
	// private GerenciadorDeInteresses gerenciadorDeInteresses;
    // private GerenciadorDeLimites gerenciadorDeLimites;
    
    // Classe de inicio do servidor
    // Inicializa o servidor e o menu

	// public ServImpl() throws RemoteException {
	// 	System.out.println("Executing ServImpl...");
	// 	mapaDeUsuarios = new Hashtable<InterfaceCli, Usuario>();
	// 	gerenciadorDeCotacoes = new GerenciadorDeCotacoes(mapaDeUsuarios);
	// 	gerenciadorDeInteresses = new GerenciadorDeInteresses(mapaDeUsuarios, gerenciadorDeCotacoes);
	// 	gerenciadorDeLimites = new GerenciadorDeLimites(mapaDeUsuarios, gerenciadorDeCotacoes);
	// 	transacao = new Transacao(mapaDeUsuarios);
	// 	transacao.start();
	// }

	// public GerenciadorDeCotacoes getGerenciadorDeCotacoes() {
	// 	return gerenciadorDeCotacoes;
	// }

	// @Override
	// public void registrarNovoCliente(String nomeDeUsuario, InterfaceCli referenciaCliente) throws RemoteException {
	// 	System.out.printf("usuário %s se conectou!\n", nomeDeUsuario);
	// 	Usuario novoUsuario = new Usuario(nomeDeUsuario, referenciaCliente);

	// 	mapaDeUsuarios.put(referenciaCliente, novoUsuario);
	// }

	// @Override
	// public void registrarSaidaDeCliente(InterfaceCli referenciaCliente) throws RemoteException {
	// 	// A: removendo ordens do usuário das filas de compra e venda
	// 	Transacao.removeDasFilas(referenciaCliente);
	// 	Usuario usuario = mapaDeUsuarios.remove(referenciaCliente);
	// 	System.out.printf("usuário %s saiu!\n", usuario.getNome());

	// }

	public void registrarOrdem(Ordem ordem) {
		// ordem.getReferenciaCliente().notificar("Servidor recebeu a ordem.");
		// /*
		//  * A: ordem.tipoDaOrdem determinará para qual fila a ordem será adicionada. O
		//  * getter getTipoDaOrdem() é utilizado.
		//  */
		// if (ordem.getTipoDaOrdem().equals("compra")) {
		// 	Transacao.adicionaCompra(ordem);
		// 	ordem.getReferenciaCliente().notificar("Ordem de compra registrada!");
		// 	ordem.start();
		// } else if (ordem.getTipoDaOrdem().equals("venda")) {
		// 	Transacao.adicionaVenda(ordem);
		// 	ordem.getReferenciaCliente().notificar("Ordem de venda registrada!");
		// 	ordem.start();
        // }
        System.out.println(ordem.toString());
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
