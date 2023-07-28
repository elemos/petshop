package br.com.metaway.petshop.controller.dto;

import br.com.metaway.petshop.model.Contato;

public class ContatoRq {
    private Integer id;

    private Integer id_cliente;

    private String tag;

    private String tipo;

    private String valor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public static Contato converter(ContatoRq contato){
        Contato c = new Contato();
        c.setId_cliente(contato.getId_cliente());
        c.setTipo(contato.getTipo());
        c.setValor(contato.getValor());
        c.setTag(contato.getTag());
        return c;
    }
}
