package com.jt.rest.service;

import com.jt.rest.model.User;
import spark.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private List<User> users = new ArrayList<>();

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User getUser(String id) {
        return users.stream()
                .filter(u -> u.getId().equalsIgnoreCase(id))
                .findFirst()
                .get();
    }

    @Override
    public String createUser(User user) {
        if (!validateUser(user)) {
            throw new IllegalArgumentException("user is not valid");
        }

        String newId = UUID.randomUUID().toString();
        user.setId(newId);
        users.add(user);

        return newId;
    }

    @Override
    public void updateUser(User user) {
        if (!validateUser(user)) {
            throw new IllegalArgumentException("user is not valid");
        }

        User foundUser = getUser(user.getId());
        users.remove(foundUser);
        users.add(user);
    }

    @Override
    public void deleteUser(String id) {
        users.remove(getUser(id));
    }

    private boolean validateUser(User user) {
        return user != null && !StringUtils.isEmpty(user.getFirstName()) && !StringUtils.isEmpty(user.getLastName());
    }
}
