package br.com.bmv.erp.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name="revendedores")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Revendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false, length=100)
    private String nome;

    @Column(unique = true, length = 20)
    private String cnpj;

    @Column(name = "razao_social", length = 100)
    private String razaoSocial;

    @Column(length = 255)
    private String endereco;

    @Column(length = 20)
    private String telefone;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String logo;

    @Column(length = 100)
    private String site;

    @Column(name = "date_criacao", nullable=false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(nullable = false, length = 20)
    private String status = "Ativo";

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @OneToMany(mappedBy = "revendedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Empresa> empresas = new ArrayList<>();
}
