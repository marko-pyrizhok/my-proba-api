package org.plast.proba.domain.pojo;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;
    @Column(name = "enabled")
    private boolean enabled;
    private String ulad;
    @ManyToOne
    @JoinColumn(name = "gurtok_id")
    private Gurtok gurtok;
    private boolean gurtokConfirmed;
    @ManyToMany
    private Set<Role> roles;

    @OneToOne
    private Picture mainPhoto;

    @OneToOne
    private Picture backgroundPhoto;

    public User() {
        this.enabled=false;
    }

    public Picture getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(Picture mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public Picture getBackgroundPhoto() {
        return backgroundPhoto;
    }

    public void setBackgroundPhoto(Picture backgroundPhoto) {
        this.backgroundPhoto = backgroundPhoto;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isGurtokConfirmed() {
        return gurtokConfirmed;
    }

    public void setGurtokConfirmed(boolean gurtokConfirmed) {
        this.gurtokConfirmed = gurtokConfirmed;
    }

    public Gurtok getGurtok() {
        return gurtok;
    }

    public void setGurtok(Gurtok gurtok) {
        this.gurtok = gurtok;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUlad() {
        return ulad;
    }

    public void setUlad(String ulad) {
        this.ulad = ulad;
    }
}