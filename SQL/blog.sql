create table if not exists qianye_blog.qy_article
(
    id          bigint(200) auto_increment
        primary key,
    title       varchar(256)            null comment '标题',
    content     longtext                null comment '文章内容',
    summary     varchar(1024)           null comment '文章摘要',
    category_id bigint                  null comment '所属分类id',
    thumbnail   varchar(256)            null comment '缩略图',
    is_top      char        default '0' null comment '是否置顶（0否，1是）',
    status      char        default '1' null comment '状态（0已发布，1草稿）',
    view_count  bigint(200) default 0   null comment '访问量',
    is_comment  char        default '1' null comment '是否允许评论 1是，0否',
    create_by   bigint                  null,
    create_time datetime                null,
    update_by   bigint                  null,
    update_time datetime                null,
    del_flag    int(1)      default 0   null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '文章表' charset = utf8mb4;

create table if not exists qianye_blog.qy_article_tag
(
    article_id bigint auto_increment comment '文章id',
    tag_id     bigint default 0 not null comment '标签id',
    primary key (article_id, tag_id)
)
    comment '文章标签关联表' charset = utf8mb4;

create table if not exists qianye_blog.qy_category
(
    id          bigint(200) auto_increment
        primary key,
    name        varchar(128)            null comment '分类名',
    pid         bigint(200) default -1  null comment '父分类id，如果没有父分类为-1',
    description varchar(512)            null comment '描述',
    status      char        default '0' null comment '状态0:正常,1禁用',
    create_by   bigint(200)             null,
    create_time datetime                null,
    update_by   bigint(200)             null,
    update_time datetime                null,
    del_flag    int         default 0   null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '分类表' charset = utf8mb4;

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
    del_flag           int(1) default 0   null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '评论表' charset = utf8mb4;

create table if not exists qianye_blog.qy_link
(
    id          bigint auto_increment
        primary key,
    name        varchar(256)       null,
    logo        varchar(256)       null,
    description varchar(512)       null,
    address     varchar(128)       null comment '网站地址',
    status      char   default '2' null comment '审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)',
    create_by   bigint             null,
    create_time datetime           null,
    update_by   bigint             null,
    update_time datetime           null,
    del_flag    int(1) default 0   null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '友链' charset = utf8mb4;

create table if not exists qianye_blog.qy_tag
(
    id          bigint auto_increment
        primary key,
    name        varchar(128)     null comment '标签名',
    create_by   bigint           null,
    create_time datetime         null,
    update_by   bigint           null,
    update_time datetime         null,
    del_flag    int(1) default 0 null comment '删除标志（0代表未删除，1代表已删除）',
    remark      varchar(500)     null comment '备注'
)
    comment '标签' charset = utf8mb4;

