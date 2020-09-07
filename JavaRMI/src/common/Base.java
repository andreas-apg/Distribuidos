package common;

import java.io.Serializable;

import interfaces.InterfaceCli;

public abstract class Base implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private InterfaceCli referenciaCliente;
    private String usuario; // quem fez a ordem
    private String codigoDaAcao; // nome da ação ex PETR4

    public InterfaceCli getReferenciaCliente() {
        return referenciaCliente;
    }

    public String getCodigoDaAcao() {
        return codigoDaAcao;
    }

    public void setCodigoDaAcao(String codigoDaAcao) {
        this.codigoDaAcao = codigoDaAcao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setReferenciaCliente(InterfaceCli referenciaCliente) {
        this.referenciaCliente = referenciaCliente;
    }
}
