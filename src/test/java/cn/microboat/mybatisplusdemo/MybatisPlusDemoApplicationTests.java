package cn.microboat.mybatisplusdemo;

import cn.microboat.mybatisplusdemo.mapper.UserMapper;
import cn.microboat.mybatisplusdemo.model.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class MybatisPlusDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(LocalDateTime.now());

    }

}
