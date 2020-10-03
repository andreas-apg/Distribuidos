package br.edu.webserver.javawebserver.models;

public class Limite {

    private String nomeDeUsuario; // quem fez a ordem
    private String codigoDaAcao; // nome da ação ex PETR4
    private String tipoDaAtualizacao; // inserir ou remover
    private String tipoDoLimite; // ganho ou perda
    private float valor; // em reais

    public String getTipoDoAtualizacao() {
        return tipoDaAtualizacao;
    }

    public String getCodigoDaAcao() {
        return codigoDaAcao;
    }

    public void setCodigoDaAcao(String codigoDaAcao) {
        this.codigoDaAcao = codigoDaAcao;
    }

    public String getNomeDeUsuario() {
        return nomeDeUsuario;
    }

    public void setNomeDeUsuario(String nomeDeUsuario) {
        this.nomeDeUsuario = nomeDeUsuario;
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
