package cn.microboat.mybatisplusdemo.controller;

import cn.microboat.mybatisplusdemo.common.ReturnBase;
import cn.microboat.mybatisplusdemo.model.entity.User;
import cn.microboat.mybatisplusdemo.model.paramObject.UserPO;
import cn.microboat.mybatisplusdemo.model.viewObject.UserVO;
import cn.microboat.mybatisplusdemo.services.servicesImpl.UserServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhouwei
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserServiceImpl userService;

    /**
     * 增加新用户
     * */
    @PostMapping("/add")
    public ReturnBase<UserVO> addUser(@RequestBody UserPO userPO) {
        return userService.addUser(userPO);
    }

    /**
     * 批量增加新用户
     * */
    @PostMapping("/batch/add")
    public ReturnBase<List<UserVO>> batchAddUser(@RequestBody List<UserPO> userPOList) {
        return userService.batchAddUser(userPOList);
    }

    /**
     * 根据 id 删除用户
     * */
    @PostMapping("/delete")
    public ReturnBase<UserVO> deleteUser(@RequestBody UserPO userPO) {
        return userService.deleteUser(userPO);
    }

    /**
     * 根据 id 批量删除用户
     * */
    @PostMapping("/batch/delete")
    public ReturnBase<List<UserVO>> batchDeleteUser(@RequestBody List<UserPO> userPOList) {
        return userService.batchDeleteUser(userPOList);
    }

    /**
     * 根据 id 修改用户信息
     * */
    @PostMapping("/update")
    public ReturnBase<UserVO> updateUser(@RequestBody UserPO userPO) {
        return userService.updateUser(userPO);
    }

    /**
     * 根据 id 批量修改用户信息
     * */
    @PostMapping("/batch/update")
    public ReturnBase<List<UserVO>> batchUpdateUser(@RequestBody List<UserPO> userPOList) {
        return userService.batchUpdateUser(userPOList);
    }

    /**
     * 根据 id 查询用户信息
     * */
    @GetMapping("/query/{id}")
    public ReturnBase<UserVO> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    /**
     * 不分页获取用户列表
     * */
    @GetMapping("/list")
    public ReturnBase<List<UserVO>> getUserList() {
        return userService.getUserList();
    }

    /**
     * 分页获取用户列表
     * */
    @PostMapping("/page/list")
    public ReturnBase<List<UserVO>> getUserPageList(@RequestBody UserPO userPO, Page<User> page) {
        return userService.getUserPageList(userPO, page);
    }

}
