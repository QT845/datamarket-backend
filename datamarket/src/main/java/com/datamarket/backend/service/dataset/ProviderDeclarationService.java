package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.request.DeclarationRequest;
import com.datamarket.backend.dto.response.DeclarationResponse;
import com.datamarket.backend.entity.dataset.ProviderDeclaration;

public interface ProviderDeclarationService {
    ProviderDeclaration declare(Long versionId, DeclarationRequest request);
    ProviderDeclaration findByVersionId(Long versionId);
    ProviderDeclaration getByVersionId(Long versionId);
}
