package com.pnb.service;

import com.pnb.po.User;

public interface UserService {
    User checkUser(String username,String password);
}
