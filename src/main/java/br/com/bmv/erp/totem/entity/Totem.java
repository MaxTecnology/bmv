package br.com.bmv.erp.totem.entity;

import br.com.bmv.erp.empresa.entity.Empresa;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "totems")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Totem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @Column(nullable = false, length = 50)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 100)
    private String localizacao;

    @Column(length = 50)
    private String ip;

    @Column(name = "mac_address", length = 50)
    private String macAddress;

    @Column(name = "hardware_id", nullable = false, length = 255)
    private String hardwareId;

    @Column(name = "sistema_operacional", length = 50)
    private String sistemaOperacional;

    @Column(name = "versao_software", length = 50)
    private String versaoSoftware;

    @Column(nullable = false, length = 20)
    private String status = "INATIVO";  // INATIVO, ATIVO, SUSPENSO, MANUTENCAO

    @Column(name = "ultima_sincronizacao")
    private LocalDateTime ultimaSincronizacao;
}
