package cn.microboat.mybatisplusdemo.mapper;

import cn.microboat.mybatisplusdemo.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhouwei
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
