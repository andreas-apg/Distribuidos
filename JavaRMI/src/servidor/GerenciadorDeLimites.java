package servidor;

import java.rmi.RemoteException;
import java.util.Map;

import common.Limite;
import interfaces.InterfaceCli;

public class GerenciadorDeLimites {

    private Map<InterfaceCli, Usuario> mapaDeUsuarios;
    private GerenciadorDeCotacoes gerenciadorDeCotacoes;

    public GerenciadorDeLimites(Map<InterfaceCli, Usuario> mapaDeUsuarios,
            GerenciadorDeCotacoes gerenciadorDeCotacoes) {
        this.mapaDeUsuarios = mapaDeUsuarios;
        this.gerenciadorDeCotacoes = gerenciadorDeCotacoes;
    }

    public void atualizarListaDeLimite(Limite limite) throws Exception {

        

        InterfaceCli referenciaCliente = limite.getReferenciaCliente();
        Usuario usuario = mapaDeUsuarios.get(referenciaCliente);
        
        System.out.println("Atualizando lista de limite para: " + usuario.getNome());

        // Set<String> listaDeInteresse = usuario.getListaDeInteresse();
        // if (interesse.getTipoDaAtualizacao().equals("inserir")){

        // } else if (interesse.getTipoDaAtualizacao().equals("remover")) {

        // }

        // switch (interesse.getTipoDaAtualizacao()) {
        //     case "inserir":
                
        //         Cotacao cotacao;
        //         try {
        //             // throws exception se a cotacao nao existe
        //             cotacao = gerenciadorDeCotacoes.obterCotacao(interesse.getCodigoDaAcao());
                    
        //             if (!listaDeInteresse.contains(cotacao.getCodigoDaAcao())) {
        //                 listaDeInteresse.add(cotacao.getCodigoDaAcao());
        //                 referenciaCliente.notificar("Adicionado " + cotacao.getCodigoDaAcao() +" a lista de interesse");
        //             } else {
        //                 referenciaCliente.notificar("Cotacao ja estava na lista de interesse: " + cotacao.getCodigoDaAcao());
        //             }

                    
                    
        //             referenciaCliente.notificar("Valor atual eh: " + cotacao.getValor());

        //         } catch (IllegalArgumentException e) {
        //             String msg = "Erro ao adicionar acao na lista de interesse, cotacao inexistente: ";
        //             msg = msg + interesse.getCodigoDaAcao();
        //             referenciaCliente.notificar(msg);
        //         }
        //         break;
            
        //     case "remover":
        //             boolean result = listaDeInteresse.remove(interesse.getCodigoDaAcao());
        //             if (result == true) {
        //                 String msg = "Acao removida da lista de interesse: " + interesse.getCodigoDaAcao();
        //                 referenciaCliente.notificar(msg);
        //             } else {
        //                 String msg = "Erro ao adicionar acao na lista de interesse, cotacao inexistente na lista: " + interesse.getCodigoDaAcao();
        //                 referenciaCliente.notificar(msg);
        //             }

        //         break;
        
        //     default:
        //         String msg = "Tipo de atualizacao invalida: " + interesse.getTipoDaAtualizacao();
        //         throw new Exception(msg);
        // }
        

    }

    public String obterListaDeLimitesComoString(InterfaceCli referenciaCliente) throws RemoteException {

        Usuario usuario = mapaDeUsuarios.get(referenciaCliente);
        System.out.println("Servidor: Imprimindo lista de limite do usuario" + usuario.getNome());

        Map<String, Cotacao> mapaDeLimiteDeGanho = usuario.getMapaDeLimiteDeGanho();
        Map<String, Cotacao> mapaDeLimiteDePerda = usuario.getMapaDeLimiteDePerda();
        
        StringBuilder msg = new StringBuilder();
        msg.append("Lista de Limite - (codigoDaAcao: valor):\n");
        gerarMsgDeLimite(mapaDeLimiteDeGanho, "ganho", msg);
        gerarMsgDeLimite(mapaDeLimiteDePerda, "perda", msg);

        return msg.toString();
    }

    private void gerarMsgDeLimite(Map<String, Cotacao> mapaDeLimite, String tipo, StringBuilder msg) {

		if (mapaDeLimite.size() == 0) {
             msg.append("Lista de limite de " + tipo + " vazia\n");		
        }
            
		else {

            msg.append("Lista de limite de" + tipo + " : ");
			
            String separador = "";

            for (Map.Entry<String, Cotacao> entry : mapaDeLimite.entrySet()) {
                String codigoDaAcao = entry.getKey();
                float valor = entry.getValue().getValor();
                msg.append(separador);
                separador = ", ";
				msg.append(codigoDaAcao + ": " + valor);
            }
			
        }
        msg.append("\n");
    }
    
    
}