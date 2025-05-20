package br.com.lsd.backlds3.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vantagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private String foto;
    private int valor;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    public String gerarCodigoResgate() {
        return java.util.UUID.randomUUID().toString();
    }
}
