package org.plast.proba.controller;

import org.plast.proba.configuration.JwtTokenUtil;
import org.plast.proba.domain.model.LoginRequest;
import org.plast.proba.domain.model.LoginResponse;
import org.plast.proba.domain.model.User;
import org.plast.proba.domain.pojo.ConfirmationToken;
import org.plast.proba.repository.ConfirmationTokenRepository;
import org.plast.proba.repository.RoleRepository;
import org.plast.proba.service.EmailSenderService;
import org.plast.proba.service.SecurityService;
import org.plast.proba.service.UserDetailsServiceImpl;
import org.plast.proba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/registration")
    public ResponseEntity<LoginResponse> registration(HttpServletRequest request, User user) {

        org.plast.proba.domain.pojo.User userPojo = new org.plast.proba.domain.pojo.User();
        userPojo.setEmail(user.getEmail());
        userPojo.setPassword(user.getPassword());
        userPojo.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
        userPojo.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(userPojo);

        ConfirmationToken confirmationToken = new ConfirmationToken(userPojo);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userPojo.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("marko@gmail.com");
        String currentUrl = request.getRequestURL().toString();
        int lastIndexOf = currentUrl.lastIndexOf('/');
        mailMessage.setText("To confirm your account, please click here : "
                + currentUrl.substring(0,lastIndexOf).concat("/confirm-account?token=")
                + confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);

        return ResponseEntity.ok(new LoginResponse("ok", user.getEmail()));

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody LoginRequest authenticationRequest) throws Exception {

        securityService.autoLogin(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token, authenticationRequest.getUsername()));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("Success");
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

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            org.plast.proba.domain.pojo.User user = userService.findByUsername(token.getUser().getEmail());
            user.setEnabled(true);
            userService.save(user);
        }

        return ResponseEntity.ok("Confirmed!");
    }
}