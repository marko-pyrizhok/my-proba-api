package org.plast.proba.repository;

import org.plast.proba.domain.pojo.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Integer> {
    List<Point> findByRank(int rank);

}
