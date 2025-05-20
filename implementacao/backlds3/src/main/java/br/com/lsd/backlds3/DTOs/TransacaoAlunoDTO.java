package br.com.lsd.backlds3.DTOs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import br.com.lsd.backlds3.models.Empresa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Adiciona a configuração para não incluir atributos nulos
public class TransacaoAlunoDTO {
    private Long id;
    private String tipo;
    private int montante;
    private Date data;
    private ProfessorResumoDTO professor;
    private Empresa empresa;
}
