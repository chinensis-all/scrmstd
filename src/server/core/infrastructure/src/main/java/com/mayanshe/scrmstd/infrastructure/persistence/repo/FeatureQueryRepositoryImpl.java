package com.mayanshe.scrmstd.infrastructure.persistence.repo;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.query.repo.FeatureQueryRepository;
import com.mayanshe.scrmstd.application.tenant.query.dto.FeatureDto;
import com.mayanshe.scrmstd.infrastructure.external.converter.FeatureConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.FeatureMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.FeaturePo;
import com.mayanshe.scrmstd.shared.contract.QueryRepositoryImpl;
import org.springframework.stereotype.Repository;

@Repository
public class FeatureQueryRepositoryImpl extends QueryRepositoryImpl<FeatureDto, OptionDto, Long, FeaturePo, FeatureMapper> implements FeatureQueryRepository {
    public FeatureQueryRepositoryImpl(FeatureMapper mapper) {
        super(mapper, FeatureConverter.INSTANCE::toDto);
    }
}
