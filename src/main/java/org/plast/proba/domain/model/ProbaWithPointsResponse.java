package org.plast.proba.domain.model;

import java.util.List;

public class ProbaWithPointsResponse {
    long probaId;
    List<PointState> pointStateList;

    public long getProbaId() {
        return probaId;
    }

    public void setProbaId(long probaId) {
        this.probaId = probaId;
    }

    public List<PointState> getPointStateList() {
        return pointStateList;
    }

    public void setPointStateList(List<PointState> pointStateList) {
        this.pointStateList = pointStateList;
    }
}
