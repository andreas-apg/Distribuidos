package servidor;

// Usuario possui nome, carteira, lista de interesse e lista de limite perda/ganho
public class Usuario {

    private String nome;

    private Carteira carteira;

    public Carteira getCarteira() {
		return carteira;
	}

	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario(String nome) {
        carteira = new Carteira();
        this.setNome(nome);
    }
        
}
