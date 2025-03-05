package br.com.bmv.erp.totem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TotemDTO {

    private Long id;

    @NotNull(message = "Empresa é obrigatória")
    private Long empresaId;

    @NotBlank(message = "Código é obrigatório")
    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    private String codigo;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @Size(max = 100, message = "Localização deve ter no máximo 100 caracteres")
    private String localizacao;

    private String ip;

    private String macAddress;

    @NotBlank(message = "ID de hardware é obrigatório")
    private String hardwareId;

    private String sistemaOperacional;

    private String versaoSoftware;

    private String status;

    private LocalDateTime ultimaSincronizacao;
}
