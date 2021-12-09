package cn.microboat.mybatisplusdemo.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhouwei
 */
@Data
@Accessors(chain = true)
@TableName(value = "user")
public class User implements Serializable {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String name;
    private Integer age;
    private String email;
    private String deleteFlag;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private String creatorId;
    private Date updateTime;
    private String updatorId;
}
