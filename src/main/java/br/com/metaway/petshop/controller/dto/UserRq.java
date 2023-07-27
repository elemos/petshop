package br.com.metaway.petshop.controller.dto;

import br.com.metaway.petshop.UserRoles;

public class UserRq {

    private String cpf;
    private String nome;
    private String ps;
    private UserRoles tipo;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public UserRoles getTipo() {
        return tipo;
    }

    public void setTipo(UserRoles tipo) {
        this.tipo = tipo;
    }
}
