package com.daowen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import javax.persistence.*;

/**
 * 进货
 */
@Entity
public class Caigou {


    //编码

    @Id
    private int id;


    //订单编号

    private String ddno;


    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;


    //创建人

    private int userid;


    //状态

    private int state;


    //商品编号

    private int spid;


    //数量

    private int count;


    //单位

    private String danwei;


    //供应商

    private int supplyid;


    //说明

    private String des;


    //进货价（元）

    private Double price;


    //总价(元)

    private Double totalfee;


    @Transient
    private String cgaccount;

    @Transient
    private String cgren;

    @Transient
    private String supplyorname;
    @Transient
    private String spname;

    public String getCgaccount() {
        return cgaccount;
    }

    public void setCgaccount(String cgaccount) {
        this.cgaccount = cgaccount;
    }

    public String getCgren() {
        return cgren;
    }

    public void setCgren(String cgren) {
        this.cgren = cgren;
    }

    public String getSupplyorname() {
        return supplyorname;
    }

    public void setSupplyorname(String supplyorname) {
        this.supplyorname = supplyorname;
    }

    public String getSpname() {
        return spname;
    }

    public void setSpname(String spname) {
        this.spname = spname;
    }

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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSpid() {
        return spid;
    }

    public void setSpid(int spid) {
        this.spid = spid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDanwei() {
        return danwei;
    }

    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }

    public int getSupplyid() {
        return supplyid;
    }

    public void setSupplyid(int supplyid) {
        this.supplyid = supplyid;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(Double totalfee) {
        this.totalfee = totalfee;
    }

}