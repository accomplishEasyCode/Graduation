package com.daowen.vo;

import com.daowen.entity.Bunit;
import com.daowen.entity.Loudong;


import java.util.Iterator;
import java.util.List;

/**
 * 寝室卫生
 */
public class PingfenVo extends Loudong {


    private List<Qshygiene> hygienes;

    public List<Qshygiene> getHygienes() {
        if(hygienes!=null) {
            Iterator<Qshygiene> it = hygienes.iterator();
            while (it.hasNext()) {
                Bunit x = it.next();
                if (x.getId() == 0) {
                    it.remove();
                }
            }
        }
        return hygienes;
    }

    public void setHygienes(List<Qshygiene> hygienes) {
        this.hygienes = hygienes;
    }
}
