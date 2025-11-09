DROP TABLE IF EXISTS `regions`;
CREATE TABLE `regions` (
   `id`             BIGINT           UNSIGNED NOT NULL PRIMARY KEY COMMENT '行政编码',
   `parent_id`      BIGINT           UNSIGNED NOT NULL DEFAULT 0 COMMENT '上级行政编码',
   `region_level`   TINYINT          UNSIGNED NOT NULL DEFAULT '1' COMMENT '行政区划级别 1:省 2:市 3:区/县 4:镇/街道 5:村/社区',
   `postal_code`    CHAR(6)          NOT NULL DEFAULT '' COMMENT '邮政编码',
   `area_code`      CHAR(6)          NOT NULL DEFAULT '' COMMENT '区号',
   `region_name`    VARCHAR(50)      NOT NULL DEFAULT '' COMMENT '行政区划名称',
   `name_pinyin`    VARCHAR(255)     NOT NULL DEFAULT '' COMMENT '行政区划名称拼音',
   `short_name`     VARCHAR(50)      NOT NULL DEFAULT '' COMMENT '行政区划简称',
   `merge_name`     VARCHAR(255)     NOT NULL DEFAULT '' COMMENT '行政区划组合名称',
   `longitude`      DECIMAL(10,6)    NOT NULL DEFAULT '0.000000' COMMENT '经度',
   `latitude`       DECIMAL(10,6)    NOT NULL DEFAULT '0.000000' COMMENT '纬度',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`) USING BTREE COMMENT '上级行政编码索引'
) ENGINE=InnoDB  COMMENT='中国行政区划表';