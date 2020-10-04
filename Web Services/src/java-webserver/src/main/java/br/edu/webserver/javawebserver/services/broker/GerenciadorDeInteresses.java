package br.edu.webserver.javawebserver.services.broker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.edu.webserver.javawebserver.models.Cotacao;
import br.edu.webserver.javawebserver.models.Interesse;
import br.edu.webserver.javawebserver.models.Usuario;


// Classe do servidor responsavel por gerenciar os interesses do usuario,
// como atualizar e imprimir
public class GerenciadorDeInteresses {

    private Map<String, Usuario> mapaDeUsuarios;
    private GerenciadorDeCotacoes gerenciadorDeCotacoes;

    public GerenciadorDeInteresses(Map<String, Usuario> mapaDeUsuarios,
            GerenciadorDeCotacoes gerenciadorDeCotacoes) {
        this.mapaDeUsuarios = mapaDeUsuarios;
        this.gerenciadorDeCotacoes = gerenciadorDeCotacoes;
    }

    // Inseri ou remove um interesse na list de usuario, conforme o objeto Interesse
    public void atualizarListaDeInteresse(Interesse interesse) throws Exception {

        String referenciaCliente = interesse.getNomeDeUsuario();
        Usuario usuario = mapaDeUsuarios.get(referenciaCliente);
        
        System.out.println("Atualizando lista de Interesse para: " + usuario.getNome());

        Set<String> listaDeInteresse = usuario.getListaDeInteresse();
        String msg; // para guardar msg a ser enviada para o usuario

        switch (interesse.getTipoDaAtualizacao()) {
            
            case "inserir":
                //Cotacao cotacao;
                try {
                    // testa primeiro se a cotacao existe e throws exception se nao
                    Cotacao cotacao = gerenciadorDeCotacoes.obterCotacao(interesse.getCodigoDaAcao());
                    
                    // Add cotacao se for nova e cria msg pro usuario
                    if (!listaDeInteresse.contains(cotacao.getCodigoDaAcao())) {
                        listaDeInteresse.add(cotacao.getCodigoDaAcao());
                        msg = "Adicionado " + cotacao.getCodigoDaAcao() +" a lista de interesse";
                        
                    } else {
                        msg = "Cotacao ja estava na lista de interesse: " + cotacao.getCodigoDaAcao();
                    }
                    usuario.getFilaDeMensagens().add(msg);
                    usuario.getFilaDeMensagens().add("Valor atual eh: " + cotacao.getValor());
                    
                } catch (IllegalArgumentException e) {
                    msg = "Erro ao adicionar acao na lista de interesse, cotacao inexistente: ";
                    msg = msg + interesse.getCodigoDaAcao();
                    usuario.getFilaDeMensagens().add(msg);
                }
                break;
            
            case "remover":
                
                boolean result = listaDeInteresse.remove(interesse.getCodigoDaAcao());
                if (result == true) {
                    msg = "Acao removida da lista de interesse: " + interesse.getCodigoDaAcao();
                } else {
                    msg = "Erro ao remover acao na lista de interesse, cotacao inexistente na lista: ";
                    msg = msg + interesse.getCodigoDaAcao();
                }
                usuario.getFilaDeMensagens().add(msg);
                break;
        
            default:
                msg = "Tipo de atualizacao invalida: " + interesse.getTipoDaAtualizacao();
                throw new Exception(msg);
        }
        
    }

    // Pesquisa a lista de interesse do usuario e o valor atual da cotacao pra elas
    // Por fim, notifica o usuario das cotacoes
    public List<Cotacao> obterCotacoesDaListaDeInteresse(String referenciaCliente) {

        Usuario usuario = mapaDeUsuarios.get(referenciaCliente);
        Set<String> listaDeInteresse = usuario.getListaDeInteresse();
        List<Cotacao> listaDeCotacoes = new ArrayList<Cotacao>();
        
        System.out.println("Enviando cotacoes de interesse para: " + usuario.getNome());
                
        // Para cada acao na lista de interesse, busca o valor e adiciona a lista de cotacoes
        for (String codigoDaAcao : listaDeInteresse) {                           
            Cotacao cotacao = gerenciadorDeCotacoes.obterCotacao(codigoDaAcao);
            listaDeCotacoes.add(cotacao);
        }

        return listaDeCotacoes;

		// if (listaDeInteresse.size() == 0) {
        //     referenciaCliente.notificar("Lista de interesse vazia");
		// 	return;
		// }
		// else {

        //     StringBuilder msg = new StringBuilder();
			
        //     String separador = "";

		// 	for (String codigoDaAcao : listaDeInteresse) {
            
		// 		msg.append(separador);
        //         separador = ", ";
        //         Cotacao cotacao = gerenciadorDeCotacoes.obterCotacao(codigoDaAcao);
		// 		msg.append(cotacao.getCodigoDaAcao() + ": " + cotacao.getValor());
		// 	}
			
        //     referenciaCliente.notificar(msg.toString());
        // }
    }
    
    
}