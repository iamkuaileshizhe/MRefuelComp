package com.neocom.mobilerefueling.bean;

import java.util.List;

/**
 * Created by admin on 2017/8/28.
 */

public class ReceiveCarListDetailBean extends BaseBean {


    /**
     * bring : {"id":"3a0f2fe12e3e4430867703d8d877d043","deliveryCode":"发的地方的地方","indentCode":"C000003","carIds":null,"indentId":null,"oilCarId":null,"senderName":"孙悟空","senderPhone":"123","deliveryId":"17c4520f6cfd1ab53d8745e84681eb49","deliveryName":"防守打法山东省","telphone":null,"receiveTime":"2017-08-24 14:39:59","estimateTime":"2017-08-24 01:30:00","confirmTime":null,"oilAmount":null,"oilCost":null,"finishTime":null,"deliveryAddress":"实得分是发送多福多寿","payType":null,"payAmount":null,"status":"1","c_user":null,"c_dt":"2017-08-24 14:40:19","u_user":null,"u_dt":null,"carList":[{"id":null,"relateId":"027160b3e2604aaba97cb96c7c4916b8","relateType":"0","vehicleCode":"看看看","pName":null,"telphone":"18799999999","oilType":"去","tankSize":"999","num":null,"dstate":null,"status":null,"c_user":null,"c_dt":null,"u_user":null,"u_dt":null}]}
     */

    private BringBean bring;

    public BringBean getBring() {
        return bring;
    }

    public void setBring(BringBean bring) {
        this.bring = bring;
    }

    public static class BringBean {
        /**
         * id : 3a0f2fe12e3e4430867703d8d877d043
         * deliveryCode : 发的地方的地方
         * indentCode : C000003
         * carIds : null
         * indentId : null
         * oilCarId : null
         * senderName : 孙悟空
         * senderPhone : 123
         * deliveryId : 17c4520f6cfd1ab53d8745e84681eb49
         * deliveryName : 防守打法山东省
         * telphone : null
         * receiveTime : 2017-08-24 14:39:59
         * estimateTime : 2017-08-24 01:30:00
         * confirmTime : null
         * oilAmount : null
         * oilCost : null
         * finishTime : null
         * deliveryAddress : 实得分是发送多福多寿
         * payType : null
         * payAmount : null
         * status : 1
         * c_user : null
         * c_dt : 2017-08-24 14:40:19
         * u_user : null
         * u_dt : null
         * carList : [{"id":null,"relateId":"027160b3e2604aaba97cb96c7c4916b8","relateType":"0","vehicleCode":"看看看","pName":null,"telphone":"18799999999","oilType":"去","tankSize":"999","num":null,"dstate":null,"status":null,"c_user":null,"c_dt":null,"u_user":null,"u_dt":null}]
         */

        private String id;
        private String deliveryCode;
        private String indentCode;
        private String carIds;
        private String indentId;
        private String oilCarId;
        private String senderName;
        private String senderPhone;
        private String deliveryId;
        private String deliveryName;
        private String telphone;
        private String receiveTime;
        private String estimateTime;
        private String confirmTime;
        private String oilAmount;
        private String oilCost;
        private String finishTime;
        private String deliveryAddress;
        private String payType;
        private String payAmount;
        private String status;
        private String c_user;
        private String c_dt;
        private String u_user;
        private String u_dt;
        private List<GCarListBean> carList;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDeliveryCode() {
            return deliveryCode == null ? "" : deliveryCode;
        }

        public void setDeliveryCode(String deliveryCode) {
            this.deliveryCode = deliveryCode;
        }

        public String getIndentCode() {
            return indentCode == null ? "" : indentCode;
        }

        public void setIndentCode(String indentCode) {
            this.indentCode = indentCode;
        }

        public String getCarIds() {
            return carIds == null ? "" : carIds;
        }

        public void setCarIds(String carIds) {
            this.carIds = carIds;
        }

        public String getIndentId() {
            return indentId == null ? "" : indentId;
        }

        public void setIndentId(String indentId) {
            this.indentId = indentId;
        }

        public String getOilCarId() {
            return oilCarId == null ? "" : oilCarId;
        }

        public void setOilCarId(String oilCarId) {
            this.oilCarId = oilCarId;
        }

        public String getSenderName() {
            return senderName == null ? "" : senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public String getSenderPhone() {
            return senderPhone;
        }

        public void setSenderPhone(String senderPhone) {
            this.senderPhone = senderPhone;
        }

        public String getDeliveryId() {
            return deliveryId;
        }

        public void setDeliveryId(String deliveryId) {
            this.deliveryId = deliveryId;
        }

        public String getDeliveryName() {
            return deliveryName;
        }

        public void setDeliveryName(String deliveryName) {
            this.deliveryName = deliveryName;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(String receiveTime) {
            this.receiveTime = receiveTime;
        }

        public String getEstimateTime() {
            return estimateTime;
        }

        public void setEstimateTime(String estimateTime) {
            this.estimateTime = estimateTime;
        }

        public String getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
        }

        public String getOilAmount() {
            return oilAmount;
        }

        public void setOilAmount(String oilAmount) {
            this.oilAmount = oilAmount;
        }

        public String getOilCost() {
            return oilCost;
        }

        public void setOilCost(String oilCost) {
            this.oilCost = oilCost;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public String getDeliveryAddress() {
            return deliveryAddress;
        }

        public void setDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
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

        public List<GCarListBean> getCarList() {
            return carList;
        }

        public void setCarList(List<GCarListBean> carList) {
            this.carList = carList;
        }
//
//        public static class CarListBean {
//            /**
//             * id : null
//             * relateId : 027160b3e2604aaba97cb96c7c4916b8
//             * relateType : 0
//             * vehicleCode : 看看看
//             * pName : null
//             * telphone : 18799999999
//             * oilType : 去
//             * tankSize : 999
//             * num : null
//             * dstate : null
//             * status : null
//             * c_user : null
//             * c_dt : null
//             * u_user : null
//             * u_dt : null
//             */
//
//            private String id;
//            private String relateId;
//            private String relateType;
//            private String vehicleCode;
//            private String pName;
//            private String telphone;
//            private String oilType;
//            private String tankSize;
//            private String num;
//            private String dstate;
//            private String status;
//            private String c_user;
//            private String c_dt;
//            private String u_user;
//            private String u_dt;
//            private String finishTime;
//            private String oilBalance;
//            private String fillTime;
//            private String oilTypeName;
//
//            public String getOilTypeName() {
//                return oilTypeName;
//            }
//
//            public void setOilTypeName(String oilTypeName) {
//                this.oilTypeName = oilTypeName;
//            }
//
//            public String getFinishTime() {
//                return finishTime;
//            }
//
//            public void setFinishTime(String finishTime) {
//                this.finishTime = finishTime;
//            }
//
//            public String getOilBalance() {
//                return oilBalance;
//            }
//
//            public void setOilBalance(String oilBalance) {
//                this.oilBalance = oilBalance;
//            }
//
//            public String getFillTime() {
//                return fillTime;
//            }
//
//            public void setFillTime(String fillTime) {
//                this.fillTime = fillTime;
//            }
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public String getRelateId() {
//                return relateId;
//            }
//
//            public void setRelateId(String relateId) {
//                this.relateId = relateId;
//            }
//
//            public String getRelateType() {
//                return relateType;
//            }
//
//            public void setRelateType(String relateType) {
//                this.relateType = relateType;
//            }
//
//            public String getVehicleCode() {
//                return vehicleCode;
//            }
//
//            public void setVehicleCode(String vehicleCode) {
//                this.vehicleCode = vehicleCode;
//            }
//
////            public String getPName() {
////                return pName;
////            }
////
////            public void setPName(String pName) {
////                this.pName = pName;
////            }
//
//
//            public String getpName() {
//                return pName == null ? "" : pName;
//            }
//
//            public void setpName(String pName) {
//                this.pName = pName;
//            }
//
//
//            public String getTelphone() {
//                return telphone;
//            }
//
//            public void setTelphone(String telphone) {
//                this.telphone = telphone;
//            }
//
//            public String getOilType() {
//                return oilType;
//            }
//
//            public void setOilType(String oilType) {
//                this.oilType = oilType;
//            }
//
//            public String getTankSize() {
//                return tankSize;
//            }
//
//            public void setTankSize(String tankSize) {
//                this.tankSize = tankSize;
//            }
//
//            public String getNum() {
//                return num;
//            }
//
//            public void setNum(String num) {
//                this.num = num;
//            }
//
//            public String getDstate() {
//                return dstate;
//            }
//
//            public void setDstate(String dstate) {
//                this.dstate = dstate;
//            }
//
//            public String getStatus() {
//                return status;
//            }
//
//            public void setStatus(String status) {
//                this.status = status;
//            }
//
//            public String getC_user() {
//                return c_user;
//            }
//
//            public void setC_user(String c_user) {
//                this.c_user = c_user;
//            }
//
//            public String getC_dt() {
//                return c_dt;
//            }
//
//            public void setC_dt(String c_dt) {
//                this.c_dt = c_dt;
//            }
//
//            public String getU_user() {
//                return u_user;
//            }
//
//            public void setU_user(String u_user) {
//                this.u_user = u_user;
//            }
//
//            public String getU_dt() {
//                return u_dt;
//            }
//
//            public void setU_dt(String u_dt) {
//                this.u_dt = u_dt;
//            }
//        }
    }
}
