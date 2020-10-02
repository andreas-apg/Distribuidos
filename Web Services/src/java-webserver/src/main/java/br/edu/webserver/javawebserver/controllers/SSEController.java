package br.edu.webserver.javawebserver.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import br.edu.webserver.javawebserver.services.ServicoDeTeste;

/**
 * https://dzone.com/articles/server-sent-events-using-spring
 * 
 */
@RestController
@RequestMapping("events/")
public class SSEController {

    // Service Dependency Injection
    @Autowired
	private ServicoDeTeste servicoDeTeste;

    // http://localhost:8080/events/emitter
    @GetMapping("/notificacao")
    public SseEmitter eventEmitter(@RequestParam String username) {
        SseEmitter emitter = new SseEmitter(); //12000 here is the timeout and it is optional   
    
        //create a single thread for sending messages asynchronously
        ExecutorService executor = Executors.newSingleThreadExecutor();
    
        executor.execute(() -> {
            try {
                emitter.send("Server: ola "+ username);
                for (int i = 0; i < 4; i++) {
                    Thread.sleep(4000);
                    emitter.send("Server: message" + i);
                }
            } catch(Exception e) {
                emitter.completeWithError(e);       
            } finally {
                emitter.complete();       
            }   
        });
    
       executor.shutdown();
    
       return emitter;
    }
    
    
}