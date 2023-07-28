package br.com.metaway.petshop.controller.dto;

import br.com.metaway.petshop.model.Client;

import java.sql.Date;

public class ClientRq {

    private String nome;

    private String cpf;

    private Date dtcadastro;

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

    public static Client converter (ClientRq cliente){
        Client c = new Client();
        c.setCpf(cliente.getCpf());
        c.setDtcadastro(cliente.getDtcadastro());
        c.setNome(cliente.getNome());
        return c;
    }
}
