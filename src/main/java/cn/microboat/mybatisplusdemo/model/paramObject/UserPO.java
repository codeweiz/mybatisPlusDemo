package cn.microboat.mybatisplusdemo.model.paramObject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhouwei
 */
@Data
@Accessors(chain = true)
public class UserPO {
    /**
     * 用户 id
     * */
    private String id;
    /**
     * 用户名称
     * */
    private String name;
    /**
     * 用户年龄
     * */
    private Integer age;
    /**
     * 电子邮箱
     * */
    private String email;
    /**
     * 操作人 id
     * */
    private String operatorId;
    /**
     * 关键词
     * */
    private String keyword;
}
