package org.plast.proba.domain.pojo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "proba_to_tochka")
public class ProbaToTochka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proba_id")
    private UserProba userProba;

    @ManyToOne
    @JoinColumn(name = "tochka_id")
    private Tochka tochka;

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

    public Tochka getTochka() {
        return tochka;
    }

    public void setTochka(Tochka tochka) {
        this.tochka = tochka;
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
