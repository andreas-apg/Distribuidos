package servidor;

import java.util.HashSet;
import java.util.Set;

import interfaces.InterfaceCli;

// Usuario possui nome, carteira, lista de interesse e lista de limite perda/ganho
public class Usuario {

    private String nome;

    private Carteira carteira;
    private Set<String> listaDeInteresse;
    private InterfaceCli referenciaCliente;


    public Usuario(String nome, InterfaceCli referenciaCliente) {
        carteira = new Carteira();
        listaDeInteresse = new HashSet<String>();

        this.setNome(nome);
        this.referenciaCliente = referenciaCliente;

        // Para fins de teste, adicionamos 1 acao na lista de interesses
        this.listaDeInteresse.add("AZUL4");      
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public Set<String> getListaDeInteresse() {
        return listaDeInteresse;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public InterfaceCli getReferenciaCliente(){
        return referenciaCliente;
    }

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
