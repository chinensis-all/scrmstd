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
package com.mayanshe.scrmstd.infrastructure.persistence.po;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class RegionPo {
    private Long id;                      // 主键ID

    private Long parentId;                // 行政区划代码

    private Short regionLevel;            // 行政区划级别：1-省级；2-地市级；3-区县级；4-乡镇级；5-村级

    private String postalCode;            // 邮政编码

    private String areaCode;              // 区号

    private String regionName;            // 行政区划名称

    private String namePinyin;            // 行政区划名称拼音

    private String shortName;             // 行政区划简称

    private String mergeName;             // 行政区划组合名称

    private String longitude;             // 经度

    private String latitude;              // 纬度
}
