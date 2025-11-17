package com.mayanshe.scrmstd.application.query.repo;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.QueryRepository;
import com.mayanshe.scrmstd.application.tenant.query.dto.FeatureDto;

public interface FeatureQueryRepository extends QueryRepository<FeatureDto, OptionDto, Long> {
}
