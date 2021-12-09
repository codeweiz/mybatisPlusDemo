package cn.microboat.mybatisplusdemo.common;

/**
 * @author zhouwei
 */

public enum LogicDeleteFlag {
    /**
     * NOT_DELETED：未删除，对应数据库的 "0"
     * DELETED：已删除，对应数据库的 "1"
     * */
    NOT_DELETED("0"),
    DELETED("1");

    private String deleteFlag;

    LogicDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
