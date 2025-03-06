package com.daowen.entity;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.*;

/**
 * 楼栋
 */

public class Loudong {


    //编码
    private int id;

    //名称
    private String name;


    @Transient
    private List<Bunit> units;

    public List<Bunit> getUnits() {
        if(units!=null) {
            Iterator<Bunit> it = units.iterator();
            while (it.hasNext()) {
                Bunit x = it.next();
                if (x.getId() == 0) {
                    it.remove();
                }
            }
        }

        return units;
    }

    public void setUnits(List<Bunit> units) {
        this.units = units;
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

}