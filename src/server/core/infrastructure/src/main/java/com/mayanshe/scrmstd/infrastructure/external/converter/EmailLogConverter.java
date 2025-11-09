/*
 * [ScrmStd] - 通用SCRM系统
 * Copyright (C) [2025] [张西海]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.mayanshe.scrmstd.infrastructure.external.converter;

import cn.hutool.json.JSONUtil;
import com.mayanshe.scrmstd.infrastructure.persistence.po.EmailLogPo;
import com.mayanshe.scrmstd.message.log.model.EmailLog;
import com.mayanshe.scrmstd.message.log.type.EmailSendStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EmailLogConverter: 邮件日志转换器
 */
@Mapper(componentModel = "spring")
@Component
public abstract class EmailLogConverter implements BaseConverter {
    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
            @Mapping(target = "cc", expression = "java(listToString(aggregate.getCc()))"),
            @Mapping(target = "params", expression = "java(mapToString(aggregate.getParams()))"),
            @Mapping(target = "attachments", expression = "java(mapStringToString(aggregate.getAttachments()))"),
            @Mapping(target = "status", expression = "java(statusToString(aggregate.getStatus()))")
    })
    public abstract EmailLogPo toPo(EmailLog aggregate);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "idToAggregateId"),
            @Mapping(target = "cc", expression = "java(stringToList(po.getCc()))"),
            @Mapping(target = "params", expression = "java(stringToMap(po.getParams()))"),
            @Mapping(target = "attachments", expression = "java(stringToMapString(po.getAttachments()))"),
            @Mapping(target = "status", expression = "java(stringToStatus(po.getStatus()))")
    })
    public abstract EmailLog toAggregate(EmailLogPo po);

    protected String listToString(List<String> list) {
        return list != null ? JSONUtil.toJsonStr(list) : "[]";
    }

    protected List<String> stringToList(String str) {
        return str != null && !str.isEmpty() ? JSONUtil.toList(str, String.class) : new ArrayList<>();
    }

    protected String mapToString(Map<String, Object> map) {
        return map != null ? JSONUtil.toJsonStr(map) : "{}";
    }

    protected String mapStringToString(Map<String, String> map) {
        return map != null ? JSONUtil.toJsonStr(map) : "{}";
    }

    protected Map<String, Object> stringToMap(String str) {
        return str != null && !str.isEmpty() ? JSONUtil.toBean(str, Map.class) : new HashMap<>();
    }

    protected Map<String, String> stringToMapString(String str) {
        return str != null && !str.isEmpty() ? JSONUtil.toBean(str, Map.class) : new HashMap<>();
    }

    protected String statusToString(EmailSendStatus status) {
        return status != null ? status.getCode() : EmailSendStatus.FAILED.getCode();
    }

    protected EmailSendStatus stringToStatus(String status) {
        return EmailSendStatus.fromCode(status);
    }
}
