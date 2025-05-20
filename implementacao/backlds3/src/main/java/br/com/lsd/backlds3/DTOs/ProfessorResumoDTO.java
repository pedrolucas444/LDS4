package br.com.lsd.backlds3.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorResumoDTO {
    private Long id;
    private String nome;
}
