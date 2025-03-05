package br.com.bmv.erp.cardapio.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OpcaoComplementoDTO {

    private Long id;

    @NotNull(message = "Grupo de complemento é obrigatório")
    private Long grupoComplementoId;

    @NotNull(message = "Complemento é obrigatório")
    private Long complementoId;

    private BigDecimal precoEspecifico;

    // Campos adicionais para facilitar a exibição
    private String nomeComplemento;
    private BigDecimal precoComplemento;
}
