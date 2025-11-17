package com.mayanshe.scrmstd.application.tenant.query.handler;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.query.repo.FeatureQueryRepository;
import com.mayanshe.scrmstd.application.tenant.query.FeatureOptionQuery;
import com.mayanshe.scrmstd.shared.contract.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetFeatureOptionHandler implements QueryHandler<FeatureOptionQuery, List<OptionDto>> {
    private final FeatureQueryRepository featureQueryRepository;

    public GetFeatureOptionHandler(FeatureQueryRepository featureQueryRepository) {
        this.featureQueryRepository = featureQueryRepository;
    }

    @Override
    public List<OptionDto> handle(FeatureOptionQuery query) {
        return featureQueryRepository.findOptions();
    }
}
