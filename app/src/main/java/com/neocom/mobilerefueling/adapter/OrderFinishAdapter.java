package com.neocom.mobilerefueling.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.bean.GCarListBean;
import com.neocom.mobilerefueling.dao.GCarListDao;
import com.neocom.mobilerefueling.utils.UIUtils;
import com.neocom.mobilerefueling.view.OrderComEdittext;
import com.neocom.mobilerefueling.view.OrderConbindView;
import com.widget.jcdialog.DialogUtils;
import com.widget.jcdialog.listener.DialogUIDateTimeSaveListener;
import com.widget.jcdialog.widget.DateSelectorWheelView;

import java.util.List;

/**
 * Created by admin on 2017/8/30.
 */

public class OrderFinishAdapter extends BaseAdapter {

    List<GCarListBean> finishBeen;
    String state;
//    List<GCarListBean> carListBeenList = new ArrayList<>();

    Context context;
    private List<GCarListBean> gCarListBeen;
    //    private Map<Integer, SubMitDeliveryBean.CarListBean> listMap;

    //    CarListDao carListDao;
    public OrderFinishAdapter(List<GCarListBean> finishBeen, Context context) {
        this.finishBeen = finishBeen;
        this.context = context;
//        carListDao = new CarListDao(context);
    }

    public OrderFinishAdapter(List<GCarListBean> finishBeen, Context context, String state) {
        this.finishBeen = finishBeen;
        this.context = context;
        this.state = state;
    }


    @Override
    public int getCount() {
        return finishBeen == null ? 0 : finishBeen.size();
    }

    @Override
    public GCarListBean getItem(int i) {
        return finishBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (view == null) {

            holder = new ViewHolder();

            view = UIUtils.inflate(R.layout.ordret_refuel_item_layout);

            holder.carFinishNum = view.findViewById(R.id.order_car_no);

            holder.carFinishName = view.findViewById(R.id.order_car_name);

            holder.carFinishPhone = view.findViewById(R.id.order_car_phone);

            holder.carFinishType = view.findViewById(R.id.order_car_oil_type);

            holder.carFinishCount = view.findViewById(R.id.order_car_oil_count);
            holder.carFinishCount.setEditable(false);
//            holder.carFinishCount.setDanweiVisibility(true);
            holder.carFinishTime = view.findViewById(R.id.order_car_finish_time);

            holder.carFinishOilFee = view.findViewById(R.id.order_car_oil_fee);
//            holder.carFinishOilFee.setEditable(false);
//            holder.carFinishOilFee.setDanweiVisibility(true);
//            holder.carFinishPutTime = view.findViewById(R.id.order_car_put_time);
            holder.orderCarOilPrice = view.findViewById(R.id.order_car_oil_price);
            holder.sureAndSaveBtn = view.findViewById(R.id.edit_sava_tv);

            view.setTag(holder);

        } else {

            holder = (ViewHolder) view.getTag();

        }

        final GCarListBean subCarItemBean = new GCarListBean();

        final GCarListBean carListBean = getItem(i);
        subCarItemBean.setId(carListBean.getId());
        subCarItemBean.setRelateId(carListBean.getRelateId());
        subCarItemBean.setRelateType(carListBean.getRelateType());
        subCarItemBean.setVehicleCode(carListBean.getVehicleCode());
        subCarItemBean.setpName(carListBean.getpName());
        subCarItemBean.setTelphone(carListBean.getTelphone());
        subCarItemBean.setOilType(carListBean.getOilType());
        subCarItemBean.setTankSize(carListBean.getTankSize());
        subCarItemBean.setNum("");
        subCarItemBean.setFillTime(carListBean.getFillTime());
        subCarItemBean.setDstate(carListBean.getDstate());
        subCarItemBean.setStatus(carListBean.getStatus());
        subCarItemBean.setC_user(carListBean.getC_user());
        subCarItemBean.setC_dt(carListBean.getC_dt());
        subCarItemBean.setC_user(carListBean.getC_user());
        subCarItemBean.setU_dt(carListBean.getU_dt());
        holder.carFinishNum.setTitle("车牌号");
        holder.carFinishNum.setContet(carListBean.getVehicleCode());
        holder.carFinishName.setTitle("联系人");
        holder.carFinishName.setContet(carListBean.getpName());
        holder.carFinishPhone.setTitle("联系电话");
        holder.carFinishPhone.setContet(carListBean.getTelphone());
        holder.carFinishType.setTitle("加油类型");
        holder.carFinishType.setContet(carListBean.getOilType());
        holder.carFinishCount.setEditTitle("加油量");
//        holder.carFinishCount.setEditContent("");


//        holder.carFinishCount.setEditContent(carListBean.getTankSize());
        holder.carFinishCount.setDanweiContent("升/L");
        holder.carFinishTime.setTitle("加油完成时间");
        holder.carFinishTime.setContet("请选择完成时间");
//
//        subCarItemBean.setFinishTime(UIUtils.getCurrentTime());


        holder.orderCarOilPrice.setTitle("单价");
        holder.orderCarOilPrice.setContet(" 5 元/L");
        holder.carFinishOilFee.setTitle("加油费用");
//        holder.carFinishOilFee.setEditContent(carListBean.get);
//        holder.carFinishOilFee.setDanweiContent("元/￥");
//        holder.carFinishPutTime.setTitle("填报时间");
//        holder.carFinishPutTime.setContet("请选择填报时间");
        Log.i("==============", "getView: ---->" + carListBean.getDstate());

        if (!TextUtils.isEmpty(state)) {
            if (("2").equals(state) || ("3").equals(state)) {
                holder.sureAndSaveBtn.setVisibility(View.GONE);
                holder.carFinishCount.setEditBacgroungNull();
                holder.carFinishCount.setEditContent(carListBean.getTankSize());
            } else {
                holder.carFinishCount.setEditContent("");
            }
        }

        holder.sureAndSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (("编辑").equals(holder.sureAndSaveBtn.getText().toString().trim())) {

                    holder.sureAndSaveBtn.setText("保存");
                    holder.carFinishCount.setEditable(true);
//                    holder.carFinishOilFee.setEditable(true);

                } else if (("保存").equals(holder.sureAndSaveBtn.getText().toString().trim())) {


                    if (("请选择完成时间").equals(holder.carFinishTime.getContent())) {

                        Toast.makeText(context, "请选择加油完成时间", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(holder.carFinishCount.getEditContent())) {
                        Toast.makeText(context, "请输入加油量", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    holder.sureAndSaveBtn.setText("编辑");
                    holder.carFinishCount.setEditable(false);
//                    holder.carFinishOilFee.setEditable(false);
                    Log.i("获取内容是::", "onClick: " + holder.carFinishCount.getEditContent());

                    String carFinishCount = holder.carFinishCount.getEditContent();
                    getItem(i).setTankSize(carFinishCount);
                    String carFinshTimeStr = holder.carFinishTime.getContent();
                    String carFinishOilFee = holder.carFinishOilFee.getContent();


                    if (TextUtils.isEmpty(carFinishCount)) {
                        subCarItemBean.setTankSize("");
                    } else {
                        subCarItemBean.setTankSize(carFinishCount);
                    }
                    if (TextUtils.isEmpty(carFinshTimeStr)) {

                        subCarItemBean.setFinishTime("");
                    } else {
                        subCarItemBean.setFinishTime(carFinshTimeStr);
                    }

                    if (TextUtils.isEmpty(carFinishOilFee)) {
                        subCarItemBean.setOilBalance("");
                    } else {
                        subCarItemBean.setOilBalance(carFinishOilFee);
                    }

                    Log.i("是不是这样的", "onClick: " + new Gson().toJson(subCarItemBean));
//                    carListDao.updateCarList(subCarItemBean);

//                    GCarListDao.getInstance().updateUserData(subCarItemBean);
                    GCarListDao.getInstance().deleteUserData(carListBean);
                    GCarListDao.getInstance().insertOrReplaceData(subCarItemBean);

                    Log.i("分割线==", "onClick: +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                    gCarListBeen = GCarListDao.getInstance().queryAllData();

//                    for (int j = 0; j < gCarListBeen.size(); j++) {
//                        Log.i("遍历出得数据是：", "onClick: " + gCarListBeen.get(j).toString());
//                    }
//
//                    Log.i("分割线==", "onClick: +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    holder.carFinishOilFee.setContet(String.valueOf(Integer.valueOf(carFinishCount) * 5) + "元");
                    amountListener.getAddOilAmount(carFinishCount);
                }
            }
        });


        holder.carFinishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    DialogUtils.showDatePick(context, Gravity.BOTTOM, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDDHHMMSS, 0, new DialogUIDateTimeSaveListener() {
                        @Override
                        public void onSaveSelectedDate(int tag, String selectedDate) {
//                            Toast.makeText(UIUtils.getContext(), "11111", Toast.LENGTH_SHORT).show();
                            holder.carFinishTime.setContet(selectedDate);
                            subCarItemBean.setFinishTime(selectedDate);


                        }
                    }).show();
                }
            }
        });

//        holder.carFinishPutTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                {
//                    DialogUtils.showDatePick(context, Gravity.BOTTOM, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDDHHMMSS, 0, new DialogUIDateTimeSaveListener() {
//                        @Override
//                        public void onSaveSelectedDate(int tag, String selectedDate) {
//                            holder.carFinishPutTime.setContet(selectedDate);
//                            subCarItemBean.setFillTime(selectedDate);
//                        }
//                    }).show();
//                }
//            }
//        });
        return view;
    }

    static class ViewHolder {

        OrderConbindView carFinishNum;

        OrderConbindView carFinishName;

        OrderConbindView carFinishPhone;

        OrderConbindView carFinishType;

        OrderComEdittext carFinishCount;
        OrderConbindView carFinishTime;

        OrderConbindView carFinishOilFee;

//        OrderConbindView carFinishPutTime;

        TextView sureAndSaveBtn;

        OrderConbindView orderCarOilPrice;
    }


    // 返回 这个 修改的 车辆列表
    public List<GCarListBean> getCarListEdit() {
        return gCarListBeen;
    }


    onGetAddOilAmountListener amountListener;

    public void setonGetAddOilAmountListener(onGetAddOilAmountListener amountListener) {
        this.amountListener = amountListener;
    }

    public interface onGetAddOilAmountListener {

        void getAddOilAmount(String oilFee);

    }

//   public void showSelectTime(){
//
//
//       {
//           DialogUtils.showDatePick(UIUtils.getContext(), Gravity.BOTTOM, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDDHHMMSS, 0, new DialogUIDateTimeSaveListener() {
//               @Override
//               public void onSaveSelectedDate(int tag, String selectedDate) {
//
////                        Toast.makeText(ReceiveListDetailActivity.this, "时间："+selectedDate, Toast.LENGTH_SHORT).show();
//                   comContentChooseTime.setText(selectedDate);
//
//               }
//           }).show();
//       }
//
//
//
//   }


}
