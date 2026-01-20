package com.datamarket.backend.service.moderator;

import com.datamarket.backend.dto.response.ModReviewResponse;

public interface ModeratorService {
    ModReviewResponse approveDataset(Long id);
    ModReviewResponse rejectDataset(Long id, String reason);
}
