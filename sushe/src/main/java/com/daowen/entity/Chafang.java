package com.daowen.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * 查房
 */
@Entity
public class Chafang {


    //编码

    @Id
    private int id;


    //学号

    private int targetid;


    //查房日期

    private Date cfdate;


    //结果

    private String result;


    //说明

    private String des;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTargetid() {
        return targetid;
    }

    public void setTargetid(int targetid) {
        this.targetid = targetid;
    }

    public Date getCfdate() {
        return cfdate;
    }

    public void setCfdate(Date cfdate) {
        this.cfdate = cfdate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

}