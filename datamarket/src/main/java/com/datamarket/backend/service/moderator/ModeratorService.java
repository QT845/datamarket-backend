package com.datamarket.backend.service.moderator;

import com.datamarket.backend.dto.response.ModReviewResponse;

public interface ModeratorService {
    ModReviewResponse approveTechnical(Long versionId);
    ModReviewResponse rejectTechnical(Long versionId, String reason);
    ModReviewResponse approveBusiness(Long versionId);
    ModReviewResponse rejectBusiness(Long versionId, String reason);
}
