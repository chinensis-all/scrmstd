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
import com.mayanshe.scrmstd.application.message.query.dto.EmailTemplateDto;
import com.mayanshe.scrmstd.infrastructure.persistence.po.EmailTemplatePo;
import com.mayanshe.scrmstd.message.template.model.EmailTemplate;
import com.mayanshe.scrmstd.message.template.type.EmailTemplateCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * EmailTemplateConverter: 邮件模板转换器
 */
@Mapper(componentModel = "spring")
@Component
public abstract class EmailTemplateConverter implements BaseConverter {
    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", source = "deleted", qualifiedByName = "deletedToDeletedAt"),
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
            @Mapping(target = "category", expression = "java(categoryToString(aggregate.getCategory()))"),
            @Mapping(target = "placeholders", expression = "java(placeholdersToString(aggregate.getPlaceholders()))")
    })
    public abstract EmailTemplatePo toPo(EmailTemplate aggregate);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", source = "deleted", qualifiedByName = "deletedToDeletedAt"),
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
            @Mapping(target = "category", expression = "java(categoryToString(aggregate.getCategory()))"),
            @Mapping(target = "placeholders", expression = "java(placeholdersToString(aggregate.getPlaceholders()))")
    })
    public abstract EmailTemplatePo updatePo(EmailTemplate aggregate, @MappingTarget EmailTemplatePo po);

    @Mappings({
            @Mapping(target = "deleted", source = "deletedAt", qualifiedByName = "deletedAtToDeleted"),
            @Mapping(target = "id", source = "id", qualifiedByName = "idToAggregateId"),
            @Mapping(target = "category", expression = "java(stringToCategory(po.getCategory()))"),
            @Mapping(target = "placeholders", expression = "java(stringToPlaceholders(po.getPlaceholders()))")
    })
    public abstract EmailTemplate toAggregate(EmailTemplatePo po);

    @Mappings({
            @Mapping(target = "category", expression = "java(stringToCategory(po.getCategory()))"),
            @Mapping(target = "placeholders", expression = "java(stringToPlaceholders(po.getPlaceholders()))")
    })
    public abstract EmailTemplateDto toDto(EmailTemplatePo po);

    protected String categoryToString(EmailTemplateCategory category) {
        return category != null ? category.getCode() : EmailTemplateCategory.OTHER.getCode();
    }

    protected EmailTemplateCategory stringToCategory(String category) {
        return EmailTemplateCategory.fromCode(category);
    }

    protected String placeholdersToString(List<String> placeholders) {
        return placeholders != null ? JSONUtil.toJsonStr(placeholders) : "[]";
    }

    protected List<String> stringToPlaceholders(String placeholders) {
        return JSONUtil.toList(placeholders, String.class);
    }
}
