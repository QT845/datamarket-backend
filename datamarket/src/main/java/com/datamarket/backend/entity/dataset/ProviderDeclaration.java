package com.datamarket.backend.entity.dataset;

import com.datamarket.backend.enums.IntendedUse;
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
public class ProviderDeclaration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dataset_version_id", nullable = false, unique = true)
    private DatasetVersion datasetVersion;

    @Column(nullable = false, length = 255)
    private String dataSource;

    @Column(nullable = false, length = 255)
    private String collectionMethod;

    @Column(nullable = false)
    private LocalDateTime timeRangeStart;

    @Column(nullable = false)
    private LocalDateTime timeRangeEnd;

    @Column(nullable = false, length = 255)
    private String updateFrequency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IntendedUse intendedUse;

    @Column
    private String additionalNotes;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "TEXT")
    private String methodologyExplanation;

    @Column(columnDefinition = "TEXT")
    private String licenseStatement;

    @Column
    private String proofDocumentUrl;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
