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

import com.mayanshe.scrmstd.infrastructure.external.converter.TenantDispatcherConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.TenantDispatcherMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.TenantDispatcherPo;
import com.mayanshe.scrmstd.shared.exception.BadRequestException;
import com.mayanshe.scrmstd.shared.exception.InternalServerException;
import com.mayanshe.scrmstd.tenant.configuration.model.TenantDispatcher;
import com.mayanshe.scrmstd.tenant.configuration.repo.TenantDispatcherRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TenantDispatcherRepositoryImpl implements TenantDispatcherRepository {
    private final TenantDispatcherMapper mapper;

    public TenantDispatcherRepositoryImpl(TenantDispatcherMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 根据租户ID和分发类型加载租户定制处理器列表
     *
     * @param tenantId     租户ID
     * @param dispatchType 分发类型
     * @return 租户定制处理器列表
     */
    public List<TenantDispatcher> loadList(Long tenantId, String dispatchType) {
        Map<String, Object> params = Map.of("tenantId", tenantId, "dispatchType", dispatchType);
        return mapper.search(params).stream().map(TenantDispatcherConverter.INSTANCE::toAggregate).toList();
    }

    /**
     * 加载聚合根
     *
     * @param id 聚合根ID
     * @return 聚合根
     */
    @Override
    public Optional<TenantDispatcher> load(Long id) {
        TenantDispatcherPo po = getPo(id);
        if (po == null) {
            return Optional.empty();
        }

        return Optional.of(TenantDispatcherConverter.INSTANCE.toAggregate(po));
    }

    @Override
    public void save(TenantDispatcher aggregate) {
        // 删除
        if (aggregate.isDeleted()) {
            mapper.delete(aggregate.getId().id());
            return;
        }

        validateExistence(aggregate);
        TenantDispatcherPo po = TenantDispatcherConverter.INSTANCE.toPo(aggregate);

        if (aggregate.getId().newed()) {
            if (mapper.insert(po) <= 0) {
                throw new InternalServerException("保存租户定制处理器失败");
            }
            return;
        }

        if (mapper.update(po) <= 0) {
            throw new InternalServerException("更新租户定制处理器信息失败");
        }
    }

    /**
     * 验证租户定制处理器是否已存在
     *
     * @param aggregate 聚合根
     */
    private void validateExistence(TenantDispatcher aggregate) {
        Map<String, Object> entityParams = Map.of("tenantId", aggregate.getTenantId(), "dispatcherClass", aggregate.getDispatcherClass());
        if (mapper.exists(entityParams)) {
            throw new BadRequestException("租户定制处理器已配置");
        }

        Map<String, Object> handlerParams = Map.of("tenantId", aggregate.getTenantId(), "dispatcherClass", aggregate.getDispatcherClass());
        if (mapper.exists(handlerParams)) {
            throw new BadRequestException("租户定制处理器已配置");
        }
    }

    /**
     * 获取持久化对象
     *
     * @param id 持久化对象ID
     * @return 持久化对象
     */
    private TenantDispatcherPo getPo(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        return mapper.findById(id);
    }
}
