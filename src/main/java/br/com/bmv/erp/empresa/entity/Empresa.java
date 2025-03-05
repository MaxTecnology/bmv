package br.com.bmv.erp.empresa.entity;

import br.com.bmv.erp.core.entity.Revendedor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="empresas")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_revendedor", nullable = false)
    private Revendedor revendedor;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true, length = 20)
    private String cnpj;

    @Column(length = 255)
    private String endereco;

    @Column(length = 20)
    private String telefone;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String logo;

    @Column(name = "cores_tema", length = 50)
    private String coresTema;

    @Column(name = "dominio_personalizado", length = 100)
    private String dominioPersonalizado;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "data_expiracao")
    private LocalDateTime dataExpiracao;

    @Column(nullable = false, length = 50)
    private String plano = "BÁSICO";

    @Column(nullable = false, length = 20)
    private String status = "ATIVO";
}
