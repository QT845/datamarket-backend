package com.datamarket.backend.mapper;

import com.datamarket.backend.dto.response.DeclarationResponse;
import com.datamarket.backend.entity.dataset.ProviderDeclaration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProviderDeclarationMapper {
    DeclarationResponse toDeclarationResponse(ProviderDeclaration entity);
}
