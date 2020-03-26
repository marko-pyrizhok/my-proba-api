package org.plast.proba.domain.pojo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_proba")
public class UserProba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @Column(name = "date_start")
    private LocalDate dateStart;
    @Column(name = "date_done")
    private LocalDate dateDone;
    @Column(name = "rank")
    private Integer rank;

    public UserProba() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateDone() {
        return dateDone;
    }

    public void setDateDone(LocalDate dateDone) {
        this.dateDone = dateDone;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
