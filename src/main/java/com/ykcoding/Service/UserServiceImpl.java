package com.ykcoding.Service;

import com.ykcoding.dao.userDao;
import com.ykcoding.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements userService {
    @Autowired
    private userDao userdao;
    @Override
    public User login(String username, String password) {

        User user = userdao.findByUsernameAndPassword(username,DigestUtils.md5DigestAsHex(password.getBytes()));
        return user;
    }
}
