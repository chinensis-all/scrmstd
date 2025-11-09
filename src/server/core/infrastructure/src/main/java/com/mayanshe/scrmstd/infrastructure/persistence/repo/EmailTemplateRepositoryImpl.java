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

import com.mayanshe.scrmstd.infrastructure.external.converter.EmailTemplateConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.EmailTemplateMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.EmailTemplatePo;
import com.mayanshe.scrmstd.message.template.model.EmailTemplate;
import com.mayanshe.scrmstd.message.template.repo.EmailTemplateRepository;
import com.mayanshe.scrmstd.shared.annotation.SaveDomainEvents;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

/**
 * EmailTemplateRepositoryImpl: 邮件模板仓储实现
 */
@Repository
public class EmailTemplateRepositoryImpl implements EmailTemplateRepository {
    private final EmailTemplateMapper mapper;
    private final EmailTemplateConverter converter;

    public EmailTemplateRepositoryImpl(EmailTemplateMapper mapper, @Lazy EmailTemplateConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }

    @Override
    public Optional<EmailTemplate> load(Long id) {
        EmailTemplatePo po = this.getPo(id);
        if (po == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(converter.toAggregate(po));
    }

    @Override
    @Transactional
    @SaveDomainEvents
    public void save(EmailTemplate aggregate) {
        // 删除
        if (aggregate.isDeleted()) {
            if (mapper.delete(aggregate.getId().id()) <= 0) {
                throw new RuntimeException("删除邮件模板失败，ID=" + aggregate.getId().id());
            }
            return;
        }

        verifyExistence(aggregate);
        EmailTemplatePo po = converter.toPo(aggregate);

        // 新增
        if (aggregate.getId().newed()) {
            if (mapper.insert(po) <= 0) {
                throw new RuntimeException("新增邮件模板失败，" + aggregate);
            }
            return;
        }

        // 修改
        if (mapper.update(po) <= 0) {
            throw new RuntimeException("修改邮件模板失败，" + aggregate);
        }
    }

    /**
     * 验证唯一性
     *
     * @param aggregate 邮件模板聚合根
     */
    private void verifyExistence(EmailTemplate aggregate) {
        Long existingId = mapper.findIdBy(Map.of("tenantId", aggregate.getTenantId(), "templateName", aggregate.getTemplateName()));
        if (existingId != null && !existingId.equals(aggregate.getId().id())) {
            throw new RuntimeException("邮件模板名称已存在，templateName=" + aggregate.getTemplateName());
        }
    }

    /**
     * 根据ID获取邮件模板
     *
     * @param id 邮件模板ID
     * @return 邮件模板PO
     */
    private EmailTemplatePo getPo(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        return mapper.findById(id);
    }
}
