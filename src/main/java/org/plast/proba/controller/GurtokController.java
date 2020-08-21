package org.plast.proba.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.plast.proba.domain.pojo.Gurtok;
import org.plast.proba.domain.pojo.User;
import org.plast.proba.repository.RoleRepository;
import org.plast.proba.service.GurtokService;
import org.plast.proba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class GurtokController {

    @Autowired
    private UserService userService;

    @Autowired
    private GurtokService gurtokService;

    @Autowired
    private RoleRepository roleRepository;

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Access Token",
                    required = true,
                    allowEmptyValue = false,
                    paramType = "header",
                    dataTypeClass = String.class,
                    example = "Bearer access_token"))
    @RequestMapping(method = RequestMethod.GET, value = "/my-gurtok")
    public List<User> getUserListByGurtokId() {
        Gurtok gurtok = userService.getUser().getGurtok();
        return gurtok != null ? userService.findByGurtok(gurtok.getId()) : Collections.emptyList();
    }

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Access Token",
                    required = true,
                    allowEmptyValue = false,
                    paramType = "header",
                    dataTypeClass = String.class,
                    example = "Bearer access_token"))
    @RequestMapping(method = RequestMethod.GET, value = "/join-to-gurtok")
    public String joinToGurtokById(@RequestParam Long gurtokId) {
        User user = userService.getUser();
        boolean role_user = user.getRoles().contains("ROLE_USER");
        Gurtok gurtok = gurtokService.getGurtokById(gurtokId);
        user.setGurtok(gurtok);
        userService.save(user);
        return "Ask vuhovnyk to confirm";
    }

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Access Token",
                    required = true,
                    allowEmptyValue = false,
                    paramType = "header",
                    dataTypeClass = String.class,
                    example = "Bearer access_token"))
    @RequestMapping(method = RequestMethod.GET, value = "/confirm-join-to-gurtok")
    public String confirmJoinToGurtokByUserId(@RequestParam Long userId) {
        User vyhovnyk = userService.getUser();
        boolean roleVyhovnyk = vyhovnyk.getRoles().contains("ROLE_VYHOVNYK");
        User user = userService.getUser(userId);
        boolean roleUser = user.getRoles().contains("ROLE_USER");


        List<User> userList = userService.findByGurtok(vyhovnyk.getGurtok().getId());
        boolean bothInGurtok = userList.contains(vyhovnyk) && userList.contains(user);
        if (roleVyhovnyk && roleUser && bothInGurtok) {

            user.setGurtokConfirmed(true);
            user.getRoles().add(roleRepository.findByName("ROLE_YUNAK"));
            userService.save(user);
            return "confirmed";
        }
        return "not confirmed";

    }
}
