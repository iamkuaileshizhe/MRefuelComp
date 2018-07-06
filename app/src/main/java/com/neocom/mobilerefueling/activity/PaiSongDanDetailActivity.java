package com.neocom.mobilerefueling.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
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

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.google.gson.Gson;
import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.amap.AmapTTSController;
import com.neocom.mobilerefueling.bean.DeliveryOrderRespBean;
import com.neocom.mobilerefueling.bean.EmptyBringGetOilBean;
import com.neocom.mobilerefueling.bean.TakeOrderBean;
import com.neocom.mobilerefueling.globle.Constant;
import com.neocom.mobilerefueling.net.HttpManger;
import com.neocom.mobilerefueling.utils.GetOrderStateUtil;
import com.neocom.mobilerefueling.utils.GetUserInfoUtils;
import com.neocom.mobilerefueling.utils.PointsConverToGD;
import com.neocom.mobilerefueling.utils.UIUtils;
import com.neocom.mobilerefueling.view.OrderConbindView;
import com.widget.jcdialog.DialogUtils;
import com.widget.jcdialog.listener.DialogUIDateTimeSaveListener;
import com.widget.jcdialog.listener.DialogUIListener;
import com.widget.jcdialog.widget.DateSelectorWheelView;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/11/30.
 */

public class PaiSongDanDetailActivity extends BaseActivity implements View.OnClickListener, INaviInfoCallback {
    @BindView(R.id.top_bar_finish_ll)
    LinearLayout topBarFinishLl;
    @BindView(R.id.top_bar_title_tv)
    TextView topBarTitleTv;
    @BindView(R.id.top_bar_ok_ll)
    LinearLayout topBarOkLl;
    @BindView(R.id.top_title_bar)
    LinearLayout topTitleBar;
    @BindView(R.id.order_no)
    OrderConbindView orderNo;
    @BindView(R.id.caiyou_leixing)
    OrderConbindView caiyouLeixing;
    @BindView(R.id.peisong_dizhi)
    OrderConbindView peisongDizhi;
    @BindView(R.id.paidan_bianhao)
    OrderConbindView paidanBianhao;
    @BindView(R.id.paidan_ren)
    OrderConbindView paidanRen;
    @BindView(R.id.paidan_dianhua)
    OrderConbindView paidanDianhua;
    @BindView(R.id.shouhua_ren)
    OrderConbindView shouhuaRen;
    @BindView(R.id.shouhua_ren_shoujihao)
    OrderConbindView shouhuaRenShoujihao;
    @BindView(R.id.zong_jiayou_liang)
    OrderConbindView zongJiayouLiang;
    @BindView(R.id.jiayou_feiyong)
    OrderConbindView jiayouFeiyong;
    @BindView(R.id.xiadan_time)
    OrderConbindView xiadanTime;
    @BindView(R.id.send_time)
    TextView sendTime;
    @BindView(R.id.com_content_choose_time)
    TextView comContentChooseTime;
    @BindView(R.id.choose_time)
    RelativeLayout chooseTime;
    @BindView(R.id.note_receive)
    EditText noteReceive;
    @BindView(R.id.pay_cancle)
    Button payCancle;
    @BindView(R.id.pay_now)
    Button payNow;
    @BindView(R.id.bottom_ll)
    LinearLayout bottomLl;
    Gson gson;
    private TakeOrderBean takeBean;
    private String itemId;
    private String indentId;
    private String paiDanState;

    private AmapTTSController amapTTSController;
    private String addressCoor; // 目的地坐标
    private String deliveryAddress;
    String mPhone;
    private String orderTel;
    private String senderPhone;

    @Override
    public void initContentView() {
        setContentView(R.layout.receive_layout);
    }

    @Override
    public void initView() {
        showLoadingDialog("加载中...");
        itemId = getIntent().getExtras().getString("itemId");
        indentId = getIntent().getStringExtra("indentId");
        paiDanState = getIntent().getStringExtra("dstate");
        gson = new Gson();
        topBarFinishLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        shouhuaRenShoujihao.setOnClickListener(this);
        paidanDianhua.setOnClickListener(this);

        if (itemId == null) {
            Toasty.error(this, "获取订单详情有误", Toast.LENGTH_SHORT, true).show();
            finish();
        }

        peisongDizhi.setOnClickListener(this);
        comContentChooseTime.setOnClickListener(this);
        payNow.setOnClickListener(this);
        payCancle.setOnClickListener(this);

//        if (paiDanState.equals("0")) {
//            bottomLl.setVisibility(View.VISIBLE);
//        }

        // SpeechUtils.getInstance(this).speakText();系统自带的语音播报
        amapTTSController = AmapTTSController.getInstance(getApplicationContext());
        amapTTSController.init();

        takeBean = new TakeOrderBean();
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
        carId.setId(itemId);

        RequestBody requestBody = HttpManger.getHttpMangerInstance().getRequestBody(carId);

//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(carId));

        Call<DeliveryOrderRespBean> call = HttpManger.getHttpMangerInstance().getServices().getDeliveryOrderById(requestBody);


        call.enqueue(new Callback<DeliveryOrderRespBean>() {
            @Override
            public void onResponse(Call<DeliveryOrderRespBean> call, Response<DeliveryOrderRespBean> response) {

//                DeliveryOrderRespBean.BringBean bringBean = response.body().getBring();
                disDialog();
                DeliveryOrderRespBean body = response.body();


                if (body != null) {
                    DeliveryOrderRespBean.BringBean bring = body.getBring();
                    if (bring != null) {
                        processData(bring);
                    }

                } else {
                    showMyDialog(response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<DeliveryOrderRespBean> call, Throwable t) {
                disDialog();
                Log.i(TAG, "onFailure: ==>>" + t.getMessage());
            }
        });


    }


    private void processData(DeliveryOrderRespBean.BringBean bringBean) {
        Log.i(TAG, "processData: ==>" + new Gson().toJson(bringBean));


        if (bringBean.getDstate().equals("0")) {
            bottomLl.setVisibility(View.VISIBLE);
        }
        orderNo.setTitle("订单号");
        orderNo.setContet(bringBean.getIndentCode());

        xiadanTime.setTitle("下单时间");
        xiadanTime.setContet(bringBean.getOrderTime());
        caiyouLeixing.setTitle(bringBean.getNationalStandardName() + bringBean.getOilType());
        caiyouLeixing.setContet(bringBean.getOilAmount() + "L");
        peisongDizhi.setTitle("配送地址");
        deliveryAddress = bringBean.getDeliveryAddress();
        peisongDizhi.setContet(deliveryAddress);

        paidanBianhao.setTitle("派单编号");
        paidanBianhao.setContet(bringBean.getDeliveryCode());

        paidanRen.setTitle("派单人");
        paidanRen.setContet(bringBean.getSenderName());

        paidanDianhua.setTitle("派单人电话");
        senderPhone = bringBean.getSenderPhone();
        paidanDianhua.setContet(senderPhone);

        shouhuaRen.setTitle("收货人");
        shouhuaRen.setContet(bringBean.getReceiverName());
        shouhuaRenShoujihao.setTitle("收货人手机号");

        orderTel = bringBean.getOrderTel();

        shouhuaRenShoujihao.setContet(orderTel);

        zongJiayouLiang.setTitle("车牌号");
        zongJiayouLiang.setContet(bringBean.getCarNum());

        jiayouFeiyong.setTitle("送货人");
        jiayouFeiyong.setContet(bringBean.getDeliveryName());

        addressCoor = bringBean.getAddressCoor();

        String dstate = bringBean.getDstate();

        if (dstate.equals("0")) {
            bottomLl.setVisibility(View.VISIBLE);
        }


        String commitId = bringBean.getId();
        takeBean.setId(commitId);
        String commitIndentId = bringBean.getIndentId();
        takeBean.setIndentId(commitIndentId);
    }

    public void showMyDialog(String msg) {
//"订单提交成功"

        DialogUtils.showAlert(this, "提示", msg, "", "", "我知道了", "", true, new DialogUIListener() {
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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.peisong_dizhi:
//                Toasty.info(this, "导航", Toast.LENGTH_SHORT).show();

//                addressCoor

                LatLng latLng;

                if (addressCoor == null) {
                    Toasty.error(PaiSongDanDetailActivity.this, "获取坐标点有误", Toast.LENGTH_SHORT, true).show();
                    return;
                } else {
                    String[] split = addressCoor.split(",");
                    String jingDuStr = split[0];
                    String weiDuStr = split[1];

                    double jingDuDb = Double.parseDouble(jingDuStr);
                    double weiDuDb = Double.parseDouble(weiDuStr);

                    latLng = PointsConverToGD.convetToGD(PaiSongDanDetailActivity.this, new LatLng(weiDuDb, jingDuDb));

//                    Log.i(TAG, "onClick: 转转后坐标==》"+new Gson().toJson(latLng)+"；；qian"+addressCoor);
                }

                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(null, null, new Poi(deliveryAddress, latLng, ""), AmapNaviType.DRIVER), PaiSongDanDetailActivity.this);

                break;
            case R.id.com_content_choose_time:
//            {
//                DialogUtils.showDatePick(this, Gravity.BOTTOM, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDDHHMMSS, 0, new DialogUIDateTimeSaveListener() {
//                    @Override
//                    public void onSaveSelectedDate(int tag, String selectedDate) {
//
//
//                        comContentChooseTime.setText(selectedDate);
//
//                    }
//                }).show();
//            }


            {
                DialogUtils.showDatePick(this, Gravity.CENTER, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDDHHMM, 0, new DialogUIDateTimeSaveListener() {
                    @Override
                    public void onSaveSelectedDate(int tag, String selectedDate) {

                        comContentChooseTime.setText(selectedDate);
                    }
                }).show();
            }

            break;

            case R.id.pay_now:


                String timeChoose = comContentChooseTime.getText().toString().trim();

                if (timeChoose.contains("选择时间")) {
                    Toasty.info(PaiSongDanDetailActivity.this, "请选择时间", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                showLoadingDialog("提交中...");
                String noteReceiveS = this.noteReceive.getText().toString().trim();

//                takeBean.setId(itemId);
                takeBean.setDstate("1"); // 同意
//                takeBean.setIndentId(indentId);
                takeBean.setEstimateArrivalTime(timeChoose);

                if (TextUtils.isEmpty(noteReceiveS)) {
                    takeBean.setRemark("");
                } else {
                    takeBean.setRemark(noteReceiveS);
                }
                takeBean.setUserId(GetUserInfoUtils.getUserInfo().getUserId());
                commitOrder(takeBean);

                break;
            case R.id.pay_cancle:

                //                拒绝
                String noteReceiveStr = this.noteReceive.getText().toString().trim();

                if (TextUtils.isEmpty(noteReceiveStr)) {
                    Toast.makeText(this, "请填写备注", Toast.LENGTH_SHORT).show();
                    return;
                }

// 拒绝 订单
//                takeBean.setId(itemId);
                takeBean.setDstate("2"); //拒绝
//                takeBean.setIndentId(indentId);
//                takeBean.setEstimateArrivalTime("");
                takeBean.setRemark(noteReceiveStr);
                takeBean.setUserId(GetUserInfoUtils.getUserInfo().getUserId());
                commitOrder(takeBean);

                break;


            case R.id.shouhua_ren_shoujihao:

                mPhone = orderTel;
                callPh();

                break;
            case R.id.paidan_dianhua:

                mPhone = senderPhone;
                callPh();

                break;


        }


    }


    public void callPh() {


        if (TextUtils.isEmpty(mPhone)) {
            Toasty.info(PaiSongDanDetailActivity.this, "号码格式不正确", Toast.LENGTH_SHORT, true).show();
            return;
        }


        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(PaiSongDanDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //请求拨打电话权限
                ActivityCompat.requestPermissions(PaiSongDanDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, Constant.REQUEST_CODE);

            } else {
                callPhone(mPhone);
            }

        } else {
            callPhone(mPhone);
        }


    }


    private void callPhone(String phoneNum) {
        //直接拨号
        Uri uri = Uri.parse("tel:" + phoneNum);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        //此处不判断就会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            startActivity(intent);
        }
    }


    /**
     * 请求权限的回调方法
     *
     * @param requestCode  请求码
     * @param permissions  请求的权限
     * @param grantResults 权限的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constant.REQUEST_CODE && PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callPhone(mPhone);
        } else {
            Toasty.info(this, "授权失败", Toast.LENGTH_SHORT, true).show();
        }
    }


    public void commitOrder(TakeOrderBean takeBean) {


        String toJson = gson.toJson(takeBean);

        RequestBody requestBody = RequestBody.create(MediaType.parse(Constant.CONTENT_TYPE), toJson);
        Call<EmptyBringGetOilBean> call = HttpManger.getHttpMangerInstance().getServices().takeDeliveryOrder(requestBody);

        call.enqueue(new Callback<EmptyBringGetOilBean>() {
            @Override
            public void onResponse(Call<EmptyBringGetOilBean> call, Response<EmptyBringGetOilBean> response) {

//                Log.i(TAG, "onResponse: " + response.body().toString().trim());
                disDialog();

                EmptyBringGetOilBean body = response.body();

                if (body != null) {
                    if (body.isRes()) {
                        showMyDialog("提交订单成功");
                    } else {
                        Toasty.info(PaiSongDanDetailActivity.this, "订单提交失败", Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                } else {
                    Toasty.info(PaiSongDanDetailActivity.this, "订单提交失败", Toast.LENGTH_SHORT, true).show();
                    return;
                }

            }

            @Override
            public void onFailure(Call<EmptyBringGetOilBean> call, Throwable t) {
                disDialog();
                Toasty.info(PaiSongDanDetailActivity.this, "订单提交失败", Toast.LENGTH_SHORT, true).show();
                Log.i(TAG, "onFailure: " + t.getMessage());
                return;

            }
        });
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onGetNavigationText(String s) {
        amapTTSController.onGetNavigationText(s);
    }

    @Override
    public void onStopSpeaking() {
        amapTTSController.stopSpeaking();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        amapTTSController.destroy();
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

}
