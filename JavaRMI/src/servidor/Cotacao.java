package servidor;

public class Cotacao {

    private String codigoDaAcao;
    private Float valor;

    public Cotacao(String codigoDaAcao, Float valor) {
        this.setCodigoDaAcao(codigoDaAcao);
        this.setValor(valor);
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {        

        if (valor <0 ){
            String msg = "Erro: valor da acao eh invalido: ";
            throw new IllegalArgumentException(msg + valor);
        }
        this.valor = valor;
    }

    public String getCodigoDaAcao() {
        return codigoDaAcao;
    }

    public void setCodigoDaAcao(String codigoDaAcao) {
        
        if (!(codigoDaAcao.length() == 5)){
            String msg = "Erro: codigo de acao invalido";
            throw new IllegalArgumentException(msg + codigoDaAcao);
        }

        this.codigoDaAcao = codigoDaAcao;
    }

}
