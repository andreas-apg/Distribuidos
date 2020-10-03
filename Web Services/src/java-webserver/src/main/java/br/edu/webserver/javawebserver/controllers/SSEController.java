package br.edu.webserver.javawebserver.controllers;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import br.edu.webserver.javawebserver.services.broker.ServicoBroker;


/**
 * https://dzone.com/articles/server-sent-events-using-spring
 * 
 */
@RestController
@RequestMapping("events/")
public class SSEController {

    // Service Dependency Injection
    @Autowired
	private ServicoBroker broker;

    // http://localhost:8080/events/notificacao
    @GetMapping("/notificacao")
    public SseEmitter eventEmitter(@RequestParam String nomeDeUsuario) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); //12000 here is the timeout and it is optional   
    

        // registra o novo cliente na lista de clientes do broker
        broker.registrarNovoCliente(nomeDeUsuario);

        //create a single thread for sending messages asynchronously
        ExecutorService executor = Executors.newSingleThreadExecutor();
    
        executor.execute(() -> {
            try {
                emitter.send("Server: ola "+ nomeDeUsuario);

                
                            
                while(true){
                    // recupera msgs do usuario
                    Vector<String> msgs = broker.getMensagensDoUsuario(nomeDeUsuario);
                    
                    // envia as msgs ate a fila ficar vazia
                    while (!msgs.isEmpty()){
                        String msgAtual = msgs.remove(0);
                        emitter.send("Server: " + msgAtual);
                    }                    

                    //emitter.send("");
                }
            } catch(Exception e) {
                emitter.completeWithError(e);       
            } finally {
                broker.registrarSaidaDeCliente(nomeDeUsuario);
                emitter.complete();       
            }   
        });
    
       executor.shutdown();
    
       return emitter;
    }
    
    
}