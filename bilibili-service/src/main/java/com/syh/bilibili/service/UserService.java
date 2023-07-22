package com.syh.bilibili.service;

import com.mysql.cj.util.StringUtils;
import com.syh.bilibili.dao.UserDao;
import com.syh.bilibili.domain.User;
import com.syh.bilibili.domain.UserInfo;
import com.syh.bilibili.domain.constant.UserConstant;
import com.syh.bilibili.domain.exception.ConditionException;
import com.syh.bilibili.service.util.MD5Util;
import com.syh.bilibili.service.util.RSAUtil;
import com.syh.bilibili.service.util.TokenUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addUser(User user) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("Phone number cannot be null or empty");
        }

        User dbUser = getUserByPhone(phone);
        if (dbUser != null) {
            throw new ConditionException("The Phone number has already been registered");
        }

        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception exception) {
            throw new ConditionException("Fail to decrypt the password");
        }

        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        user.setSalt(salt);
        user.setPassword(md5Password);
        user.setCreateTime(now);
        userDao.addUser(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNickname(UserConstant.DEFAULT_NICKNAME);
        userInfo.setGender(UserConstant.GENDER_UNKNOWN);
        userInfo.setBirthday(UserConstant.DEFAULT_BIRTH);
        userInfo.setCreateTime(now);
        userDao.addUserInfo(userInfo);
    }

    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    public String login(User user) throws Exception {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("Phone number cannot be null or empty");
        }

        User dbUser = getUserByPhone(phone);
        if (dbUser == null) {
            throw new ConditionException("The user does not exist");
        }

        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception exception) {
            throw new ConditionException("Fail to decrypt the password");
        }

        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");

        if (!md5Password.equals(dbUser.getPassword())) {
            throw new ConditionException("Password is not matched");
        }

        return TokenUtil.generateToken(dbUser.getId(), 3600);
    }

    public User getUserInfo(Long userId) {
        User user = userDao.getUserById(userId);

        UserInfo userInfo = userDao.getUserInfoByUserId(userId);
        user.setUserInfo(userInfo);

        return user;
    }
}
