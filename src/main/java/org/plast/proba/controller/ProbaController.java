package org.plast.proba.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.plast.proba.domain.ProbaFactory;
import org.plast.proba.domain.model.ConfirmPointRequest;
import org.plast.proba.domain.model.PointState;
import org.plast.proba.domain.model.Proba;
import org.plast.proba.domain.pojo.User;
import org.plast.proba.service.ProbaService;
import org.plast.proba.service.SecurityService;
import org.plast.proba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProbaController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProbaService probaService;

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Access Token",
                    required = true,
                    allowEmptyValue = false,
                    paramType = "header",
                    dataTypeClass = String.class,
                    example = "Bearer access_token"))
    @RequestMapping(method = RequestMethod.GET, value = "/my-proba")
    public List<Proba> getProbaListByLoggedInUser() {
        String loggedInUsername = securityService.findLoggedInUsername();
        User user = userService.findByUsername(loggedInUsername);
        List<Proba> probaList = probaService.probaListByUserId(user.getId())
                .stream()
                .map(p -> ProbaFactory.toModel(p))
                .collect(Collectors.toList());
        return probaList;
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
    @RequestMapping(method = RequestMethod.GET, value = "/my-proba-points")
    public List<PointState> getPointListByProbaIdForLoggedInUser(@RequestParam Long probaId) {
        return probaService.probaWithPointsById(probaId);
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
    @RequestMapping(method = RequestMethod.POST, value = "/my-proba-points")
    public List<PointState> confirmPoint(@RequestBody ConfirmPointRequest confirm) {
        User loggedInUser = userService.getLoggedInUser();
        if (loggedInUser.getUlad().equals("USP")) {
            probaService.confirmPoint(confirm, loggedInUser);
        }
        return probaService.probaWithPointsById(confirm.getProbaId());
    }
}
