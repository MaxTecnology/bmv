package br.com.bmv.erp.cardapio.entity;

import br.com.bmv.erp.empresa.entity.Empresa;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias_menu")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CategoriaMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false)
    private Integer ordem = 0;

    @Column(length = 255)
    private String imagem;

    @Column(nullable = false, length = 20)
    private String status = "ATIVO";

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemMenu> itens = new ArrayList<>();
}
