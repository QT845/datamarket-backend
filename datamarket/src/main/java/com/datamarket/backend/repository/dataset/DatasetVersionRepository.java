package com.datamarket.backend.repository.dataset;

import com.datamarket.backend.entity.dataset.Dataset;
import com.datamarket.backend.entity.dataset.DatasetVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatasetVersionRepository extends JpaRepository<DatasetVersion, Long> {
    boolean existsByDatasetAndChecksum(Dataset dataset, String checksum);
}
