package br.com.metaway.petshop.controller.dto;

import br.com.metaway.petshop.model.Client;
import jakarta.persistence.Column;

import java.sql.Date;

public class ClientR {

    private Integer id;

    private String nome;

    private String cpf;

    private Date dtcadastro;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDtcadastro() {
        return dtcadastro;
    }

    public void setDtcadastro(Date dtcadastro) {
        this.dtcadastro = dtcadastro;
    }


    public static ClientR converter (Client cliente){
        ClientR c = new ClientR();
        c.setId(cliente.getId());
        c.setCpf(cliente.getCpf());
        c.setDtcadastro(cliente.getDtcadastro());
        c.setNome(cliente.getNome());
        return c;
    }
}
