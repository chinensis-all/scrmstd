package com.mayanshe.scrmstd.infrastructure.external.converter;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.tenant.query.dto.FeatureDto;
import com.mayanshe.scrmstd.domain.tenant.subscription.model.Feature;
import com.mayanshe.scrmstd.infrastructure.persistence.po.FeaturePo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FeatureConverter extends IConverter {
    FeatureConverter INSTANCE = Mappers.getMapper(FeatureConverter.class);

    FeaturePo toPo(Feature aggregate);

    void updatePo(@MappingTarget FeaturePo po, Feature aggregate);

    Feature toAggregate(FeaturePo po);



    FeatureDto toDto(FeaturePo po);

    OptionDto toOptionDto(FeaturePo po);
}
