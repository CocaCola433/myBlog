package com.pnb.service;

import com.pnb.dao.UserRepository;
import com.pnb.po.User;
import com.pnb.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username, MD5Util.encode(password));
        return user;
    }
}
