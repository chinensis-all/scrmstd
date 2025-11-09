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
package com.mayanshe.scrmstd.bossapi.controllers;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.mayanshe.scrmstd.application.CommandBus;
import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.QueryBus;
import com.mayanshe.scrmstd.application.generic.query.dto.IndustryDto;
import com.mayanshe.scrmstd.application.generic.query.GetIndustryListQuery;
import com.mayanshe.scrmstd.application.generic.query.GetRegionOptionQuery;
import com.mayanshe.scrmstd.application.tentant.command.CreateTenantCommand;
import com.mayanshe.scrmstd.application.tentant.command.ModifyTenantBaseCommand;
import com.mayanshe.scrmstd.application.tentant.command.ModifyTenantLocationCommand;
import com.mayanshe.scrmstd.bossapi.requests.CreateTenantRequest;
import com.mayanshe.scrmstd.bossapi.requests.ModifyTenantBaseRequest;
import com.mayanshe.scrmstd.bossapi.requests.ModifyTenantLocationRequest;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tenants")
public class TenantController {
    private final CommandBus commandBus;

    private final QueryBus queryBus;

    public TenantController(@Lazy @Qualifier("simpleCommandBus") CommandBus commandBus, @Lazy @Qualifier("simpleQueryBus") QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @SaCheckLogin
    @PostMapping
    public IDResponse createTenant(@Valid @RequestBody CreateTenantRequest request) {
        // 所在地区信息
        List<Long> regionIds = new ArrayList<>(List.of(request.getProvinceId(), request.getCityId(), request.getDistrictId()));
        List<OptionDto> regionOptions = queryBus.execute(new GetRegionOptionQuery(null, null, regionIds));
        String province = regionOptions.stream().filter(o -> o.id().equals(String.valueOf(request.getProvinceId()))).findFirst().map(OptionDto::name).orElse("");
        String city = regionOptions.stream().filter(o -> o.id().equals(String.valueOf(request.getCityId()))).findFirst().map(OptionDto::name).orElse("");
        String district = regionOptions.stream().filter(o -> o.id().equals(String.valueOf(request.getDistrictId()))).findFirst().map(OptionDto::name).orElse("");

        // 行业信息
        List<IndustryDto> industries = queryBus.execute(new GetIndustryListQuery(null, request.getIndustryCode(), null, null, null));
        String industryName = industries.isEmpty() ? "" : industries.get(0).industryName();

        CreateTenantCommand command = new CreateTenantCommand(request.getTenantName(), request.getLegalName(), request.getCreditCode(), request.getIndustryCode(), industryName, request.getProvinceId(), province, request.getCityId(), city, request.getDistrictId(), district, request.getAddress(), request.getContactPerson(), request.getContactPhone(), request.getContactEmail(), request.getContactWechat());

        Long id = commandBus.execute(command);
        return new IDResponse(IdGenerator.toBase62(id));
    }

    @PutMapping("/{id}/base")
    public void modifyBase(@PathVariable("id") String id, @Valid @RequestBody ModifyTenantBaseRequest request) {
        // 行业信息
        List<IndustryDto> industries = queryBus.execute(new GetIndustryListQuery(null, request.getIndustryCode(), null, null, null));
        String industryName = industries.isEmpty() ? "" : industries.get(0).industryName();

        ModifyTenantBaseCommand command = new ModifyTenantBaseCommand(IdGenerator.fromBase62(id), request.getTenantName(), request.getLegalName(), request.getCreditCode(), request.getIndustryCode(), industryName);

        commandBus.execute(command);
    }

    @PutMapping("/{id}/location")
    public IDResponse modifyLocation(@PathVariable("id") String id, @Valid @RequestBody ModifyTenantLocationRequest request) {
        // 所在地区信息
        List<Long> regionIds = new ArrayList<>(List.of(request.getProvinceId(), request.getCityId(), request.getDistrictId()));
        List<OptionDto> regionOptions = queryBus.execute(new GetRegionOptionQuery(null, null, regionIds));
        String province = regionOptions.stream().filter(o -> o.id().equals(String.valueOf(request.getProvinceId()))).findFirst().map(OptionDto::name).orElse("");
        String city = regionOptions.stream().filter(o -> o.id().equals(String.valueOf(request.getCityId()))).findFirst().map(OptionDto::name).orElse("");
        String district = regionOptions.stream().filter(o -> o.id().equals(String.valueOf(request.getDistrictId()))).findFirst().map(OptionDto::name).orElse("");

        ModifyTenantLocationCommand command = new ModifyTenantLocationCommand(IdGenerator.fromBase62(id), request.getProvinceId(), province, request.getCityId(), city, request.getDistrictId(), district, request.getAddress());
        return commandBus.execute(command);
    }
}
