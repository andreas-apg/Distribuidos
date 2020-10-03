package br.edu.webserver.javawebserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.webserver.javawebserver.models.*;
import br.edu.webserver.javawebserver.services.broker.ServicoBroker;
import br.edu.webserver.javawebserver.services.ServicoDeTeste;

/**
 * ExampleController
 * 
 */
@RestController
@RequestMapping("api/")
public class ExampleController {

    // Service Dependency Injection
    @Autowired
	private ServicoBroker broker;

    // POST endpoint at http://localhost:8080/api/ordem
	@PostMapping(value = "/ordem", consumes = "application/json")
	public ResponseEntity<String> criarOrdem(@RequestBody Ordem newOrdem) {
        
        // Imprime o JSON recebido no POST como Java Object
        System.out.println(newOrdem.toString());    

        // Executa um servico para saber que ta funcionando
        broker.registrarOrdem(newOrdem);

        // Retorna uma resposta 200 OK com String no corpo
        return ResponseEntity.ok("Hello World!");
    }
    

    @GetMapping("/members")
    public ResponseEntity<String> all() {
        return ResponseEntity.ok("Hello World!");
    }
}