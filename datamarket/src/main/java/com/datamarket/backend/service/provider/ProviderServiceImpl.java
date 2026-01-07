package com.datamarket.backend.service.provider;

import com.datamarket.backend.entity.Provider;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements  ProviderService {
    private final ProviderRepository providerRepository;
    @Override
    public Provider findById(Long id) {
        return providerRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.PROVIDER_001));
    }
}
