package br.com.bmv.erp.cardapio.entity;

import br.com.bmv.erp.empresa.entity.Empresa;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "itens_menu")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaMenu categoria;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(length = 255)
    private String imagem;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal preco;

    @Column(name = "tempo_preparo")
    private Integer tempoPreparo;  // em minutos

    @Column(nullable = false)
    private Boolean destaque = false;

    @Column(nullable = false)
    private Boolean disponivel = true;

    @ManyToMany
    @JoinTable(
            name = "item_grupo_complemento",
            joinColumns = @JoinColumn(name = "id_item"),
            inverseJoinColumns = @JoinColumn(name = "id_grupo_complemento")
    )
    private List<GrupoComplemento> gruposComplementos = new ArrayList<>();
}
