package br.edu.webserver.javawebserver.services.broker;

import java.util.HashMap;
import java.util.Map;

import br.edu.webserver.javawebserver.models.Cotacao;
import br.edu.webserver.javawebserver.models.Limite;
import br.edu.webserver.javawebserver.models.Usuario;

// Classe do servidor responsavel por gerenciar os limites
// do usuario, como fazer operacoes de insercao e remocao
public class GerenciadorDeLimites {

    private Map<String, Usuario> mapaDeUsuarios;
    private GerenciadorDeCotacoes gerenciadorDeCotacoes;

    public GerenciadorDeLimites(Map<String, Usuario> mapaDeUsuarios,
            GerenciadorDeCotacoes gerenciadorDeCotacoes) {
        this.mapaDeUsuarios = mapaDeUsuarios;
        this.gerenciadorDeCotacoes = gerenciadorDeCotacoes;
    }

    // Verifica o tipo da atualizacao e chama os metodos correspondentes
    // Insercao ou remocao
    public void atualizarListaDeLimite(Limite limite) throws Exception {

        Usuario usuario = mapaDeUsuarios.get(limite.getNomeDeUsuario());
        System.out.println("Servidor: atualizando lista de limite do usuario" + usuario.getNome());

        String tipoDoAtualizacao = limite.getTipoDoAtualizacao();
        String result;

        switch (tipoDoAtualizacao) {
            case "inserir":
                result = inserirNaListaDeLimite(limite);
                break;

            case "remover":
                result = removerDaListaDeLimite(limite);
                break;
            default:
                result = "Tipo de atualizacao invalida: " + tipoDoAtualizacao;
                break;
        }
            
        usuario.getFilaDeMensagens().add(result);

    }

    // Inseri na lista de limite e notifica o cliente
    public String inserirNaListaDeLimite(Limite limite) throws Exception {

        // obtem o mapa de limite conforme o usuario e o tipo do limite presente no objeto Limite
        Map<String, Cotacao> mapaDeLimite = obterMapaDeLimiteParaAtualizacao(limite);

        String codigoDaAcao = limite.getCodigoDaAcao();
        String msg;

        try {
            // throws exception se a cotacao nao existe
            gerenciadorDeCotacoes.obterCotacao(codigoDaAcao);

            Cotacao cotacao = new Cotacao(limite.getCodigoDaAcao(), limite.getValor());

            // TODO: 
            if (!mapaDeLimite.containsKey(codigoDaAcao)) {
                mapaDeLimite.put(codigoDaAcao, cotacao);
                msg = "Adicionado " + codigoDaAcao + " a lista de limite";
            } 
            else {
                
                msg = "Cotacao ja estava na lista de limite: " + codigoDaAcao;
            }
            
        } 
        catch (IllegalArgumentException e) {
            msg = "Erro ao adicionar acao na lista de interesse, cotacao inexistente: ";
            msg = msg + codigoDaAcao;
        } 

        return msg;
    }


    // Remove da lista de limite e notifica o cliente
    public String removerDaListaDeLimite(Limite limite) throws Exception {
        
        Map<String, Cotacao> mapaDeLimite = obterMapaDeLimiteParaAtualizacao(limite);

        String codigoDaAcao = limite.getCodigoDaAcao();
        Cotacao result = mapaDeLimite.remove(codigoDaAcao);
        String msg;

        // verifica se a remocao foi bem sucedida
        if (result != null) {
            msg = "Acao removida da lista de limite: " + codigoDaAcao;
        } else {
            msg = "Erro ao remover acao da lista de limite, cotacao inexistente na lista: "
                    + codigoDaAcao;
        }

        return msg;
    }

    // Analisa o tipo de limite a ser atualizado e fornece o mapa de ganho ou perda
    // do usuario, conforme o usuario e limite para o metodo de atualizacao
    private Map<String, Cotacao> obterMapaDeLimiteParaAtualizacao(Limite limite) throws Exception {
        
        Usuario usuario = mapaDeUsuarios.get(limite.getNomeDeUsuario());
        String tipoDoLimite = limite.getTipoDoLimite();

        Map<String, Cotacao> mapaDeLimite;

        switch (tipoDoLimite) {
            case "ganho":
                mapaDeLimite = usuario.getMapaDeLimiteDeGanho();
                break;
            case "perda":
                mapaDeLimite = usuario.getMapaDeLimiteDePerda();
                break;
            default:
                String msg = "Tipo de limite invalido: " + tipoDoLimite;
                throw new Exception(msg);
        }
        return mapaDeLimite;
    }

    // Fornece a lista de limite como string para ser usado em uma notificao ao usuario
    public Map<String, Map<String, Cotacao>> obterListaDeLimite(String referenciaCliente) {
        
        // recupera usuario pelo nome
        Usuario usuario = mapaDeUsuarios.get(referenciaCliente);
        
        System.out.println("Servidor: Enviando lista de limite do usuario" + usuario.getNome());
        
        // recupera mapas de limite do usuario
        Map<String, Cotacao> mapaDeLimiteDeGanho = usuario.getMapaDeLimiteDeGanho();
        Map<String, Cotacao> mapaDeLimiteDePerda = usuario.getMapaDeLimiteDePerda();

        // gera um unico mapa para os limites de ganho e perda
        Map<String, Map<String, Cotacao>> mapaDeLimites = new HashMap<String, Map<String, Cotacao>>();
        mapaDeLimites.put("limitesDeGanho", mapaDeLimiteDeGanho);
        mapaDeLimites.put("limitesDePerda", mapaDeLimiteDePerda);

        return mapaDeLimites;
    }
}