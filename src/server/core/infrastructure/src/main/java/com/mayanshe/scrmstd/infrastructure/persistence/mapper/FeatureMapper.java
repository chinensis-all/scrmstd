package com.mayanshe.scrmstd.infrastructure.persistence.mapper;

import com.mayanshe.scrmstd.infrastructure.persistence.po.FeaturePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FeatureMapper {
    @Select("SELECT * FROM features WHERE id = #{id}")
    FeaturePo findById(Long id);

    void insert(FeaturePo po);

    void update(FeaturePo po);

    void delete(FeaturePo po);
}
