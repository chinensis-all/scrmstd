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
package com.mayanshe.scrmstd.file.storeage.service;

import com.mayanshe.scrmstd.file.storeage.event.FileDeletedEvent;
import com.mayanshe.scrmstd.file.storeage.event.FileStoredEvent;

import java.io.IOException;

/**
 * CosStorageService: 对象存储服务借口
 */
public interface CosStorageService {
    /**
     * 上传文件
     *
     * @param originalFilename 原始文件名
     * @param data             文件数据
     * @param mimeType         MIME类型
     * @param isPublicRead     是否是共有读
     * @param tenantId         租户ID
     * @return 文件存储事件
     * @throws IOException 异常
     */
    FileStoredEvent upload(String originalFilename, byte[] data, String mimeType, boolean isPublicRead, Long tenantId) throws IOException;

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 文件删除事件
     * @throws IOException 异常
     */
    FileDeletedEvent delete(String filePath) throws IOException;
}
