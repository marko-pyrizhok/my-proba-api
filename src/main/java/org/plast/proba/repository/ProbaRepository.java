package org.plast.proba.repository;

import org.plast.proba.domain.pojo.UserProba;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProbaRepository extends JpaRepository<UserProba, Long> {

    List<UserProba> findByUserId(Long userId);

}
