package br.com.metaway.petshop.controller.dto;

import br.com.metaway.petshop.UserRoles;
import br.com.metaway.petshop.model.User;

public class UserR {

    private String cpf;
    private String nome;
    private UserRoles tipo;

    public static UserR converter(User p){
        var user = new UserR();
        user.setCpf(p.getCpf());
        user.setNome(p.getNome());
        user.setTipo(p.getTipo());
        return user;
    }

    public UserRoles getTipo() {
        return this.tipo;
    }

    public void setTipo(UserRoles tipo) {
        this.tipo = tipo;
    }

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
}
