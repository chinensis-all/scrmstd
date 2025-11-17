package com.mayanshe.scrmstd.infrastructure.persistence.repo;

import com.mayanshe.scrmstd.domain.tenant.subscription.model.Feature;
import com.mayanshe.scrmstd.domain.tenant.subscription.repo.FeatureRepository;
import com.mayanshe.scrmstd.infrastructure.external.converter.FeatureConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.FeatureMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.FeaturePo;
import com.mayanshe.scrmstd.shared.contract.DomainRepositoryImpl;
import org.springframework.stereotype.Repository;

@Repository
public class FeatureRepositoryImpl extends DomainRepositoryImpl<Feature, Long, FeaturePo, FeatureMapper> implements FeatureRepository {
    public FeatureRepositoryImpl(FeatureMapper mapper) {
        super(mapper, FeatureConverter.INSTANCE::toAggregate);
    }
}
