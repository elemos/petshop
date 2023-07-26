package br.com.metaway.petshop.controller.dto;

import br.com.metaway.petshop.model.Endereco;

public class EnderecoR {

    private Integer id;

    private Integer id_cliente;

    private String logradouro;

    private String cidade;

    private String bairro;

    private String complemento;

    private String tag;

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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static EnderecoR converter(Endereco endereco){
        EnderecoR e = new EnderecoR();
        e.setId(endereco.getId());
        e.setId_cliente(endereco.getId_cliente());
        e.setCidade(endereco.getCidade());
        e.setBairro(endereco.getBairro());
        e.setLogradouro(endereco.getLogradouro());
        e.setComplemento(endereco.getComplemento());
        e.setTag(endereco.getTag());
        return e;
    }


}
