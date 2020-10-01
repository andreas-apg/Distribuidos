package br.edu.webserver.javawebserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.webserver.javawebserver.models.*;

/**
 * ExampleController
 * 
 */
@RestController
@RequestMapping("api/")
public class ExampleController {

	@PostMapping(value = "/ordem", consumes = "application/json")
	public ResponseEntity<String> criarOrdem(@RequestBody Ordem newOrdem) {
        System.out.println(newOrdem.toString());        
        return ResponseEntity.ok("Hello World!");
    }
    

    // @GetMapping("/members")
    // public List<Member> all() {
    //    return memberService.getAllMembers();
    // }
}