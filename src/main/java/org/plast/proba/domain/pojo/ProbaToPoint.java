package org.plast.proba.domain.pojo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "proba_to_point")
public class ProbaToPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proba_id")
    private UserProba userProba;

    @ManyToOne
    @JoinColumn(name = "point_id")
    private Point point;

    @ManyToOne
    @JoinColumn(name = "confirm_user_id")
    private User confirmUser;

    @Column(name = "confirm_date")
    private LocalDate confirmDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProba getUserProba() {
        return userProba;
    }

    public void setUserProba(UserProba userProba) {
        this.userProba = userProba;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public User getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(User confirmUser) {
        this.confirmUser = confirmUser;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }
}
