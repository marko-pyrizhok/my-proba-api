package org.plast.proba.service;

import org.plast.proba.domain.pojo.Gurtok;
import org.plast.proba.domain.pojo.User;
import org.plast.proba.repository.RoleRepository;
import org.plast.proba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SecurityService securityService;

    @Override
    public User getUser() {
        String loggedInUsername = securityService.findLoggedInUsername();
        return findByUsername(loggedInUsername);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public List<User> findByGurtok(Long gurtokId) {
        Gurtok gurtok = new Gurtok();
        gurtok.setId(gurtokId);
        return userRepository.findByGurtok(gurtok);
    }

    @Override
    public User getLoggedInUser() {
        return findByUsername(securityService.findLoggedInUsername());
    }
}