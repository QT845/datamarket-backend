package com.datamarket.backend.entity.dataset;

import com.datamarket.backend.enums.ValidationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatasetQualityReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dataset_version_id", nullable = false, unique = true)
    private DatasetVersion datasetVersion;

    @Column(nullable = false)
    private Long recordCount;

    @Column(nullable = false)
    private Double nullRate;

    @Column(nullable = false)
    private Double duplicateRate;

    @Column(nullable = false)
    private boolean formatValid;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ValidationStatus status;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
