package org.plast.proba.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.plast.proba.domain.ProbaFactory;
import org.plast.proba.domain.model.Proba;
import org.plast.proba.domain.pojo.User;
import org.plast.proba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GurtokController {

    @Autowired
    private UserService userService;

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
        List<User> userList = userService.findByGurtok(1L);
        return userList;
    }
}
