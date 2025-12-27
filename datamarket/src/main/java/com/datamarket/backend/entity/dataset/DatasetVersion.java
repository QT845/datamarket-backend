package com.datamarket.backend.entity.dataset;

import com.datamarket.backend.enums.DatasetGenerationType;
import com.datamarket.backend.enums.DatasetVersionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatasetVersion {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dataset_id", nullable = false)
    private Dataset dataset;

    @Column(nullable = false, length = 255)
    private String version;

    @Enumerated(EnumType.STRING)
    private DatasetVersionStatus status;

    @Enumerated(EnumType.STRING)
    private DatasetGenerationType generationType;

    @Column(nullable = false, length = 100)
    private String dataLocation;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime approvedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
