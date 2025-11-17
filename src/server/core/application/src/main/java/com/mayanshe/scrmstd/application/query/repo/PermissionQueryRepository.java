package com.mayanshe.scrmstd.application.query.repo;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.Pagination;
import com.mayanshe.scrmstd.application.QueryRepository;
import com.mayanshe.scrmstd.application.tenant.query.dto.PermissionDto;

import java.util.List;
import java.util.Optional;

public interface PermissionQueryRepository extends QueryRepository<PermissionDto, OptionDto, Long> {
    Optional<PermissionDto> findById(Long id);
    List<OptionDto> listAsOption(Object query);
    Pagination<PermissionDto> pagination(Object query);
}
