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
package com.mayanshe.scrmstd.shared.model;

import java.util.Objects;

/**
 * UserDetail: 用户详情
 */
public class UserDetail {
    private final Long id;
    private final String username;
    private final String fullName;

    public UserDetail(Long id, String username, String fullName) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    // Manually implement equals(), hashCode(), and toString() if needed
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetail that = (UserDetail) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, fullName);
    }

    @Override
    public String toString() {
        return "UserDetail[id=" + id + ", username=" + username + ", fullName=" + fullName + "]";
    }

    public String toJsonString() {
        return "{\"id\":" + id + ",\"username\":\"" + username + "\",\"fullName\":\"" + fullName + "\"}";
    }
}
