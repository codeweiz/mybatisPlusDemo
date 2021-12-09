package cn.microboat.mybatisplusdemo.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhouwei
 */
@Data
@Accessors(chain = true)
public class Error {
    // 错误提示信息
    private String message;
    // 错误编码
    private Integer code;
}
