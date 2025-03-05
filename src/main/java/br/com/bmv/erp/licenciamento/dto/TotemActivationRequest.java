package br.com.bmv.erp.licenciamento.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TotemActivationRequest {

    @NotBlank(message = "Chave de licença é obrigatória")
    private String chaveLicenca;

    @NotBlank(message = "ID de hardware é obrigatório")
    private String hardwareId;

    private String ipAddress;

    private String sistemaOperacional;

    private String versaoSoftware;
}
