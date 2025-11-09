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
package com.mayanshe.scrmstd.infrastructure.external.converter;

import com.mayanshe.scrmstd.shared.model.AggregateId;
import org.mapstruct.Named;

public interface BaseConverter {
    @Named("aggregateIdToId")
    default Long aggregateIdToId(AggregateId aggregateId) {
        if (aggregateId == null || aggregateId.id() <= 0) {
            throw new IllegalArgumentException("Invalid AggregateId: ");
        }

        return aggregateId.id();
    }

    @Named("idToAggregateId")
    default AggregateId idToAggregateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }

        return new AggregateId(id, false);
    }

    @Named("deletedToDeletedAt")
    default long deletedToDeletedAt(boolean deleted) {
        return deleted ? System.currentTimeMillis() : 0L;
    }

    @Named("deletedAtToDeleted")
    default boolean deletedAtToDeleted(Long deletedAt) {
        return deletedAt != null && deletedAt > 0;
    }
}
