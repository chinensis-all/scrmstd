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
package com.mayanshe.scrmstd.infrastructure.config;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * MyBatis拦截器：自动设置PO的createdAt和updatedAt字段
 * 适用于所有插入和更新操作
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Component
public class MybatisTimestampInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object parameter = invocation.getArgs()[1];

            // 获取SQL命令类型
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

            if (parameter != null) {
                // 获取参数对象的MetaObject
                MetaObject metaObject = SystemMetaObject.forObject(parameter);

                // 获取当前时间戳(毫秒级)
                long currentTimestamp = System.currentTimeMillis();

                if (sqlCommandType == SqlCommandType.INSERT) {
                    // 插入操作设置createdAt和updatedAt
                    if (metaObject.hasGetter("createdAt")) {
                        metaObject.setValue("createdAt", currentTimestamp);
                    }
                    if (metaObject.hasGetter("updatedAt")) {
                        metaObject.setValue("updatedAt", currentTimestamp);
                    }
                } else if (sqlCommandType == SqlCommandType.UPDATE) {
                    // 更新操作只设置updatedAt
                    if (metaObject.hasGetter("updatedAt")) {
                        metaObject.setValue("updatedAt", currentTimestamp);
                    }
                }
            }
        } catch (Exception ignore) {}

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
