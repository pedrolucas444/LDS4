package br.com.lsd.backlds3.models;

import br.com.lsd.backlds3.enums.TipoAcesso;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    private TipoAcesso tipoAcesso;

}
