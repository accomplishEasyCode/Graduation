package com.daowen.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * 寝室
 */

public class Bunit {


    //编码
    private int id;

    //名称
    private String name;



    //楼栋编号
    private int ldid;


    private int bjid;
    //宿舍类型
    private   int    typeid;

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }


    //洗衣机

    private   int    haswm;


    //空调

    private   int    hasac;


    //说明

    private   String    des;




    public int getHaswm() {
        return haswm;
    }

    public void setHaswm(int haswm) {
        this.haswm = haswm;
    }

    public int getHasac() {
        return hasac;
    }

    public void setHasac(int hasac) {
        this.hasac = hasac;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getBjid() {
        return bjid;
    }

    public void setBjid(int bjid) {
        this.bjid = bjid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLdid() {
        return ldid;
    }

    public void setLdid(int ldid) {
        this.ldid = ldid;
    }

    @Transient
    private int maxcount;
    @Transient
    private String typename;

    @Transient
    private String ldname;
    @Transient
    private String bjname;


    private int cicount;

    public int getCicount() {
        return cicount;
    }

    public void setCicount(int cicount) {
        this.cicount = cicount;
    }

    public int getMaxcount() {
        return maxcount;
    }

    public void setMaxcount(int maxcount) {
        this.maxcount = maxcount;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getLdname() {
        return ldname;
    }

    public void setLdname(String ldname) {
        this.ldname = ldname;
    }

    public String getBjname() {
        return bjname;
    }

    public void setBjname(String bjname) {
        this.bjname = bjname;
    }
}