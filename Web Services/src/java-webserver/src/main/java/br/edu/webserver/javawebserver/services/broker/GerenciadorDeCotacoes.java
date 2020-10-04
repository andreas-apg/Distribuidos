package br.edu.webserver.javawebserver.services.broker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.edu.webserver.javawebserver.models.Cotacao;
import br.edu.webserver.javawebserver.models.Usuario;


// Classe do Servidor para ser utilizada em testes
// Permite simular o mercado atualizando manualmente o preco das acoes,
// e tambem imprimir o preco atual, lista de transacoes realizadas e fila de
// compra e venda
public class GerenciadorDeCotacoes {

    private Map<String, Cotacao> mapaDeCotacoes;
    private Map<String, Usuario> mapaDeUsuarios;

    public GerenciadorDeCotacoes(Map<String, Usuario> mapaDeUsuarios) {
        mapaDeCotacoes = new HashMap<String, Cotacao>();
        this.mapaDeUsuarios = mapaDeUsuarios;

        // Para facilitar os testes, iniciamos o servidor com 3 cotacoes
        mapaDeCotacoes.put("AZUL4", new Cotacao("AZUL4", (float) 22.58));
        mapaDeCotacoes.put("VALE3", new Cotacao("VALE3", (float) 61.95));
        mapaDeCotacoes.put("PETR4", new Cotacao("PETR4", (float) 21.88));

    }

    // imprime o valor atual das cotacoes
    public void imprimirCotacoes() throws Exception {

        System.out.println("Imprimindo cotacoes...");

        if (mapaDeCotacoes.size() == 0) {
            System.out.println("Lista de cotacoes vazia");
            return;
        } else {
            StringBuilder nomes = new StringBuilder();
            String separador = "";

            Collection<Cotacao> listaDeCotacoes = mapaDeCotacoes.values();

            for (Cotacao cotacao : listaDeCotacoes) {

                nomes.append(separador);
                separador = ", ";
                nomes.append(cotacao.getCodigoDaAcao() + ": " + cotacao.getValor());
            }

            System.out.println(nomes);
        }

    }

    // Permite atualizar manualmente o valor de uma determinada acao
    public void atualizarCotacao(Cotacao novaCotacao) throws Exception {

        Cotacao cotacao = mapaDeCotacoes.get(novaCotacao.getCodigoDaAcao());

        if (cotacao == null) {
            mapaDeCotacoes.put(novaCotacao.getCodigoDaAcao(), novaCotacao);
        } else {
            cotacao.setValor(novaCotacao.getValor());
        }

        verificarLimitesDosUsuarios(novaCotacao);

    }

    // Retorna o valor atual de uma determinada acao
    public Cotacao obterCotacao(String codigoDaAcao) {
        Cotacao cotacao = mapaDeCotacoes.get(codigoDaAcao);

        if (cotacao == null) {
            String msg = "Erro: Cotacao nao existente para o codigo: " + codigoDaAcao;
            throw new IllegalArgumentException(msg);
        }

        return cotacao;
    }

    // Metodo usado apos cada atualizacao de preco para verificar
    // os limites de cada usuario. Se algum limite for excedido,
    // chama um metodo para notifica-lo
    private void verificarLimitesDosUsuarios(Cotacao novaCotacao) {
        String codigoDaAcao = novaCotacao.getCodigoDaAcao();
        Float novoValor = novaCotacao.getValor();

        Collection<Usuario> usuarios = mapaDeUsuarios.values();

        for (Usuario usuario : usuarios) {

            Cotacao cotacao = null;

            // Notificacao de Limite de Ganho
            cotacao = usuario.getMapaDeLimiteDeGanho().get(codigoDaAcao);
            if (cotacao != null) {
                if (novoValor >= cotacao.getValor()) {
                    notificarUsuario(codigoDaAcao, novoValor, usuario, cotacao, "ganho");
                }
            }

            // Notificacao de Limite de Perda
            cotacao = usuario.getMapaDeLimiteDePerda().get(codigoDaAcao);
            if (cotacao != null) {
                if (novoValor <= cotacao.getValor()) {
                    notificarUsuario(codigoDaAcao, novoValor, usuario, cotacao, "perda");
                }
            }

        }
    }

    // metodo utilizado pra notificar o usuario quando um dos seus limites foi excedido
    private void notificarUsuario(String codigoDaAcao, Float novoValor, Usuario usuario,
        Cotacao cotacao, String tipo) {

        String msg = "Limite de "+ tipo + " atingido para acao : ";
        msg = msg + codigoDaAcao + ": " + novoValor + " x " + cotacao.getValor();
            
        usuario.getFilaDeMensagens().add(msg);
        msg = "Servidor: usuario notificado de limite de " + tipo + ": " + usuario.getNome();
        System.out.println(msg);
    
    }
    

}
