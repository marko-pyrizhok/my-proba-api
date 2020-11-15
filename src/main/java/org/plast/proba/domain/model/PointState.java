package org.plast.proba.domain.model;

import java.time.LocalDate;

public class PointState {
    private Integer id;
    private String code;
    private String name;
    private Long confirmUserId;
    private LocalDate confirmDate;

    public PointState(Integer id, String code, String name, Long confirmUserId, LocalDate confirmDate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.confirmUserId = confirmUserId;
        this.confirmDate = confirmDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getConfirmUserId() {
        return confirmUserId;
    }

    public void setConfirmUserId(Long confirmUserId) {
        this.confirmUserId = confirmUserId;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }
}
