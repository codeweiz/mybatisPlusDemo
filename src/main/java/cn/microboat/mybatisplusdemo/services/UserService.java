package cn.microboat.mybatisplusdemo.services;

import cn.microboat.mybatisplusdemo.common.ReturnBase;
import cn.microboat.mybatisplusdemo.model.entity.User;
import cn.microboat.mybatisplusdemo.model.paramObject.UserPO;
import cn.microboat.mybatisplusdemo.model.viewObject.UserVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author zhouwei
 * @date 2021/12/09 14:11
 */
public interface UserService extends IService<User> {
    /**
     * 不分页获取所有用户列表
     * @param userPO
     * @return List<UserVO>
     * */
    ReturnBase<List<UserVO>> getUserList(UserPO userPO);

    /**
     * 根据 id 获取用户信息
     * @param id
     * @return UserVO
     * */
    ReturnBase<UserVO> getUserById(String id);

    /**
     * 根据 id 删除用户
     * @param userPO
     * @return UserVO
     * */
    ReturnBase<UserVO> deleteUser(UserPO userPO);

    /**
     * 根据 id 修改用户
     * @param userPO
     * @return UserVO
     * */
    ReturnBase<UserVO> updateUser(UserPO userPO);

    /**
     * 添加用户
     * @param userPO
     * @return UserVO
     * */
    ReturnBase<UserVO> addUser(UserPO userPO);

    /**
     * 批量添加用户
     * @param userPOList
     * @return List<UserVO>
     * */
    ReturnBase<List<UserVO>> batchAddUser(List<UserPO> userPOList);

    /**
     * 根据 id 批量删除用户
     * @param userPOList
     * @return List<UserVO>
     * */
    ReturnBase<List<UserVO>> batchDeleteUser(List<UserPO> userPOList);

    /**
     * 根据 id 批量修改用户
     * @param userPOList
     * @return List<UserVO>
     * */
    ReturnBase<List<UserVO>> batchUpdateUser(List<UserPO> userPOList);

    /**
     * 分页获取用户列表
     * @param userPO, page
     * @return List<UserVO>
     * */
    ReturnBase<List<UserVO>> getUserPageList(UserPO userPO, Page<User> page);
}
