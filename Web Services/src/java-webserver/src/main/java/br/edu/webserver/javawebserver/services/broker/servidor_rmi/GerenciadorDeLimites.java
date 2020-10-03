// package servidor;

// import java.rmi.RemoteException;
// import java.util.Map;

// import common.Limite;
// import interfaces.InterfaceCli;

// // Classe do servidor responsavel por gerenciar os limites
// // do usuario, como fazer operacoes de insercao e remocao
// public class GerenciadorDeLimites {

//     private Map<InterfaceCli, Usuario> mapaDeUsuarios;
//     private GerenciadorDeCotacoes gerenciadorDeCotacoes;

//     public GerenciadorDeLimites(Map<InterfaceCli, Usuario> mapaDeUsuarios,
//             GerenciadorDeCotacoes gerenciadorDeCotacoes) {
//         this.mapaDeUsuarios = mapaDeUsuarios;
//         this.gerenciadorDeCotacoes = gerenciadorDeCotacoes;
//     }

//     // Verifica o tipo da atualizacao e chama os metodos correspondentes
//     // Insercao ou remocao
//     public void atualizarListaDeLimite(Limite limite) throws Exception {

//         Usuario usuario = mapaDeUsuarios.get(limite.getReferenciaCliente());
//         System.out.println("Servidor: atualizando lista de limite do usuario" + usuario.getNome());

//         String tipoDoAtualizacao = limite.getTipoDoAtualizacao();

//         switch (tipoDoAtualizacao) {
//             case "inserir":
//                 inserirNaListaDeLimite(limite);
//                 break;

//             case "remover":
//                 removerDaListaDeLimite(limite);
//                 break;
//             default:
//                 String msg = "Tipo de atualizacao invalida: " + tipoDoAtualizacao;
//                 limite.getReferenciaCliente().notificar(msg);
//                 break;
//         }

//     }

//     // Inseri na lista de limite e notifica o cliente
//     public void inserirNaListaDeLimite(Limite limite) throws Exception {

//         Map<String, Cotacao> mapaDeLimite = obterMapaDeLimiteParaAtualizacao(limite);

//         InterfaceCli referenciaCliente = limite.getReferenciaCliente();
//         String codigoDaAcao = limite.getCodigoDaAcao();

//         try {
//             // throws exception se a cotacao nao existe
//             gerenciadorDeCotacoes.obterCotacao(codigoDaAcao);

//             Cotacao cotacao = new Cotacao(limite.getCodigoDaAcao(), limite.getValor());

//             if (!mapaDeLimite.containsKey(codigoDaAcao)) {
//                 mapaDeLimite.put(codigoDaAcao, cotacao);
//                 referenciaCliente.notificar("Adicionado " + codigoDaAcao + " a lista de limite");
//             } else {
//                 referenciaCliente.notificar("Cotacao ja estava na lista de limite: " + codigoDaAcao);
//             }

//         } catch (IllegalArgumentException e) {
//             String msg = "Erro ao adicionar acao na lista de interesse, cotacao inexistente: ";
//             msg = msg + codigoDaAcao;
//             referenciaCliente.notificar(msg);
//         }
//     }


//     // Remove da lista de limite e notifica o cliente
//     public void removerDaListaDeLimite(Limite limite) throws Exception {
        
//         Map<String, Cotacao> mapaDeLimite = obterMapaDeLimiteParaAtualizacao(limite);

//         String codigoDaAcao = limite.getCodigoDaAcao();
//         InterfaceCli referenciaCliente = limite.getReferenciaCliente();

//         Cotacao result = mapaDeLimite.remove(codigoDaAcao);

//         if (result != null) {
//             String msg = "Acao removida da lista de limite: " + codigoDaAcao;
//             referenciaCliente.notificar(msg);
//         } else {
//             String msg = "Erro ao remover acao da lista de limite, cotacao inexistente na lista: "
//                     + codigoDaAcao;
//             referenciaCliente.notificar(msg);
//         }

//     }

//     // Analisa o tipo de limite a ser atualizado e fornece o mapa de ganho ou perda
//     // do usuario, conforme o limite para o metodo de atualizacao
//     private Map<String, Cotacao> obterMapaDeLimiteParaAtualizacao(Limite limite) throws Exception {
//         Usuario usuario = mapaDeUsuarios.get(limite.getReferenciaCliente());
//         String tipoDoLimite = limite.getTipoDoLimite();

//         Map<String, Cotacao> mapaDeLimite;

//         switch (tipoDoLimite) {
//             case "ganho":
//                 mapaDeLimite = usuario.getMapaDeLimiteDeGanho();
//                 break;
//             case "perda":
//                 mapaDeLimite = usuario.getMapaDeLimiteDePerda();
//                 break;
//             default:
//                 String msg = "Tipo de limite invalido: " + tipoDoLimite;
//                 throw new Exception(msg);
//         }
//         return mapaDeLimite;
//     }

//     // Fornece a lista de limite como string para ser usado em uma notificao ao usuario
//     public String obterListasDeLimiteComoString(InterfaceCli referenciaCliente) throws RemoteException {

//         Usuario usuario = mapaDeUsuarios.get(referenciaCliente);
//         System.out.println("Servidor: Imprimindo lista de limite do usuario" + usuario.getNome());

//         Map<String, Cotacao> mapaDeLimiteDeGanho = usuario.getMapaDeLimiteDeGanho();
//         Map<String, Cotacao> mapaDeLimiteDePerda = usuario.getMapaDeLimiteDePerda();

//         StringBuilder msg = new StringBuilder();
//         msg.append("Lista de Limite - (codigoDaAcao: valor):\n");
//         gerarMsgDeLimite(mapaDeLimiteDeGanho, "ganho", msg);
//         gerarMsgDeLimite(mapaDeLimiteDePerda, "perda", msg);

//         return msg.toString();
//     }

//     // Gera a lista de limite para impressao no usuario
//     private void gerarMsgDeLimite(Map<String, Cotacao> mapaDeLimite, String tipo, StringBuilder msg) {

//         if (mapaDeLimite.size() == 0) {
//             msg.append("Lista de limite de " + tipo + " vazia\n");
//         }

//         else {

//             msg.append("Lista de limite de " + tipo + " : ");

//             String separador = "";

//             for (Map.Entry<String, Cotacao> entry : mapaDeLimite.entrySet()) {
//                 String codigoDaAcao = entry.getKey();
//                 float valor = entry.getValue().getValor();
//                 msg.append(separador);
//                 separador = ", ";
//                 msg.append(codigoDaAcao + ": " + valor);
//             }

//         }
//         msg.append("\n");
//     }

// }