package com.neocom.mobilerefueling.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.adapter.AddOilItemAdapter;
import com.neocom.mobilerefueling.bean.AddOrderBean;
import com.neocom.mobilerefueling.bean.AddressListRequest;
import com.neocom.mobilerefueling.bean.ChooseCarBean;
import com.neocom.mobilerefueling.bean.OrderSuccessBean;
import com.neocom.mobilerefueling.bean.SubmitOrderBean;
import com.neocom.mobilerefueling.bean.SubmitOrderOilsBean;
import com.neocom.mobilerefueling.bean.TicketSubmitBean;
import com.neocom.mobilerefueling.globle.Constant;
import com.neocom.mobilerefueling.net.HttpManger;
import com.neocom.mobilerefueling.utils.GetOrderStateUtil;
import com.neocom.mobilerefueling.utils.GetUserInfoUtils;
import com.neocom.mobilerefueling.utils.UIUtils;
import com.neocom.mobilerefueling.view.ListViewWithScroll;
import com.widget.jcdialog.DialogUtils;
import com.widget.jcdialog.listener.DialogUIListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/11/1.
 */

public class AddOilActivity extends BaseActivity implements View.OnClickListener {

    private TextView addressTv;
    //    AddedCarAdapter carAdapter;
    public List<ChooseCarBean> lastSlected;//最后一次变化后的值
    private ListViewWithScroll addedCarLv;

    Handler mHandler;
    Gson gson;
    private TextView addressName;
    private TextView addressPhone;
    private RelativeLayout adressRl;

    //    private TextView deliverWay;
    private Button orderCommitBtn;

    //    private AddOrderBean addOrderBean = new AddOrderBean();
    private EditText remarInfo;

    //    private final static int ADDRESS_REQUESTCODE = 0X101; // 请求码
//    @BindView(R.id.ticket_paytype_rl)
//    RelativeLayout ticketPaytypeRl;
//    @BindView(R.id.pay_type_select_tv)
//    TextView payTypeSelectTv;
    @BindView(R.id.ticket_rl)
    RelativeLayout ticketRl;

    private List<AddOrderBean.CarsBean> carsRequestBeen;

    SubmitOrderBean submitOrderBean;
    List<SubmitOrderOilsBean> oilsBeen = new ArrayList<>();
    private AddOilItemAdapter oilItemAdapter;

    TextView allPriceMoney;
    private TextView ticketType;

    List<SubmitOrderOilsBean> submitOilsBeen = new ArrayList<>();

    @Override
    public void initContentView() {
        setContentView(R.layout.refurling_fg_layout);
    }

    @Override
    public void initView() {

        RelativeLayout chooseAddress = (RelativeLayout) findViewById(R.id.refuel_deliver_chooseaddress_ll);
        chooseAddress.setOnClickListener(this);

//        LinearLayout chooseTime = findViewById(R.id.refuel_delivery_time);
//        chooseTime.setOnClickListener(this);
        addressTv = (TextView) findViewById(R.id.refuel_delivery_address);
        addressName = (TextView) findViewById(R.id.refuel_name);
        addressPhone = (TextView) findViewById(R.id.refuel_user_phone);
        orderCommitBtn = (Button) findViewById(R.id.commit_order_btn);
        orderCommitBtn.setOnClickListener(this);
        RelativeLayout addCarRl = (RelativeLayout) findViewById(R.id.add_car_rl);
        addCarRl.setOnClickListener(this);
        remarInfo = (EditText) findViewById(R.id.remark_info);
//        Button addCarBtn = findViewById(R.id.refuel_delivery_add_car);
//        addCarBtn.setOnClickListener(this);

        addedCarLv = (ListViewWithScroll) findViewById(R.id.added_car_lv);


        ViewGroup.LayoutParams params = addedCarLv.getLayoutParams();
        params.height = 10; //需要设置的listview的高度，你可以设置成一个定值，也可以设置成其他容器的高度，如果是其他容器高度，那么不要在oncreate中执行，需要做延时处理，否则高度为0
        addedCarLv.setLayoutParams(params);

        adressRl = (RelativeLayout) findViewById(R.id.address_rl);

        allPriceMoney = (TextView) findViewById(R.id.all_price_money);
//        deliverWay = findViewById(R.id.deliver_way);

        ticketType = (TextView) findViewById(R.id.ticket_type);

//        ticketPaytypeRl.setOnClickListener(this);
        ticketRl.setOnClickListener(this);
        submitOrderBean = new SubmitOrderBean();
        setData();


    }


    private void setData() {

//        for (int i = 0; i < 5; i++) {
//            SubmitOrderOilsBean oilsBean = new SubmitOrderOilsBean();
//            oilsBean.setOilType("种类" + i);
//            oilsBean.setAmount("数量" + i);
//            oilsBean.setOilPrice("单价" + i);
//            oilsBeen.add(oilsBean);
//        }
        SubmitOrderOilsBean oilsBean0 = new SubmitOrderOilsBean();
        oilsBean0.setAmount("0");
        oilsBean0.setOilType("1");
        oilsBean0.setOilPrice("5");
        oilsBeen.add(oilsBean0);

        oilItemAdapter = new AddOilItemAdapter(UIUtils.getContext(), addedCarLv, oilsBeen);
        addedCarLv.setAdapter(oilItemAdapter);
        calculateMoney();
    }


    @Override
    public void onStart() {
        super.onStart();
        oilItemAdapter.setOnGetOilAmountListener(new AddOilItemAdapter.OnGetOilAmountListener() {
            @Override
            public void GetOilAmount(SubmitOrderOilsBean orderOilsBean, int position) {
                Log.i(TAG, "GetOilAmount:orderOilsBean " + orderOilsBean.toString() + ";position:" + position);
//                oilItemAdapter.getItem(position).setAmount(amount);
                calculateMoney();
            }
        });

//        oilItemAdapter.setOnGetOilTypeValueListener(new AddOilItemAdapter.OnGetOilTypeValueListener() {
//            @Override
//            public void getOilTypeValue(int position, SubmitOrderOilsBean itemOrder) {
//
//            }
//        });

    }

    @Override
    public void initData() {
        gson = new Gson();
        lastSlected = new ArrayList<>();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
//                    case Constant.ADDCAR_REQUESTCODE:
//                        Log.i(TAG, "handleMessage: carAdapter" + lastSlected.toString());
//                        lastSlected = (List<ChooseCarBean>) msg.obj;
//                        carAdapter = new AddedCarAdapter(UIUtils.getContext(), lastSlected);
//                        addedCarLv.setAdapter(carAdapter);
////                        Toast.makeText(UIUtils.getContext(), "0000000", Toast.LENGTH_SHORT).show();
//
//                        carsRequestBeen = new ArrayList<>();
//                        for (int i = 0; i < lastSlected.size(); i++) {
//
//                            AddOrderBean.CarsBean bean = new AddOrderBean.CarsBean();
//
//                            ChooseCarBean chooseCarBean = lastSlected.get(i);
//
//                            bean.setVehicleCode(chooseCarBean.getCarNum()); // 车牌号
//                            bean.setOilType(chooseCarBean.getFuelType()); // 燃油类型
//                            bean.setTankSize(chooseCarBean.getTankSize()); // 油箱大小
//                            bean.setPName(chooseCarBean.getName()); //联系人
//                            bean.setTelphone(chooseCarBean.getTelephone());// 联系人电话 联系人电话号码
//                            carsRequestBeen.add(bean);
//                        }
//
//
//                        break;
                    case Constant.ADDRESS_REQUESTCODE:

                        AddressListRequest.BringBean fromJsonBean = gson.fromJson((String) msg.obj, AddressListRequest.BringBean.class);
                        Log.i(TAG, "handleMessage: ==>" + fromJsonBean);

//                        addressTv.setText(fromJsonBean.getProvince() + fromJsonBean.getDetail());
//                        addressName.setText(fromJsonBean.getUserName());
//                        addressPhone.setText(fromJsonBean.getUserPhone());
//
//                        addOrderBean.setIndentAddress(fromJsonBean.getProvince() + fromJsonBean.getDetail()); // 地址
//                        addOrderBean.setLinkMan(fromJsonBean.getUserName());
//                        addOrderBean.setPhone(fromJsonBean.getUserPhone());

                        if (fromJsonBean != null) {

                            String addressStr = fromJsonBean.getAddress();
                            String nameStr = fromJsonBean.getPname();
                            String phoneStr = fromJsonBean.getTelphone();
                            addressTv.setText(addressStr);
                            addressName.setText(nameStr);
                            addressPhone.setText(phoneStr);
                            submitOrderBean.setIndentAddress(addressStr);
                            submitOrderBean.setLinkMan(nameStr);
                            submitOrderBean.setPhone(phoneStr);

                            submitOrderBean.setAddressCode(fromJsonBean.getProvince() + ";" + fromJsonBean.getCity() + ";" + (fromJsonBean.getRegion() == null ? "" : fromJsonBean.getRegion()));


                        }

                        break;
                    default:
                        break;
                }

            }
        };
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refuel_deliver_chooseaddress_ll:
//                Toast.makeText(UIUtils.getContext(), "选择地址", Toast.LENGTH_SHORT).show();

//                startActivity(new Intent(UIUtils.getContext(), ChooseAddress .class));

                startActivityForResult(new Intent(UIUtils.getContext(), AddressListActivity.class), Constant.ADDRESS_REQUESTCODE);

                break;

//            ChooseOilCarActivity

//            case R.id.refuel_delivery_add_car:
            case R.id.add_car_rl:
//                startActivity(new Intent(UIUtils.getContext(), ChooseOilCarActivity.class));
//                startActivityForResult(new Intent(UIUtils.getContext(), ChooseOilCarActivity.class), Constant.ADDCAR_REQUESTCODE);

//              添加 一条 下单

                SubmitOrderOilsBean oilsBean0 = new SubmitOrderOilsBean();
                oilsBean0.setAmount("0");
                oilsBean0.setOilType("1");
                oilsBean0.setOilPrice("5");
                oilsBeen.add(oilsBean0);
                oilItemAdapter.notifyDataSetChanged();
//                calculateMoney();
                break;

//            case R.id.refuel_delivery_time:
//                selectTime();
//                break;

//            case R.id.ticket_paytype_rl:
//                showPayType();
//                break;
            case R.id.commit_order_btn:
                commitOrder();

//                int childCount = addedCarLv.getCount();
//                int allPrice = 0;
//                Log.i(TAG, "calculateMoney: ==>" + childCount);
//                for (int i = 0; i < childCount; i++) {
//                    Log.i(TAG, "calculateMoney: ==" + i);
//                    SubmitOrderOilsBean orderOilsBean = oilItemAdapter.getItem(i);
//                    Log.i(TAG, "calculateMoney: 提交的参数是：" + orderOilsBean.toString() + ";==;" + i);
//                    String oilAmount = orderOilsBean.getAmount();
//                    String oilPrice = orderOilsBean.getOilPrice();
//                    Integer oilAmountInt;
//                    Integer oilPriceInt;
//                    if (!TextUtils.isEmpty(oilAmount)) {
//                        oilAmountInt = Integer.valueOf(oilAmount);
//                    } else {
//                        oilAmountInt = Integer.valueOf("0");
//                    }
//                    if (!TextUtils.isEmpty(oilPrice)) {
//                        oilPriceInt = Integer.valueOf(oilPrice);
//                    } else {
//                        oilPriceInt = Integer.valueOf("0");
//                    }
//
//                    Integer allItemPrice = oilPriceInt * oilAmountInt;
//                    Log.i(TAG, "calculateMoney: oilAmountInt:" + oilAmountInt + ",oilPriceInt:" + oilPriceInt);
//                    allPrice += allItemPrice;
//                }
//
//                Log.i(TAG, "calculateMoney: 计算总价是:" + allPrice);
//                allPriceMoney.setText("￥" + allPrice);

                break;
            case R.id.ticket_rl:
//              跳转 获取发票信息
//startActivity(new Intent(UIUtils.getContext(), TicketActivity.class));

                startActivityForResult(new Intent(UIUtils.getContext(), TicketActivity.class), 11);

                break;
            default:
                break;
        }
    }
//submitOrderBean.setOils();

    public void calculateMoney() {

        if (submitOilsBeen != null) {
            submitOilsBeen.clear();
        }


        submitOrderBean.setOils(null);
        int childCount = addedCarLv.getCount();
        int allPrice = 0;
        Log.i(TAG, "calculateMoney: ==>" + childCount);
        for (int i = 0; i < childCount; i++) {
            Log.i(TAG, "calculateMoney: ==" + i);
            SubmitOrderOilsBean orderOilsBean = oilItemAdapter.getItem(i);
            submitOilsBeen.add(orderOilsBean);
            Log.i(TAG, "calculateMoney: 提交的参数是：" + orderOilsBean.toString() + ";==;" + i);
            String oilAmount = orderOilsBean.getAmount();
            String oilPrice = orderOilsBean.getOilPrice();
            Integer oilAmountInt;
            Integer oilPriceInt;
            if (!TextUtils.isEmpty(oilAmount)) {
                oilAmountInt = Integer.valueOf(oilAmount);
            } else {
                oilAmountInt = Integer.valueOf("0");
            }
            if (!TextUtils.isEmpty(oilPrice)) {
                oilPriceInt = Integer.valueOf(oilPrice);
            } else {
                oilPriceInt = Integer.valueOf("0");
            }

            Integer allItemPrice = oilPriceInt * oilAmountInt;
            Log.i(TAG, "calculateMoney: oilAmountInt:" + oilAmountInt + ",oilPriceInt:" + oilPriceInt);
            allPrice += allItemPrice;
        }
        submitOrderBean.setOils(submitOilsBeen);
        Log.i(TAG, "calculateMoney: 计算总价是:" + allPrice);
        allPriceMoney.setText("￥" + allPrice);
    }


//    public void selectTime() {
//        DialogUtils.showAlert(getActivity(), "请选择配送方式", "", "", "", "尽快配送", "预约配送", true, new DialogUIListener() {
//            @Override
//            public void onPositive() {
//                deliverWay.setText("尽快配送");
////                        addOrderBean.setDeliveryTime("尽快配送");
//                addOrderBean.setDelStatus("0");
//                addOrderBean.setDeliveryTime("");
//            }
//
//            @Override
//            public void onNegative() {
//                {
//                    DialogUtils.showDatePick(getActivity(), Gravity.BOTTOM, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDDHHMMSS, 0, new DialogUIDateTimeSaveListener() {
//                        @Override
//                        public void onSaveSelectedDate(int tag, String selectedDate) {
//                            deliverWay.setText(selectedDate);
//                            addOrderBean.setDelStatus("1");
//                            addOrderBean.setDeliveryTime(selectedDate);
//                        }
//                    }).show();
//                }
//
//            }
//
//        }).show();
//
//    }

//    public void showPayType() {
//
//        /**
//         * payType=0 预付款payType= 1 在线支付
//         payType= 2 货到付款
//         payType= 3 公司转账
//         */
//
//        List<TieBean> datas2 = new ArrayList<>();
////        datas2.add(new tiebean("支付宝"));
////        datas2.add(new tiebean("微信"));
////        datas2.add(new tiebean("第三方"));
////        datas2.add(new tiebean("银联"));
//        datas2.add(new TieBean("预付款"));
//        datas2.add(new TieBean("在线支付"));
//        datas2.add(new TieBean("货到付款"));
//        datas2.add(new TieBean("公司转账"));
//        TieAdapter adapter = new TieAdapter(getActivity(), datas2);
//        BuildBean buildBean = DialogUtils.showMdBottomSheet(getActivity(), true, "", datas2, "", 0, new DialogUIItemListener() {
//            @Override
//            public void onItemClick(CharSequence text, int position) {
////                Toast.makeText(UIUtils.getContext(), "++>" + text + "---" + position, Toast.LENGTH_SHORT).show();
//
//                addOrderBean.setPayType(String.valueOf(position)); // 根据后台 而定
//                payTypeSelectTv.setText(text);
//            }
//        });
//        buildBean.mAdapter = adapter;
//        buildBean.show();
//    }

    public void commitOrder() {


        submitOrderBean.setUserId(GetUserInfoUtils.getUserInfo().getUserId()); // userId

        submitOrderBean.setRemark(remarInfo.getText().toString().trim());

//        addOrderBean.setCars(carsRequestBeen);
//        Toast.makeText(UIUtils.getContext(), "提交", Toast.LENGTH_SHORT).show();


        if (submitOrderBean.getInvMess() == null) {
            submitOrderBean.setHaveInv("2"); // 没有发票
        } else {
            submitOrderBean.setHaveInv("1");
        }


//        if (TextUtils.isEmpty(addOrderBean.getIndentAddress())) {
//
//            Toast.makeText(UIUtils.getContext(), "请选择地址", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (addOrderBean.getCars() == null || addOrderBean.getCars().size() == 0) {
//            Toast.makeText(UIUtils.getContext(), "请选择车辆", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (TextUtils.isEmpty(addOrderBean.getPayType())) {
//
//            Toast.makeText(UIUtils.getContext(), "请选择支付方式", Toast.LENGTH_SHORT).show();
//            return;
//
//        }
//
//        if (TextUtils.isEmpty(addOrderBean.getDelStatus()) || TextUtils.isEmpty(addOrderBean.getDeliveryTime())) {
//
//            addOrderBean.setDelStatus("0");
//            addOrderBean.setDeliveryTime("");
//
//        }

        String toJson = new Gson().toJson(submitOrderBean);
        Log.i(TAG, "onClick: ==>" + toJson);

        /***********************************************************************************************/

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toJson);

        Call<OrderSuccessBean> call = HttpManger.getHttpMangerInstance().getServices().addOrder(requestBody);
        call.enqueue(new Callback<OrderSuccessBean>() {
            @Override
            public void onResponse(Call<OrderSuccessBean> call, Response<OrderSuccessBean> response) {


                OrderSuccessBean body = response.body();
                if (body != null) {
                    boolean res = body.isRes();
                    if (res) {
//                    showDialog("订单提交成功");
                        startActivity(new Intent(UIUtils.getContext(), OrderSuccessActivity.class));
                    } else {
                        showDialog("订单提交失败");
                    }

                }


            }

            @Override
            public void onFailure(Call<OrderSuccessBean> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                showDialog("订单提交失败");
            }
        });
/***********************************************************************************************/

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Message message = Message.obtain();
//        Toast.makeText(UIUtils.getContext(), ">>"+requestCode+";;"+resultCode+";;"+data.getStringExtra("addcar_data"), Toast.LENGTH_SHORT).show();

        if (Constant.ADDRESS_REQUESTCODE == requestCode && Constant.ADDRESS_RESP == resultCode) {

//            data.getStringExtra()
            Log.i(TAG, "onActivityResult:" + data.getStringExtra("address"));


            message.what = Constant.ADDRESS_REQUESTCODE;
            message.obj = data.getStringExtra("address");
            mHandler.sendMessage(message);
//            addressTv.setText(data.getStringExtra("address"));


        }

//
//        else if (Constant.ADDCAR_REQUESTCODE == requestCode && Constant.ADDCAR_RESP == resultCode) {
//
////            data.getStringExtra()  Extra("addcar_data"));
//            Log.i(TAG, "onActivityResult:添加汽车" + data.getSerializableExtra("addcar_data"));
//
////            if (lastSlected != null) {
////                lastSlected.clear();
////            }
////
//            List<ChooseCarBean> carSelecte = (List<ChooseCarBean>) data.getSerializableExtra("addcar_data");
////            Log.i(TAG, "onActivityResult: "+carSelecte.toString());
////            lastSlected.addAll(carSelecte);
////            carAdapter.notifyDataSetChanged();
//            if (carSelecte != null) {
//
//                List<ChooseCarBean> carAdded = new ArrayList<>();
//                for (int i = 0; i < carSelecte.size(); i++) {
//
//                    ChooseCarBean carBean = carSelecte.get(i);
//
//                    if (carBean.isChecked()) {
//                        Log.i(TAG, "onActivityResult: " + carBean.toString());
//                        carAdded.add(carBean);
//
//                    }
//
//                }
//
//                Log.i(TAG, "onActivityResult: ==" + carAdded.toString());
//                message.what = Constant.ADDCAR_REQUESTCODE;
//                message.obj = carAdded;
//                mHandler.sendMessage(message);
//
//            }
//
//        }else

        if (requestCode == 11 && resultCode == 12) {
            String ticketJsonData = data.getStringExtra("ticketJsonData");
            Log.i(TAG, "onActivityResult:" + ticketJsonData);

            TicketSubmitBean ticketSubmitBean = gson.fromJson(ticketJsonData, TicketSubmitBean.class);
            if (ticketSubmitBean == null) {
                Log.i(TAG, "onActivityResult: =====================================");
                Log.i(TAG, "onActivityResult: =" + ticketSubmitBean.toString());

                String invoiceType = ticketSubmitBean.getInvoiceType();

//            此处 显示 发票 类型 在 界面上.....

                ticketType.setText(GetOrderStateUtil.getTicketype(invoiceType));
                submitOrderBean.setInvMess(ticketSubmitBean);
            } else {
                ticketType.setText(GetOrderStateUtil.getTicketype(""));
            }

        }
    }


    public void showDialog(String msg) {

        DialogUtils.showAlert(AddOilActivity.this, "提示", msg, "", "", "我知道了", "", true, new DialogUIListener() {
            @Override
            public void onPositive() {
//                showToast("onPositive");
            }

            @Override
            public void onNegative() {
//                showToast("onNegative");
            }

        }).show();


    }
}
