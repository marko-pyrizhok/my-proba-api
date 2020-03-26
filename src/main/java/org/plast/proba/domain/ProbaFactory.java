package org.plast.proba.domain;

import org.plast.proba.domain.model.PointState;
import org.plast.proba.domain.model.Proba;
import org.plast.proba.domain.pojo.Point;
import org.plast.proba.domain.pojo.ProbaToPoint;
import org.plast.proba.domain.pojo.UserProba;

import java.util.List;
import java.util.Optional;

public class ProbaFactory {

    public static Proba toModel(UserProba userProba) {
        return new Proba(userProba.getId(),
                userProba.getUser().getId(),
                userProba.getDateStart(),
                userProba.getDateDone(),
                userProba.getRank(),
                null);
    }

    public static PointState createProbaState(List<ProbaToPoint> proba, Point point) {
        Optional<ProbaToPoint> probaToPoint = proba.stream()
                .filter(p -> p.getPoint().getId().equals(point.getId()))
                .findAny();
        boolean signed = probaToPoint.isPresent() && probaToPoint.get().getConfirmUser() != null;
        return new PointState(point.getCode(),
                point.getName(),
                signed ? probaToPoint.get().getConfirmUser().getId() : null,
                signed ? probaToPoint.get().getConfirmDate() : null);
    }
}
