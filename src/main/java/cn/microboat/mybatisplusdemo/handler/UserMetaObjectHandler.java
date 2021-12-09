package cn.microboat.mybatisplusdemo.handler;

import cn.microboat.mybatisplusdemo.utils.DateUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * @author zhouwei
 */
@Component
public class UserMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, DateUtils.getCurrentDate(DateUtils.YMDHMS_FORMAT));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Date.class, DateUtils.getCurrentDate(DateUtils.YMDHMS_FORMAT));
    }
}
