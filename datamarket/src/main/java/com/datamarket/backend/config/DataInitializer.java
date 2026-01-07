package com.datamarket.backend.config;

import com.datamarket.backend.entity.Provider;
import com.datamarket.backend.entity.User;
import com.datamarket.backend.entity.dataset.DatasetDomain;
import com.datamarket.backend.enums.ProviderStatus;
import com.datamarket.backend.enums.RoleType;
import com.datamarket.backend.enums.UserStatus;
import com.datamarket.backend.repository.ProviderRepository;
import com.datamarket.backend.repository.UserRepository;
import com.datamarket.backend.repository.dataset.DatasetDomainRepository;
import com.datamarket.backend.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            UserRepository userRepository,
            ProviderRepository providerRepository,
            DatasetDomainRepository datasetDomainRepository,
            PasswordUtil passwordUtil
    ) {
        return args -> {

            // ===============================
            // 1. PROVIDER
            // ===============================
            if (!userRepository.existsByEmail("provider@test.com")) {

                User providerUser = User.builder()
                        .email("provider@test.com")
                        .fullName("provider")
                        .password(passwordUtil.hash("1"))
                        .role(RoleType.PROVIDER)
                        .status(UserStatus.ACTIVE)
                        .isEmailVerified(true)
                        .tokenVersion(0)
                        .build();

                userRepository.save(providerUser);

                Provider provider = Provider.builder()
                        .user(providerUser)
                        .phone("0123456789")
                        .status(ProviderStatus.APPROVED)
                        .build();

                providerRepository.save(provider);
            }

            // ===============================
            // 2. MODERATOR
            // ===============================
            if (!userRepository.existsByEmail("moderator@test.com")) {

                User moderator = User.builder()
                        .email("moderator@test.com")
                        .fullName("moderator")
                        .password(passwordUtil.hash("1"))
                        .role(RoleType.MODERATOR)
                        .status(UserStatus.ACTIVE)
                        .isEmailVerified(true)
                        .tokenVersion(0)
                        .build();

                userRepository.save(moderator);
            }

            // ===============================
            // 3. ADMIN (optional)
            // ===============================
            if (!userRepository.existsByEmail("admin@test.com")) {

                User admin = User.builder()
                        .email("admin@test.com")
                        .fullName("admin")
                        .password(passwordUtil.hash("1"))
                        .role(RoleType.ADMIN)
                        .status(UserStatus.ACTIVE)
                        .isEmailVerified(true)
                        .tokenVersion(0)
                        .build();

                userRepository.save(admin);
            }
            // ===============================
// DATASET DOMAINS
// ===============================
            if (datasetDomainRepository.count() == 0) {

                datasetDomainRepository.save(
                        DatasetDomain.builder()
                                .name("Finance")
                                .code("FIN")
                                .description("Financial, banking, stock market data")
                                .build()
                );

                datasetDomainRepository.save(
                        DatasetDomain.builder()
                                .name("E-commerce")
                                .code("ECO")
                                .description("Orders, products, customer behavior")
                                .build()
                );

                datasetDomainRepository.save(
                        DatasetDomain.builder()
                                .name("Healthcare")
                                .code("HEA")
                                .description("Medical, hospital, health statistics")
                                .build()
                );

                datasetDomainRepository.save(
                        DatasetDomain.builder()
                                .name("Transportation")
                                .code("TRA")
                                .description("Logistics, traffic, shipping data")
                                .build()
                );

                datasetDomainRepository.save(
                        DatasetDomain.builder()
                                .name("Social Media")
                                .code("SOC")
                                .description("User interaction, sentiment, trends")
                                .build()
                );
            }
        };
    }
}
