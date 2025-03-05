package br.com.bmv.erp.licenciamento.entity;


import br.com.bmv.erp.totem.entity.Totem;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ativacoes_totem",
        uniqueConstraints = @UniqueConstraint(columnNames = {"hardware_id", "id_licenca"},
                name = "uk_hardware_licenca"))
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class AtivacaoTotem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_totem", nullable = false)
    private Totem totem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_licenca", nullable = false)
    private LicencaTotem licenca;

    @Column(name = "codigo_ativacao", nullable = false, length = 100)
    private String codigoAtivacao;

    @Column(name = "hardware_id", nullable = false, length = 255)
    private String hardwareId;

    @Column(name = "ip_ativacao", length = 50)
    private String ipAtivacao;

    @Column(name = "data_ativacao", nullable = false)
    private LocalDateTime dataAtivacao = LocalDateTime.now();

    @Column(name = "ultimo_checkin", nullable = false)
    private LocalDateTime ultimoCheckin = LocalDateTime.now();

    @Column(nullable = false, length = 20)
    private String status = "ATIVO";
}