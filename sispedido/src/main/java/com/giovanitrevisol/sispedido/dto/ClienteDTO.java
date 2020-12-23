package com.giovanitrevisol.sispedido.dto;

import com.giovanitrevisol.sispedido.domain.Cliente;
import com.giovanitrevisol.sispedido.services.valildation.ClienteUpdate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteUpdate
public class ClienteDTO implements Serializable {
        private static final long serialVersionUID = 1L;


    private Integer id;
    @NotEmpty(message = "Preenchimento obrigatório")
    @Max(value = 150, message = "O tanho máximo é de 150 caracteres")
    @Min(value = 5, message = "O tamanho mínimo é de 5 caracteres")
    private String nome;
    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido")
    private String email;

    public ClienteDTO() {
    }
    public ClienteDTO(Cliente obj) {
        id = obj.getId();
        nome = obj.getNome();
        email = obj.getEmail();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
