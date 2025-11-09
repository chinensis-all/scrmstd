-- 演示数据（可选导入）

-- 示例租户
INSERT INTO `tenants` (`id`, `tenant_name`, `legal_name`, `credit_code`, `industry_code`, `province_id`,
                                 `province`, `city_id`, `city`, `district_id`, `district`, `address`, `contact_person`,
                                 `contact_phone`, `contact_email`, `contact_wechat`, `subscription_plan`,
                                 `subscription_start`, `subscription_end`, `max_users`, `max_storage_gb`,
                                 `payment_method`, `status`, `data_isolation_level`, `created_at`, `updated_at`,
                                 `deleted_at`)
VALUES (76396119485399040, '北京撸铁棒子信息科技有限公司', '肖宝宝', '900000000000000000', 'C434314314', 0, '', 0, '',
        0, '', '', '张小龙', '17810365776', 'mail@sniu.com', '17810365776', 'basic', '2025-10-01', '2026-10-01', 20,
        200, 'other', 'pending', 'row', 1759987778000, 0, 0);
