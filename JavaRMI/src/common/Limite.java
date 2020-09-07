package common;


public class Limite extends Base {

    private static final long serialVersionUID = 1L;

    private String tipoDaAtualizacao; // inserir ou remover
    private String tipoDoLimite; // ganho ou perda
    private float valor; // em reais

    public String getTipoDaAtualizacao() {
        return tipoDaAtualizacao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getTipoDoLimite() {
        return tipoDoLimite;
    }

    public void setTipoDoLimite(String tipoDoLimite) {
        this.tipoDoLimite = tipoDoLimite;
    }

    public void setTipoDaAtualizacao(String tipoDaAtualizacao) {
        this.tipoDaAtualizacao = tipoDaAtualizacao;
    }

}
