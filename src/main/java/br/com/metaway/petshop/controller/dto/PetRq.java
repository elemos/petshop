package br.com.metaway.petshop.controller.dto;

import java.sql.Date;

public class PetRq {

    private Integer id;
    private Integer id_cliente;
    private Integer id_raca;
    private Date dtnascimento;
    private String nome;

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

    public Integer getId_raca() {
        return id_raca;
    }

    public void setId_raca(Integer id_raca) {
        this.id_raca = id_raca;
    }

    public Date getDtnascimento() {
        return dtnascimento;
    }

    public void setDtnascimento(Date dtnascimento) {
        this.dtnascimento = dtnascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
