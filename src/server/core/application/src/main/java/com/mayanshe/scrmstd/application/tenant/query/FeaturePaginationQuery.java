package com.mayanshe.scrmstd.application.tenant.query;

import com.mayanshe.scrmstd.application.Pagination;
import com.mayanshe.scrmstd.application.tenant.query.dto.FeatureDto;
import com.mayanshe.scrmstd.shared.contract.Query;

public record FeaturePaginationQuery(
    int page,
    int pageSize
) implements Query<Pagination<FeatureDto>> {
}
