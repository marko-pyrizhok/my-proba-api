package org.plast.proba.repository;

import org.plast.proba.domain.pojo.Tochka;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TochkaRepository extends JpaRepository<Tochka, Integer> {
    List<Tochka> findByRank(int rank);

}
