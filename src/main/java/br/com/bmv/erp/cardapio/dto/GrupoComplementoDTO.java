package br.com.bmv.erp.cardapio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class GrupoComplementoDTO {

    private Long id;

    @NotNull(message = "Empresa é obrigatória")
    private Long empresaId;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotNull(message = "Seleção mínima é obrigatória")
    @PositiveOrZero(message = "Seleção mínima deve ser positiva ou zero")
    private Integer selecaoMinima;

    @NotNull(message = "Seleção máxima é obrigatória")
    @PositiveOrZero(message = "Seleção máxima deve ser positiva ou zero")
    private Integer selecaoMaxima;

    private List<OpcaoComplementoDTO> opcoes;
}
