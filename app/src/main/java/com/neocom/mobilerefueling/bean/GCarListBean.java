package com.neocom.mobilerefueling.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 2017/9/4.
 */
@Entity
public class GCarListBean {


    /**
     * id : 78234238c5384e358dc427574928365c
     * relateId : 2bd7345898284b6b909164e8a54628d8
     * relateType : 0
     * vehicleCode : 鲁A45678
     * pName : null
     * telphone : 2
     * oilType : 2
     * tankSize : 2
     * num : 1
     * finishTime : null
     * oilBalance : null
     * fillTime : null
     * oilTypeName : 0号柴油
     * dstate : null
     * status : 1
     * c_user : 17c4520f6cfd1ab53d8745e84681eb49
     * c_dt : 2017-08-24 10:28:56
     * u_user : null
     * u_dt :
     */
    @Id(autoincrement = true)
    private Long _id;
    private String id;
    private String relateId;
    private String relateType;
    private String vehicleCode;
    private String pName;
    private String telphone;
    private String oilType;
    private String tankSize;
    private String num;
    private String finishTime;
    private String oilBalance;
    private String fillTime;
    private String oilTypeName;
    private String dstate;
    private String status;
    private String c_user;
    private String c_dt;
    private String u_user;
    private String u_dt;

    @Generated(hash = 224689085)
    public GCarListBean(Long _id, String id, String relateId, String relateType,
                        String vehicleCode, String pName, String telphone, String oilType,
                        String tankSize, String num, String finishTime, String oilBalance,
                        String fillTime, String oilTypeName, String dstate, String status,
                        String c_user, String c_dt, String u_user, String u_dt) {
        this._id = _id;
        this.id = id;
        this.relateId = relateId;
        this.relateType = relateType;
        this.vehicleCode = vehicleCode;
        this.pName = pName;
        this.telphone = telphone;
        this.oilType = oilType;
        this.tankSize = tankSize;
        this.num = num;
        this.finishTime = finishTime;
        this.oilBalance = oilBalance;
        this.fillTime = fillTime;
        this.oilTypeName = oilTypeName;
        this.dstate = dstate;
        this.status = status;
        this.c_user = c_user;
        this.c_dt = c_dt;
        this.u_user = u_user;
        this.u_dt = u_dt;
    }

    @Generated(hash = 480241511)
    public GCarListBean() {
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelateId() {
        return relateId;
    }

    public void setRelateId(String relateId) {
        this.relateId = relateId;
    }

    public String getRelateType() {
        return relateType;
    }

    public void setRelateType(String relateType) {
        this.relateType = relateType;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }


    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getOilType() {
        return oilType;
    }

    public void setOilType(String oilType) {
        this.oilType = oilType;
    }

    public String getTankSize() {
        return tankSize;
    }

    public void setTankSize(String tankSize) {
        this.tankSize = tankSize;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getOilBalance() {
        return oilBalance;
    }

    public void setOilBalance(String oilBalance) {
        this.oilBalance = oilBalance;
    }

    public String getFillTime() {
        return fillTime;
    }

    public void setFillTime(String fillTime) {
        this.fillTime = fillTime;
    }

    public String getOilTypeName() {
        return oilTypeName;
    }

    public void setOilTypeName(String oilTypeName) {
        this.oilTypeName = oilTypeName;
    }

    public String getDstate() {
        return dstate;
    }

    public void setDstate(String dstate) {
        this.dstate = dstate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getC_user() {
        return c_user;
    }

    public void setC_user(String c_user) {
        this.c_user = c_user;
    }

    public String getC_dt() {
        return c_dt;
    }

    public void setC_dt(String c_dt) {
        this.c_dt = c_dt;
    }

    public String getU_user() {
        return u_user;
    }

    public void setU_user(String u_user) {
        this.u_user = u_user;
    }

    public String getU_dt() {
        return u_dt;
    }

    public void setU_dt(String u_dt) {
        this.u_dt = u_dt;
    }

    public String getPName() {
        return this.pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    @Override
    public String toString() {
        return "GCarListBean{" +
                "_id=" + _id +
                ", id='" + id + '\'' +
                ", relateId='" + relateId + '\'' +
                ", relateType='" + relateType + '\'' +
                ", vehicleCode='" + vehicleCode + '\'' +
                ", pName='" + pName + '\'' +
                ", telphone='" + telphone + '\'' +
                ", oilType='" + oilType + '\'' +
                ", tankSize='" + tankSize + '\'' +
                ", num='" + num + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", oilBalance='" + oilBalance + '\'' +
                ", fillTime='" + fillTime + '\'' +
                ", oilTypeName='" + oilTypeName + '\'' +
                ", dstate='" + dstate + '\'' +
                ", status='" + status + '\'' +
                ", c_user='" + c_user + '\'' +
                ", c_dt='" + c_dt + '\'' +
                ", u_user='" + u_user + '\'' +
                ", u_dt='" + u_dt + '\'' +
                '}';
    }
}
