package cn.microboat.mybatisplusdemo.utils;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhouwei
 */
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    private final AtomicLong atomicLong = new AtomicLong(1);

    // 对应 TableId(type = IdType.ASSIGN_ID)
    @Override
    public Long nextId(Object entity) {
        // 可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
//        String bizKey = entity.getClass().getName();
//        MetaObject metaObject = SystemMetaObject.forObject(entity);
//        String name = (String) metaObject.getValue("name");
//        final long id = atomicLong.getAndAdd(1);
        return atomicLong.getAndAdd(1);
    }

    // 对应 TableId(type = IdType.ASSIGN_UUID)
    @Override
    public String nextUUID(Object entity) {
//        return IdentifierGenerator.super.nextUUID(entity);
        return String.valueOf(new UUIDGenerator(1,2).nextId());
    }
}
