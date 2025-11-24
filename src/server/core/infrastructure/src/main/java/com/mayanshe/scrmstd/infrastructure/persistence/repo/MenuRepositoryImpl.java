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

import com.mayanshe.scrmstd.infrastructure.external.converter.MenuConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.MenuMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.MenuPo;
import com.mayanshe.scrmstd.platform.identity.model.Menu;
import com.mayanshe.scrmstd.platform.identity.repo.MenuRepository;
import com.mayanshe.scrmstd.shared.annotation.SaveDomainEvents;
import com.mayanshe.scrmstd.shared.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

/**
 * MenuRepositoryImpl: 菜单仓储接口实现
 */
@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepository {

    private final MenuMapper mapper;

    private MenuPo getPo(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        return mapper.findById(id);
    }

    @Override
    public Optional<Menu> load(Long id) {
        MenuPo po = getPo(id);
        if (po == null) {
            return Optional.empty();
        }
        Menu aggregate = MenuConverter.INSTANCE.toAggregate(po);
        return Optional.of(aggregate);
    }


    @Override
    @Transactional
    @SaveDomainEvents
    public void save(Menu aggregate) {
        if (aggregate.isDeleted()) {
            if (mapper.delete(aggregate.getId().id()) <= 0) {
                throw new InternalServerException("删除菜单失败: " + aggregate.getId().id());
            }
        }

        verifyConflict(aggregate);
        MenuPo po = MenuConverter.INSTANCE.toPo(aggregate);

        if (aggregate.getId().newed()) {
            if (mapper.insert(po) <= 0) {
                throw new InternalServerException("新增菜单失败: " + aggregate.getName());
            }
        }

        if (mapper.update(po) <= 0) {
            throw new InternalServerException("修改菜单失败: " + aggregate.getId().id());
        }
    }

    private void verifyConflict(Menu aggregate) {
        Long nameExistingId = mapper.findIdByCondition(Map.of("name", aggregate.getName()));
        if (nameExistingId != null && !nameExistingId.equals(aggregate.getId().id())) {
            throw new InternalServerException("菜单名称已存在: " + aggregate.getName());
        }
    }
}
