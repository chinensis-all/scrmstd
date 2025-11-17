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
package com.mayanshe.scrmstd.domain.tenant.identity.event;

import com.mayanshe.scrmstd.shared.contract.DomainEvent;
import lombok.Builder;

import java.time.Instant;

/**
 * @author: 张西海
 * @date: 2025-11-16
 * @version: 1.0
 * @description:
 */
@Builder(toBuilder = true)
public record ActivatePermissionEvent(
        Long id,
        Long occurredAt
) implements DomainEvent {
    public ActivatePermissionEvent(
            Long id,
            Long occurredAt
    ) {
        this.id = id;
        this.occurredAt = Instant.now().toEpochMilli();
    }
}
