package br.com.bmv.erp.cardapio.entity;

import br.com.bmv.erp.empresa.entity.Empresa;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grupos_complementos")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class GrupoComplemento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "selecao_minima", nullable = false)
    private Integer selecaoMinima = 0;

    @Column(name = "selecao_maxima", nullable = false)
    private Integer selecaoMaxima = 1;

    @ManyToMany(mappedBy = "gruposComplementos")
    private List<ItemMenu> itens = new ArrayList<>();

    @OneToMany(mappedBy = "grupoComplemento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpcaoComplemento> opcoes = new ArrayList<>();
}
