package com.mayanshe.scrmstd.application.tenant.query;

import com.mayanshe.scrmstd.application.tenant.query.dto.PermissionDto;
import com.mayanshe.scrmstd.shared.contract.Query;

public record PermissionDetailQuery(
        Long id
) implements Query<PermissionDto> {
}
