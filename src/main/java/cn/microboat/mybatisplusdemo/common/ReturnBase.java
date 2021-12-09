package cn.microboat.mybatisplusdemo.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhouwei
 */
@Data
@Accessors(chain = true)
public class ReturnBase<T> {
    /**
     * 是否返回正确
     * */
    private boolean success;
    /**
     * 返回数据，T 通用范型
     * */
    private T data;
    /**
     * 错误提示信息与错误编码（静态常量实现）
     * */
    private Error error;
    /**
     * 集合长度
     * */
    private Long dataCount;
    /**
     * 错误提示信息与错误编码（枚举实现）
     * */
    private ErrorEnum errorEnum;

    /**
     * 确认返回无误
     * */
    public <W> ReturnBase<W> succeed(W w) {
        return new ReturnBase<W>().setSuccess(true).setData(w);
    }

    /**
     * 确认返回无误（带数据长度）
     * */
    public <W> ReturnBase<W> succeedWithDataCount(W w, Long dataCount) {
        return new ReturnBase<W>().setSuccess(true).setData(w).setDataCount(dataCount);
    }

    /**
     * 确认操作失败（以枚举返回）
     * */
    public <W> ReturnBase<W> failedWithErrorEnum(ErrorEnum errorEnum) {
        return new ReturnBase<W>().setSuccess(false).setErrorEnum(errorEnum);
    }

    /**
     * 确认操作失败（以静态常量返回）
     * */
    public <W> ReturnBase<W> failedWithError(Error error) {
        return new ReturnBase<W>().setSuccess(false).setError(error);
    }
}
