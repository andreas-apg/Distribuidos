package br.edu.webserver.javawebserver.controllers;

import java.util.List;
import java.util.Map;

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

    // Endpoint to test if the server is working
    // http://localhost:8080/api/ping
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Hello World!");
    }

    @PostMapping(value = "/ordem", consumes = "application/json")
    public ResponseEntity<String> criarOrdem(@RequestBody Ordem newOrdem) {

        newOrdem.setCodigoDaAcao(newOrdem.getCodigoDaAcao().toUpperCase());

        ResponseEntity<String> response;
        try {
            broker.registrarOrdem(newOrdem);
            response = ResponseEntity.ok("Ordem registrada");
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body("Erro ao registrar Ordem");
        }
        return response;
    }

    //
    @GetMapping("/carteira")
    public Carteira carteira(@RequestParam String nomeDeUsuario) {
        Carteira carteira = broker.obterCarteira(nomeDeUsuario);
        return carteira;
    }

    // Retorna uma lista de acoes que o usuario tem interesse com as respectivas
    // cotacoes
    @GetMapping("/interesses")
    public List<Cotacao> obterInteresses(@RequestParam String nomeDeUsuario) {
        List<Cotacao> listaDeCotacoes = broker.obterCotacoesDaListaDeInteresse(nomeDeUsuario);
        return listaDeCotacoes;
    }

    // Adiciona um novo interesse do usuario na lista dele
    @PostMapping(value = "/interesses", consumes = "application/json")
    public ResponseEntity<String> atualizarInteresses(@RequestBody Interesse interesse) {

        interesse.setCodigoDaAcao(interesse.getCodigoDaAcao().toUpperCase());

        ResponseEntity<String> response;
        try {
            broker.atualizarListaDeInteresse(interesse);
            response = ResponseEntity.ok("Interesses atualizados");
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body("Erro ao atualizar interesses");
        }
        return response;
    }

    // Retorna uma os limites de perda e ganho do usuario
    @GetMapping("/limites")
    public Map<String, Map<String, Cotacao>> obterListaDeLimite(@RequestParam String nomeDeUsuario) {
        Map<String, Map<String, Cotacao>> listaDeLimites = broker.obterListaDeLimite(nomeDeUsuario);
        return listaDeLimites;
    }

    // Adiciona um novo interesse do usuario na lista dele
    @PostMapping(value = "/limites", consumes = "application/json")
    public ResponseEntity<String> atualizarListaDeLimite(@RequestBody Limite limite) {

        limite.setCodigoDaAcao(limite.getCodigoDaAcao().toUpperCase());

        ResponseEntity<String> response;
        try {
            broker.atualizarListaDeLimite(limite);
            response = ResponseEntity.ok("Limites atualizados");
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body("Erro ao atualizar limite");
        }
        return response;
    }
}