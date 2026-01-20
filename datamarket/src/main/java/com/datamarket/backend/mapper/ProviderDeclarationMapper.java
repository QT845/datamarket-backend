package com.datamarket.backend.mapper;

import com.datamarket.backend.dto.response.datasetResponse.DeclarationResponse;
import com.datamarket.backend.entity.dataset.ProviderDeclaration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProviderDeclarationMapper {
    @Mapping(target = "version",
            source = "datasetVersion.version")
    DeclarationResponse toDeclarationResponse(ProviderDeclaration entity);
}
