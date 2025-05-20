package br.com.lsd.backlds3.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExtratoProfessorDTO {
    private int saldoMoedas;
    private List<TransacaoProfessorDTO> transacoes;
}
