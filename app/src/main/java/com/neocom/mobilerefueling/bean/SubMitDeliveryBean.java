package com.neocom.mobilerefueling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/8/31.
 */

public class SubMitDeliveryBean implements Serializable {


//    "ramark": "完成备注",
//            "u_user":"17c4520f6cfd1ab53d8745e84681eb49",

    //    private List<CarListBean> carList;

    public String ramark;

    public String u_user;


    public String getRamark() {
        return ramark;
    }

    public void setRamark(String ramark) {
        this.ramark = ramark;
    }

    public String getU_user() {
        return u_user;
    }

    public void setU_user(String u_user) {
        this.u_user = u_user;
    }

    private List<GCarListBean> carList;

    public List<GCarListBean> getCarList() {
        return carList;
    }

    public void setCarList(List<GCarListBean> carList) {
        this.carList = carList;
    }
    
}
