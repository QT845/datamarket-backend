package com.datamarket.backend.repository.dataset;

import com.datamarket.backend.entity.dataset.DatasetDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatasetDomainRepository extends JpaRepository<DatasetDomain, Long> {
    Optional<DatasetDomain> findByName(String name);
}
