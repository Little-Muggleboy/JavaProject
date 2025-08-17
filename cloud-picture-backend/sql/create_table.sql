-- 创建数据库
create database if not exists yiyun_picture;


-- 切换库
use yiyun_picture;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin',
    editTime     datetime     default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    UNIQUE KEY uk_userAccount (userAccount),
    INDEX idx_userName (userName)
    ) comment '用户表' collate = utf8mb4_unicode_ci;


-- 图片表
create table if not exists picture
(
    id           bigint auto_increment comment 'id' primary key,
    url          varchar(512)                       not null comment '图片 url',
    name         varchar(128)                       not null comment '图片名称',
    introduction varchar(512)                       null comment '简介',
    category     varchar(64)                        null comment '分类',
    tags         varchar(512)                       null comment '标签（JSON 数组）',
    picSize      bigint                             null comment '图片体积',
    picWidth     int                                null comment '图片宽度',
    picHeight    int                                null comment '图片高度',
    picScale     double                             null comment '图片宽高比例',
    picFormat    varchar(32)                        null comment '图片格式',
    userId       bigint                             not null comment '创建用户 id',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    editTime     datetime default CURRENT_TIMESTAMP not null comment '编辑时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除',
    reviewStatus INT DEFAULT 0 NOT NULL COMMENT '审核状态：0-待审核; 1-通过; 2-拒绝',
    reviewMessage VARCHAR(512) NULL COMMENT '审核信息',
    reviewerId BIGINT NULL COMMENT '审核人 ID',
    reviewTime DATETIME NULL COMMENT '审核时间',
    thumbnailUrl varchar(512) NULL COMMENT '缩略图 url',
    spaceId bigint  null comment '空间 id（为空表示公共空间）',
    picColor varchar(16) null comment '图片主色调'
    ) comment '图片表' collate = utf8mb4_unicode_ci;

-- 空间表
create table if not exists space
(
    id         bigint auto_increment comment 'id' primary key,
    spaceName  varchar(128)                       null comment '空间名称',
    spaceLevel int      default 0                 null comment '空间级别：0-普通版 1-专业版 2-旗舰版',
    maxSize    bigint   default 0                 null comment '空间图片的最大总大小',
    maxCount   bigint   default 0                 null comment '空间图片的最大数量',
    totalSize  bigint   default 0                 null comment '当前空间下图片的总大小',
    totalCount bigint   default 0                 null comment '当前空间下的图片数量',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    editTime   datetime default CURRENT_TIMESTAMP not null comment '编辑时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    spaceType  int      default 0                 not null comment '空间类型：0-私有 1-团队'
    ) comment '空间表' collate = utf8mb4_unicode_ci;

-- 空间用户关联表
create table if not exists space_user
(
    id         bigint auto_increment comment 'id' primary key,
    spaceId    bigint                                 not null comment '空间 id',
    userId     bigint                                 not null comment '用户 id',
    spaceRole  varchar(128) default 'viewer'          null comment '空间角色：viewer/editor/admin',
    createTime datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
    ) comment '空间用户关联表' collate = utf8mb4_unicode_ci;

-- 用户表索引
CREATE UNIQUE INDEX uk_userAccount ON user (userAccount);
CREATE INDEX idx_userName ON user (userName);

-- 图片表索引
CREATE INDEX idx_name ON picture (name);
CREATE INDEX idx_introduction ON picture (introduction);
CREATE INDEX idx_category ON picture (category);
CREATE INDEX idx_tags ON picture (tags);
CREATE INDEX idx_userId ON picture (userId);
CREATE INDEX idx_reviewStatus ON picture (reviewStatus);
CREATE INDEX idx_spaceId ON picture (spaceId);

-- 空间表索引
CREATE INDEX idx_userId ON space (userId);
CREATE INDEX idx_spaceName ON space (spaceName);
CREATE INDEX idx_spaceLevel ON space (spaceLevel);
CREATE INDEX idx_spaceType ON space (spaceType);

-- 空间用户关联表索引
CREATE UNIQUE INDEX uk_spaceId_userId ON space_user (spaceId, userId);
CREATE INDEX idx_spaceId ON space_user (spaceId);
CREATE INDEX idx_userId ON space_user (userId);