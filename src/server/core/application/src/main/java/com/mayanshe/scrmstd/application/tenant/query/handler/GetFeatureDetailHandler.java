package com.mayanshe.scrmstd.application.tenant.query.handler;

import com.mayanshe.scrmstd.application.query.repo.FeatureQueryRepository;
import com.mayanshe.scrmstd.application.tenant.query.FeatureDetailQuery;
import com.mayanshe.scrmstd.application.tenant.query.dto.FeatureDto;
import com.mayanshe.scrmstd.shared.contract.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetFeatureDetailHandler implements QueryHandler<FeatureDetailQuery, FeatureDto> {
    private final FeatureQueryRepository featureQueryRepository;

    public GetFeatureDetailHandler(FeatureQueryRepository featureQueryRepository) {
        this.featureQueryRepository = featureQueryRepository;
    }

    @Override
    public FeatureDto handle(FeatureDetailQuery query) {
        return featureQueryRepository.findById(query.id());
    }
}
