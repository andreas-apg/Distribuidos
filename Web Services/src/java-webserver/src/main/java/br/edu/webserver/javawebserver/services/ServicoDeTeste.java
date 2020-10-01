package br.edu.webserver.javawebserver.services;

import org.springframework.stereotype.Service;

// Classe para testar funcionamento de servicos e
// Dependency Injection
@Service
public class ServicoDeTeste {
    
    public ServicoDeTeste(){
        System.out.println("Servico de teste criado");
    }

    public void run(){
        System.out.println("Servico de teste executando");
    }
}
