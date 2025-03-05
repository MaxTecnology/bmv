package br.com.bmv.erp.cardapio.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "opcoes_complemento")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class OpcaoComplemento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grupo_complemento", nullable = false)
    private GrupoComplemento grupoComplemento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_complemento", nullable = false)
    private Complemento complemento;

    @Column(name = "preco_especifico", precision = 15, scale = 2)
    private BigDecimal precoEspecifico;

}
