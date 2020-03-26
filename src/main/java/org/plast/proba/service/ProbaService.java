package org.plast.proba.service;

import org.plast.proba.domain.model.ConfirmPointRequest;
import org.plast.proba.domain.model.PointState;
import org.plast.proba.domain.pojo.User;
import org.plast.proba.domain.pojo.UserProba;

import java.time.LocalDate;
import java.util.List;

public interface ProbaService {

    List<UserProba> probaListByUserId(long userId);

    UserProba probaById(long id);

    List<PointState> probaWithPointsById(long id);

    void confirmPoint(ConfirmPointRequest confirm, User loggedInUser);
}
