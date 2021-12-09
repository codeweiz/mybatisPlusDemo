package cn.microboat.mybatisplusdemo.services.servicesImpl;

import cn.microboat.mybatisplusdemo.common.ErrorEnum;
import cn.microboat.mybatisplusdemo.common.LogicDeleteFlag;
import cn.microboat.mybatisplusdemo.common.ReturnBase;
import cn.microboat.mybatisplusdemo.constant.UserConstant;
import cn.microboat.mybatisplusdemo.mapper.UserMapper;
import cn.microboat.mybatisplusdemo.model.entity.User;
import cn.microboat.mybatisplusdemo.model.paramObject.UserPO;
import cn.microboat.mybatisplusdemo.model.viewObject.UserVO;
import cn.microboat.mybatisplusdemo.services.UserService;
import cn.microboat.mybatisplusdemo.utils.DateUtils;
import cn.microboat.mybatisplusdemo.utils.User2VO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhouwei
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    /**
     *  根据 id 获取未删除的用户
     * */
    private User getUser(String id) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("id", id).eq("delete_flag", "0"));
    }

    @Override
    public ReturnBase<UserVO> addUser(UserPO userPO) {
        ReturnBase<User> returnBase = new ReturnBase<>();
        User user = new User();
        if (ObjectUtils.isEmpty(userPO)) {
            return returnBase.failedWithErrorEnum(ErrorEnum.POST_DATA_IS_NULL);
        }

        BeanUtils.copyProperties(userPO, user, "id", "operatorId");
        if (!ObjectUtils.isEmpty(userPO.getOperatorId())) {
            user.setUpdatorId(userPO.getOperatorId());
        }

        if (UserConstant.SUCCESS_FLAG.equals(userMapper.insert(user))) {
            return returnBase.succeed(new User2VO().user2Vo(user));
        } else {
            return returnBase.failedWithErrorEnum(ErrorEnum.USER_ADD_FAIL);
        }
    }

    @Override
    public ReturnBase<List<UserVO>> batchAddUser(List<UserPO> userPOList) {
        ReturnBase<List<UserVO>> returnBase = new ReturnBase<>();

        if (ObjectUtils.isEmpty(userPOList)) {
            return returnBase.failedWithErrorEnum(ErrorEnum.POST_DATA_IS_NULL);
        }

        ArrayList<User> userList = new ArrayList<>();
        userPOList.forEach(userPO -> {
            User user = new User();
            BeanUtils.copyProperties(userPO, user, "id", "operatorId");
            if (!ObjectUtils.isEmpty(userPO.getOperatorId())) {
                user.setUpdatorId(userPO.getOperatorId());
            }
            userList.add(user);
        });
        if (userService.saveBatch(userList)) {
            return returnBase.succeed(new User2VO().userList2VO(userList));
        }
        return returnBase.failedWithErrorEnum(ErrorEnum.USER_BATCH_ADD_FAIL);
    }

    @Override
    public ReturnBase<List<UserVO>> batchDeleteUser(List<UserPO> userPOList) {
        ReturnBase<List<UserVO>> returnBase = new ReturnBase<>();

        // 防止传入重复 id，使用 HashMap 去重，key 为 id，value 为出现的次数，重复项会在相同的 key 对应的 value 数值 + 1
        HashMap<String, Integer> idMap = new HashMap<>(UserConstant.INITIAL_CAPACITY_HASHMAP);
        if (ObjectUtils.isEmpty(userPOList)) {
            return returnBase.failedWithErrorEnum(ErrorEnum.POST_DATA_IS_NULL);
        }
        userPOList.forEach(userPO -> {
            if (!ObjectUtils.isEmpty(userPO.getId())) {
                idMap.put(userPO.getId(), idMap.getOrDefault(userPO.getId(), 0) + 1);
            }
        });

        int length = idMap.keySet().toArray().length;
        Object[] list = idMap.keySet().toArray();
        ArrayList<User> userList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            User user = this.getUser((String) list[i]);
            if (!ObjectUtils.isEmpty(user)) {
                userList.add(user);
            }
        }

        if (ObjectUtils.isEmpty(userList)) {
            return returnBase.failedWithErrorEnum(ErrorEnum.CANNOT_FIND_USER);
        }

        userList.forEach(user -> user.setDeleteFlag(LogicDeleteFlag.DELETED.getDeleteFlag()));

        if (userService.updateBatchById(userList)) {
            return returnBase.setSuccess(true);
        }

        return returnBase.failedWithErrorEnum(ErrorEnum.USER_BATCH_DELETE_FAIL);
    }

    @Override
    public ReturnBase<List<UserVO>> batchUpdateUser(List<UserPO> userPOList) {
        ReturnBase<List<UserVO>> returnBase = new ReturnBase<>();
        if (ObjectUtils.isEmpty(userPOList)) {
            return returnBase.failedWithErrorEnum(ErrorEnum.POST_DATA_IS_NULL);
        }
        HashMap<String, UserPO> idMap = new HashMap<>();
        userPOList.forEach(userPO -> idMap.put(userPO.getId(), userPO));
        int length = idMap.keySet().toArray().length;
        Object[] list = idMap.keySet().toArray();
        ArrayList<User> userList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            User user = this.getUser((String) list[i]);
            if (!ObjectUtils.isEmpty(user)) {
                userList.add(user);
            }
        }
        userList.forEach(user -> {
            if (idMap.containsKey(user.getId())) {
                BeanUtils.copyProperties(idMap.get(user.getId()), user, "id", "operatorId");
                user.setUpdateTime(DateUtils.getCurrentDate(DateUtils.YMDHMS_FORMAT));
                if (!ObjectUtils.isEmpty(idMap.get(user.getId()).getOperatorId())) {
                    user.setUpdatorId(idMap.get(user.getId()).getOperatorId());
                }
            }
        });

        if (userService.saveOrUpdateBatch(userList)) {
            return returnBase.succeed(new User2VO().userList2VO(userList));
        }
        return returnBase.failedWithErrorEnum(ErrorEnum.UPDATE_FAIL);
    }

    @Override
    public ReturnBase<List<UserVO>> getUserPageList(UserPO userPO, Page<User> page) {
        ReturnBase<UserVO> returnBase = new ReturnBase<>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(userPO.getId())) {
            queryWrapper.eq("id", userPO.getId());
        }

        if (!StringUtils.isEmpty(userPO.getName())) {
            queryWrapper.eq("name", userPO.getName());
        }

        /*
            likeRight，即通配符 % 在右边，这时候是可以走索引的
            like、likeLeft，有通配符 % 在左边，这时候是不走索引的
            这里根据需求选择
          */
        if (!StringUtils.isEmpty(userPO.getKeyword())) {
            queryWrapper.like("name", userPO.getKeyword())
                    .or().like("email", userPO.getKeyword());
        }

        Page<User> selectPage = userMapper.selectPage(page, queryWrapper);

        return returnBase.succeedWithDataCount(new User2VO().userList2VO(selectPage.getRecords()), selectPage.getTotal());
    }

    @Override
    public ReturnBase<UserVO> deleteUser(UserPO userPO) {
        ReturnBase<UserVO> returnBase = new ReturnBase<>();
        User user = this.getUser(userPO.getId());
        if (ObjectUtils.isEmpty(user)) {
            return returnBase.failedWithErrorEnum(ErrorEnum.CANNOT_FIND_USER);
        }
        if (!ObjectUtils.isEmpty(userPO.getOperatorId())) {
            user.setUpdatorId(userPO.getOperatorId());
        }
        user.setUpdateTime(DateUtils.getCurrentDate(DateUtils.YMDHMS_FORMAT));
        if (UserConstant.SUCCESS_FLAG.equals(userMapper.updateById(user.setDeleteFlag(LogicDeleteFlag.DELETED.getDeleteFlag())))) {
            return returnBase.setSuccess(true);
        } else {
            return returnBase.failedWithErrorEnum(ErrorEnum.UPDATE_FAIL);
        }
    }

    @Override
    public ReturnBase<UserVO> updateUser(UserPO userPO) {
        ReturnBase<UserVO> returnBase = new ReturnBase<>();
        if (ObjectUtils.isEmpty(userPO)){
            return returnBase.failedWithErrorEnum(ErrorEnum.POST_DATA_IS_NULL);
        }
        User user = this.getUser(userPO.getId());
        if (ObjectUtils.isEmpty(user)) {
            return returnBase.failedWithErrorEnum(ErrorEnum.CANNOT_FIND_USER);
        }

        BeanUtils.copyProperties(userPO, user, "id", "operatorId");

        if (!ObjectUtils.isEmpty(userPO.getOperatorId())) {
            user.setUpdatorId(userPO.getOperatorId());
        }

        user.setUpdateTime(DateUtils.getCurrentDate(DateUtils.YMDHMS_FORMAT));

        if (UserConstant.SUCCESS_FLAG.equals(userMapper.updateById(user))) {
            return returnBase.setSuccess(true);
        } else {
            return returnBase.failedWithErrorEnum(ErrorEnum.UPDATE_FAIL);
        }
    }

    @Override
    public ReturnBase<UserVO> getUserById(String id) {
        User user = this.getUser(id);
        if (ObjectUtils.isEmpty(user)) {
            return new ReturnBase<UserVO>().failedWithErrorEnum(ErrorEnum.CANNOT_FIND_USER);
        }
        return new ReturnBase<>().succeed(new User2VO().user2Vo(user));
    }

    @Override
    public ReturnBase<List<UserVO>> getUserList() {
        return new ReturnBase<List<UserVO>>().succeed(new User2VO().userList2VO(userMapper.selectList(new QueryWrapper<User>().eq("delete_flag", "0"))));
    }
}
