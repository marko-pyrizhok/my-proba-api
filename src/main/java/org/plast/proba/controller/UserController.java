package org.plast.proba.controller;

import org.plast.proba.configuration.JwtTokenUtil;
import org.plast.proba.domain.model.LoginRequest;
import org.plast.proba.domain.model.LoginResponse;
import org.plast.proba.domain.model.User;
import org.plast.proba.repository.RoleRepository;
import org.plast.proba.service.SecurityService;
import org.plast.proba.service.UserDetailsServiceImpl;
import org.plast.proba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/registration")
    public String registration(User user) {

        org.plast.proba.domain.pojo.User userPojo = new org.plast.proba.domain.pojo.User();
        userPojo.setEmail(user.getUsername());
        userPojo.setPassword(user.getPassword());
        userPojo.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
        userService.save(userPojo);

        securityService.autoLogin(user.getUsername(), user.getPassword());

        return "";
    }


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody LoginRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}