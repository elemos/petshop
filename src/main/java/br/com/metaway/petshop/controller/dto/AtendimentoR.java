package br.com.metaway.petshop.controller.dto;

import br.com.metaway.petshop.model.Atendimento;
import br.com.metaway.petshop.model.Pet;

import java.sql.Date;

public class AtendimentoR {

    private Integer id;

    private Integer id_pet;

    private String descricao;

    private double valor;

    private Date dtatendimento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_pet() {
        return id_pet;
    }

    public void setId_pet(Integer id_pet) {
        this.id_pet = id_pet;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDtatendimento() {
        return dtatendimento;
    }

    public void setDtatendimento(Date dtatendimento) {
        this.dtatendimento = dtatendimento;
    }

    public static AtendimentoR converter(Atendimento a){
        var at = new AtendimentoR();
        at.setId_pet(a.getId_pet());
        at.setId(a.getId());
        at.setDescricao(a.getDescricao());
        at.setDtatendimento(a.getDtatendimento());
        at.setValor(a.getValor());
        return at;
    }

}
