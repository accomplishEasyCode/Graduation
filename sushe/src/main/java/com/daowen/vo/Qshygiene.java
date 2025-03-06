package com.daowen.vo;


import com.daowen.entity.Bunit;

public class Qshygiene extends Bunit {

    private double score;
    private String weekname;

    private String qsname;
    private String ldname;

    public String getQsname() {
        return qsname;
    }

    public void setQsname(String qsname) {
        this.qsname = qsname;
    }

    public String getLdname() {
        return ldname;
    }

    public void setLdname(String ldname) {
        this.ldname = ldname;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getWeekname() {
        return weekname;
    }

    public void setWeekname(String weekname) {
        this.weekname = weekname;
    }
}
