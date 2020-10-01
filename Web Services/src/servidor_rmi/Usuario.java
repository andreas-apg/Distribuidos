package servidor;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import interfaces.InterfaceCli;

// Classe responsavel por armazenar os dados do usuario
// Usuario possui nome, carteira, lista de interesse e lista de limite perda/ganho
public class Usuario {

    private String nomeDeUsuario;

    private Carteira carteira;
    private Set<String> listaDeInteresse;
    private InterfaceCli referenciaCliente;
    private Map<String, Cotacao> mapaDeLimiteDeGanho;
    private Map<String, Cotacao> mapaDeLimiteDePerda;

    public Usuario(String nome, InterfaceCli referenciaCliente) {
        carteira = new Carteira();
        listaDeInteresse = new HashSet<String>();
        mapaDeLimiteDeGanho = new Hashtable<String, Cotacao>();
        mapaDeLimiteDePerda = new Hashtable<String, Cotacao>();

        this.setNome(nome);
        this.referenciaCliente = referenciaCliente;
        
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

    public InterfaceCli getReferenciaCliente(){
        return referenciaCliente;
    }

    // Dois usuarios sao iguais se possuem a mesma referenciaCliente
    @Override
    public boolean equals(Object o){
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Usuario)) { 
            return false; 
        } 
          
        Usuario user = (Usuario) o; 

        boolean usuariosIguais = user.getReferenciaCliente() == this.getReferenciaCliente();
          
        return (usuariosIguais);
    
    }
}
