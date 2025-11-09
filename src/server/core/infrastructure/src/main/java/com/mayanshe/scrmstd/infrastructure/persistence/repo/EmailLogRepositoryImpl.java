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

import com.mayanshe.scrmstd.infrastructure.external.converter.EmailLogConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.EmailLogMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.EmailLogPo;
import com.mayanshe.scrmstd.message.log.model.EmailLog;
import com.mayanshe.scrmstd.message.log.repo.EmailLogRepository;
import com.mayanshe.scrmstd.shared.annotation.SaveDomainEvents;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 邮件日志仓储实现
 */
@Repository
public class EmailLogRepositoryImpl implements EmailLogRepository {
    private final EmailLogMapper mapper;
    private final EmailLogConverter converter;

    public EmailLogRepositoryImpl(EmailLogMapper mapper, @Lazy EmailLogConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }

    @Override
    public Optional<EmailLog> load(Long id) {
        EmailLogPo po = this.getPo(id);
        if (po == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(converter.toAggregate(po));
    }

    @Override
    @Transactional
    @SaveDomainEvents
    public void save(EmailLog aggregate) {
        EmailLogPo po = converter.toPo(aggregate);

        // 新增
        if (aggregate.getId().newed()) {
            if (mapper.insert(po) <= 0) {
                throw new RuntimeException("新增邮件日志失败，" + aggregate);
            }
            return;
        }

        // 修改
        if (mapper.update(po) <= 0) {
            throw new RuntimeException("修改邮件日志失败，" + aggregate);
        }
    }

    /**
     * 根据ID获取邮件日志
     *
     * @param id 邮件日志ID
     * @return 邮件日志PO
     */
    private EmailLogPo getPo(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        return mapper.findById(id);
    }
}
