package com.daowen.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * 宿舍申请
 */
@Entity
public class Tiaosu {


    //编码

    @Id
    private int id;


    //订单号

    private String ddno;


    //标题

    private String title;


    //申请时间

    private Date createtime;


    //申请人

    private int applyor;


    //状态

    private int state;


    //宿舍楼

    private int ldid;


    //宿舍

    private int unitid;


    //原生宿舍

    private String oldqs;


    //说明

    private String des;

    @Transient
    private String hyaccount;
    @Transient
    private String hyname;
    @Transient
    private String ldname;
    @Transient
    private String unitname;

    public String getHyaccount() {
        return hyaccount;
    }

    public void setHyaccount(String hyaccount) {
        this.hyaccount = hyaccount;
    }

    public String getHyname() {
        return hyname;
    }

    public void setHyname(String hyname) {
        this.hyname = hyname;
    }

    public String getLdname() {
        return ldname;
    }

    public void setLdname(String ldname) {
        this.ldname = ldname;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    //回复

    private String spdes;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDdno() {
        return ddno;
    }

    public void setDdno(String ddno) {
        this.ddno = ddno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getApplyor() {
        return applyor;
    }

    public void setApplyor(int applyor) {
        this.applyor = applyor;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getLdid() {
        return ldid;
    }

    public void setLdid(int ldid) {
        this.ldid = ldid;
    }

    public int getUnitid() {
        return unitid;
    }

    public void setUnitid(int unitid) {
        this.unitid = unitid;
    }

    public String getOldqs() {
        return oldqs;
    }

    public void setOldqs(String oldqs) {
        this.oldqs = oldqs;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getSpdes() {
        return spdes;
    }

    public void setSpdes(String spdes) {
        this.spdes = spdes;
    }

}