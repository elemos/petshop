package br.com.metaway.petshop.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name= "Client")
public class Client {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name="nome")
    private String nome;
    @Column(name="cpf")
    private String cpf;
    @Column(name="dtcadastro")
    private Date dtcadastro;

    public Client(){}

    public Client(Integer id, String nome, String cpf, Date dtcadastro) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dtcadastro = dtcadastro;
    }

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
}
