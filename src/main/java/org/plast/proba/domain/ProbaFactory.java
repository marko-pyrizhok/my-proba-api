package org.plast.proba.domain;

import org.plast.proba.domain.model.PointState;
import org.plast.proba.domain.model.Proba;
import org.plast.proba.domain.pojo.Tochka;
import org.plast.proba.domain.pojo.ProbaToTochka;
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

    public static PointState createProbaState(List<ProbaToTochka> proba, Tochka tochka) {
        Optional<ProbaToTochka> probaToPoint = proba.stream()
                .filter(p -> p.getTochka().getId().equals(tochka.getId()))
                .findAny();
        boolean signed = probaToPoint.isPresent() && probaToPoint.get().getConfirmUser() != null;
        return new PointState(tochka.getCode(),
                tochka.getName(),
                signed ? probaToPoint.get().getConfirmUser().getId() : null,
                signed ? probaToPoint.get().getConfirmDate() : null);
    }
}
