package com.daowen.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * 寝室评分
 */
@Entity
public class Qspingfen {


    //编码

    @Id
    private int id;


    //周

    private int weekid;


    //得分

    private Double score;


    //评分人

    private String pfren;


    //评分时间

    private Date createtime;


    //说明

    private String des;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeekid() {
        return weekid;
    }

    public void setWeekid(int weekid) {
        this.weekid = weekid;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getPfren() {
        return pfren;
    }

    public void setPfren(String pfren) {
        this.pfren = pfren;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    private int qsid;

    public int getQsid() {
        return qsid;
    }

    public void setQsid(int qsid) {
        this.qsid = qsid;
    }

    @Transient
    private String qsname;
    @Transient
    private String weekname;

    public String getQsname() {
        return qsname;
    }

    public void setQsname(String qsname) {
        this.qsname = qsname;
    }

    public String getWeekname() {
        return weekname;
    }

    public void setWeekname(String weekname) {
        this.weekname = weekname;
    }
}