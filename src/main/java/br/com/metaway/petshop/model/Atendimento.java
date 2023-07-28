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

    private Integer id_cliente;

    private String descricao;

    private Double valor;

    @Column(name="dtatendimento")
    private Date dtatendimento;

    public Atendimento() {
    }

    public Atendimento(Integer id, Integer id_pet, Integer id_cliente, String descricao, Double valor, Date dtatendimento) {
        this.id = id;
        this.id_pet = id_pet;
        this.id_cliente = id_cliente;
        this.descricao = descricao;
        this.valor = valor;
        this.dtatendimento = dtatendimento;
    }

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

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
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
        return dtatendimento;
    }

    public void setDtatendimento(Date data) {
        this.dtatendimento = data;
    }




}
