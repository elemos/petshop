package br.com.metaway.petshop.controller.dto;

import br.com.metaway.petshop.model.User;

public class UserR {

    private Integer id;
    private String cpf;
    private String nome;
    private Integer Tipo;

    public static UserR converter(User p){
        var user = new UserR();
        user.setCpf(p.getCpf());
        user.setNome(p.getNome());
        user.setTipo(p.getTipo());
        return user;
    }

    public Integer getTipo() {
        return Tipo;
    }

    public void setTipo(Integer tipo) {
        Tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
