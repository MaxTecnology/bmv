package br.com.bmv.erp.licenciamento.entity;

import br.com.bmv.erp.empresa.entity.Empresa;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class LicencaTotem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @Column(name = "chave_licenca", nullable = false, unique = true, length = 100)
    private String chaveLicenca;

    @Column(name = "data_emissao", nullable = false)
    private LocalDateTime dataEmissao = LocalDateTime.now();

    @Column(name = "data_expiracao", nullable = false)
    private LocalDateTime dataExpiracao;

    @Column(name = "max_totems", nullable = false)
    private Integer maxTotems;

    @Column(nullable = false, length = 20)
    private String status = "ATIVO";

    @Column(columnDefinition = "TEXT")
    private String observacoes;
}
