package com.mayanshe.scrmstd.application.tenant.query;

import com.mayanshe.scrmstd.application.tenant.query.dto.FeatureDto;
import com.mayanshe.scrmstd.shared.contract.Query;

public record FeatureDetailQuery(
    Long id
) implements Query<FeatureDto> {
}
