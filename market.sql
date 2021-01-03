drop table if exists tb_coupon;
CREATE TABLE `tb_coupon`  (
  `coupon_id` bigint(10) AUTO_INCREMENT NOT NULL COMMENT '订货单id',
  `coupon_goods_id` bigint(14) NOT NULL COMMENT '产品编号规定为13位或14位阿拉伯数字',
  `coupon_unit_id` int NOT NULL COMMENT '单位',
  `coupon_num` int NOT NULL COMMENT '订货数量',
  `coupon_time` datetime NOT NULL COMMENT '订货时间',
  `coupon_status` int NOT NULL default 0 COMMENT '订货状态',
  `coupon_staff_id` int NOT NULL COMMENT '发起职工id',
  PRIMARY KEY (`coupon_id`)
);

drop table if exists tb_delivery;
CREATE TABLE `tb_delivery`  (
  `delivery_id` varchar(32) NOT NULL COMMENT '出库单id',
  `delivery_stock_goods_id` bigint(15) NOT NULL COMMENT '超市对商品的编号',
  `delivery_price` decimal(8, 2) NOT NULL COMMENT '批发单价',
  `delivery_num` int(8) NOT NULL COMMENT '出库数量',
  PRIMARY KEY (`delivery_id`, `delivery_stock_goods_id`)
);

drop table if exists tb_delivery_record;
CREATE TABLE `tb_delivery_record`  (
  `delivery_id` varchar(32) NOT NULL COMMENT '出库单id',
  `delivery_paid` decimal(10, 2) NOT NULL COMMENT '已付款项',
  `delivery_status` int(2) NOT NULL COMMENT '出库状态',
  `delivery_launched_staff_id` bigint(10) NOT NULL COMMENT '发起职工id(收款员工)',
  `delivery_handle_staff_id` bigint(10) NULL COMMENT '处理职工id',
  `delivery_total_price` decimal(10, 2) NULL COMMENT '总价格',
  `delivery_check_out_status` int(2) NOT NULL COMMENT '结账状态',
  `delivery_refund_status` int(2) NOT NULL COMMENT '退款状态(default 0)(0、未发生退款1、已发生退款)',
  `delivery_create_date` datetime NOT NULL COMMENT '入库单创建时间',
  PRIMARY KEY (`delivery_id`)
);

drop table if exists tb_export_bill;
CREATE TABLE `tb_export_bill`  (
  `export_bill_id` varchar(32) NOT NULL COMMENT '入库单id',
  `export_bill_coupon_id` bigint(10) NOT NULL COMMENT '订货单id',
  `export_bill_supplier_id` bigint(10) NULL COMMENT '供应商id(仓库管理员选择)',
  `export_bill_goods_batch_number` varchar(8) NULL COMMENT '产品批号(仓库管理员填写)',
  `export_bill_production_date` date NULL COMMENT '生产日期(仓库管理员选择)',
  `export_bill_shelf_life` int(5) NULL COMMENT '保质期(仓库管理员填写)',
  `export_bill_price` decimal(8, 2) NULL COMMENT '供货价格(仓库管理员填写)',
  `export_bill_status` int(2) NULL COMMENT '入库状态(仓库管理员改变)',
  `export_bill_paid` decimal(10, 2) NULL COMMENT '已付款项(仓库管理员填写)',
  `export_bill_time` datetime NULL COMMENT '入库日期(入库状态变为已入库时修改）',
  `export_confirm_staff_id` bigint(10) NULL COMMENT '确认职工id',
  `export_submit_staff_id` bigint(10) NULL COMMENT '提交职工id',
  `export_bill_mark` varchar(255) NULL COMMENT '入库单备注',
  PRIMARY KEY (`export_bill_id`)
);




drop table if exists tb_goods;
CREATE TABLE `tb_goods`  (
  `goods_id` bigint(14) NOT NULL COMMENT '产品编号规定为13位或14位阿拉伯数字',
  `goods_name` varchar(255) NOT NULL COMMENT '产品名称',
  `goods_category_id` int(8) NOT NULL COMMENT '产品类别',
  `goods_brand` varchar(255) NOT NULL COMMENT '品牌名称',
  `goods_specifications` varchar(255) NOT NULL COMMENT '产品规格',
  `goods_picture` varchar(255) NULL COMMENT '产品图片',
  PRIMARY KEY (`goods_id`)
);

drop table if exists tb_goods_category;
CREATE TABLE `tb_goods_category`  (
  `category_id` int(8) NOT NULL AUTO_INCREMENT COMMENT '产品类别id',
  `category_name` varchar(32) NOT NULL COMMENT '产品类别名',
  `stocktaking_staff_id` int(10) NULL COMMENT '关联盘点员工id',
  PRIMARY KEY (`category_id`)
);



drop table if exists tb_retail_record;
CREATE TABLE `tb_retail_record`  (
  `retail_id` varchar(32) NOT NULL COMMENT '订单编号',
  `retail_time` datetime NOT NULL COMMENT '付款时间',
  `retail_collection_staff_id` bigint(10) NOT NULL COMMENT '收款员工编号',
  `retail_total_price` decimal(8, 2) NOT NULL COMMENT '商品总价格',
  `retail_refund_status` int(2) NOT NULL COMMENT '退款状态(default 0)(0、未发生退款1、已发生退款)',
  PRIMARY KEY (`retail_id`)
);

drop table if exists tb_retail;
CREATE TABLE `tb_retail`  (
  `retail_id` varchar(32) NOT NULL COMMENT '订单编号',
  `retail_stock_goods_id` bigint(15) NOT NULL COMMENT '超市对商品的编号',
  `retail_num` int(8) NOT NULL COMMENT '数量',
  `retail_price` decimal(8, 2) NOT NULL COMMENT '价格(单价)',
  PRIMARY KEY (`retail_id`, `retail_stock_goods_id`)
);

drop table if exists tb_stocktaking_record;
CREATE TABLE `tb_stocktaking_record`  (
  `stocktaking_id` bigint(10) NOT NULL COMMENT '盘点id(年月日+第几次盘点(2位))',
  `stocktaking_launched_staff_id` bigint(10) NOT NULL COMMENT '发起员工id',
  `stocktaking_submit_staff_id` bigint(10) NULL COMMENT '提交员工id',
  `stocktaking_profit_loss_price` decimal(10, 2) NULL COMMENT '该次盘点盈亏金额',
  `stocktaking_all_status` int(2) NULL COMMENT '盘点总状态',
  `stocktaking_launched_date` datetime NOT NULL COMMENT '盘点发起时间',
  `stocktaking_commit_date` datetime NULL COMMENT '盘点提交时间',
  PRIMARY KEY (`stocktaking_id`)
);

drop table if exists tb_stocktaking;
CREATE TABLE `tb_stocktaking`  (
  `stocktaking_id` bigint(10) NOT NULL COMMENT '盘点id(年月日+第几次盘点(2位))',
  `stocktaking_stock_goods_id` bigint(15) NOT NULL COMMENT '超市对商品的编号',
  `stock_num` int(8) NOT NULL COMMENT '当前库存数量',
  `stocktaking_num` int(8) NULL COMMENT '盘点数量(职工可修改)',
  `stocking_staff_id` bigint(10) NULL COMMENT '盘点员工id',
  `stocktaking_status` int(2) NOT NULL COMMENT '盘点状态(0、初始状态待盘点1、已盘点待提交2、仓库管理员已提交更新库存-1、取消盘点)',
  `stocking_remarks` varchar(255) NULL COMMENT '盘点备注(职工可修改)',
  `stocking_time` datetime NULL COMMENT '盘点时间(系统生成)',
  `stocktaking_profit_loss_status` int(2) NULL DEFAULT NULL COMMENT '盘点盈亏状态()',
  `stocking_price` decimal(8, 2) NULL COMMENT '盘点单价',
  PRIMARY KEY (`stocktaking_id`, `stocktaking_stock_goods_id`)
);

drop table if exists tb_unit;
CREATE TABLE `tb_unit`  (
  `unit_id` int(4) AUTO_INCREMENT NOT NULL COMMENT '销售单位id',
  `unit_name` varchar(10) NOT NULL COMMENT '销售单位名(瓶、箱、盒、袋）',
  PRIMARY KEY (`unit_id`)
);

drop table if exists tb_stock;
CREATE TABLE `tb_stock`  (
  `stock_goods_id` bigint(15) AUTO_INCREMENT NOT NULL COMMENT '超市对商品的编号(要不要通过编号中部分字段来表示类别？)AUTO_INCREMENT？',
  `stock_id` int(4) NOT NULL COMMENT '仓库编号(预留字段)',
  `goods_stock_id` bigint(14) NOT NULL COMMENT '产品编号规定为13位或14位阿拉伯数字',
  `stock_unit_id` int(4) NOT NULL COMMENT '销售单位id(瓶、箱、盒、袋）',
  `stock_goods_batch_number` int(8) NOT NULL COMMENT '产品批号(一般为6位或8位）',
  `stock_goods_production_date` date NOT NULL COMMENT '生产日期',
  `stock_goods_shelf_life` int(5) NOT NULL COMMENT '保质期(单位：天)',
  `stock_goods_price` decimal(8, 2) NOT NULL COMMENT '售价(单位：元)',
  `stock_inventory` int(8) NOT NULL COMMENT '实时库存',
  `stock_export_bill_id` varchar(32) NOT NULL COMMENT '入库编号(便于回溯)',
  PRIMARY KEY (`stock_goods_id`)
);

-- 退货记录表
drop table if exists tb_refund_customer_record;
CREATE TABLE `tb_refund_customer_record`  (
  `refund_customer_id` varchar(32) NOT NULL COMMENT '退货编号',
  `refund_customer_order_id` varchar(32) NOT NULL COMMENT '订单编号',
  `refund_customer_time` datetime NOT NULL COMMENT '退货日期',
  `refund_customer_reason` varchar(255) NULL COMMENT '退货原因',
  `order_type` int NOT NULL COMMENT '订单类型(1、批发订单2、零售订单)',
  `refund_customer_status` int NOT NULL COMMENT '退货状态()',
  `refund_staff_id` bigint(10) NOT NULL COMMENT '退货员工id',
  PRIMARY KEY (`refund_customer_id`, `order_type`)
);

-- 退货详情表
drop table if exists tb_refund_customer;
CREATE TABLE `tb_refund_customer`  (
  `refund_customer_id` varchar(32) NOT NULL COMMENT '退货编号',
  `refund_customer_stock_goods_id` bigint(15) NOT NULL COMMENT '商品代码',
  `refund_customer_num` int NOT NULL COMMENT '退货数量',
  `refund_customer_price` decimal(8, 2) NOT NULL COMMENT '退货单价',
  PRIMARY KEY (`refund_customer_id`, `refund_customer_stock_goods_id`)
);
