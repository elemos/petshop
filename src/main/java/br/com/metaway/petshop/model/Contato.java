package br.com.metaway.petshop.model;

import jakarta.persistence.*;

@Entity
@Table(name= "Contato")
public class Contato {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer id_cliente;

    private String tag;

    private String tipo;

    private String valor;

    public Contato(){}

    public Contato(Integer id, Integer id_cliente, String tag, String tipo, String valor) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.tag = tag;
        this.tipo = tipo;
        this.valor = valor;
    }

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
}
