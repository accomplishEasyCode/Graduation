package com.daowen.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * 户型
 */
@Entity
public class Stag {


    //编码

    @Id
    private int id;


    //名称

    private String name;


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

}