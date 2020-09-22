package common;



public class Interesse extends Base  {

    private static final long serialVersionUID = 1L;

    private String tipoDaAtualizacao; // inserir ou remover

    public String getTipoDaAtualizacao() {
        return tipoDaAtualizacao;
    }

    public void setTipoDaAtualizacao(String tipoDaAtualizacao) {
        this.tipoDaAtualizacao = tipoDaAtualizacao;
    }    
    
}