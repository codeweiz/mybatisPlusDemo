package cn.microboat.mybatisplusdemo.common;

/**
 * @author zhouwei
 */

public enum ErrorEnum {
    CANNOT_FIND_USER("找不到用户", 101),
    UPDATE_FAIL("修改失败", 102),
    POST_DATA_IS_NULL("上传信息为空", 103),
    USER_ADD_FAIL("用户添加失败", 104),
    USER_BATCH_ADD_FAIL("批量添加新用户失败", 105),
    USER_BATCH_DELETE_FAIL("批量删除用户失败", 106);

    private String message;
    private Integer errorCode;

    ErrorEnum(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }
}
