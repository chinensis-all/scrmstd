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
package com.mayanshe.scrmstd.application.message.query.handler;

import com.mayanshe.scrmstd.application.QueryHandler;
import com.mayanshe.scrmstd.application.message.query.EmailTemplateDetailQuery;
import com.mayanshe.scrmstd.application.message.query.dto.EmailTemplateDto;
import com.mayanshe.scrmstd.application.message.query.repo.EmailTemplateQueryRepository;
import com.mayanshe.scrmstd.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 查询处理器：获取邮件模板详情
 */
@Service
public class GetEmailTemplateDetailHandler implements QueryHandler<EmailTemplateDetailQuery, EmailTemplateDto> {
    private final EmailTemplateQueryRepository repository;

    public GetEmailTemplateDetailHandler(EmailTemplateQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmailTemplateDto handle(EmailTemplateDetailQuery query) {
        return repository.single(query.templateId())
                .orElseThrow(() -> new ResourceNotFoundException("邮件模板不存在"));
    }
}
