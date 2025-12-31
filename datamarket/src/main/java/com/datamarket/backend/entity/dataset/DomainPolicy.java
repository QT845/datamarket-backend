package com.datamarket.backend.entity.dataset;

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
public class DomainPolicy {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_id", nullable = false, unique = true)
    private DatasetDomain datasetDomain;

    @Column(nullable = false)
    private boolean allowRaw;

    @Column(nullable = false)
    private boolean allowAggregated;

    @Column(nullable = false)
    private boolean allowAnalytics;
}
