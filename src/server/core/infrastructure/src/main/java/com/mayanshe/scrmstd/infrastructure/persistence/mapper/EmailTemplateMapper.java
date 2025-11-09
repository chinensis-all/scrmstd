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
package com.mayanshe.scrmstd.infrastructure.persistence.mapper;

import com.mayanshe.scrmstd.application.message.query.dto.EmailTemplateDto;
import com.mayanshe.scrmstd.infrastructure.persistence.po.EmailTemplatePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * EmailTemplateMapper: 邮件模板Mapper
 */
@Mapper
public interface EmailTemplateMapper extends PaginateMapper<EmailTemplatePo> {
    long insert(EmailTemplatePo po);

    long update(EmailTemplatePo po);

    Long delete(Long id);

    @Select("SELECT * FROM email_templates WHERE id = #{id}")
    EmailTemplatePo findById(Long id);

    @Select("SELECT id, tenant_id, template_name, category, subject, body, placeholders, created_at, updated_at FROM email_templates WHERE id = #{id} AND deleted_at = 0")
    EmailTemplateDto selectById(Long id);

    Long findIdBy(Map<String, Object> params);

    Boolean exists(Map<String, Object> params);
}
