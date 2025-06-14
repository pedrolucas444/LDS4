package br.com.lsd.backlds3.DTOs;

import lombok.Data;

@Data
public class LoginDTO {
    private String identificador;
    private String senha;

     public String getIdentificador() {
        return identificador;
    }
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
