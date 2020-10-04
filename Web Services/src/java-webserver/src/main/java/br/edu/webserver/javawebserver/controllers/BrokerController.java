package br.edu.webserver.javawebserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.webserver.javawebserver.models.*;
import br.edu.webserver.javawebserver.services.broker.ServicoBroker;

/**
 * 
 */
@RestController
@RequestMapping("api/")
public class BrokerController {

    // Service Dependency Injection
    @Autowired
    private ServicoBroker broker;
    

    // server_endpoints: dict = {
    //     "interesses": "api/interesses",
    //     "cotacoes": "api/cotacoes",
    //     "limites": "api/limites"
    // }

    // Endpoint to test if the server is working
    // http://localhost:8080/api/ping
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Hello World!");
    }

    
	@PostMapping(value = "/ordem", consumes = "application/json")
	public ResponseEntity<String> criarOrdem(@RequestBody Ordem newOrdem) {                

        newOrdem.setCodigoDaAcao(newOrdem.getCodigoDaAcao().toUpperCase());
        // Executa um servico para saber que ta funcionando
        broker.registrarOrdem(newOrdem);

        // Retorna uma resposta 200 OK com String no corpo
        return ResponseEntity.ok("Hello World!");
    }

    // 
    @GetMapping("/carteira")
    public Carteira carteira(@RequestParam String nomeDeUsuario) {
        Carteira carteira = broker.obterCarteira(nomeDeUsuario);
        return carteira;
    }

    // Retorna uma lista de acoes que o usuario tem interesse com as respectivas cotacoes
    @GetMapping("/interesses")
    public List<Cotacao> obterInteresses(@RequestParam String nomeDeUsuario) {
        List<Cotacao> listaDeCotacoes = broker.obterCotacoesDaListaDeInteresse(nomeDeUsuario);
        return listaDeCotacoes;
    }

    // Adiciona um novo interesse do usuario na lista dele
    @PostMapping(value = "/interesses", consumes = "application/json")
	public ResponseEntity<String> atualizarInteresses(@RequestBody Interesse interesse) {                

        interesse.setCodigoDaAcao(interesse.getCodigoDaAcao().toUpperCase());
        broker.atualizarListaDeInteresse(interesse);
        return ResponseEntity.ok("Interesses atualizados");
    }

}