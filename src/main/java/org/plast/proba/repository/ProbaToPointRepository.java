package org.plast.proba.repository;

import org.plast.proba.domain.pojo.ProbaToPoint;
import org.plast.proba.domain.pojo.UserProba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProbaToPointRepository extends JpaRepository<ProbaToPoint, Long> {
    List<ProbaToPoint> findByUserProba(UserProba one);
}