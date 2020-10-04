package br.edu.webserver.javawebserver.controllers;

import java.util.Collection;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.webserver.javawebserver.models.Cotacao;
import br.edu.webserver.javawebserver.services.broker.ServicoBroker;
import br.edu.webserver.javawebserver.services.broker.Transacao;

@RestController
@RequestMapping("api/")
public class MenuController {
    // Service Dependency Injection
    @Autowired
    private ServicoBroker broker;

    // // POST endpoint at http://localhost:8080/api/ordem
    // @PostMapping(value = "/ordem", consumes = "application/json")
    // public ResponseEntity<String> criarOrdem(@RequestBody Ordem newOrdem) {

    // newOrdem.setCodigoDaAcao(newOrdem.getCodigoDaAcao().toUpperCase());
    // // Executa um servico para saber que ta funcionando
    // broker.registrarOrdem(newOrdem);

    // // Retorna uma resposta 200 OK com String no corpo
    // return ResponseEntity.ok("Hello World!");
    // }

    @GetMapping("/transacoes")
    public Vector<Transacao> transacoes() {

        Vector<Transacao> filaDeTransacoes = Transacao.getFilaDeTransacoes();
        return filaDeTransacoes;
    }

    @GetMapping("/cotacoes")
    public ResponseEntity<Collection<Cotacao>> cotacoes() {
        Collection<Cotacao> cotacoes = null;

        ResponseEntity<Collection<Cotacao>> response;
        try {
            cotacoes = broker.getGerenciadorDeCotacoes().getCotacoes();
            response = ResponseEntity.ok(cotacoes);
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(cotacoes);
        }
        return response; 
    }

   // Endpoint para adicionar ou atualizar uma cotação já existente
   @PostMapping(value = "/cotacoes", consumes = "application/json")
   public ResponseEntity<String> atualizarInteresses(@RequestBody Cotacao cotacao) {

       cotacao.setCodigoDaAcao(cotacao.getCodigoDaAcao().toUpperCase());

       ResponseEntity<String> response;
       try {
           broker.getGerenciadorDeCotacoes().atualizarCotacao(cotacao);;
           response = ResponseEntity.ok("Cotacoes atualizadas");
       } catch (Exception e) {
           response = ResponseEntity.badRequest().body("Erro ao atualizar cotacao");
       }
       return response;
   }
}
