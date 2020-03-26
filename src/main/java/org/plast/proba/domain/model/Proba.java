package org.plast.proba.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Proba {
    private Long id;
    private Long userId;
    private LocalDate dateStart;
    private LocalDate dateDone;
    private Integer rank;
    private List<PointState> pointList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Proba(Long id, Long userId, LocalDate dateStart, LocalDate dateDone, Integer rank, List<PointState> pointList) {
        this.id = id;
        this.userId = userId;
        this.dateStart = dateStart;
        this.dateDone = dateDone;
        this.rank = rank;
        this.pointList = pointList;
    }
}
