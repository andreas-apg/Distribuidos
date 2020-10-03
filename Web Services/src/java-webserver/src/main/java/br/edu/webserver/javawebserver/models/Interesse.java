package br.edu.webserver.javawebserver.models;

public class Interesse {

    private String nomeDeUsuario; // quem fez a ordem
    private String codigoDaAcao; // nome da ação ex PETR4
    private String tipoDaAtualizacao; // inserir ou remover

    public String getTipoDaAtualizacao() {
        return tipoDaAtualizacao;
    }

    public String getNomeDeUsuario() {
        return nomeDeUsuario;
    }

    public void setNomeDeUsuario(String nomeDeUsuario) {
        this.nomeDeUsuario = nomeDeUsuario;
    }

    public String getCodigoDaAcao() {
        return codigoDaAcao;
    }

    public void setCodigoDaAcao(String codigoDaAcao) {
        this.codigoDaAcao = codigoDaAcao;
    }

    public void setTipoDaAtualizacao(String tipoDaAtualizacao) {
        this.tipoDaAtualizacao = tipoDaAtualizacao;
    }    
    
}