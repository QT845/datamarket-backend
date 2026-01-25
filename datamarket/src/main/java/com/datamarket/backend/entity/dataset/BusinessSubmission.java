package com.datamarket.backend.entity.dataset;

import com.datamarket.backend.entity.Provider;
import com.datamarket.backend.enums.BusinessSubmissionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "dataset_version_id", nullable = false, unique = true)
    private DatasetVersion datasetVersion;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by_provider_id", nullable = false)
    private Provider submittedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessSubmissionStatus submissionStatus;

    @PrePersist
    public void prePersist() {
        this.submittedAt = LocalDateTime.now();
    }
}
