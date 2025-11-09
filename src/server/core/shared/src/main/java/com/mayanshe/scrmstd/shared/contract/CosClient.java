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
package com.mayanshe.scrmstd.shared.contract;

import com.mayanshe.scrmstd.shared.model.CosFile;
import com.mayanshe.scrmstd.shared.model.CosFileDto;

import java.io.IOException;

public interface CosClient {
    /**
     * 上传文件
     *
     * @param originalFilename 文件名
     * @param data             文件数据
     * @param mimeType         文件类型
     * @param isPublicRead     是否是公开读
     * @param tenantId         租户ID(null表示公共)
     * @return CosFileResponse
     */
    CosFile uploadFile(String originalFilename, byte[] data, String mimeType, boolean isPublicRead, Long tenantId) throws IOException;

    /**
     * 删除文件
     *
     * @param originalFilename 文件名(含存储路径)
     */
    void deleteFile(String originalFilename) throws IOException;

    /**
     * 获取文件路径
     *
     * @param fileName 文件名(含存储路径)
     * @return 文件路径(如果有CDN ， 则是CDN路径)
     */
    String getFileUrl(String fileName) throws IOException;

    CosFileDto getFileDto(String fileName) throws IOException;

    /**
     * 根据MIME类型和扩展名获取媒体类型
     *
     * @param mimeType  MIME类型
     * @param extension 扩展名
     * @return 媒体类型(image 、 video 、 audio 、 document等)
     */
    default String getMediaType(String mimeType, String extension) {
        mimeType = mimeType.toLowerCase();
        extension = extension.toLowerCase();

        if (mimeType.startsWith("image/") || extension.matches("jpg|jpeg|png|gif|bmp|webp")) {
            return "image";
        }

        if (mimeType.startsWith("video/") || extension.matches("mp4|avi|mov|wmv|mkv")) {
            return "video";
        }

        if (mimeType.startsWith("audio/") || extension.matches("mp3|wav|ogg|aac|flac")) {
            return "audio";
        }

        if (mimeType.contains("pdf") || extension.matches("pdf|doc|docx|xls|xlsx|ppt|pptx|txt")) {
            return "document";
        }

        if (mimeType.contains("zip") || extension.matches("zip|rar|7z|tar|gz")) {
            return "archive";
        }

        return "other";
    }
}
