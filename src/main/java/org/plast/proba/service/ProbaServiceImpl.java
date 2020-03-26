package org.plast.proba.service;

import org.plast.proba.domain.ProbaFactory;
import org.plast.proba.domain.model.ConfirmPointRequest;
import org.plast.proba.domain.model.PointState;
import org.plast.proba.domain.pojo.Point;
import org.plast.proba.domain.pojo.ProbaToPoint;
import org.plast.proba.domain.pojo.User;
import org.plast.proba.domain.pojo.UserProba;
import org.plast.proba.repository.PointRepository;
import org.plast.proba.repository.ProbaRepository;
import org.plast.proba.repository.ProbaToPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProbaServiceImpl implements ProbaService {

    @Autowired
    private ProbaRepository probaRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private ProbaToPointRepository probaToPointRepository;

    @Override
    public List<UserProba> probaListByUserId(long id) {
        return probaRepository.findByUserId(id);
    }

    @Override
    public UserProba probaById(long id) {
        return probaRepository.getOne(id);
    }

    @Override
    public List<PointState> probaWithPointsById(long id) {
        UserProba userProba = probaRepository.getOne(id);
        List<ProbaToPoint> proba = probaToPointRepository.findByUserProba(userProba);

        List<Point> pointList = pointRepository.findByRank(userProba.getRank());
        List<PointState> pointStateList = pointList.stream()
                .map(point -> ProbaFactory.createProbaState(proba, point))
                .collect(Collectors.toList());
        return pointStateList;
    }

    @Override
    public void confirmPoint(ConfirmPointRequest confirm, User loggedInUser) {
        UserProba userProba = probaRepository.getOne(confirm.getProbaId());
        List<ProbaToPoint> proba = probaToPointRepository.findByUserProba(userProba);
        ProbaToPoint newConfirmPoint = new ProbaToPoint();
        newConfirmPoint.setPoint(pointRepository.getOne(confirm.getPointId()));
        newConfirmPoint.setUserProba(userProba);
        newConfirmPoint.setConfirmUser(loggedInUser);
        newConfirmPoint.setConfirmDate(confirm.getConfirmDate());
        probaToPointRepository.save(newConfirmPoint);
    }

}
