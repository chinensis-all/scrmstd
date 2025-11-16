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
package com.mayanshe.scrmstd.infrastructure.persistence.repo;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.message.query.dto.EmailTemplateDto;
import com.mayanshe.scrmstd.application.message.query.repo.EmailTemplateQueryRepository;
import com.mayanshe.scrmstd.infrastructure.external.converter.EmailTemplateConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.EmailTemplateMapper;
import com.mayanshe.scrmstd.infrastructure.support.Pager;
import com.mayanshe.scrmstd.shared.model.Pagination;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 邮件模板查询仓储实现
 */
@Repository
public class EmailTemplateQueryRepositoryImpl implements EmailTemplateQueryRepository {
    private final EmailTemplateMapper mapper;

    private final EmailTemplateConverter converter;

    public EmailTemplateQueryRepositoryImpl(EmailTemplateMapper mapper, EmailTemplateConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }

    @Override
    public Optional<EmailTemplateDto> single(Long templateId) {
        if (templateId == null || templateId <= 0) {
            return Optional.empty();
        }

        EmailTemplateDto dto = mapper.selectById(templateId);
        if (dto != null) {
            return Optional.of(dto);
        }

        return Optional.empty();
    }

    @Override
    public List<OptionDto> search(Map<String, Object> criteria, long limit) {
        if (criteria == null) {
            criteria = new HashMap<>();
        }
        if (limit > 0) {
            criteria.put("limit", limit);
        } else {
            criteria.put("limit", 200);
        }

        return mapper.search(criteria).stream().map(po -> new OptionDto(po.getId().toString(), po.getTemplateName())).toList();
    }

    @Override
    public Pagination<EmailTemplateDto> paginate(Map<String, Object> criteria, long page, long size) {
        if (criteria == null) {
            criteria = new HashMap<>();
        }

        return Pager.paginate(mapper, criteria, converter::toDto, page, size);
    }
}
