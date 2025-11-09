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
package com.mayanshe.scrmstd.shared.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * PrintUtil: 控制台打印工具类
 */
public final class PrintUtil {
    private PrintUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static String wrapSmart(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        final int limit = 120; // 每段最大字符数
        StringBuilder sb = new StringBuilder(input);
        int index = limit;

        while (index < sb.length()) {
            int breakPos = -1;

            // 从 index 开始向后查找最近的 , 或 空格
            for (int i = index; i < sb.length(); i++) {
                char c = sb.charAt(i);
                if (c == ',' || c == ' ') {
                    breakPos = i;
                    break;
                }
            }

            // 如果找到合适的断点
            if (breakPos != -1) {
                sb.insert(breakPos + 1, '\n');
                // 下一个计数从换行后的第一个字符继续
                index = breakPos + 1 + limit;
            } else {
                // 没找到合适断点就直接硬断
                sb.insert(index, '\n');
                index += limit + 1;
            }
        }

        return sb.toString();
    }

    /** 信息打印，支持占位符{}
     *
     * @param message 消息内容，支持占位符{}
     * @param args    可变参数，任意数量的对象
     */
    public static void info(String message, Object... args) {
        message = getString(message, args);

        System.out.printf("\u001B[34m%s\u001B[0m %n", message);
    }

    /** 将消息中的占位符{}替换为参数的JSON字符串
     *
     * @param message 消息内容，支持占位符{}
     * @param args    可变参数，任意数量的对象
     * @return 替换后的消息字符串
     */
    private static String getString(String message, Object[] args) {
        if (args != null && args.length > 0) {
            ObjectMapper objectMapper = new ObjectMapper();

            for (Object arg : args) {
                try {
                    String json = objectMapper.writeValueAsString(arg);
                    message = message.replaceFirst("\\{\\}", json);
                } catch (Exception e) {
                    System.out.printf("\u001B[31m%s\u001B[0m %n", e.getMessage());
                }
            }
        }
        return message;
    }

    /** 错误打印，支持占位符{}
     *
     * @param message 消息内容，支持占位符{}
     * @param args    可变参数，任意数量的对象
     */
    public static void error(String message, Object... args) {
        message = getString(message, args);

        System.out.printf("\u001B[31m%s\u001B[0m %n", message);
    }

    /**
     * 打印到控制台命令行
     *
     * @param args 可变参数，任意数量的对象
     */
    public static void toConsole(boolean stress, Object... args) {
        if (args == null || args.length == 0) {
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        for (Object arg : args) {
            try {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);

                String json = objectMapper.writeValueAsString(arg);
                json = wrapSmart(json);

                if (stress) {
                    System.out.printf("\u001B[93m[%s]\u001B[0m \u001B[31m %s \u001B[0m %n", formattedDateTime, json);
                    System.out.println("                       \u001B[31m^\u001B[0m");
                } else {
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    if (json.startsWith("\"SQL")) {
                        System.out.printf("\u001B[93m[%s]\u001B[0m \u001B[32m %s \u001B[0m %n", formattedDateTime, json);
                        System.out.println("                       \u001B[32m^\u001B[0m");
                    } else {
                        System.out.printf("\u001B[93m[%s]\u001B[0m \u001B[36m %s \u001B[0m %n", formattedDateTime, json);
                        System.out.println("                       \u001B[36m^\u001B[0m");
                    }
                }
            } catch (Exception e) {
                System.err.println("打印错误: " + e.getMessage());
            }
        }
    }

    /**
     * 打印到指定文件
     *
     * @param file 文件路径
     * @param args 可变参数，任意数量的对象
     */
    public static void toFile(String file, Object... args) {
        if (file == null || file.isEmpty() || args == null || args.length == 0) {
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        for (Object arg : args) {
            try {

                String json = objectMapper.writeValueAsString(arg);
                sb.append(String.format("[%s] %s", formattedDateTime, json)).append(System.lineSeparator()).append(System.lineSeparator());
            } catch (Exception e) {
                System.err.println("Error converting object to JSON: " + e.getMessage());
            }
        }

        try {
            Path path = Paths.get(file);
            Files.write(path, sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * 发送到指定邮箱地址
     *
     * @param email 邮箱地址
     * @param args  可变参数，任意数量的对象
     */
    public static void toEmail(String email, Object... args) {
        if (email == null || email.isEmpty() || args == null || args.length == 0) {
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder();

        for (Object arg : args) {
            try {
                String json = objectMapper.writeValueAsString(arg);
                sb.append(json).append(System.lineSeparator());
            } catch (Exception e) {
                System.err.println("Error converting object to JSON: " + e.getMessage());
            }
        }

        // todo: 实现发送邮件的逻辑
    }
}
