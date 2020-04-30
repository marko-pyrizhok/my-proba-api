package org.plast.proba.repository;

import org.plast.proba.domain.pojo.Gurtok;
import org.plast.proba.domain.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByGurtok(Gurtok gurtok);

}