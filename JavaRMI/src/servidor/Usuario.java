package servidor;

// Usuario possui nome, carteira, lista de interesse e lista de limite perda/ganho
public class Usuario {

    private String nome;

    public Carteira carteira;

    public Usuario() {
        carteira = new Carteira();

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario(String nome) {
        super();
        this.setNome(nome);
    }
        
}
