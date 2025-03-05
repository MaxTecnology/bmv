package br.com.bmv.erp.licenciamento.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "heartbeat_totems")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class HeartbeatTotem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ativacao", nullable = false)
    private AtivacaoTotem ativacao;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "ip_address", length = 50)
    private String ipAddress;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "versao_software", length = 50)
    private String versaoSoftware;

    @Column(name = "metricas_sistema", columnDefinition = "TEXT")
    private String metricasSistema;
}
