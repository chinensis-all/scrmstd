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
package com.mayanshe.scrmstd.infrastructure.external.adapter.cos;

import com.mayanshe.scrmstd.infrastructure.cache.CacheKeyGenerator;
import com.mayanshe.scrmstd.infrastructure.cache.RedisCache;
import com.mayanshe.scrmstd.shared.contract.CosClient;
import com.mayanshe.scrmstd.shared.model.CosFileDto;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.*;
import org.springframework.scheduling.annotation.Async;
import com.mayanshe.scrmstd.shared.model.CosFile;

import java.io.IOException;
import java.net.URL;
import java.util.TreeMap;

import com.tencent.cloud.cos.util.Jackson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;
import java.text.SimpleDateFormat;

/**
 * 腾讯云对象象存储Client
 */
public class TencentCosClient implements CosClient {
    private final CosSetting setting;

    private final RedisCache redis;

    public TencentCosClient(CosSetting setting, RedisCache redis) {
        this.setting = setting;
        this.redis = redis;
    }

    /**
     * 上传文件
     *
     * @param originalFilename 文件名
     * @param data             文件数据
     * @param mimeType         文件类型
     * @param isPublicRead     是否是公开读
     * @param tenantId         租户ID(null表示公共)
     * @return CosFile
     * @throws IOException 异常
     */
    public CosFile uploadFile(String originalFilename, byte[] data, String mimeType, boolean isPublicRead, Long tenantId) throws IOException {
        setting.verify();
        COSClient cosClient = getCosClient(isPublicRead);

        try (InputStream inputStream = new ByteArrayInputStream(data)) {
            String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String randomStr = UUID.randomUUID().toString().replace("-", "");
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String mediaType = getMediaType(mimeType, extension);
            String key = String.format("/%s/%s/%s/%s%s", dateStr.toLowerCase(), tenantId == null || tenantId <= 0 ? "common" : String.valueOf(tenantId), mediaType.toLowerCase(), randomStr.toLowerCase(), extension.toLowerCase());
            String bucket = isPublicRead ? setting.getPublicBucket() : setting.getBucket();

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, inputStream, null);
            PutObjectResult result = cosClient.putObject(putObjectRequest);
            if (result == null || result.getETag() == null) {
                throw new IOException("Failed to upload file to Tencent object storage");
            }

            String filePath = (isPublicRead ? "public:" : "") + key;

            return new CosFile(filePath, getFileUrl(filePath), mimeType, mediaType, data.length / 1024, isPublicRead);
        } catch (Exception e) {
            throw new IOException("Failed to upload file to Tencent object storage", e);
        } finally {
            cosClient.shutdown();
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件名(含存储路径)
     * @throws IOException 异常
     */
    public void deleteFile(String filePath) throws IOException {
        boolean isPublicRead = filePath.startsWith("public:");

        filePath = filePath.substring(isPublicRead ? 7 : 8);
        COSClient cosClient = getCosClient(isPublicRead);

        String bucket = isPublicRead ? setting.getBucket() : setting.getPublicBucket();
        try {
            cosClient.deleteObject(bucket, filePath);
        } catch (Exception e) {
            throw new IOException("Failed to delete file from Tencent object storage", e);
        } finally {
            cosClient.shutdown();
        }
    }

    /**
     * 获取文件路径
     *
     * @param fileName 文件名(含存储路径)
     * @return 文件路径(如果有CDN ， 则是CDN路径)
     * @throws IOException 异常
     */
    public String getFileUrl(String fileName) throws IOException {
        // 获取公开读链接
        if (fileName.startsWith("public:")) {
            fileName = fileName.substring(7);

            if (fileName.startsWith("/")) {
                fileName = fileName.substring(1);
            }

            if (setting.getCdnDomain() != null && !setting.getCdnDomain().isBlank()) {
                return String.format("%s/%s", setting.getCdnDomain(), fileName);
            } else {
                return String.format("https://%s.cos.%s.myqcloud.com/%s", setting.getPublicBucket(), setting.getRegion(), fileName);
            }
        }

        // 获取私有读链接
        fileName = fileName.substring(8);
        COSClient cosClient = getCosClient(false);

        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(setting.getBucket(), fileName);
        req.setExpiration(expiration);

        URL signedUrl = cosClient.generatePresignedUrl(req);
        cosClient.shutdown();

        return signedUrl.toString();
    }

    /**
     * 获取文件信息
     *
     * @param fileName 文件名(含存储路径)
     * @return CosFileDto
     * @throws IOException 异常
     */
    public CosFileDto getFileDto(String fileName) throws IOException {
        if (fileName == null || fileName.isBlank()) {
            return new CosFileDto("", "");
        }

        String fileUrl = getFileUrl(fileName);

        return new CosFileDto(fileName, fileUrl);
    }

    /**
     * 获取COS客户端
     *
     * @param isPublicRead 是否公共读
     * @return COSClient
     * @throws IOException 异常
     */
    private COSClient getCosClient(boolean isPublicRead) throws IOException {
        Credentials credentials = getCredentials(isPublicRead);
        BasicSessionCredentials cred = new BasicSessionCredentials(credentials.tmpSecretId, credentials.tmpSecretKey, credentials.sessionToken);

        Region region = new Region(setting.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setMaxErrorRetry(4);

        return new COSClient(cred, clientConfig);
    }

    /**
     * 获取腾讯云临时秘钥
     *
     * @param isPublicRead 是否公共读
     * @return 临时秘钥
     * @throws IOException 异常
     */
    private Credentials getCredentials(boolean isPublicRead) throws IOException {
        String cacheKey = CacheKeyGenerator.getTencentCosCredentialsKey(isPublicRead);
        if (redis.hasKey(cacheKey)) {
            Credentials credentials = redis.get(cacheKey, Credentials.class, null);
            if (credentials != null) {
                return credentials;
            }
        }

        TreeMap<String, Object> config = new TreeMap<>();
        try {
            Statement statement = new Statement();
            statement.setEffect("allow");
            statement.addActions(new String[]{"cos:PutObject", "cos:DeleteObject", "cos:DeleteMultipleObjects"});
            statement.addResources(new String[]{String.format("qcs::cos:%s:uid/%s:%s/*", setting.getRegion(), setting.getUid(), setting.getPublicBucket()), String.format("qcs::cos:%s:uid/%s:%s/*", setting.getRegion(), setting.getUid(), setting.getBucket())});

            Policy policy = new Policy();
            policy.addStatement(statement);

            config.put("secretId", setting.getSecretId());
            config.put("secretKey", setting.getSecretKey());
            config.put("durationSeconds", setting.getDurationSeconds());
            config.put("bucket", isPublicRead ? setting.getPublicBucket() : setting.getBucket());
            config.put("region", setting.getRegion());
            config.put("policy", Jackson.toJsonPrettyString(policy));

            Response response = CosStsClient.getCredential(config);

            storeCredentialsCache(response.credentials, setting.getDurationSeconds(), isPublicRead);

            return response.credentials;
        } catch (Exception e) {
            throw new IOException("Failed to retrieve session key for Tencent object storage");
        }
    }

    /**
     * 缓存cos临时秘钥
     *
     * @param credentials     临时秘钥
     * @param durationSeconds 有效期，单位秒
     */
    @Async
    protected void storeCredentialsCache(Credentials credentials, int durationSeconds, boolean isPublicRead) {
        String cacheKey = CacheKeyGenerator.getTencentCosCredentialsKey(isPublicRead);
        redis.set(cacheKey, credentials, durationSeconds - 300);
    }
}
