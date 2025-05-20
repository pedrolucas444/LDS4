package br.com.lsd.backlds3.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    private String cnpj;

    @JsonIgnore
    @OneToMany(mappedBy = "empresa")
    private List<Vantagem> vantagens;

}
