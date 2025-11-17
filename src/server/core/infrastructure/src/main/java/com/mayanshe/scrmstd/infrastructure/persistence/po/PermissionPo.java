package com.mayanshe.scrmstd.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("permissions")
public class PermissionPo {
    private Long id;
    private Long groupId;
    private String permissionName;
    private String displayName;
    private String description;
    private Long createdAt;
    private Long updatedAt;
}
