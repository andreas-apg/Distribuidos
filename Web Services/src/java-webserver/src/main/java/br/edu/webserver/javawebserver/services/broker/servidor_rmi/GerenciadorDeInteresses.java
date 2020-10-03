// package servidor;

// import java.rmi.RemoteException;
// import java.util.Map;
// import java.util.Set;

// import common.Interesse;
// import interfaces.InterfaceCli;

// // Classe do servidor responsavel por gerenciar os interesses do usuario,
// // como atualizar e imprimir
// public class GerenciadorDeInteresses {

//     private Map<InterfaceCli, Usuario> mapaDeUsuarios;
//     private GerenciadorDeCotacoes gerenciadorDeCotacoes;

//     public GerenciadorDeInteresses(Map<InterfaceCli, Usuario> mapaDeUsuarios,
//             GerenciadorDeCotacoes gerenciadorDeCotacoes) {
//         this.mapaDeUsuarios = mapaDeUsuarios;
//         this.gerenciadorDeCotacoes = gerenciadorDeCotacoes;
//     }

//     // Inseri ou remove um interesse na list de usuario, conforme o objeto Interesse
//     public void atualizarListaDeInteresse(Interesse interesse) throws Exception {

//         InterfaceCli referenciaCliente = interesse.getReferenciaCliente();
//         Usuario usuario = mapaDeUsuarios.get(referenciaCliente);
        
//         System.out.println("Atualizando lista de Interesse para: " + usuario.getNome());

//         Set<String> listaDeInteresse = usuario.getListaDeInteresse();
        

//         switch (interesse.getTipoDaAtualizacao()) {
//             case "inserir":
                
//                 Cotacao cotacao;
//                 try {
//                     // throws exception se a cotacao nao existe
//                     cotacao = gerenciadorDeCotacoes.obterCotacao(interesse.getCodigoDaAcao());
                    
//                     if (!listaDeInteresse.contains(cotacao.getCodigoDaAcao())) {
//                         listaDeInteresse.add(cotacao.getCodigoDaAcao());
//                         referenciaCliente.notificar("Adicionado " + cotacao.getCodigoDaAcao() +" a lista de interesse");
//                     } else {
//                         referenciaCliente.notificar("Cotacao ja estava na lista de interesse: " + cotacao.getCodigoDaAcao());
//                     }

                    
                    
//                     referenciaCliente.notificar("Valor atual eh: " + cotacao.getValor());

//                 } catch (IllegalArgumentException e) {
//                     String msg = "Erro ao adicionar acao na lista de interesse, cotacao inexistente: ";
//                     msg = msg + interesse.getCodigoDaAcao();
//                     referenciaCliente.notificar(msg);
//                 }
//                 break;
            
//             case "remover":
//                     boolean result = listaDeInteresse.remove(interesse.getCodigoDaAcao());
//                     if (result == true) {
//                         String msg = "Acao removida da lista de interesse: " + interesse.getCodigoDaAcao();
//                         referenciaCliente.notificar(msg);
//                     } else {
//                         String msg = "Erro ao remover acao na lista de interesse, cotacao inexistente na lista: ";
//                         msg = msg + interesse.getCodigoDaAcao();
//                         referenciaCliente.notificar(msg);
//                     }

//                 break;
        
//             default:
//                 String msg = "Tipo de atualizacao invalida: " + interesse.getTipoDaAtualizacao();
//                 throw new Exception(msg);
//         }
        

//     }

//     // Pesquisa a lista de interesse do usuario e o valor atual da cotacao pra elas
//     // Por fim, notifica o usuario das cotacoes
//     public void obterCotacoesDaListaDeInteresse(InterfaceCli referenciaCliente) throws RemoteException {

//         Usuario usuario = mapaDeUsuarios.get(referenciaCliente);
//         Set<String> listaDeInteresse = usuario.getListaDeInteresse();

        
//         System.out.println("Imprimindo cotacoes de interesse para: " + usuario.getNome());
        
// 		if (listaDeInteresse.size() == 0) {
//             referenciaCliente.notificar("Lista de interesse vazia");
// 			return;
// 		}
// 		else {

//             StringBuilder msg = new StringBuilder();
			
//             String separador = "";

// 			for (String codigoDaAcao : listaDeInteresse) {
            
// 				msg.append(separador);
//                 separador = ", ";
//                 Cotacao cotacao = gerenciadorDeCotacoes.obterCotacao(codigoDaAcao);
// 				msg.append(cotacao.getCodigoDaAcao() + ": " + cotacao.getValor());
// 			}
			
//             referenciaCliente.notificar(msg.toString());
//         }
//     }
    
    
// }