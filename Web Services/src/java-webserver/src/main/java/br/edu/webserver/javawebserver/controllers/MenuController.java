package br.edu.webserver.javawebserver.controllers;

import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.webserver.javawebserver.services.broker.ServicoBroker;
import br.edu.webserver.javawebserver.services.broker.Transacao;

@RestController
@RequestMapping("api/")
public class MenuController {
    // Service Dependency Injection
    // @Autowired
	// private ServicoBroker broker;

    // // POST endpoint at http://localhost:8080/api/ordem
	// @PostMapping(value = "/ordem", consumes = "application/json")
	// public ResponseEntity<String> criarOrdem(@RequestBody Ordem newOrdem) {                

    //     newOrdem.setCodigoDaAcao(newOrdem.getCodigoDaAcao().toUpperCase());
    //     // Executa um servico para saber que ta funcionando
    //     broker.registrarOrdem(newOrdem);

    //     // Retorna uma resposta 200 OK com String no corpo
    //     return ResponseEntity.ok("Hello World!");
    // }
    

    @GetMapping("/transacoes")
    public Vector<Transacao> transacoes() {
        
        Vector<Transacao> filaDeTransacoes = Transacao.getFilaDeTransacoes();
        
        // if(filaDeTransacoes.isEmpty()){
        //     return new Transacao();
        // }    

        return filaDeTransacoes;
    }
}
