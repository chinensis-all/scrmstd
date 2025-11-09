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

/**
 * 对象存储文件相应结构
 *
 * @param filePath     文件名(含存储路径)
 * @param fileUrl      全路径(如果有CDN，则是CDN路径)
 * @param mimeType     MIME类型
 * @param mediaType    媒体类型(image、video、audio、document等)
 * @param size         大小(kb)
 * @param isPublicRead 是否是共有读
 */
public record CosFile(
        String filePath,
        String fileUrl,
        String mimeType,
        String mediaType,
        long size,
        boolean isPublicRead
) {}