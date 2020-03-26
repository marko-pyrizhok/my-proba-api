package org.plast.proba.service;

import org.plast.proba.domain.pojo.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    User getLoggedInUser();
}