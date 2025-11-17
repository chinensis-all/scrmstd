package com.mayanshe.scrmstd.infrastructure.persistence.repo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.Pagination;
import com.mayanshe.scrmstd.application.query.repo.PermissionQueryRepository;
import com.mayanshe.scrmstd.application.tenant.query.dto.PermissionDto;
import com.mayanshe.scrmstd.infrastructure.external.converter.PermissionConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.PermissionMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.PermissionPo;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PermissionQueryRepositoryImpl implements PermissionQueryRepository {
    private final PermissionMapper permissionMapper;

    public PermissionQueryRepositoryImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public Optional<PermissionDto> findById(Long id) {
        PermissionPo po = permissionMapper.selectById(id);
        return Optional.ofNullable(PermissionConverter.INSTANCE.toDto(po));
    }

    @Override
    public List<PermissionDto> listByIds(List<Long> ids) {
        List<PermissionPo> pos = permissionMapper.selectBatchIds(ids);
        return pos.stream().map(PermissionConverter.INSTANCE::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OptionDto> listAsOption(Object query) {
        return Collections.emptyList();
    }

    @Override
    public Pagination<PermissionDto> pagination(Object query) {
        return null;
    }
}
