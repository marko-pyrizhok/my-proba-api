package org.plast.proba.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.plast.proba.domain.ProbaFactory;
import org.plast.proba.domain.model.ConfirmPointRequest;
import org.plast.proba.domain.model.PointState;
import org.plast.proba.domain.model.Proba;
import org.plast.proba.domain.model.ProbaWithPointsResponse;
import org.plast.proba.domain.pojo.User;
import org.plast.proba.domain.pojo.UserProba;
import org.plast.proba.service.ProbaService;
import org.plast.proba.service.SecurityService;
import org.plast.proba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
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
        List<Proba> probaList = getCurrentUserProbaList()
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
    @RequestMapping(method = RequestMethod.GET, value = "/my-last-proba-points")
    public ProbaWithPointsResponse getPointListForLastProbaForLoggedInUser() {
        List<UserProba> userProbaList = getCurrentUserProbaList();
        UserProba userProba = userProbaList.stream()
                .max(Comparator.comparing(UserProba::getRank))
                .orElseThrow(NoSuchElementException::new);
        ProbaWithPointsResponse probaWithPointsResponse = new ProbaWithPointsResponse();
        probaWithPointsResponse.setProbaId(userProba.getId());
        probaWithPointsResponse.setPointStateList(probaService.probaWithPointsById(userProba.getId()));
        return probaWithPointsResponse;
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
    public ProbaWithPointsResponse getPointListByProbaIdForLoggedInUser(@RequestParam Long probaId) {
        // todo: ensure, we do have such proba
        ProbaWithPointsResponse probaWithPointsResponse = new ProbaWithPointsResponse();
        probaWithPointsResponse.setProbaId(probaId);
        probaWithPointsResponse.setPointStateList(probaService.probaWithPointsById(probaId));
        return probaWithPointsResponse;
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
    public ProbaWithPointsResponse confirmPoint(@RequestBody ConfirmPointRequest confirm) {
        User loggedInUser = userService.getLoggedInUser();
        probaService.confirmPoint(confirm, loggedInUser);
        ProbaWithPointsResponse probaWithPointsResponse = new ProbaWithPointsResponse();
        probaWithPointsResponse.setProbaId(confirm.getProbaId());
        probaWithPointsResponse.setPointStateList(probaService.probaWithPointsById(confirm.getProbaId()));
        return probaWithPointsResponse;
    }

    private List<UserProba> getCurrentUserProbaList() {
        return probaService.probaListByUserId(userService.getUser().getId());
    }

}
