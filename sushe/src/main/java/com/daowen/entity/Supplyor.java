package com.daowen.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * 供应商
 */
@Entity
public class Supplyor {


    //编码

    @Id
    private int id;


    //名称

    private String name;


    //传真

    private String fax;


    //邮箱

    private String email;


    //地址

    private String address;


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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}