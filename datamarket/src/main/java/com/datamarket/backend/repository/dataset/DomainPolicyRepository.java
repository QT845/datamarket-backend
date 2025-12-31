package com.datamarket.backend.repository.dataset;

import com.datamarket.backend.entity.dataset.DomainPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainPolicyRepository extends JpaRepository<DomainPolicy, Long> {
}
