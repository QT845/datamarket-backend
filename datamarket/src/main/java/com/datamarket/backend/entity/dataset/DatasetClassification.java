package com.datamarket.backend.entity.dataset;

import com.datamarket.backend.enums.DatasetLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatasetClassification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "dataset_version_id", nullable = false, unique = true)
    private DatasetVersion datasetVersion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DatasetLevel dataLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "derived_from_version_id")
    private DatasetVersion derivedFromVersion;
}
