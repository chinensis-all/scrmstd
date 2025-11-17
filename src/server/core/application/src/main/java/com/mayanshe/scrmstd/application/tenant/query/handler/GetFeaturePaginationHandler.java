package com.mayanshe.scrmstd.application.tenant.query.handler;

import com.mayanshe.scrmstd.application.Pagination;
import com.mayanshe.scrmstd.application.query.repo.FeatureQueryRepository;
import com.mayanshe.scrmstd.application.tenant.query.FeaturePaginationQuery;
import com.mayanshe.scrmstd.application.tenant.query.dto.FeatureDto;
import com.mayanshe.scrmstd.shared.contract.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetFeaturePaginationHandler implements QueryHandler<FeaturePaginationQuery, Pagination<FeatureDto>> {
    private final FeatureQueryRepository featureQueryRepository;

    public GetFeaturePaginationHandler(FeatureQueryRepository featureQueryRepository) {
        this.featureQueryRepository = featureQueryRepository;
    }

    @Override
    public Pagination<FeatureDto> handle(FeaturePaginationQuery query) {
        return featureQueryRepository.paginate(query.page(), query.pageSize());
    }
}
