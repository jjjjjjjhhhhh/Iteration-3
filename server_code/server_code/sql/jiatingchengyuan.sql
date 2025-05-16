-- 创建家庭成员表
CREATE TABLE `jiatingchengyuan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `yonghuzhanghao` varchar(200) DEFAULT NULL COMMENT '用户账号',
  `yonghuxingming` varchar(200) DEFAULT NULL COMMENT '用户姓名',
  `chengyuanzhanghao` varchar(200) DEFAULT NULL COMMENT '家庭成员账号',
  `chengyuanxingming` varchar(200) DEFAULT NULL COMMENT '家庭成员姓名',
  `guanxi` varchar(200) DEFAULT NULL COMMENT '关系',
  `beizhu` varchar(200) DEFAULT NULL COMMENT '备注',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='家庭成员'; 