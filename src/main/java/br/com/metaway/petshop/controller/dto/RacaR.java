package br.com.metaway.petshop.controller.dto;

import br.com.metaway.petshop.model.Raca;

public class RacaR {

    private Integer id;
    private String descricao;

    public static RacaR converter(Raca r){
        var raca = new RacaR();
        raca.setId(r.getId());
        raca.setDescricao(r.getDescricao());
        return raca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
