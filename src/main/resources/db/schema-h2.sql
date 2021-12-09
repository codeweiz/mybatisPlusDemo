DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id varchar(20) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(3) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    delete_flag varchar(1) not null default '0' comment '删除标志',
    creator_id varchar(20) null comment '创建人id',
    create_time datetime null comment '创建时间',
    updator_id varchar(20) null comment '更新人id',
    update_time datetime null comment '更新时间',
    PRIMARY KEY (id)
);
