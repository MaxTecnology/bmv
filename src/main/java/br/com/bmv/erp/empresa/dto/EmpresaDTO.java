package br.com.bmv.erp.empresa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmpresaDTO {
    private Long id;

    @NotNull(message = "Revendedor é obrigatório")
    private Long revendedorId;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @Size(max = 20, message = "CNPJ deve ter no máximo 20 caracteres")
    private String cnpj;

    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    private String endereco;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    private String logo;

    @Size(max = 50, message = "Cores do tema deve ter no máximo 50 caracteres")
    private String coresTema;

    @Size(max = 100, message = "Domínio personalizado deve ter no máximo 100 caracteres")
    private String dominioPersonalizado;

    private LocalDateTime dataExpiracao;

    @Size(max = 50, message = "Plano deve ter no máximo 50 caracteres")
    private String plano;

    private String status;

}
