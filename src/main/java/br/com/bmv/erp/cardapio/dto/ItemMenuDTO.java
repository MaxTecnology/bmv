package br.com.bmv.erp.cardapio.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ItemMenuDTO {

    private Long id;

    @NotNull(message = "Empresa é obrigatória")
    private Long empresaId;

    @NotNull(message = "Categoria é obrigatória")
    private Long categoriaId;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    private String descricao;

    private String imagem;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    private BigDecimal preco;

    @PositiveOrZero(message = "Tempo de preparo deve ser positivo ou zero")
    private Integer tempoPreparo;

    private Boolean destaque;

    private Boolean disponivel;

    private List<Long> gruposComplementosIds;
}
