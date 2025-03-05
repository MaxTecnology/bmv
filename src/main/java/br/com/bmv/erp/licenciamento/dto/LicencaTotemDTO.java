package br.com.bmv.erp.licenciamento.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class LicencaTotemDTO {

    private Long id;

    @NotNull(message = "Empresa é obrigatória")
    private Long empresaId;

    private String chaveLicenca;

    private LocalDateTime dataEmissao;

    @NotNull(message = "Data de expiração é obrigatória")
    @Future(message = "Data de expiração deve ser no futuro")
    private LocalDateTime dataExpiracao;

    @NotNull(message = "Número máximo de totems é obrigatório")
    @Min(value = 1, message = "Número máximo de totems deve ser pelo menos 1")
    private Integer maxTotems;

    private String status;

    private String observacoes;
}
