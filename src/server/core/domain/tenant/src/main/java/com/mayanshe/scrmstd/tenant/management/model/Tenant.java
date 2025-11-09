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
package com.mayanshe.scrmstd.tenant.management.model;

import com.mayanshe.scrmstd.shared.base.Aggregate;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.tenant.management.event.TenantBaseModifiedEvent;
import com.mayanshe.scrmstd.tenant.management.event.TenantCreatedEvent;
import com.mayanshe.scrmstd.tenant.management.event.TenantLocationModifiedEvent;
import com.mayanshe.scrmstd.tenant.management.type.TenantStatus;
import com.mayanshe.scrmstd.tenant.management.valueobj.TenantAddress;
import com.mayanshe.scrmstd.tenant.management.valueobj.TenantContactInfo;
import com.mayanshe.scrmstd.tenant.management.valueobj.TenantSubscription;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Tenant: 租户聚合根
 */
@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Tenant extends Aggregate {
    private AggregateId id;                                            // 租户ID

    private String tenantName;                                         // 租户名称

    private String legalName;                                         // 法人代表

    private String creditCode;                                         // 统一社会信用代码

    private String industryCode;                                       // 行业编码

    private String industryName;                                       // 行业名称

    private TenantAddress addressInfo;                                 // 地址信息

    private TenantContactInfo contactInfo;                             // 联系人信息

    private TenantSubscription subscription;                           // 订阅信息

    @Builder.Default
    private TenantStatus status = TenantStatus.of("PENDING");     // 租户状态

    @Builder.Default
    private Boolean deleted = false;                                   // 是否删除

    @Builder.Default
    private List<String> saves = new ArrayList<>();                    // 管理员用户ID列表

    /**
     * 添加地址信息
     *
     * @param provinceId 省份ID
     * @param province   省份名称
     * @param cityId     城市ID
     * @param city       城市名称
     * @param districtId 区县ID
     * @param district   区县名称
     * @param address    详细地址
     */
    public void addAddressInfo(Long provinceId, String province, Long cityId, String city, Long districtId, String district, String address) {
        this.addressInfo = new TenantAddress(provinceId == null ? 0L : provinceId, province, cityId == null ? 0L : cityId, city, districtId == null ? 0L : districtId, district, address);
    }

    /**
     * 添加联系人信息
     *
     * @param contactPerson 联系人姓名
     * @param contactPhone  联系人电话
     * @param contactEmail  联系人邮箱
     * @param contactWechat 联系人微信
     */
    public void addContactInfo(String contactPerson, String contactPhone, String contactEmail, String contactWechat) {
        this.contactInfo = new TenantContactInfo(contactPerson, contactPhone, contactEmail, contactWechat);
    }

    /**
     * 创建租户
     */
    public void create() {
        this.saves.add("base");
        this.setDeleted(false);

        TenantCreatedEvent event = TenantCreatedEvent.builder().refId(this.getId().id()).tenantId(this.getId()).tenantName(this.getTenantName()).legalName(this.getLegalName()).creditCode(this.getCreditCode()).industryCode(this.getIndustryCode()).addressInfo(this.getAddressInfo()).contactInfo(this.getContactInfo()).build();
        this.registerEvent(event);
    }

    /**
     * 修改基础信息
     *
     * @param tenantName   租户名称
     * @param legalName    法人名称
     * @param creditCode   统一社会信用代码
     * @param industryCode 行业编码
     * @param industryName 行业名称
     */
    public void modifyBase(String tenantName, String legalName, String creditCode, String industryCode, String industryName) {
        this.setTenantName(tenantName);
        this.setLegalName(legalName);
        this.setCreditCode(creditCode);
        this.setIndustryCode(industryCode);
        this.setIndustryName(industryName);

        this.saves.add("base");
        this.setDeleted(false);

        TenantBaseModifiedEvent event = TenantBaseModifiedEvent.builder()
                .refId(this.getId().id())
                .tenantId(this.getId())
                .tenantName(this.getTenantName())
                .legalPerson(this.getLegalName())
                .creditCode(this.getCreditCode())
                .industryCode(this.getIndustryCode())
                .status(this.getStatus())
                .build();
        this.registerEvent(event);
    }

    /**
     * 修改地址信息
     *
     * @param address 地址信息
     */
    public void modifyLocation(TenantAddress address) {
        this.setAddressInfo(address);
        this.saves.add("base");
        this.setDeleted(false);

        TenantLocationModifiedEvent event = TenantLocationModifiedEvent.builder()
                .refId(this.getId().id())
                .tenantId(this.getId())
                .provinceId(address.provinceId())
                .province(address.province())
                .cityId(address.cityId())
                .city(address.city())
                .districtId(address.districtId())
                .address(address.address())
                .build();
        this.registerEvent(event);
    }
}
