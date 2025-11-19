# 基于Mysql表定义开发Http Crud接口

## 1 名称定义
说明： `{名称}` 用于表示需要替换的内容。
- {表} : permissions
- {模型名} : Permission
- {领域} ： tenant
- {上下文} ： identity
- {持久化模型} : {模型名}Po
- {持久化映射} : {模型名}Mapper
- {持久化Mapper} : {模型名}Mapper
- {转换器} ： {模型名}Converter
- {命令仓储接口} : {模型名}Repository
- {命令仓储接口实现} : {模型名}RepositoryImpl
- {查询仓储接口} : {模型名}QueryRepository
- {查询仓储接口实现} : {模型名}QueryRepositoryImpl
- {聚合根} ： {模型名}
- {聚合根创建成功事事件} : {模型名}CreatedEvent
- {聚合根修改成功事件} ：{模型名}ModifiedEvent
- {聚合根禁用成功事件} ：{模型名}DestroyedEvent
- {聚合根激活成功事件} ：{模型名}ActivateEvent
- {聚合根删除成功事件} : {模型名}DeletedEvent
- {创建聚合根命令} : Create{模型名}Command
- {修改聚合根命令} : Modify{模型名}Command
- {禁用聚合根命令} : Destroy{模型名}Command
- {激活聚合根命令} : Activate{模型名}Command
- {删除聚合根命令} : Delete{模型名}Command
- {创建聚合根处理器} ： Create{模型名}CommandHandler
- {修改聚合根处理器} ： Modify{模型名}CommandHandler
- {禁用聚合根处理器} ： Destroy{模型名}CommandHandler
- {激活聚合根处理器} ： Activate{模型名}CommandHandler
- {删除聚合根处理器} ： Delete{模型名}CommandHandler
- {查询Dto} : {模型名}Dto
- {列表查询Dto} : OptionDto
- {详情查询} : {模型名}DetailQuery
- {列表查询} : {模型名}OptionQuery
- {分页查询} ： {模型名}PaginationQuery
- {详情查询处理器} ：{模型名}DetailQueryHandler
- {列表查询处理器} ：{模型名}OptionQueryHandler
- {分页查询处理器} ： {模型名}PaginationQueryHandler
- {控制器} ： {模型名}Controller
- {创建请求载体} ： Create{模型名}Request
- {修改请求载体} ： Modify{模型名}Request

## 2 路径定义
说明： `{路径}` 用于表示代码文件存放的位置。
- {持久化映射路径} ： `src/server/core/infrastructure/src/main/resources/mapper/`
- {持久化模型路径} ： `src/server/core/infrastructure/src/main/java/com/mayanshe/scrmstd/infrastructure/persistence/po`
- {持久化Mapper路径} ： `src/server/core/infrastructure/src/main/java/com/mayanshe/scrmstd/infrastructure/persistence/mapper`
- {转换器路径} ： `src/server/core/infrastructure/src/main/java/com/mayanshe/scrmstd/infrastructure/external/converter/`
- {领域项目路径} ： `src/server/core/domain/tenant/src/main/java/com/mayanshe/scrmstd/{领域}`
- {领域上下文路径} ：`{领域路径}/{上下文}`
- {领域上下文聚合根路径} ： `{领域上下文路径}/model`
- {领域上下文事件路径} ： `{领域上下文路径}/event`
- {领域上下文仓储接口路径} ： `{领域上下文路径}/repo`
- {领域上下文服务路径} ： `{领域上下文路径}/service`
- {领域上下文类型路径} ： `{领域上下文路径}/type`
- {领域上下文值模型路径} ： `{领域上下文路径}/valueobj`
- {应用层路径} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/{领域}`
- {应用层命令路径} ： `{应用层路径}/command`
- {应用层命令处理器路径} ： `{应用层命令路径}/handler`
- {应用层查询路径} ： `{应用层路径}/query`
- {应用层查询处理器路径} ： `{应用层查询路径}/handler`
- {应用层Dto路径} ： `{应用层路径}/dto`
- {应用查询仓储接口路径} ： `{应用层路径}/repo`
- {BoosApi控制器路径} ： `src/server/interface/boss.api/src/main/java/com/mayanshe/scrmstd/bossapi/controllers/`
- {请求载体路径} : `src/server/interface/boss.api/src/main/java/com/mayanshe/scrmstd/bossapi/requests`

## 3 参考代码路径

- {参考持久化映射文件} ： `src/server/core/infrastructure/src/main/resources/mapper/PermissionGroupMapper.xml`
- {参考持久化模型文件} ： `src/server/core/infrastructure/src/main/java/com/mayanshe/scrmstd/infrastructure/persistence/po/PermissionGroupPo.java`
- {参考持久化Mapper路径} ： `src/server/core/infrastructure/src/main/java/com/mayanshe/scrmstd/infrastructure/persistence/mapper/PermissionGroupMapper.java`
- {参考转换器文件} ： `src/server/core/infrastructure/src/main/java/com/mayanshe/scrmstd/infrastructure/external/converter/PermissionGroupConverter.java`
- {参考聚合根文件} ： `src/server/core/domain/tenant/src/main/java/com/mayanshe/scrmstd/tenant/identity/model/PermissionGroup.java`
- {参考聚合根创建成功事件文件} ： `src/server/core/domain/tenant/src/main/java/com/mayanshe/scrmstd/tenant/identity/event/PermissionGroupCreatedEvent.java`
- {参考聚合根修改成功事件文件} ： `src/server/core/domain/tenant/src/main/java/com/mayanshe/scrmstd/tenant/identity/event/PermissionGroupModifiedEvent.java`
- {参考聚合根禁用成功事件文件} ： `src/server/core/domain/tenant/src/main/java/com/mayanshe/scrmstd/tenant/identity/event/PermissionGroupDestroyedEvent.java`
- {参考聚合根激活成功事件文件} ： `src/server/core/domain/tenant/src/main/java/com/mayanshe/scrmstd/tenant/identity/event/PermissionGroupActivateEvent.java`
- {参考聚合根删除成功事件文件} ： `src/server/core/domain/tenant/src/main/java/com/mayanshe/scrmstd/tenant/identity/event/PermissionGroupDeletedEvent.java`
- {参考命令仓储接口文件} ： `src/server/core/domain/tenant/src/main/java/com/mayanshe/scrmstd/tenant/identity/repo/PermissionGroupRepository.java`
- {参考命令仓储接口实现文件} ： `src/server/core/infrastructure/src/main/java/com/mayanshe/scrmstd/infrastructure/persistence/repo/PermissionGroupRepositoryImpl.java`
- {参考查询仓储接口文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/repo/PermissionGroupQueryRepository.java`
- {参考查询仓储接口实现文件} ： `src/server/core/infrastructure/src/main/java/com/mayanshe/scrmstd/infrastructure/persistence/repo/PermissionGroupQueryRepositoryImpl.java`
- {参考创建聚合根命令文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/command/CreatePermissionGroupCommand.java`
- {参考修改聚合根命令文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/command/ModifyPermissionGroupCommand.java`
- {参考禁用聚合根命令文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/command/DestroyPermissionGroupCommand.java`
- {参考激活聚合根命令文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/command/ActivatePermissionGroupCommand.java`
- {参考删除聚合根命令文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/command/DeletePermissionGroupCommand.java`
- {参考创建聚合根处理器文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/command/handler/CreatePermissionGroupCommandHandler.java`
- {参考修改聚合根处理器文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/command/handler/ModifyPermissionGroupCommandHandler.java`
- {参考禁用聚合根处理器文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/command/handler/DestroyPermissionGroupCommandHandler.java`
- {参考激活聚合根处理器文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/command/handler/ActivatePermissionGroupCommandHandler.java`
- {参考删除聚合根处理器文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/command/handler/DeletePermissionGroupCommandHandler.java`
- {参考详情查询文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/query/PermissionGroupDetailQuery.java`
- {参考列表查询文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/query/PermissionGroupOptionQuery.java`
- {参考分页查询文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/query/PermissionGroupPaginationQuery.java`
- {参考详情查询处理器文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/query/handler/PermissionGroupDetailQueryHandler.java`
- {参考列表查询处理器文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/query/handler/PermissionGroupOptionQueryHandler.java`
- {参考分页查询处理器文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/query/handler/PermissionGroupPaginationQueryHandler.java`
- {参考Dto文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/dto/PermissionGroupDto.java`
- {参考列表Dto文件} ： `src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/identity/dto/PermissionGroupListDto.java`
- {参考控制器文件} ： `src/server/interface/boss.api/src/main/java/com/mayanshe/scrmstd/bossapi/controllers/PermissionGroupController.java`
- {参考创建请求载体文件} ： `src/server/interface/boss.api/src/main/java/com/mayanshe/scrmstd/bossapi/requests/CreatePermissionGroupRequest.java`
- {参考修改请求载体文件} ： `src/server/interface/boss.api/src/main/java/com/mayanshe/scrmstd/bossapi/requests/ModifyPermissionGroupRequest.java`
- {参考BoosApi控制器文件} ： `src/server/interface/boss.api/src/main/java/com/mayanshe/scrmstd/bossapi/controllers/PermissionGroupController.java`
- {参考领域上下文类型} ： `src/server/core/domain/tenant/src/main/java/com/mayanshe/scrmstd/tenant/management/type/TenantContractStatus.java`

## 4 开发步骤

### 4.1 开发准备

`doc/data/schema.sql`是本项目的数据库DDL定义文件，请仔细于都找到`表`的定义， 根据它的备注理解这样表的意义。

### 4.2 模型开发

#### 4.2.1 创建"持久化模型"

- 路径： `{持久化模型路径}/{持久化模型}.java`
- 参考： {参考持久化模型文件}
- 模型属性驼峰命名法
- BIGINT类型使用Long
- TINYINT类型使用Byte
- 如果数据表字段以_at结尾，那对应的模型属性设置默认0L
- 如果数据表字段是enum类型，则此字段对应的模型属性使用String类型
- 此模型中id为long类型

#### 4.2.2 创建"聚合根创建成功事事件"

- 路径： `{领域上下文事件路径}/{聚合根创建成功事事件}.java`
- 参考： {参考聚合根创建成功事件文件}
- 继承`DomainEvent`
- 包含聚合根所有属性, id改为`{模型名}Id`(首字母小写)
- 包含聚合根所有字段，除了`version`和`deleted`字段。

### 4.2.3 创建"聚合根修改成功事件"

- 路径： `{领域上下文事件路径}/{聚合根修改成功事件}.java`
- 参考: {参考聚合根修改成功事件文件}
- 继承`DomainEvent`
- 包含聚合根所有属性, id改为`{模型名}Id`(首字母小写)
- 包含聚合根所有字段，除了`version`和`deleted`字段。

### 4.2.4 创建"聚合根禁用成功事件"

- 判断： 如果{表}中含有`deleted_at`字段才可以执行此创建
- 路径： `{领域上下文事件路径}/{聚合根禁用成功事件}.java`
- 参考: {参考聚合根禁用成功事件文件}
- 继承`DomainEvent`
- 只包含Aggregate属性, id改为`{模型名}Id`(首字母小写

### 4.2.5 创建"聚合根激活成功事件"
- 判断： 如果{表}中含有`deleted_at`字段才可以执行此创建
- 路径： `{领域上下文事件路径}/{聚合根激活成功事件}.java`
- 参考: {参考聚合根激活成功事件文件}
- 继承`DomainEvent`
- 只包含Aggregate属性, id改为`{模型名}Id`(首字母小写

### 4.2.6 创建"聚合根删除成功事件"

- 判断： 如果{表}中不含有`deleted_at`字段才可以执行此创建
- 路径： `{领域上下文事件路径}/{聚合根删除成功事件}.java`
- 参考: {参考聚合根删除成功事件文件}
- 继承`DomainEvent`
- 只包含Aggregate属性, id改为`{模型名}Id`(首字母小写

#### 4.2.6 创建"聚合根"

- 路径： `{领域上下文聚合根路径}/{聚合根}.java`
- 参考： {参考聚合根文件}
- 继承`Aggregate`
- 必须有`public boolean deleted = false;`用于表示是否删除
- 添加create()方法
- 添加modify(...)方法
- 如果{表}中包含`deleted_at`字段，则添加forbid()和activate()方法
- 如果{表}中不包含`deleted_at`字段，则添加delete()方法

#### 4.2.7 创建"领域上下文类型"

- 判断：如果{表}中包含enum类型字段，则需要创建对应的“领域上下文类型"
- {领域上下文类型名称} = 字段名称驼峰化，首字母大写
- 路径： `{领域上下文类型路径}/{领域上下文类型名称}.java`
- 参考： `{参考领域上下文类型}`

#### 4.2.3 创建"查询Dto"

- 路径: `{应用层Dto路径}/{查询Dto}.java`
- 参考: {参考Dto文件}
- 如果{表}中包含enum类型字段，则对应的Dto属性使用对应的领域上下文类型
- 如果{表}中的字段以`_at`结尾，则此对象中采用String类型，参考以下处理进行格式化
```java
@Override
    public String createdAt() {
        return String.format(String.format(createdAt == null || createdAt.isBlank() ? "" : DateUtil.format(DateUtil.date(Long.parseLong(createdAt)), "yyyy-MM-dd HH:mm:ss")));
    }
```

### 4.3 持久化开发

#### 4.3.1 创建"持久化映射"

- 路径： `{持久化映射路径}/{持久化映射}.xml`
- 参考： {参考持久化映射文件}
- 命名空间： `com.mayanshe.scrmstd.infrastructure.persistence.mapper.{持久化Mapper}`
- 必然包括 insert、update、delete、findById、count、search 这些基本操作

#### 4.3.2 创建"持久化Mapper"

- 路径： `{持久化Mapper路径}/{持久化Mapper}.java`
- 参考： {参考持久化Mapper路径}


#### 4.3.3 创建"转换器"

- 路径： `{转换器路径}/{转换器}.java`
- 参考： {参考转换器文件}
- toPo方法是将聚合根转换为持久化模型
- updatePo方法是将聚合根更新到持久化模型
- toAggregate方法是将持久化模型转换为聚合根
- toDto方法是将持久化模型转换为查询Dto
- toOptionDto方法是将持久化模型转换为列表查询Dto

#### 4.3.4 创建“命令仓储接口”

- 路径： `{领域上下文仓储接口路径}/{命令仓储接口}.java`
- 参考： {参考命令仓储接口文件}
- 仅接口，不包含实现

#### 4.3.5 创建“命令仓储接口实现”

- 路径： `{领域上下文仓储接口路径}/{命令仓储接口实现}.java`
- 参考： {参考命令仓储接口实现文件}

#### 4.3.6 创建“查询仓储接口”

- 路径： `{应用查询仓储接口路径}/{查询仓储接口}.java`
- 参考： {参考查询仓储接口文件}

#### 4.3.7 创建“查询仓储接口实现”

- 路径： `{应用查询仓储接口路径}/{查询仓储接口实现}.java`
- 参考： {参考查询仓储接口实现文件}

### 4.4 命令开发

#### 4.4.1 创建"创建聚合根命令"

- 路径： `{应用层命令路径}/{创建聚合根命令}.java`
- 参考： {参考创建聚合根命令文件}
- 继承`Command`

#### 4.4.2 创建"修改聚合根命令"

- 路径： `{应用层命令路径}/{修改聚合根命令}.java`
- 参考： {参考修改聚合根命令文件}
- 继承`Command`

#### 4.4.3 创建"禁用聚合根命令"

- 判断： 如果{表}中含有`deleted_at`字段才可以执行此创建
- 路径： `{应用层命令路径}/{禁用聚合根命令}.java`
- 参考： {参考禁用聚合根命令文件}
- 继承`Command`

#### 4.4.4 创建"激活聚合根命令"
- 判断： 如果{表}中含有`deleted_at`字段才可以执行此创建
- 路径： `{应用层命令路径}/{激活聚合根命令}.java`
- 参考： {参考激活聚合根命令文件}
- 继承`Command`

#### 4.4.5 创建"删除聚合根命令"
- 判断： 如果{表}中不含有`deleted_at`字段才可以执行此创建
- 路径： `{应用层命令路径}/{删除聚合根命令}.java`
- 参考： {参考删除聚合根命令文件}
- 继承`Command`

#### 4.4.6 创建"创建聚合根处理器"
- 路径： `{应用层命令处理器路径}/{创建聚合根处理器}.java`
- 参考： {参考创建聚合根处理器文件}
- 实现`CommandHandler<{删除聚合根命令}, Long>`
- 注入`{命令仓储接口}`
- 要确认事件发布

#### 4.4.7 创建"修改聚合根处理器"
- 路径： `{应用层命令处理器路径}/{修改聚合根处理器}.java`
- 参考： {参考修改聚合根处理器文件}
- 实现`CommandHandler<{修改聚合根命令}, Boolean>`
- 注入`{命令仓储接口}`
- 要确认事件发布

#### 4.4.8 创建"禁用聚合根处理器"
- 判断： 如果{表}中含有`deleted_at`字段才可以执行此创建
- 路径： `{应用层命令处理器路径}/{禁用聚合根处理器}.java`
- 参考： {参考禁用聚合根处理器文件}
- 实现`CommandHandler<{禁用聚合根命令}, Boolean>`
- 注入`{命令仓储接口}`
- 要确认事件发布

#### 4.4.9 创建"激活聚合根处理器"
- 判断： 如果{表}中含有`deleted_at`字段才可以执行此创建
- 路径： `{应用层命令处理器路径}/{激活聚合根处理器}.java`
- 参考： {参考激活聚合根处理器文件}
- 实现`CommandHandler<{激活聚合根命令}, Boolean>`
- 注入`{命令仓储接口}`
- 要确认事件发布

### 4.5 查询开发

### 4.5.1 创建"详情查询"

- 路径： `{应用层查询路径}/{详情查询}.java`
- 参考： {参考详情查询文件}
- 继承`Query<{查询Dto}>`

### 4.5.2 创建"列表查询"

- 路径： `{应用层查询路径}/{列表查询}.java`
- 参考： {参考列表查询文件}
- 继承`Query<List<{OptionDto}>>`

#### 4.5.3 创建"分页查询"

- 路径： `{应用层查询路径}/{分页查询}.java`
- 参考： {参考分页查询文件}
- 继承`Query<Pagination<{查询Dto}>>`

#### 4.5.4 创建"详情查询处理器"

- 路径： `{应用层查询处理器路径}/{详情查询处理器}.java`
- 参考： {参考详情查询处理器文件}

#### 4.5.5 创建"列表查询处理器"

- 路径： `{应用层查询处理器路径}/{列表查询处理器}.java`
- 参考： {参考列表查询处理器文件}

#### 4.5.6 创建"分页查询处理器"

- 路径： `{应用层查询处理器路径}/{分页查询处理器}.java`
- 参考： {参考分页查询处理器文件}

### 4.6 接口开发

#### 4.6.1 创建"创建请求载体"
- 路径： `{请求载体路径}/{创建请求载体}.java`
- 参考： {参考创建请求载体文件}

#### 4.6.2 创建"修改请求载体"
- 路径： `{请求载体路径}/{修改请求载体}.java`
- 参考： {参考修改请求载体文件}

#### 4.6.3 创建"控制器"
- 路径： `{BoosApi控制器路径}/{控制器}.java`
- 参考： {参考BoosApi控制器文件}
- 如果{表}中有`deleted_at`字段则在{控制器}中添加激活和删除接口
- 如果{表}中没有`deleetd_at`字段则在{控制器}中添加删除接口
- 控制器restful路径： `/{表}`, 将其中的下划线_改为- 

## 5 修正

以上章节是通用的开发步骤，阅读后请务必进行以下修正：

无修正

## 6 全局规则

- 无需进行任何测试，完成即询问提交；
- 请用中文交互；
- 不存在的文件里可以创建，你也可以覆盖；
- 不要动这个文档没有提到的任何文件；
- 所有代码必须符合现有代码风格；
- 没个Java文件都至少有两个注释，一个在头部用于声明版权，一个在类（注解）的上部，包含类名和功能；
- 不要import项目中不存在的文件，不要使用项目中不存在的文件；
- 本项目使用的DDD + CQRS + 多模块的开发范式，请知悉。
- 提交分名字名为`feature-{表}`，将其中的下划线改为中划-。
- 如果你觉得本提示文档哪里有不清楚的地方，请向我提问，并修改本文档为更清晰的你容易理解的文档，修改时考虑后续的模板示例（只需要少量的改动，就可以形成新的Agent，如果没有，继续你的工作，列出开发计划。


