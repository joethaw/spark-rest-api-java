package com.jt.rest.service;

import com.jt.rest.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(String id);

    String createUser(User user);

    void updateUser(User user);

    void deleteUser(String id);
}
