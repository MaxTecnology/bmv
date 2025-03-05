package br.com.bmv.erp.licenciamento.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HeartbeatRequest {
    @NotBlank(message = "Código de ativação é obrigatório")
    private String codigoAtivacao;

    private String ipAddress;

    private String status;

    private String versaoSoftware;

    private String metricasSistema;
}
