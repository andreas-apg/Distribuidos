package br.edu.webserver.javawebserver.models;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

// Classe responsavel por armazenar os dados do usuario
// Usuario possui nome, carteira, lista de interesse e lista de limite perda/ganho
public class Usuario {

    private String nomeDeUsuario;
    private Carteira carteira;
    private Set<String> listaDeInteresse;
    private Map<String, Cotacao> mapaDeLimiteDeGanho;
    private Map<String, Cotacao> mapaDeLimiteDePerda;
    private Vector<String> filaDeMensagens;

    public Usuario(String nome) {
        carteira = new Carteira();
        listaDeInteresse = new HashSet<String>();
        mapaDeLimiteDeGanho = new Hashtable<String, Cotacao>();
        mapaDeLimiteDePerda = new Hashtable<String, Cotacao>();
        filaDeMensagens = new Vector<String>();

        this.setNome(nome);
        
        this.mapaDeLimiteDeGanho.put("AZUL4", new Cotacao("AZUL4", (float) 70));
        this.mapaDeLimiteDeGanho.put("PETR4", new Cotacao("PETR4", (float) 70));
        //this.mapaDeLimiteDePerda.put("AZUL4", new Cotacao("AZUL4", (float) 10));
    }

    public Map<String, Cotacao> getMapaDeLimiteDeGanho(){
        return mapaDeLimiteDeGanho;
    }

    public Map<String, Cotacao> getMapaDeLimiteDePerda(){
        return mapaDeLimiteDePerda;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public Set<String> getListaDeInteresse() {
        return listaDeInteresse;
    }


    public String getNome() {
        return nomeDeUsuario;
    }

    public void setNome(String nome) {
        this.nomeDeUsuario = nome;
    }

    public Vector<String> getFilaDeMensagens() {
        return filaDeMensagens;
    }

}
