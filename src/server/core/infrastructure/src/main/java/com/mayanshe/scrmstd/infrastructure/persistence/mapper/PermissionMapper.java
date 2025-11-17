package com.mayanshe.scrmstd.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.PermissionPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<PermissionPo> {
}
