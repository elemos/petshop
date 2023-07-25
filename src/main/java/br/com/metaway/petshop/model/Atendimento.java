package br.com.metaway.petshop.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name= "Atendimento")
public class Atendimento {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer id_pet;

    private String descricao;

    private Double valor;

    @Column(name="dtatendimento")
    private Date dtAtendimento;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getDtatendimento() {
        return dtAtendimento;
    }

    public void setDtatendimento(Date data) {
        this.dtAtendimento = data;
    }




}
