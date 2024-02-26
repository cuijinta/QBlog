# 项目sql
create table if not exists qianye_blog.qy_article
(
    id          bigint auto_increment
        primary key,
    title       varchar(256)       null comment '标题',
    content     longtext           null comment '文章内容',
    summary     varchar(1024)      null comment '文章摘要',
    category_id bigint             null comment '所属分类id',
    thumbnail   varchar(256)       null comment '缩略图',
    is_top      char   default '0' null comment '是否置顶（0否，1是）',
    status      char   default '1' null comment '状态（0已发布，1草稿）',
    view_count  bigint default 0   null comment '访问量',
    is_comment  char   default '1' null comment '是否允许评论 1是，0否',
    create_by   bigint             null,
    create_time datetime           null,
    update_by   bigint             null,
    update_time datetime           null,
    del_flag    int    default 0   null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '文章表';

create table if not exists qianye_blog.qy_article_tag
(
    article_id bigint null comment '文章id',
    tag_id     bigint null comment '标签id',
    id bigint primary key AUTO_INCREMENT
)
    comment '文章标签关联表';

create table if not exists qianye_blog.qy_category
(
    id          bigint auto_increment
        primary key,
    name        varchar(128)       null comment '分类名',
    pid         bigint default -1  null comment '父分类id，如果没有父分类为-1',
    description varchar(512)       null comment '描述',
    status      char   default '0' null comment '状态0:正常,1禁用',
    create_by   bigint             null,
    create_time datetime           null,
    update_by   bigint             null,
    update_time datetime           null,
    del_flag    int    default 0   null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '分类表';

create table if not exists qianye_blog.qy_comment
(
    id                 bigint auto_increment
        primary key,
    type               char   default '0' null comment '评论类型（0代表文章评论，1代表友链评论）',
    article_id         bigint             null comment '文章id',
    root_id            bigint default -1  null comment '根评论id',
    content            varchar(512)       null comment '评论内容',
    to_comment_user_id bigint default -1  null comment '所回复的目标评论的userid',
    to_comment_id      bigint default -1  null comment '回复目标评论id',
    create_by          bigint             null,
    create_time        datetime           null,
    update_by          bigint             null,
    update_time        datetime           null,
    del_flag           int    default 0   null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '评论表';

create table if not exists qianye_blog.qy_link
(
    id          bigint auto_increment
        primary key,
    name        varchar(256)     null,
    logo        varchar(256)     null,
    description varchar(512)     null,
    address     varchar(128)     null comment '网站地址',
    status      char default '2' null comment '审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)',
    create_by   bigint           null,
    create_time datetime         null,
    update_by   bigint           null,
    update_time datetime         null,
    del_flag    int  default 0   null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '友链';

create table if not exists qianye_blog.qy_tag
(
    id          bigint auto_increment
        primary key,
    name        varchar(128)  null comment '标签名',
    create_by   bigint        null,
    create_time datetime      null,
    update_by   bigint        null,
    update_time datetime      null,
    del_flag    int default 0 null comment '删除标志（0代表未删除，1代表已删除）',
    remark      varchar(500)  null comment '备注'
)
    comment '标签';

create table if not exists qianye_blog.sys_menu
(
    id          bigint auto_increment comment '菜单ID'
        primary key,
    menu_name   varchar(50)              not null comment '菜单名称',
    parent_id   bigint       default 0   null comment '父菜单ID',
    order_num   int(4)       default 0   null comment '显示顺序',
    path        varchar(200) default ''  null comment '路由地址',
    component   varchar(255)             null comment '组件路径',
    is_frame    int(1)       default 1   null comment '是否为外链（0是 1否）',
    menu_type   char         default ''  null comment '菜单类型（M目录 C菜单 F按钮）',
    visible     char         default '0' null comment '菜单状态（0显示 1隐藏）',
    status      char         default '0' null comment '菜单状态（0正常 1停用）',
    perms       varchar(100)             null comment '权限标识',
    icon        varchar(100) default '#' null comment '菜单图标',
    create_by   bigint                   null comment '创建者',
    create_time datetime                 null comment '创建时间',
    update_by   bigint                   null comment '更新者',
    update_time datetime                 null comment '更新时间',
    remark      varchar(500) default ''  null comment '备注',
    del_flag    char         default '0' null
)
    comment '菜单权限表' charset = utf8;

create table if not exists qianye_blog.sys_role
(
    id          bigint auto_increment comment '角色ID'
        primary key,
    role_name   varchar(30)      not null comment '角色名称',
    role_key    varchar(100)     not null comment '角色权限字符串',
    role_sort   int(4)           not null comment '显示顺序',
    status      char             not null comment '角色状态（0正常 1停用）',
    del_flag    char default '0' null comment '删除标志（0代表存在 1代表删除）',
    create_by   bigint           null comment '创建者',
    create_time datetime         null comment '创建时间',
    update_by   bigint           null comment '更新者',
    update_time datetime         null comment '更新时间',
    remark      varchar(500)     null comment '备注'
)
    comment '角色信息表' charset = utf8;

create table if not exists qianye_blog.sys_role_menu
(
    role_id bigint comment '角色ID',
    menu_id bigint comment '菜单ID',
    id bigint  primary key
)
    comment '角色和菜单关联表' charset = utf8;

create table if not exists qianye_blog.sys_user_role
(
    user_id bigint not null comment '用户ID',
    role_id bigint not null comment '角色ID',
    primary key (user_id, role_id)
)
    comment '用户和角色关联表' charset = utf8;

create table if not exists qianye_blog.user
(
    id           bigint auto_increment comment '主键'
        primary key,
    user_name    varchar(64) default 'NULL' not null comment '用户名',
    nick_name    varchar(64) default 'NULL' not null comment '昵称',
    password     varchar(64) default 'NULL' not null comment '密码',
    type         char        default '0'    null comment '用户类型：0代表普通用户，1代表管理员',
    status       char        default '0'    null comment '账号状态（0正常 1停用）',
    email        varchar(64)                null comment '邮箱',
    phone_number varchar(32)                null comment '手机号',
    sex          char                       null comment '用户性别（0男，1女，2未知）',
    avatar       varchar(128)               null comment '头像',
    create_by    bigint                     null comment '创建人的用户id',
    create_time  datetime                   null comment '创建时间',
    update_by    bigint                     null comment '更新人',
    update_time  datetime                   null comment '更新时间',
    del_flag     int         default 0      null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '用户表';

# user表名修改  2024.2.16
rename table user to sys_user;

# 2024.2.25 数据库创建时间与更新时间设置
alter table qy_tag
    modify update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP;
alter table qy_tag
    modify create_time datetime default CURRENT_TIMESTAMP null;

# 2024.2.26 修改bug,设置角色菜单表主键自增
alter table sys_role_menu
    modify id bigint auto_increment;

alter table sys_role_menu
    auto_increment = 1;