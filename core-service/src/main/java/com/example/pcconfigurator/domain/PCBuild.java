package com.example.pcconfigurator.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pc_builds")
@EntityListeners(AuditingEntityListener.class)
public class PCBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Кому принадлежит сборка
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TargetUsage targetUsage = TargetUsage.OFFICE;

    @Column(precision = 14, scale = 2)
    private BigDecimal totalPrice;

    // Время создания и изменения
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    // Что входит
    @OneToMany(mappedBy = "build", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PCBuildItem> items = new ArrayList<>();
}

