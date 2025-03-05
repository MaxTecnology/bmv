package br.com.bmv.erp.licenciamento.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtivacaoTotemDTO {

    private Long id;
    private Long totemId;
    private Long licencaId;
    private String codigoAtivacao;
    private String hardwareId;
    private String ipAtivacao;
    private LocalDateTime dataAtivacao;
    private LocalDateTime ultimoCheckin;
    private String status;
}
