package com.giovanitrevisol.sispedido.dto;

import com.giovanitrevisol.sispedido.domain.Categoria;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CategoriaDTO  implements Serializable {
    private static final long serialVersionUID = 1l;
//esta classe serve apenas para definir quais dados vão trafegar quando fizer operacoes com o objeto categoria

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatório!")
    @Size(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
    private String nome;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Categoria obj){
        this.id = obj.getId();
        this.nome = obj.getNome();
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
}
