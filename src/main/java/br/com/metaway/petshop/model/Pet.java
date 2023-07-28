package br.com.metaway.petshop.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name= "Pets")
public class Pet {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer id_cliente;

    private Integer id_raca;

    private Date dtnascimento;

    private String nome;

    public Pet(){}

    public Pet(Integer id, Integer id_cliente, Integer id_raca, Date dtnascimento, String nome) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_raca = id_raca;
        this.dtnascimento = dtnascimento;
        this.nome = nome;
    }

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
