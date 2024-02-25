package com.lut.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

import java.util.Date;
import java.io.Serializable;

/**
 * 标签(Tag)实体类
 *
 * @author qianye
 * @since 2024-02-21 11:08:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("qy_tag")
public class Tag implements Serializable {
    private static final long serialVersionUID = 868614145257718964L;

    @TableId
    private Long id;
    /**
     * 标签名
     */
    private String name;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableLogic
    private Integer delFlag;
    /**
     * 备注
     */
    private String remark;
}

