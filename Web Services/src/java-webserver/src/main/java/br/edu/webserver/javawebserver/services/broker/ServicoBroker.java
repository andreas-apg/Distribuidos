package br.edu.webserver.javawebserver.services.broker;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.stereotype.Service;

import br.edu.webserver.javawebserver.models.Carteira;
import br.edu.webserver.javawebserver.models.Cotacao;
import br.edu.webserver.javawebserver.models.Interesse;
import br.edu.webserver.javawebserver.models.Limite;
import br.edu.webserver.javawebserver.models.Ordem;
import br.edu.webserver.javawebserver.models.Usuario;

@Service
public class ServicoBroker {

	private Map<String, Usuario> mapaDeUsuarios;
	private Transacao transacao;
	private GerenciadorDeCotacoes gerenciadorDeCotacoes;
	private GerenciadorDeInteresses gerenciadorDeInteresses;
	private GerenciadorDeLimites gerenciadorDeLimites;

	// Classe de inicio do servidor
	// Inicializa o servidor e o menu

	public ServicoBroker() {
		System.out.println("Servico Broker executando");
		mapaDeUsuarios = new Hashtable<String, Usuario>();
		transacao = new Transacao(mapaDeUsuarios);
		gerenciadorDeCotacoes = new GerenciadorDeCotacoes(mapaDeUsuarios);
		gerenciadorDeInteresses = new GerenciadorDeInteresses(mapaDeUsuarios, gerenciadorDeCotacoes);
		gerenciadorDeLimites = new GerenciadorDeLimites(mapaDeUsuarios,gerenciadorDeCotacoes);
	}

	public GerenciadorDeCotacoes getGerenciadorDeCotacoes() {
		return gerenciadorDeCotacoes;
	}

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

	public void atualizarListaDeInteresse(Interesse interesse) throws Exception {
		try {
			gerenciadorDeInteresses.atualizarListaDeInteresse(interesse);
		} catch (Exception e) {
			System.out.println("Servidor: erro ao atualizar lista de interesse");
			e.printStackTrace();
			throw e;
		}
	}

	public List<Cotacao> obterCotacoesDaListaDeInteresse(String referenciaCliente) {
		return gerenciadorDeInteresses.obterCotacoesDaListaDeInteresse(referenciaCliente);
	}

	public Carteira obterCarteira(String nomeDeUsuario) {
		Usuario usuario = mapaDeUsuarios.get(nomeDeUsuario);
		Carteira carteira = usuario.getCarteira();
		String msg = "Servidor: imprimindo carteira do usuario: " + usuario.getNome();
		System.out.println(msg);
		return carteira;
		
	}

	// Retorna um mapa com os limites de perda e ganho do usuario
	public Map<String, Map<String, Cotacao>> obterListaDeLimite(String referenciaCliente) {
		Map<String, Map<String, Cotacao>> limites = gerenciadorDeLimites.obterListaDeLimite(referenciaCliente);
		return limites;
	}

	public void atualizarListaDeLimite(Limite limite) throws Exception{
		try {
			gerenciadorDeLimites.atualizarListaDeLimite(limite);
		} catch (Exception e) {
			System.out.println("Erro ao atualizar lista de limite");
			throw e;
		}

	}

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
