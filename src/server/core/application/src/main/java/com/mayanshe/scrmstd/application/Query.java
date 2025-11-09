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
package com.mayanshe.scrmstd.application;

import java.lang.reflect.RecordComponent;
import java.util.HashMap;
import java.util.Map;

/**
 * Query: 查询载体接口
 *
 * @param <R> 查询结果类型
 */
public interface Query<R> {
    default Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        if (this.getClass().isRecord()) {
            RecordComponent[] components = this.getClass().getRecordComponents();
            for (RecordComponent component : components) {
                try {
                    Object value = component.getAccessor().invoke(this);
                    if (value != null) {
                        map.put(component.getName(), value);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Failed to read record component: " + component.getName(), e);
                }
            }
        } else {
            try {
                var fields = this.getClass().getDeclaredFields();
                for (var field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(this);
                    if (value != null) {
                        map.put(field.getName(), value);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to convert to map", e);
            }
        }

        return map;
    }
}
