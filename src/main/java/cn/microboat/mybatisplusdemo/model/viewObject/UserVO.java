package cn.microboat.mybatisplusdemo.model.viewObject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhouwei
 */
@Data
@Accessors(chain = true)
public class UserVO {
    private String id;
    private String name;
    private Integer age;
    private String email;
}
