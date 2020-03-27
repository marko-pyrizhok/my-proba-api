package org.plast.proba.repository;

import org.plast.proba.domain.pojo.ProbaToTochka;
import org.plast.proba.domain.pojo.UserProba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProbaToTochkaRepository extends JpaRepository<ProbaToTochka, Long> {
    List<ProbaToTochka> findByUserProba(UserProba one);
}