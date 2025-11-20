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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MenuRepositoryImpl: 菜单仓储接口实现
 */
@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepository {

    private final MenuMapper menuMapper;
    private final MenuConverter menuConverter;

    @Override
    public void save(Menu aggregate) {
        if (aggregate.isDeleted()) {
            menuMapper.delete(aggregate.getId());
        } else {
            MenuPo po = menuConverter.toPo(aggregate);
            MenuPo exist = menuMapper.findById(po.getId());
            if (exist == null) {
                long now = System.currentTimeMillis() / 1000;
                po.setCreatedAt(now);
                po.setUpdatedAt(now);
                menuMapper.insert(po);
            } else {
                po.setUpdatedAt(System.currentTimeMillis() / 1000);
                menuMapper.update(po);
            }
        }
    }

    @Override
    public Menu find(Long id) {
        MenuPo po = menuMapper.findById(id);
        if (po == null) {
            return null;
        }
        return menuConverter.toAggregate(po);
    }

    @Override
    public void remove(Long id) {
        menuMapper.delete(id);
    }
}
