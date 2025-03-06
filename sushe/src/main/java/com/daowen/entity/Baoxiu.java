package com.daowen.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * 报修
 */
@Entity
public class Baoxiu {


    //编码

    @Id
    private int id;


    //设备号

    private String sbno;


    //设备类别

    private int typeid;


    //状态

    private int state;


    //订单号

    private String ddno;


    //报修时间

    private Date createtime;


    //报修人

    private int bxrid;


    //图片

    private String tupian;


    //故障说明

    private String des;


    //处理

    private String replydes;

    @Transient
    private String bxaccount;
    @Transient
    private String bxname;
    @Transient
    private String typename;

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getBxaccount() {
        return bxaccount;
    }

    public void setBxaccount(String bxaccount) {
        this.bxaccount = bxaccount;
    }

    public String getBxname() {
        return bxname;
    }

    public void setBxname(String bxname) {
        this.bxname = bxname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSbno() {
        return sbno;
    }

    public void setSbno(String sbno) {
        this.sbno = sbno;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDdno() {
        return ddno;
    }

    public void setDdno(String ddno) {
        this.ddno = ddno;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getBxrid() {
        return bxrid;
    }

    public void setBxrid(int bxrid) {
        this.bxrid = bxrid;
    }

    public String getTupian() {
        return tupian;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getReplydes() {
        return replydes;
    }

    public void setReplydes(String replydes) {
        this.replydes = replydes;
    }

}