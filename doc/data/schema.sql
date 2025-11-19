CREATE DATABASE IF NOT EXISTS `scrmstd` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `scrmstd`;
SET NAMES utf8mb4;

DROP TABLE IF EXISTS `_events`;
CREATE TABLE IF NOT EXISTS `_events`
(
    `id`                 BIGINT UNSIGNED         NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    `ref_id`             BIGINT UNSIGNED         NOT NULL DEFAULT 0 COMMENT '事件关联ID',
    `event_type`         VARCHAR(75)             NOT NULL DEFAULT '' COMMENT '事件类型',
    `event_id`           VARCHAR(125)            NOT NULL DEFAULT '' COMMENT '事件标识',
    `event_data`         TEXT                    NOT NULL COMMENT '事件数据(仅关键数据)',
    `occurred_at`        BIGINT UNSIGNED         NOT NULL DEFAULT 0 COMMENT '事件发生时间戳',
    `state`              TINYINT(1) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '事件状态(0-待发布 1-已发布 2-发布失败)',
    `created_by`         VARCHAR(255)            NOT NULL DEFAULT '' COMMENT '创建人',
    `created_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '创建时间',
    `updated_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '更新时间',
    `version`            BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '乐观锁',
    INDEX `idx_ref_id` (`ref_id`) USING BTREE COMMENT '事件关联ID索引',
    UNIQUE KEY `uk_event_id` (`event_id`) USING BTREE COMMENT '事件标识唯一索引',
    INDEX `idx_event_type` (`event_type`) USING BTREE COMMENT '事件类型索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT '事件表';

DROP TABLE IF EXISTS `_boss_admins`;
CREATE TABLE IF NOT EXISTS `_boss_admins`
(
    `id`                 BIGINT UNSIGNED         NOT NULL PRIMARY KEY COMMENT 'ID',
    `username`           VARCHAR(45)             NOT NULL COMMENT '用户名',
    `password`           VARCHAR(64)             NOT NULL COMMENT '密码（加密存储）',
    `full_name`          VARCHAR(100)            NOT NULL COMMENT '姓名',
    `email`              VARCHAR(100)            NOT NULL COMMENT '邮箱',
    `phone`              VARCHAR(20)             NOT NULL COMMENT '联系电话',
    `avatar`             VARCHAR(255)            NOT NULL DEFAULT '' COMMENT '头像URL',
    `last_login_at`      BIGINT UNSIGNED         NOT NULL DEFAULT 0 COMMENT '最后登录时间',
    `last_login_ip`      VARCHAR(45)             NOT NULL DEFAULT '' COMMENT '最后登录IP',
    `created_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '创建时间',
    `updated_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '更新时间',
    `deleted_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '软删除时间',
    UNIQUE KEY `uk_username` (`username`) USING BTREE COMMENT '用户名唯一索引',
    UNIQUE KEY `uk_email` (`email`) USING BTREE COMMENT '邮箱唯一索引',
    UNIQUE KEY `uk_phone` (`phone`) USING BTREE COMMENT '联系电话唯一索引'
) ENGINE = InnoDB COMMENT '系统管理员表';

DROP TABLE IF EXISTS `_contacts`;
CREATE TABLE IF NOT EXISTS `_contacts`
(
    `id`                 BIGINT UNSIGNED         NOT NULL PRIMARY KEY COMMENT 'ID',
    `identity`           ENUM('boss', 'admin', 'user')  NOT NULL DEFAULT 'user' COMMENT '联系人身份（系统管理员、租户管理员、普通用户）',
    `full_name`          VARCHAR(100)            NOT NULL COMMENT '联系人姓名',
    `email`              VARCHAR(125)            NOT NULL COMMENT '联系人邮箱',
    `phone`              VARCHAR(20)             NOT NULL COMMENT '联系人手机号',
    `created_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '创建时间',
    `updated_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '更新时间',
    INDEX `idx_email` (`email`) USING BTREE COMMENT '邮箱索引',
    INDEX `idx_phone` (`phone`) USING BTREE COMMENT '手机号索引'
) ENGINE = InnoDB COMMENT '联系信息表';

DROP TABLE IF EXISTS `tenants`;
CREATE TABLE IF NOT EXISTS `tenants`
(
    `id`                    BIGINT UNSIGNED    NOT NULL PRIMARY KEY COMMENT 'ID 租户编码',
    `tenant_name`           VARCHAR(100)       NOT NULL COMMENT '租户名称（企业或组织名称）',
    `legal_name`            VARCHAR(125)       NOT NULL DEFAULT '' COMMENT '法人姓名',
    `credit_code`           CHAR(18)           NOT NULL DEFAULT '' COMMENT '统一社会信用代码',
    `industry_code`         VARCHAR(10)        NOT NULL DEFAULT '' COMMENT '行业分类代码（基于 GB/T 4754-2017，例如 A01 为农业）',
    `industry_name`         VARCHAR(100)       NOT NULL DEFAULT '' COMMENT '行业分类名称, 冗余存储',
    `province_id`           BIGINT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '所在省ID',
    `province`              VARCHAR(50)        NOT NULL DEFAULT '' COMMENT '所在省名称',
    `city_id`               BIGINT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '所在市ID',
    `city`                  VARCHAR(50)        NOT NULL DEFAULT '' COMMENT '所在市名称',
    `district_id`           BIGINT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '所在区县ID',
    `district`              VARCHAR(50)        NOT NULL DEFAULT '' COMMENT '所在区县名称',
    `address`               VARCHAR(255)       NOT NULL DEFAULT '' COMMENT '详细地址',
    `contact_person`        VARCHAR(125)       NOT NULL DEFAULT '' COMMENT '联系人姓名',
    `contact_phone`         VARCHAR(20)        NOT NULL DEFAULT '' COMMENT '联系电话（支持中国手机号格式）',
    `contact_email`         VARCHAR(125)       NOT NULL DEFAULT '' COMMENT '联系邮箱',
    `contact_wechat`        VARCHAR(50)        NOT NULL DEFAULT '' COMMENT '联系微信号（中国市场常用）',
    `subscription_plan`     ENUM('basic', 'standard', 'premium', 'enterprise', 'custom', 'none')    NOT NULL DEFAULT 'none' COMMENT '订阅计划（基础、标准、高级、企业级）',
    `subscription_start`    DATE               DEFAULT NULL COMMENT '订阅开始日期',
    `subscription_end`      DATE               DEFAULT NULL COMMENT '订阅结束日期（NULL 表示无限期）',
    `status`                ENUM('pending','active', 'inactive', 'suspended', 'trial')    NOT NULL DEFAULT 'pending' COMMENT '租户状态（待配置、激活、禁用、终结、试用）',
    `created_at`            BIGINT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '创建时间',
    `updated_at`            BIGINT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '更新时间',
    `deleted_at`            BIGINT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '软删除时间',
    INDEX `idx_tenant_name` (`tenant_name`) USING BTREE COMMENT '租户名称索引',
    UNIQUE KEY `uk_credit_code` (`credit_code`) USING BTREE COMMENT '统一社会信用代码唯一索引'
) ENGINE = InnoDB COMMENT '租户表';

DROP TABLE IF EXISTS `tenant_contracts`;
CREATE TABLE IF NOT EXISTS `tenant_contracts` (
    `id`                    BIGINT UNSIGNED     NOT NULL PRIMARY KEY COMMENT '合同ID',
    `tenant_id`              BIGINT UNSIGNED    NOT NULL COMMENT '租户ID，关联tenants表',
    `contract_number`        VARCHAR(64)        NOT NULL COMMENT '合同编号',
    `title`                  VARCHAR(128)       NOT NULL COMMENT '合同标题或名称',
    `contract_type`          ENUM('trail', 'standard', 'custom', 'nda', 'sla', 'other') NOT NULL DEFAULT 'standard' COMMENT '合同类型 (trial-试用合同, standard-标准合同, custom-定制合同, nda-保密协议, sla-服务水平协议, other-其他)',
    `start_date`             DATE               DEFAULT NULL COMMENT '合同开始日期',
    `end_date`               DATE               DEFAULT NULL COMMENT '合同结束日期',
    `total_amount`           DECIMAL(18,2)      NOT NULL DEFAULT 0.00 COMMENT '合同总金额',
    `currency`               CHAR(3)            NOT NULL DEFAULT 'CNY' COMMENT '币种(预留)',
    `status`                 ENUM('draft', 'active', 'expired', 'terminated', 'cancelled') NOT NULL DEFAULT 'draft' COMMENT '合同状态 (draft-草拟, active-生效, expired-过期, terminated-终止, cancelled-取消)',
    `sign_date`              DATE               DEFAULT NULL COMMENT '签署日期',
    `termination_date`       DATE               DEFAULT NULL COMMENT '终止日期',
    `termination_reason`     VARCHAR(255)       NOT NULL DEFAULT '' COMMENT '终止原因',
    `description`            TEXT               DEFAULT NULL COMMENT '合同描述',
    `attachments`            TEXT               DEFAULT NULL COMMENT '附件信息（如合同文件URL等, JSON格式存储）',
    `created_by`             BIGINT UNSIGNED    DEFAULT NULL COMMENT '创建人',
    `created_at`             DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`             DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_tenant_id` (`tenant_id`),
    INDEX `idx_start_date` (`start_date`),
    INDEX `idx_end_date` (`end_date`)
) ENGINE=InnoDB COMMENT='租户合同表';

-- `max_users`             INT UNSIGNED       NOT NULL DEFAULT 10 COMMENT '最大用户数限制',
-- `max_storage_gb`        INT UNSIGNED       NOT NULL DEFAULT 5 COMMENT '最大存储空间（GB）',
-- `data_isolation_level`  ENUM('schema', 'row', 'none')    NOT NULL DEFAULT 'none' COMMENT '数据隔离级别（数据库模式隔离、行级隔离、无隔离）',

DROP TABLE IF EXISTS `tenant_datasources`;
CREATE TABLE IF NOT EXISTS `tenant_datasources`
(
    `id`                 BIGINT UNSIGNED                       NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    `tenant_id`          BIGINT UNSIGNED                       NOT NULL DEFAULT 0 COMMENT '租户ID',
    `datasource_type`    ENUM('mysql', 'postgresql', 'mongodb', 'redis', 'elasticsearch', 'other')   NOT NULL DEFAULT 'mysql' COMMENT '数据源类型',
    `datasource_name`    VARCHAR(125)                          NOT NULL DEFAULT '' COMMENT '数据源名称',
    `connection_url`     VARCHAR(255)                          NOT NULL DEFAULT '' COMMENT '连接URL',
    `username`           VARCHAR(100)                          NOT NULL DEFAULT '' COMMENT '用户名',
    `password`           VARCHAR(100)                          NOT NULL DEFAULT '' COMMENT '密码（加密存储）',
    `driver_class`       VARCHAR(255)                          NOT NULL DEFAULT '' COMMENT '驱动类名',
    `created_at`         BIGINT(11) UNSIGNED                   NOT NULL DEFAULT 0 COMMENT '创建时间',
    `updated_at`         BIGINT(11) UNSIGNED                   NOT NULL DEFAULT 0 COMMENT '更新时间',
    `version`            BIGINT(11) UNSIGNED                   NOT NULL DEFAULT 0 COMMENT '乐观锁',
    UNIQUE KEY `uk_tenant_datasource` (`tenant_id`, `datasource_name`) USING BTREE COMMENT '租户数据源唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT '租户数据源表';

DROP TABLE IF EXISTS `tenant_dispatchers`;
CREATE TABLE IF NOT EXISTS `tenant_dispatchers`
(
    `id`                 BIGINT UNSIGNED                       NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    `tenant_id`          BIGINT UNSIGNED                       NOT NULL DEFAULT 0 COMMENT '租户ID',
    `dispatcher_type`    ENUM('command', 'query', 'service')   NOT NULL DEFAULT 'QUERY' COMMENT '处理器类型',
    `dispatcher_name`    VARCHAR(125)                          NOT NULL DEFAULT '' COMMENT '处理器名称',
    `dispatcher_class`   VARCHAR(255)                          NOT NULL DEFAULT '' COMMENT '处理器类名',
    `entity_class`       VARCHAR(255)                          NOT NULL DEFAULT '' COMMENT '载体类名',
    `created_at`         BIGINT(11) UNSIGNED                   NOT NULL DEFAULT 0 COMMENT '创建时间',
    `updated_at`         BIGINT(11) UNSIGNED                   NOT NULL DEFAULT 0 COMMENT '更新时间',
    `version`            BIGINT(11) UNSIGNED                   NOT NULL DEFAULT 0 COMMENT '乐观锁',
    UNIQUE KEY `uk_tenant_handler` (`tenant_id`, `dispatcher_class`) USING BTREE COMMENT '租户处理器唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT '租户应用层处理器表';

DROP TABLE IF EXISTS `admins`;
CREATE TABLE IF NOT EXISTS `admins`
(
    `id`                 BIGINT UNSIGNED         NOT NULL PRIMARY KEY COMMENT 'ID',
    `tenant_id`          BIGINT UNSIGNED         NOT NULL COMMENT '租户ID',
    `username`           VARCHAR(45)             NOT NULL COMMENT '用户名',
    `password`           VARCHAR(64)             NOT NULL COMMENT '密码（加密存储）',
    `full_name`          VARCHAR(100)            NOT NULL COMMENT '姓名',
    `email`              VARCHAR(100)            NOT NULL COMMENT '邮箱',
    `phone`              VARCHAR(20)             NOT NULL COMMENT '联系电话',
    `avatar`             VARCHAR(255)            NOT NULL DEFAULT '' COMMENT '头像URL',
    `last_login_at`      BIGINT UNSIGNED         NOT NULL DEFAULT 0 COMMENT '最后登录时间',
    `last_login_ip`      VARCHAR(45)             NOT NULL DEFAULT '' COMMENT '最后登录IP',
    `created_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '创建时间',
    `updated_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '更新时间',
    `deleted_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '软删除时间',
    UNIQUE KEY `uk_username` (`username`) USING BTREE COMMENT '用户名唯一索引',
    UNIQUE KEY `uk_email` (`email`) USING BTREE COMMENT '邮箱唯一索引',
    UNIQUE KEY `uk_phone` (`phone`) USING BTREE COMMENT '联系电话唯一索引'
) ENGINE = InnoDB COMMENT '租户管理员表';

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles`
(
    `id`                 BIGINT UNSIGNED         NOT NULL PRIMARY KEY COMMENT 'ROLE ID',
    `tenant_id`          BIGINT UNSIGNED         NOT NULL COMMENT '租户ID',
    `role_name`          VARCHAR(100)            NOT NULL COMMENT '角色名称',
    `description`        VARCHAR(255)            NOT NULL DEFAULT '' COMMENT '角色描述',
    `created_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '创建时间',
    `updated_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '更新时间',
    UNIQUE KEY `uk_tenant_role` (`tenant_id`, `role_name`) USING BTREE COMMENT '租户角色唯一索引'
) ENGINE = InnoDB COMMENT '租户角色表';

DROP TABLE IF EXISTS `admin_roles`;
CREATE TABLE IF NOT EXISTS `admin_roles`
(
    `admin_id`          BIGINT UNSIGNED         NOT NULL COMMENT '管理员ID',
    `role_id`           BIGINT UNSIGNED         NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`admin_id`, `role_id`) USING BTREE,
    INDEX `idx_role_id` (`role_id`) USING BTREE
) ENGINE = InnoDB COMMENT '租户管理员角色关联表';

DROP TABLE IF EXISTS `email_templates`;
CREATE TABLE IF NOT EXISTS `email_templates`
(
    `id`                 BIGINT UNSIGNED         NOT NULL PRIMARY KEY COMMENT 'ID',
    `tenant_id`          BIGINT UNSIGNED         NOT NULL DEFAULT 0 COMMENT '租户ID, 0表示系统级模板',
    `template_name`      VARCHAR(100)            NOT NULL COMMENT '模板名称',
    `category`           ENUM('notification', 'promotion', 'transactional', 'other') NOT NULL DEFAULT 'transactional' COMMENT '模板类别',
    `subject`            VARCHAR(255)            NOT NULL COMMENT '邮件主题',
    `body`               TEXT                    NOT NULL COMMENT '邮件正文（支持HTML格式）',
    `placeholders`       JSON                    NOT NULL COMMENT '占位符列表（JSON格式存储）',
    `created_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '创建时间',
    `updated_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '更新时间',
    `deleted_at`         BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '软删除时间',
    `version`            BIGINT(11) UNSIGNED     NOT NULL DEFAULT 0 COMMENT '乐观锁',
    UNIQUE KEY `uk_tenant_template` (`tenant_id`, `template_name`) USING BTREE COMMENT '租户模板唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT '租户邮件模板表';

DROP TABLE IF EXISTS `email_tasks`;
CREATE TABLE IF NOT EXISTS `email_tasks`
(
    `id`                  BIGINT UNSIGNED     NOT NULL PRIMARY KEY COMMENT '任务ID',
    `tenant_id`           BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '租户ID, 0表示系统级任务',
    `template_id`         BIGINT UNSIGNED     NOT NULL COMMENT '模板ID',
    `task_name`           VARCHAR(100)        NOT NULL COMMENT '任务名称',
    `status`              VARCHAR(20)         NOT NULL DEFAULT 'pending' COMMENT '任务状态: pending-待|processing-中|completed-完成|failed-失败|cancelled-取消',
    `total_recipients`    INT UNSIGNED       NOT NULL DEFAULT 0 COMMENT '收件人数',
    `success_count`       INT UNSIGNED       NOT NULL DEFAULT 0 COMMENT '成功发送数',
    `fail_count`          INT UNSIGNED       NOT NULL DEFAULT 0 COMMENT '失败数',
    `send_mode`           ENUM('immediate','scheduled','manual') NOT NULL DEFAULT 'manual' COMMENT '发送模式 immediate-立刻|scheduled-定时|manual-手动',
    `scheduled_at`        BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '计划发送时间',
    `created_at`          BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '创建时间',
    `updated_at`          BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '更新时间',
    `deleted_at`          BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '软删除时间',
    `version`             BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '乐观锁',
    UNIQUE KEY `uk_tenant_task` (`tenant_id`, `task_name`) USING BTREE COMMENT '租户任务唯一索引',
    INDEX `idx_tenant_id` (`tenant_id`) USING BTREE COMMENT '租户ID索引'
) ENGINE=InnoDB COMMENT='邮件发送任务表';

DROP TABLE IF EXISTS `email_logs`;
CREATE TABLE IF NOT EXISTS `email_logs`
(
    `id`              BIGINT UNSIGNED     NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `task_id`         BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '任务ID, 0为立即发送',
    `template_id`     BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '模板ID',
    `tenant_id`       BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '租户ID(冗余存储), 0表示系统级日志',
    `sender`          VARCHAR(255)        NOT NULL COMMENT '发件人邮箱',
    `recipient`       VARCHAR(255)        NOT NULL COMMENT '收件人邮箱',
    `cc`              TEXT                NOT NULL COMMENT '抄送邮箱(JSON数组)',
    `subject`         VARCHAR(255)        NOT NULL COMMENT '邮件主题',
    `body`            TEXT                NOT NULL COMMENT '邮件正文(渲染后内容)',
    `params`          TEXT                NOT NULL COMMENT '邮件原始数据(JSON格式存储)',
    `attachments`     Text                NOT NULL COMMENT '附件信息(JSON格式存储)',
    `status`          VARCHAR(20)         NOT NULL DEFAULT 'sent' COMMENT '发送状态 sent|failed|bounced|deferred',
    `created_at`      DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`      DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`         INT UNSIGNED        NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    INDEX `idx_task_id` (`task_id`) USING BTREE COMMENT '任务ID索引',
    INDEX `idx_tenant_id` (`tenant_id`) USING BTREE COMMENT '租户ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='邮件发送日志表';

DROP TABLE IF EXISTS `features`;
CREATE TABLE IF NOT EXISTS `features` (
    `id`              BIGINT UNSIGNED     NOT NULL COMMENT '主键ID',
    `parent_id`       BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '父功能ID，0表示顶级功能',
    `feature_name`    VARCHAR(100)        NOT NULL COMMENT '功能名称',
    `display_name`    VARCHAR(100)        NOT NULL COMMENT '显示名称',
    `description`     TEXT                NOT NULL COMMENT '功能描述',
    `configurable`    TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否可配置（0-否:每个租户都有的，1-是）',
    `created_at`      BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '创建时间',
    `updated_at`      BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '更新时间',
    `deleted_at`      BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '软删除时间',
    `version`         BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    PRIMARY KEY (`id`),
    INDEX `idx_parent_id` (`parent_id`) COMMENT '父功能ID索引',
    UNIQUE KEY `uk_feature_name` (`feature_name`) COMMENT '功能名称唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Sass系统功能表';

DROP TABLE IF EXISTS `permission_groups`;
CREATE TABLE IF NOT EXISTS `permission_groups`
(
    `id`              BIGINT UNSIGNED     NOT NULL PRIMARY KEY COMMENT '主键ID',
    `parent_id`       BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '父权限组ID，0表示顶级权限组',
    `group_name`      VARCHAR(100)        NOT NULL COMMENT '权限组名称',
    `display_name`    VARCHAR(100)        NOT NULL COMMENT '显示名称',
    `description`     VARCHAR(255)        NOT NULL DEFAULT '' COMMENT '权限组描述',
    `created_at`      BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '创建时间',
    `updated_at`      BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '更新时间',
    `deleted_at`      BIGINT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '软删除时间',
    UNIQUE KEY `uk_group_name` (`group_name`) USING BTREE COMMENT '权限组名称唯一索引'
) ENGINE=InnoDB COMMENT 'SAAS系统权限组表';

DROP TABLE IF EXISTS permissions;
CREATE TABLE IF NOT EXISTS permissions
(
    id                 BIGINT UNSIGNED    NOT NULL PRIMARY KEY COMMENT '主键ID',
    group_id BIGINT    UNSIGNED           NOT NULL COMMENT '权限组ID，关联permission_groups表',
    permission_name    VARCHAR(100)       NOT NULL COMMENT '权限名称',
    display_name       VARCHAR(100)       NOT NULL COMMENT '显示名称',
    description        VARCHAR(255)       NOT NULL DEFAULT '' COMMENT '权限描述',
    created_at         BIGINT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '创建时间',
    updated_at         BIGINT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '更新时间',
    UNIQUE KEY uk_permission_name (permission_name) USING BTREE COMMENT '权限名称唯一索引'
) ENGINE=InnoDB COMMENT 'SAAS系统权限表';