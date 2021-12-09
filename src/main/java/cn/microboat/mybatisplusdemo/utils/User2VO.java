package cn.microboat.mybatisplusdemo.utils;

import cn.microboat.mybatisplusdemo.model.entity.User;
import cn.microboat.mybatisplusdemo.model.viewObject.UserVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouwei
 */
public class User2VO {

    public UserVO user2Vo(User user) {
        return new UserVO()
                .setId(user.getId())
                .setAge(user.getAge())
                .setName(user.getName())
                .setEmail(user.getEmail());
    }

    public List<UserVO> userList2VO(List<User> userList) {
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : userList) {
            UserVO userVO = new UserVO();
            userVO.setId(user.getId())
                    .setName(user.getName())
                    .setAge(user.getAge())
                    .setEmail(user.getEmail());
            userVOList.add(userVO);
        }
        return userVOList;
    }
}
