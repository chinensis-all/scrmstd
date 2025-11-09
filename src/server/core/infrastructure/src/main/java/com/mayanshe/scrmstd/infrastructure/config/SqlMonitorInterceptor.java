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

import com.mayanshe.scrmstd.shared.util.PrintUtil;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SqlMonitorInterceptor: MyBatis SQL监控拦截器
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, org.apache.ibatis.session.ResultHandler.class}), @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}), @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
@Component
@Profile({"dev", "test"})
public class SqlMonitorInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(SqlMonitorInterceptor.class);

    @Value("${mybatis.sql-print-to}")
    private String sqlPrintTo = "";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long end = System.currentTimeMillis();
            StatementHandler handler = (StatementHandler) invocation.getTarget();
            BoundSql boundSql = handler.getBoundSql();

            ParameterHandler parameterHandler = handler.getParameterHandler();
            Object parameterObject = parameterHandler.getParameterObject();

            String sql = boundSql.getSql().replace("\n", " ").replaceAll("\n+", "").replaceAll(" +", " ");

            List<ParameterMapping> parameters = boundSql.getParameterMappings();
            Pattern placeholderPattern = Pattern.compile("\\?");
            Matcher matcher = placeholderPattern.matcher(sql);
            StringBuilder formattedSql = new StringBuilder();
            int parameterIndex = 0;
            int lastIndex = 0;

            // Replace placeholders with actual parameter values
            while (matcher.find()) {
                if (parameterIndex >= parameters.size()) {
                    logger.warn("More placeholders than parameters in SQL: {}", sql);
                    break;
                }
                ParameterMapping parameter = parameters.get(parameterIndex++);
                String parameterName = parameter.getProperty();
                Class<?> javaType = parameter.getJavaType();
                String parameterValue = getParameterValue(parameterObject, parameterName, javaType);

                // Append part of SQL before the placeholder
                formattedSql.append(sql, lastIndex, matcher.start());
                // Append the escaped parameter value
                formattedSql.append(parameterValue);
                lastIndex = matcher.end();
            }
            // Append remaining SQL
            formattedSql.append(sql.substring(lastIndex));
            sql = formattedSql.toString();

            long span = end - start;
            String message = String.format("SQL: %s | Time: %d ms", sql, span);

            PrintUtil.toConsole(span > 50, message);

            Path printTo = Paths.get(sqlPrintTo);
            if (Files.exists(printTo)) {
                PrintUtil.toFile(sqlPrintTo, message);
            } else {
                logger.info(message);
            }
        }
    }

    private String getParameterValue(Object parameterObject, String property, Class<?> javaType) {
        try {
            Object value;
            if (parameterObject instanceof java.util.Map) {
                value = ((java.util.Map<?, ?>) parameterObject).get(property);
            } else if (parameterObject instanceof Number || parameterObject instanceof String || parameterObject instanceof Boolean) {
                value = parameterObject;
            } else {
                java.lang.reflect.Field field = parameterObject.getClass().getDeclaredField(property);
                field.setAccessible(true);
                value = field.get(parameterObject);
            }

            if (value == null) {
                return "NULL";
            }
            if (javaType == String.class) {
                // Escape single quotes and dollar signs
                String escapedValue = value.toString().replace("'", "''").replace("$", "\\$");
                return "'" + escapedValue + "'";
            }
            return value.toString();
        } catch (Exception e) {
            logger.warn("Failed to get parameter value for property {}: {}", property, e.getMessage());
            return "UNKNOWN";
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}