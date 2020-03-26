package org.plast.proba.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

public class ConfirmPointRequest implements Serializable {
    private long probaId;
    private int pointId;
    private LocalDate confirmDate;

    public ConfirmPointRequest() {
    }

    public long getProbaId() {
        return probaId;
    }

    public void setProbaId(long probaId) {
        this.probaId = probaId;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }
}
