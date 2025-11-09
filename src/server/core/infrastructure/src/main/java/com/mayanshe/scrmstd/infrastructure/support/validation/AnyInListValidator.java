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
package com.mayanshe.scrmstd.infrastructure.support.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 注解实现 : 字符串是否在指定列表中
 */
public class AnyInListValidator implements ConstraintValidator<AnyInList, Object> {
    private Set<String> allowedValues;

    private boolean matchEnumName;

    @Override
    public void initialize(AnyInList annotation) {
        allowedValues = Arrays.stream(annotation.value()).filter(Objects::nonNull).collect(Collectors.toSet());
        matchEnumName = annotation.matchEnumName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;

        // ✅ 统一转换成字符串再比对
        String strValue;

        if (value instanceof Enum<?> e) {
            strValue = matchEnumName ? e.name() : e.toString();
        } else {
            strValue = String.valueOf(value);
        }

        return allowedValues.contains(strValue);
    }
}
