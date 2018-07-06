package com.neocom.mobilerefueling.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.adapter.OrderFinishAdapter;
import com.neocom.mobilerefueling.bean.EmptyBringGetOilBean;
import com.neocom.mobilerefueling.bean.GCarListBean;
import com.neocom.mobilerefueling.bean.ReceiveCarListDetailBean;
import com.neocom.mobilerefueling.bean.SubMitDeliveryBean;
import com.neocom.mobilerefueling.dao.GCarListDao;
import com.neocom.mobilerefueling.globle.Constant;
import com.neocom.mobilerefueling.net.HttpManger;
import com.neocom.mobilerefueling.view.ListViewWithScroll;
import com.neocom.mobilerefueling.view.OrderConbindView;
import com.widget.jcdialog.DialogUtils;
import com.widget.jcdialog.adapter.TieAdapter;
import com.widget.jcdialog.bean.BuildBean;
import com.widget.jcdialog.bean.TieBean;
import com.widget.jcdialog.listener.DialogUIDateTimeSaveListener;
import com.widget.jcdialog.listener.DialogUIItemListener;
import com.widget.jcdialog.listener.DialogUIListener;
import com.widget.jcdialog.widget.DateSelectorWheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/8/28.
 */

public class OrderFinishActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.top_bar_finish_ll)
    LinearLayout topBarFinishLl;
    @BindView(R.id.top_bar_title_tv)
    TextView topBarTitleTv;
    @BindView(R.id.top_bar_ok_ll)
    LinearLayout topBarOkLl;
    @BindView(R.id.top_title_bar)
    LinearLayout topTitleBar;
    //    @BindView(R.id.order_state_tv)
//    TextView orderStateTv;
    @BindView(R.id.receive_base_no)
    OrderConbindView receiveBaseNo;
    @BindView(R.id.receive_to_name)
    OrderConbindView receiveToName;
    @BindView(R.id.receive_no_time)
    OrderConbindView receiveNoTime;
    @BindView(R.id.receive_order_no)
    OrderConbindView receiveOrderNo;
    @BindView(R.id.receive_about_time)
    OrderConbindView receiveAboutTime;
    @BindView(R.id.receive_address)
    OrderConbindView receiveAddress;
    @BindView(R.id.receive_list)
    ListViewWithScroll receiveList;
    //    @BindView(R.id.receive_send_car_no)
//    OrderConbindView receiveSendCarNo;
//    @BindView(R.id.receive_send_car_name)
//    OrderConbindView receiveSendCarName;
//    @BindView(R.id.send_time)
//    TextView sendTime;
//    @BindView(R.id.com_content_choose_time)
//    TextView comContentChooseTime;
//    @BindView(R.id.choose_time)
//    RelativeLayout chooseTime;
    @BindView(R.id.sender_car_no)
    OrderConbindView senderCarNo;
    @BindView(R.id.sender_name)
    OrderConbindView senderName;
    @BindView(R.id.sender_time)
    OrderConbindView senderTime;
    @BindView(R.id.send_time_timer)
    OrderConbindView sendTimeTimer;
    @BindView(R.id.order_receive)
    OrderConbindView orderReceive;
    @BindView(R.id.arrival_time)
    OrderConbindView arrivalTime;
    //    @BindView(R.id.com_title)
//    TextView comTitle;
//    @BindView(R.id.finish_time)
//    TextView finishTime;
//    @BindView(R.id.finish_time_rl)
//    RelativeLayout finishTimeRl;
    @BindView(R.id.com_count_title)
    TextView comCountTitle;
    @BindView(R.id.finish_count)
    TextView finishCount;
    @BindView(R.id.finish_count_rl)
    RelativeLayout finishCountRl;
    @BindView(R.id.com_count_fee_title)
    TextView comCountFeeTitle;
    @BindView(R.id.finish_count_fee)
    TextView finishCountFee;
    @BindView(R.id.finish_count_fee_rl)
    RelativeLayout finishCountFeeRl;
    @BindView(R.id.com_count_pic_title)
    TextView comCountPicTitle;
    @BindView(R.id.finish_count_pici_fee)
    TextView finishCountPiciFee;
    @BindView(R.id.finish_count_pici_rl)
    RelativeLayout finishCountPiciRl;
    @BindView(R.id.com_count_paytype_title)
    TextView comCountPaytypeTitle;
    @BindView(R.id.finish_count_paytype_fee)
    TextView finishCountPaytypeFee;
    @BindView(R.id.finish_count_paytype_rl)
    RelativeLayout finishCountPaytypeRl;
    @BindView(R.id.com_count_money_title)
    TextView comCountMoneyTitle;
    @BindView(R.id.finish_count_money_fee)
    TextView finishCountMoneyFee;
    @BindView(R.id.finish_count_money_rl)
    RelativeLayout finishCountMoneyRl;
    @BindView(R.id.note_receive)
    EditText noteReceive;
    //    @BindView(R.id.pay_cancle)
//    Button payCancle;
    @BindView(R.id.pay_now)
    Button payNow;
    @BindView(R.id.bottom_ll)
    LinearLayout bottomLl;

    @BindView(R.id.send_car_cv)
    CardView sendCarCv;

    private String itemId;
    //    List<ReceiveCarListDetailBean.BringBean.CarListBean> carListNew;
    List<GCarListBean> carListNew;
    SubMitDeliveryBean subMitDeliveryBean = new SubMitDeliveryBean();
    //    CarListDao carListDao;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);


//            carListNew = (List<ReceiveCarListDetailBean.BringBean.CarListBean>) msg.obj;
            carListNew = (List<GCarListBean>) msg.obj;

//            carListDao.insertCarList(carListNew);
//  插入数据库..............
            GCarListDao.getInstance().insertOrReplaceMultiData(carListNew);
            Log.i(TAG, "handleMessage: ==>插入数据库了....");
//
            if (Constant.ORDER_REFUSE.equals(dstate)||Constant.ORDER_FINISHED.equals(dstate)) {
                bottomLl.setVisibility(View.GONE);
                noteReceive.setVisibility(View.GONE);
                finishAdapter = new OrderFinishAdapter(carListNew, OrderFinishActivity.this, dstate);
            } else {
                finishAdapter = new OrderFinishAdapter(carListNew, OrderFinishActivity.this);
            }

            receiveList.setAdapter(finishAdapter);
//            int childCount = receiveList.getCount();
//            Log.i(TAG, "handleMessage: ---childCount>>>" + childCount);
//            int sumCount = 0;
//            for (int i = 0; i < childCount; i++) {
//                String tankSize = finishAdapter.getItem(i).getTankSize();
//                Log.i(TAG, "handleMessage: ==>加油量" + tankSize);
//                if (!TextUtils.isEmpty(tankSize)) {
//                    int oilCount = Integer.valueOf(tankSize);
//                    sumCount += oilCount;
//                    Log.i(TAG, "handleMessage:内： " + oilCount);
//                }
//                Log.i(TAG, "handleMessage: ==>"+sumCount);
//
//                finishCount.setText(sumCount);
//
//            }
            sumAmountOil();

            finishAdapter.setonGetAddOilAmountListener(new OrderFinishAdapter.onGetAddOilAmountListener() {
                @Override
                public void getAddOilAmount(String oilFee) {
                    Toast.makeText(OrderFinishActivity.this, "加油量" + oilFee, Toast.LENGTH_SHORT).show();
                    sumAmountOil();
                }
            });

        }
    };


    public void sumAmountOil() {
        int childCount = receiveList.getCount();
        Log.i(TAG, "handleMessage: ---childCount>>>" + childCount);
        int sumCount = 0;
        for (int i = 0; i < childCount; i++) {
            String tankSize = finishAdapter.getItem(i).getTankSize();
            Log.i(TAG, "handleMessage: ==>加油量" + tankSize);
            if (!TextUtils.isEmpty(tankSize)) {
                int oilCount = Integer.valueOf(tankSize);
                sumCount += oilCount;
                Log.i(TAG, "handleMessage:内： " + oilCount);
            }
            Log.i(TAG, "handleMessage: ==>" + sumCount);

            finishCount.setText(String.valueOf(sumCount));

            int oilPrice = sumCount * 5;

            finishCountPaytypeFee.setText(String.valueOf(oilPrice) + "元");

        }
    }


    private ReceiveCarListDetailBean.BringBean bringBeanRes;
    private OrderFinishAdapter finishAdapter;
    private String dstate;

    @Override
    public void initContentView() {
        setContentView(R.layout.order_finish_layout);
    }

    @Override
    public void initView() {
        topBarFinishLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        List<OrderFinishBean> finishBeen = new ArrayList<>();
//
//        for (int i = 0; i < 3; i++) {
//            OrderFinishBean finishBeanItem = new OrderFinishBean();
//
//            finishBeanItem.setCarNum("车牌号—" + i);
//            finishBeanItem.setOilCount("加油量-" + i);
//
//            finishBeen.add(finishBeanItem);
//
//        }

//        OrderFinishAdapter finishAdapter = new OrderFinishAdapter(finishBeen, OrderFinishActivity.this);
//
//        receiveList.setAdapter(finishAdapter);


        itemId = getIntent().getExtras().getString("itemId");
        dstate = getIntent().getExtras().getString("dstate");
        Log.i(TAG, "initView: ++>" + itemId.toString() + ";dstate:" + dstate);

//        getContentResolver().notifyChange(Constant.UPDATE_RECEIVE, null);

        payNow.setOnClickListener(this);
        arrivalTime.setOnClickListener(this);
        finishCountMoneyRl.setOnClickListener(this);
//        carListDao = new CarListDao(OrderFinishActivity.this);
        if (Constant.ORDER_REFUSE.equals(dstate)) {
            bottomLl.setVisibility(View.GONE);
            noteReceive.setVisibility(View.GONE);
            topBarTitleTv.setText("拒接订单详情");
        }

        if (Constant.ORDER_FINISHED.equals(dstate)) {

            bottomLl.setVisibility(View.GONE);
            noteReceive.setFocusable(false);
            noteReceive.setEnabled(false);
            topBarTitleTv.setText("已完成订单详情");
        }

        if (Constant.ORDER_RECEIVED.equals(dstate)) {
            sendCarCv.setVisibility(View.GONE);
            topBarTitleTv.setText("已接单详情");

        }

        GCarListDao.getInstance().deleteAllData();

        getDataFromServer();
    }

    @Override
    public void initData() {

    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    public void getDataFromServer() {


        CarId carId = new CarId();
//        carId.setId("258cc612e58b480a9ce3b0494386a9db");
        carId.setId(itemId);

//        Gson gson = new Gson();
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(carId));
//        RequestBody requestBody = HttpManger.getHttpMangerInstance().getRequestBody(carId);
//
//        Call<ReceiveCarListDetailBean> call = HttpManger.getHttpMangerInstance().getService(Constant.BASEURL_CAOKUN).getDeliveryOrderDetail(requestBody);
//
//        call.enqueue(new Callback<ReceiveCarListDetailBean>() {
//            @Override
//            public void onResponse(Call<ReceiveCarListDetailBean> call, Response<ReceiveCarListDetailBean> response) {
//                Log.i(TAG, "onResponse: ==>>" + response.body());
//                bringBeanRes = response.body().getBring();
//
//                processData(bringBeanRes);
//
//            }
//
//            @Override
//            public void onFailure(Call<ReceiveCarListDetailBean> call, Throwable t) {
//                Log.i(TAG, "onFailure: ==>>" + t.getMessage());
//            }
//        });


    }


    private void processData(ReceiveCarListDetailBean.BringBean bringBean) {

        receiveBaseNo.setTitle("派单编号");
        receiveBaseNo.setContet(bringBean.getDeliveryCode());

        receiveToName.setTitle("派单人");
        receiveToName.setContet(bringBean.getSenderName());

        receiveNoTime.setTitle("派单时间");
        receiveNoTime.setContet(bringBean.getReceiveTime());

        receiveOrderNo.setTitle("订单编号");
        receiveOrderNo.setContet(bringBean.getIndentCode());

        receiveAboutTime.setTitle("预约时间");
        receiveAboutTime.setContet(bringBean.getEstimateTime());

        receiveAddress.setTitle("收货地址");
        receiveAddress.setContet(bringBean.getDeliveryAddress());

//        List<ReceiveCarListDetailBean.BringBean.CarListBean> carList = bringBean.getCarList();
        List<GCarListBean> carList = bringBean.getCarList();

        Message message = Message.obtain();
        message.what = 10;
        message.obj = carList;

        mHandler.sendMessage(message);


        senderCarNo.setTitle("车牌号");
        senderCarNo.setContet("加油车车牌号");

        senderName.setTitle("送货人");
        senderName.setContet(bringBean.getDeliveryName());

        senderTime.setTitle("预计送达时间");
        senderTime.setContet(bringBean.getEstimateTime());

        sendTimeTimer.setTitle("接单时间");
        sendTimeTimer.setContet(bringBean.getReceiveTime());

        orderReceive.setTitle("接单人");
        orderReceive.setContet(bringBean.getDeliveryName());

        arrivalTime.setTitle("到场确认时间");

        if (TextUtils.isEmpty(bringBean.getConfirmTime())) {
            arrivalTime.setContet("请选择时间");
        } else {
            arrivalTime.setContet(bringBean.getConfirmTime());
        }

        finishCount.setText(bringBean.getOilAmount());// 加油量
        finishCountFee.setText(bringBean.getOilCost());

        finishCountPiciFee.setText("加油批次...");

        finishCountPaytypeFee.setText(bringBean.getPayAmount());

        finishCountMoneyFee.setText(bringBean.getPayType());


//备注
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.pay_now:

                if (arrivalSure){



                String noteStr = noteReceive.getText().toString().trim();

                if (TextUtils.isEmpty(noteStr)) {
                    noteStr = "";
                }

                subMitDeliveryBean.setRamark(noteStr); // 备注

                subMitDeliveryBean.setCarList(finishAdapter.getCarListEdit()); // 设置 车辆列表

                Log.i(TAG, "onClick: 提交数据" + new Gson().toJson(subMitDeliveryBean));


                subMitInfo(subMitDeliveryBean);

                }else {

                    Toast.makeText(this, "请先确认到场", Toast.LENGTH_SHORT).show();
return;
                }

                break;

            case R.id.arrival_time:


            {
                DialogUtils.showDatePick(OrderFinishActivity.this, Gravity.BOTTOM, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDDHHMMSS, 0, new DialogUIDateTimeSaveListener() {
                    @Override
                    public void onSaveSelectedDate(int tag, String selectedDate) {

//                        subMitDeliveryBean.setFinishTime(selectedDate);
                        arrivalTime.setContet(selectedDate);


                    }
                }).show();
            }
            break;

            case R.id.finish_count_money_rl:
                showPayType();
                break;

            case R.id.pay_cancle:


                arrivalSure=true;


                break;


        }


    }
    boolean arrivalSure=false;
    private void subMitInfo(SubMitDeliveryBean subMitDeliveryBean) {


        RequestBody body = RequestBody.create(MediaType.parse(Constant.CONTENT_TYPE), new Gson().toJson(subMitDeliveryBean));
        Call<EmptyBringGetOilBean> call = HttpManger.getHttpMangerInstance().getServices().submitDeliveryOrder(body);

        call.enqueue(new Callback<EmptyBringGetOilBean>() {
            @Override
            public void onResponse(Call<EmptyBringGetOilBean> call, Response<EmptyBringGetOilBean> response) {

//                Log.i(TAG, "onResponse: " + response.body().toString().trim());

                EmptyBringGetOilBean body = response.body();
                if (body != null) {
                    if (body.isRes()) {
                        showDialog("提交订单成功");
                    } else {
                        showDialog("提交订单失败");
                    }
                }

            }

            @Override
            public void onFailure(Call<EmptyBringGetOilBean> call, Throwable t) {
                showDialog("提交订单失败");
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void showDialog(String msg) {
        //"订单提交成功"
        DialogUtils.showAlert(OrderFinishActivity.this, "提示", msg, "", "", "我知道了", "", true, new DialogUIListener() {
            @Override
            public void onPositive() {
//                showToast("onPositive");
                finish();

            }

            @Override
            public void onNegative() {
//                showToast("onNegative");
            }

        }).show();


    }

    public class CarId {

        public String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "CarId{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }


    public void showPayType() {

        /**
         * payType=0 预付款payType= 1 在线支付
         payType= 2 货到付款
         payType= 3 公司转账
         */

        List<TieBean> datas2 = new ArrayList<>();
//        datas2.add(new tiebean("支付宝"));
//        datas2.add(new tiebean("微信"));
//        datas2.add(new tiebean("第三方"));
//        datas2.add(new tiebean("银联"));
        datas2.add(new TieBean("预付款"));
        datas2.add(new TieBean("在线支付"));
        datas2.add(new TieBean("货到付款"));
        datas2.add(new TieBean("公司转账"));
        TieAdapter adapter = new TieAdapter(OrderFinishActivity.this, datas2);
        BuildBean buildBean = DialogUtils.showMdBottomSheet(OrderFinishActivity.this, true, "", datas2, "", 0, new DialogUIItemListener() {
            @Override
            public void onItemClick(CharSequence text, int position) {
//                Toast.makeText(UIUtils.getContext(), "++>" + text + "---" + position, Toast.LENGTH_SHORT).show();

//                addOrderBean.setPayType(String.valueOf(position)); // 根据后台 而定
//                payTypeSelectTv.setText(text);
                finishCountMoneyFee.setText(text);

            }
        });
        buildBean.mAdapter = adapter;
        buildBean.show();
    }


}
