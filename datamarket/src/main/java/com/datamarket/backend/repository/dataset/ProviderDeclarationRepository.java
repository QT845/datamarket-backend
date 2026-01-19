package com.datamarket.backend.repository.dataset;

import com.datamarket.backend.entity.dataset.ProviderDeclaration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderDeclarationRepository extends JpaRepository<ProviderDeclaration,Long> {
    Optional<ProviderDeclaration> findByDatasetVersionId(Long versionId);
}
