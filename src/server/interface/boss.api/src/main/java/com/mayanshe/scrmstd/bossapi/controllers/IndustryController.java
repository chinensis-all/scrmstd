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

import com.mayanshe.scrmstd.application.CommandBus;
import com.mayanshe.scrmstd.application.QueryBus;
import com.mayanshe.scrmstd.application.generic.query.dto.IndustryDto;
import com.mayanshe.scrmstd.application.generic.query.GetIndustryListQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * IndustryController: 行业控制器
 */
@RestController
@RequestMapping("/industries")
public class IndustryController {
    private final CommandBus commandBus;

    private final QueryBus queryBus;

    public IndustryController(@Lazy @Qualifier("simpleCommandBus") CommandBus commandBus, @Lazy @Qualifier("simpleQueryBus") QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @GetMapping("/options")
    public List<IndustryDto> getIndustryOptions(@RequestParam(value = "parentId", defaultValue = "0") Long parentId) {
        return queryBus.execute(new GetIndustryListQuery(parentId, null, null, null, null));
    }
}
