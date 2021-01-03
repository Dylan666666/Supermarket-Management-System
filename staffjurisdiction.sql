

-- 一级菜单表
drop table if exists tb_primary_menu;
CREATE TABLE `tb_primary_menu`  (
  `primary_menu_id` int AUTO_INCREMENT NOT NULL COMMENT '一级菜单ID',
  `primary_menu_name` varchar(255) NOT NULL COMMENT '一级菜单名',
  `primary_menu_weight` int NOT NULL COMMENT '一级菜单权重',
  PRIMARY KEY (`primary_menu_id`)
);

-- 库房管理员
insert into tb_primary_menu(primary_menu_name,primary_menu_weight) values ('库存信息',1);
insert into tb_primary_menu(primary_menu_name,primary_menu_weight) values ('出入库信息',2);
insert into tb_primary_menu(primary_menu_name,primary_menu_weight) values ('库存盘点',3);


-- 营业员
insert into tb_primary_menu(primary_menu_name,primary_menu_weight) values ('收银管理',1);
insert into tb_primary_menu(primary_menu_name,primary_menu_weight) values ('退货管理',2);

-- 职工
insert into tb_primary_menu(primary_menu_name,primary_menu_weight) values ('职工一级菜单名',1);


-- 超级管理员
insert into tb_primary_menu(primary_menu_name,primary_menu_weight) values ('用户管理',1);
insert into tb_primary_menu(primary_menu_name,primary_menu_weight) values ('权限管理',2);


-- 二级菜单表
drop table if exists tb_secondary_menu;
CREATE TABLE `tb_secondary_menu`  (
  `secondary_menu_id` int  AUTO_INCREMENT NOT NULL COMMENT '二级菜单ID',
  `secondary_menu_weight` int NOT NULL COMMENT '二级菜单权重',
  `primary_menu_id` int NOT NULL COMMENT '一级菜单ID',
  `secondary_menu_name` varchar(255) NOT NULL COMMENT '二级菜单名',
  `secondary_menu_url` varchar(255) NULL COMMENT '二级菜单功能请求url',
  PRIMARY KEY (`secondary_menu_id`)
);

-- 库房管理员 库存信息
insert into tb_secondary_menu select null,1,primary_menu_id,'查库存','/showinventory' from tb_primary_menu where primary_menu_name='库存信息';
insert into tb_secondary_menu select null,2,primary_menu_id,'补货申请','/replenishmentapplication' from tb_primary_menu where primary_menu_name='库存信息';
insert into tb_secondary_menu select null,3,primary_menu_id,'订单信息','/orderInformation' from tb_primary_menu where primary_menu_name='库存信息';
-- 库房管理员 出入库信息
insert into tb_secondary_menu select null,1,primary_menu_id,'采购入库单','/purchaselist' from tb_primary_menu where primary_menu_name='出入库信息';
insert into tb_secondary_menu select null,2,primary_menu_id,'批发出库单','/wholesaledeliverylist' from tb_primary_menu where primary_menu_name='出入库信息';
insert into tb_secondary_menu select null,3,primary_menu_id,'零售出库单','/retaildeliverylist' from tb_primary_menu where primary_menu_name='出入库信息';
-- 库房管理员 库存盘点
insert into tb_secondary_menu select null,1,primary_menu_id,'盘点管理','/stocktaking' from tb_primary_menu where primary_menu_name='库存盘点';
insert into tb_secondary_menu select null,2,primary_menu_id,'盘点设置','/stocktaking/viewStocktakingRules' from tb_primary_menu where primary_menu_name='库存盘点';

-- 营业员 收银管理
insert into tb_secondary_menu select null,1,primary_menu_id,'批发收银','/deliverycashier' from tb_primary_menu where primary_menu_name='收银管理';
insert into tb_secondary_menu select null,2,primary_menu_id,'零售收银','/retailcashier' from tb_primary_menu where primary_menu_name='收银管理';

-- 营业员 退货管理
insert into tb_secondary_menu select null,1,primary_menu_id,'批发退货','/deliveryreturn' from tb_primary_menu where primary_menu_name='退货管理';
insert into tb_secondary_menu select null,2,primary_menu_id,'零售退货','/retailreturn' from tb_primary_menu where primary_menu_name='退货管理';

-- 职工 职工一级菜单名
insert into tb_secondary_menu select null,1,primary_menu_id,'货品盘点','/stocktaking/viewStocktakingGoodsList' from tb_primary_menu where primary_menu_name='职工一级菜单名';
insert into tb_secondary_menu select null,2,primary_menu_id,'入库检查','/exportinspect' from tb_primary_menu where primary_menu_name='职工一级菜单名';


-- 超级管理员 用户管理
insert into tb_secondary_menu select null,2,primary_menu_id,'用户列表','/stafflist' from tb_primary_menu where primary_menu_name='用户管理';

-- 超级管理员 权限管理
insert into tb_secondary_menu select null,1,primary_menu_id,'用户列表','/stafflistjurisdiction' from tb_primary_menu where primary_menu_name='权限管理';



-- 三级功能表
drop table if exists tb_function;
CREATE TABLE `tb_function`  (
  `function_id` int AUTO_INCREMENT NOT NULL COMMENT '功能id',
  `function_weight` int NOT NULL COMMENT '功能权重',
  `function_name` varchar(50) NOT NULL COMMENT '功能名',
  `secondary_menu_id` int NOT NULL COMMENT '二级菜单ID',
  `function_url` varchar(255) NULL COMMENT '功能请求url',
  PRIMARY KEY (`function_id`)
);

-- 库房管理员 查库存
insert into tb_function select null,1,'查看',secondary_menu_id,'/showinventory/goodsdetails' from tb_secondary_menu where secondary_menu_name='查库存';
insert into tb_function select null,2,'修改',secondary_menu_id,'/showinventory/modifygoods' from tb_secondary_menu where secondary_menu_name='查库存';

insert into tb_function select null,4,'新货补充',secondary_menu_id,'/showinventory/newgoods' from tb_secondary_menu where secondary_menu_name='查库存';

-- 库房管理员 补货申请
-- ？？？？？？？？？
insert into tb_function select null,1,'补货提交',secondary_menu_id,'/replenishmentapplication/replenishmentcommit' from tb_secondary_menu where secondary_menu_name='补货申请';
insert into tb_function select null,2,'新货补充提交',secondary_menu_id,'/showinventory/newgoodscommit' from tb_secondary_menu where secondary_menu_name='补货申请';

-- 库房管理员 订单信息
insert into tb_function select null,1,'查看',secondary_menu_id,'/orderInformation/orderdetails' from tb_secondary_menu where secondary_menu_name='订单信息';

-- 库房管理员 采购入库单
insert into tb_function select null,1,'查看',secondary_menu_id,'/purchaselist/purchasedetails' from tb_secondary_menu where secondary_menu_name='采购入库单';
insert into tb_function select null,2,'修改',secondary_menu_id,'/purchaselist/purchasedetails/modify' from tb_secondary_menu where secondary_menu_name='采购入库单';
insert into tb_function select null,3,'确认入库',secondary_menu_id,'/purchaselist/confirm' from tb_secondary_menu where secondary_menu_name='采购入库单';
insert into tb_function select null,4,'拒收',secondary_menu_id,'/purchaselist/rejection' from tb_secondary_menu where secondary_menu_name='采购入库单';


-- 库房管理员 批发出库单
insert into tb_function select null,1,'确认出库',secondary_menu_id,'/wholesaledeliverylist/confirmwarehousing' from tb_secondary_menu where secondary_menu_name='批发出库单';
insert into tb_function select null,2,'查看',secondary_menu_id,'/wholesaledeliverylist/warehousingdetails' from tb_secondary_menu where secondary_menu_name='批发出库单';
insert into tb_function select null,3,'查看详情',secondary_menu_id,'/wholesaledeliverylist/warehousinggoodsdetails' from tb_secondary_menu where secondary_menu_name='批发出库单';

-- 库房管理员 零售出库单
insert into tb_function select null,1,'查看',secondary_menu_id,'/retaildeliverylist/retaildetails' from tb_secondary_menu where secondary_menu_name='零售出库单';
insert into tb_function select null,2,'查看详情',secondary_menu_id,'/retaildeliverylist/retailgoodsdetails' from tb_secondary_menu where secondary_menu_name='零售出库单';

-- 库房管理员 盘点管理
insert into tb_function select null,1,'查看',secondary_menu_id,'/stocktaking/stocktakinggoodslist' from tb_secondary_menu where secondary_menu_name='盘点管理';
insert into tb_function select null,2,'查看详情',secondary_menu_id,'/stocktaking/stocktakinggoodsdetails' from tb_secondary_menu where secondary_menu_name='盘点管理';
insert into tb_function select null,3,'修改',secondary_menu_id,'/stocktaking/stocktakinggoodsmodify' from tb_secondary_menu where secondary_menu_name='盘点管理';
insert into tb_function select null,4,'提醒',secondary_menu_id,'/stocktaking/stocktakinggoodsremind' from tb_secondary_menu where secondary_menu_name='盘点管理';
insert into tb_function select null,5,'提交总盘点',secondary_menu_id,'/stocktaking/submitStocktaking' from tb_secondary_menu where secondary_menu_name='盘点管理';

insert into tb_function select null,6,'选取需盘点商品列表',secondary_menu_id,'/stocktaking/selectStocktakingGoods' from tb_secondary_menu where secondary_menu_name='盘点管理';
insert into tb_function select null,7,'发起盘点',secondary_menu_id,'/stocktaking/initiateStocktaking' from tb_secondary_menu where secondary_menu_name='盘点管理';

-- 库房管理员 盘点设置
insert into tb_function select null,1,'确认修改',secondary_menu_id,'/stocktaking/modifyStocktakingRules' from tb_secondary_menu where secondary_menu_name='盘点设置';

-- 营业员 批发收银
insert into tb_function select null,1,'批发出库单提交',secondary_menu_id,'/deliverycashier/commit' from tb_secondary_menu where secondary_menu_name='批发收银';

-- 营业员 零售收银
insert into tb_function select null,1,'零售订单提交',secondary_menu_id,'/etailcashier/commit' from tb_secondary_menu where secondary_menu_name='零售收银';

-- 营业员 批发退货
insert into tb_function select null,1,'查看详情',secondary_menu_id,'/deliveryreturn/deliverydetails' from tb_secondary_menu where secondary_menu_name='批发退货';
insert into tb_function select null,2,'提交',secondary_menu_id,'/deliveryreturn/commit' from tb_secondary_menu where secondary_menu_name='批发退货';

-- 营业员 零售退货
insert into tb_function select null,1,'查看详情',secondary_menu_id,'/retailreturn/retaildetails' from tb_secondary_menu where secondary_menu_name='零售退货';
insert into tb_function select null,2,'提交',secondary_menu_id,'/retailreturn/commit' from tb_secondary_menu where secondary_menu_name='零售退货';

-- 职工 货品盘点
insert into tb_function select null,1,'盘点',secondary_menu_id,'/stocktaking/stocktaking' from tb_secondary_menu where secondary_menu_name='货品盘点';
insert into tb_function select null,2,'盘点提交',secondary_menu_id,'/stocktaking/submitStocktakingGood' from tb_secondary_menu where secondary_menu_name='货品盘点';

-- 职工 入库检查
insert into tb_function select null,1,'确认信息',secondary_menu_id,'/exportinspect/confirm' from tb_secondary_menu where secondary_menu_name='入库检查';
insert into tb_function select null,2,'提交',secondary_menu_id,'/exportinspect/submit' from tb_secondary_menu where secondary_menu_name='入库检查';


-- 超级管理员 用户列表
insert into tb_function select null,1,'提交修改',secondary_menu_id,'/stafflist/modifycommit' from tb_secondary_menu where secondary_menu_name='用户列表' and primary_menu_id=(select primary_menu_id from tb_primary_menu where primary_menu_name='用户管理');
insert into tb_function select null,2,'删除',secondary_menu_id,'/stafflist/deletestaff' from tb_secondary_menu where secondary_menu_name='用户列表' and primary_menu_id=(select primary_menu_id from tb_primary_menu where primary_menu_name='用户管理');
insert into tb_function select null,3,'角色分配',secondary_menu_id,'/stafflist/positiondistribution' from tb_secondary_menu where secondary_menu_name='用户列表' and primary_menu_id=(select primary_menu_id from tb_primary_menu where primary_menu_name='用户管理');
insert into tb_function select null,4,'角色分配提交',secondary_menu_id,'/stafflist/positiondistributioncommit' from tb_secondary_menu where secondary_menu_name='用户列表' and primary_menu_id=(select primary_menu_id from tb_primary_menu where primary_menu_name='用户管理');

-- 超级管理员 用户列表（权限）
insert into tb_function select null,1,'提交修改',secondary_menu_id,'/stafflistjurisdiction/modifycommit' from tb_secondary_menu where secondary_menu_name='用户列表' and primary_menu_id=(select primary_menu_id from tb_primary_menu where primary_menu_name='权限管理');
insert into tb_function select null,2,'删除',secondary_menu_id,'/stafflistjurisdiction/deletestaff' from tb_secondary_menu where secondary_menu_name='用户列表' and primary_menu_id=(select primary_menu_id from tb_primary_menu where primary_menu_name='权限管理');
insert into tb_function select null,3,'权限分配',secondary_menu_id,'/stafflistjurisdiction/jurisdictiondistribution' from tb_secondary_menu where secondary_menu_name='用户列表' and primary_menu_id=(select primary_menu_id from tb_primary_menu where primary_menu_name='权限管理');
insert into tb_function select null,4,'权限分配提交',secondary_menu_id,'/stafflistjurisdiction/jurisdictiondistributioncommit' from tb_secondary_menu where secondary_menu_name='用户列表' and primary_menu_id=(select primary_menu_id from tb_primary_menu where primary_menu_name='权限管理');
insert into tb_function select null,5,'权限删除',secondary_menu_id,'/stafflistjurisdiction/jurisdictiondelete' from tb_secondary_menu where secondary_menu_name='用户列表' and primary_menu_id=(select primary_menu_id from tb_primary_menu where primary_menu_name='权限管理');
insert into tb_function select null,6,'权限删除提交',secondary_menu_id,'/stafflistjurisdiction/jurisdictiondeletecommit' from tb_secondary_menu where secondary_menu_name='用户列表' and primary_menu_id=(select primary_menu_id from tb_primary_menu where primary_menu_name='权限管理');


-- 职工拥有的功能权限表
drop table if exists tb_staff_jurisdiction;
CREATE TABLE `tb_staff_jurisdiction` (
  `staff_id` int NOT NULL COMMENT '职工ID',
  `function_id` int NOT NULL COMMENT '功能id',
  `jurisdiction_status` int default 1 NOT NULL COMMENT '功能状态(预留字段1、可用0、不可用)',  
  PRIMARY KEY (`staff_id`, `function_id`)
);
-- rollback;
-- 职位字典表
drop table if exists tb_staff_position;
CREATE TABLE `tb_staff_position`  (
  `staff_position_id` int NOT NULL COMMENT '职位id',
  `staff_position_name` varchar(255) NOT NULL COMMENT '职位名',
  PRIMARY KEY (`staff_position_id`)
);
insert into tb_staff_position values(1,'总经理');
insert into tb_staff_position values(2,'副经理');
insert into tb_staff_position values(3,'财务');
insert into tb_staff_position values(4,'库房管理员');
insert into tb_staff_position values(5,'职工');
insert into tb_staff_position values(6,'营业员');
insert into tb_staff_position values(7,'超级管理员');

-- 职工职位表
drop table if exists tb_staff_position_relation;
CREATE TABLE `tb_staff_position_relation`  (
  `staff_id` int NOT NULL COMMENT '职工ID',
  `staff_position_id` int NOT NULL COMMENT '职位id',
  `staff_position_status` int NULL COMMENT '职位状态(预留字段1、可用0、冻结)',
  PRIMARY KEY (`staff_id`, `staff_position_id`)
);


drop table if exists tb_default_position_menu;
CREATE TABLE `tb_default_position_menu`  (
  `secondary_menu_id` int NOT NULL COMMENT '二级菜单ID',
  `staff_position_id` int NOT NULL COMMENT '职位id',
  PRIMARY KEY (`secondary_menu_id`, `staff_position_id`)
);
insert into tb_default_position_menu select secondary_menu_id,4 from tb_secondary_menu where secondary_menu_name='查库存';
insert into tb_default_position_menu select secondary_menu_id,4 from tb_secondary_menu where secondary_menu_name='补货申请';
insert into tb_default_position_menu select secondary_menu_id,4 from tb_secondary_menu where secondary_menu_name='订单信息';
insert into tb_default_position_menu select secondary_menu_id,4 from tb_secondary_menu where secondary_menu_name='采购入库单';
insert into tb_default_position_menu select secondary_menu_id,4 from tb_secondary_menu where secondary_menu_name='批发出库单';
insert into tb_default_position_menu select secondary_menu_id,4 from tb_secondary_menu where secondary_menu_name='零售出库单';
insert into tb_default_position_menu select secondary_menu_id,4 from tb_secondary_menu where secondary_menu_name='盘点管理';
insert into tb_default_position_menu select secondary_menu_id,4 from tb_secondary_menu where secondary_menu_name='盘点设置';

insert into tb_default_position_menu select secondary_menu_id,5 from tb_secondary_menu where secondary_menu_name='货品盘点';
insert into tb_default_position_menu select secondary_menu_id,5 from tb_secondary_menu where secondary_menu_name='入库检查';



insert into tb_default_position_menu select secondary_menu_id,6 from tb_secondary_menu where secondary_menu_name='批发收银';
insert into tb_default_position_menu select secondary_menu_id,6 from tb_secondary_menu where secondary_menu_name='零售收银';
insert into tb_default_position_menu select secondary_menu_id,6 from tb_secondary_menu where secondary_menu_name='批发退货';
insert into tb_default_position_menu select secondary_menu_id,6 from tb_secondary_menu where secondary_menu_name='零售退货';

insert into tb_default_position_menu select secondary_menu_id,7 from tb_secondary_menu where secondary_menu_name='用户列表';

-- 默认功能赋权触发器
drop trigger if exists auto_insert_function;

delimiter //
create trigger auto_insert_function 
after insert on tb_staff_position_relation 
for each row
BEGIN
	insert into tb_staff_jurisdiction select new.staff_id,function_id,1 from 
	(select function_id from tb_function where secondary_menu_id in (select secondary_menu_id from tb_default_position_menu where staff_position_id=new.staff_position_id)) newtable;
END;
//
-- test auto_insert_function
-- insert into tb_staff_position_relation values('2018110417','4',1);

-- 取消职位删除或者改变状态auto
drop trigger if exists auto_delete_function;
//
create trigger auto_delete_function 
after delete on tb_staff_position_relation 
for each row
BEGIN
	delete from tb_staff_jurisdiction where staff_id=old.staff_id and function_id in  
	(select function_id from tb_function where secondary_menu_id in (select secondary_menu_id from tb_default_position_menu where staff_position_id=old.staff_position_id));
END;
//
delimiter ;
-- test auto_delete_function
-- delete from tb_staff_position_relation where staff_id='2018110417' and staff_position_id='4';

drop table if exists tb_staff;
CREATE TABLE `tb_staff` (
  `staff_id` int(10) NOT NULL AUTO_INCREMENT,
  `staff_name` varchar(32) NOT NULL,
  `staff_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `staff_phone` varchar(32) NOT NULL,
  `staff_position` int(9) DEFAULT NULL COMMENT '职工职位',
  `staff_status` int(9) NOT NULL DEFAULT '1001' COMMENT '职工状态',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `staff_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `staff_expire_time` datetime DEFAULT NULL,
  `staff_login_time` datetime DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`staff_id`)
);

-- 初始化一个系统管理员
-- insert into tb_staff(staff_name,staff_password,staff_phone) values('administrater','123456','17608211356');
insert into tb_staff_position_relation select staff_id,7,null from tb_staff where staff_phone='13219430016';
-- insert into tb_staff_position_relation select staff_id,7,null from tb_staff where staff_phone='17608211355';