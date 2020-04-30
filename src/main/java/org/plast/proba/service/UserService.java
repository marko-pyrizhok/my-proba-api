package org.plast.proba.service;

import org.plast.proba.domain.pojo.User;

import java.util.List;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    User getLoggedInUser();

    List<User> findByGurtok(Long gurtokId);

}