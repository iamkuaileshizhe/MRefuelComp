package com.neocom.mobilerefueling.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.neocom.mobilerefueling.adapter.PaiSongDanJieDanAdapter;
import com.neocom.mobilerefueling.adapter.PaiSongDanShowAdapter;
import com.neocom.mobilerefueling.adapter.RecyclerAdapter;
import com.neocom.mobilerefueling.amap.AmapTTSController;
import com.neocom.mobilerefueling.bean.DeliveryOrderRespBean;
import com.neocom.mobilerefueling.bean.EmptyBringGetOilBean;
import com.neocom.mobilerefueling.bean.JiaYouJiRespBean;
import com.neocom.mobilerefueling.bean.OilRequestBean;
import com.neocom.mobilerefueling.bean.OilResponseBean;
import com.neocom.mobilerefueling.bean.PaiSongDanCommitBean;
import com.neocom.mobilerefueling.bean.PaisongDanItemBean;
import com.neocom.mobilerefueling.bean.TakeOrderBean;
import com.neocom.mobilerefueling.fragment.ChooseOilDataFragment;
import com.neocom.mobilerefueling.globle.Constant;
import com.neocom.mobilerefueling.net.HttpManger;
import com.neocom.mobilerefueling.utils.GetUserInfoUtils;
import com.neocom.mobilerefueling.utils.PointsConverToGD;
import com.neocom.mobilerefueling.utils.UIUtils;
import com.neocom.mobilerefueling.view.ListViewWithScroll;
import com.neocom.mobilerefueling.view.OrderConbindView;
import com.widget.jcdialog.DialogUtils;
import com.widget.jcdialog.listener.DialogUIDateTimeSaveListener;
import com.widget.jcdialog.listener.DialogUIListener;
import com.widget.jcdialog.widget.DateSelectorWheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/11/30.
 * <p>
 * 加油中的 界面 即是 已接单界面
 */

public class PaiSongDanJieDanUI extends BaseActivity implements View.OnClickListener, ChooseOilDataFragment.GetSelectedDataCallBack, AdapterView.OnItemLongClickListener, INaviInfoCallback {
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
    @BindView(R.id.add_car)
    ImageView addCar;
//    @BindView(R.id.receive_list)
//    ListViewWithScroll receiveList;


    @BindView(R.id.car_lv_rl)
    LinearLayout carLvRl;
    @BindView(R.id.zong_jiayou_liang)
    OrderConbindView zongJiayouLiang;
    @BindView(R.id.jiayou_feiyong)
    OrderConbindView jiayouFeiyong;
    @BindView(R.id.order_time)
    OrderConbindView orderTime;
    @BindView(R.id.jiayou_paytype)
    OrderConbindView jiayouPaytype;
    @BindView(R.id.send_time)
    TextView sendTime;
    @BindView(R.id.com_content_choose_time)
    TextView comContentChooseTime;
    @BindView(R.id.choose_time)
    RelativeLayout chooseTime;
    @BindView(R.id.note_receive)
    EditText noteReceive;
    @BindView(R.id.pay_now)
    Button payNow;
    @BindView(R.id.bottom_ll)
    LinearLayout bottomLl;
    Gson gson;
    @BindView(R.id.all_price_money)
    TextView allPriceMoney;

    @BindView(R.id.bottom_ll_yes_no)
    LinearLayout bottomLlYesNo;
    @BindView(R.id.agree_order)
    Button agreeOrder;
    @BindView(R.id.disagree_order)
    Button disagreeOrder;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    private String itemId;
    private String indentId;
    private String paiDanState;
    private AmapTTSController amapTTSController;

    public List<PaiSongDanCommitBean.CarListBean> addedToCommitCarList = new ArrayList<>();
    public List<JiaYouJiRespBean.BringBean> readyToAdd = new ArrayList<>();

    public static List<JiaYouJiRespBean.BringBean> showInDialogist = new ArrayList<>();

    public Handler mHandler;

    DeliveryOrderRespBean.BringBean mBringBean;

//    PaiSongDanJieDanAdapter jieDanAdapter;
    PaiSongDanCommitBean paiSongDanCommitBean;

    RecyclerAdapter recyclerJDAdapter;

    TakeOrderBean takeBean;

    @Override
    public void initContentView() {
        setContentView(R.layout.pai_song_jie_dan_recy_layout);
    }

    @Override
    public void initView() {

        itemId = getIntent().getExtras().getString("itemId");
        indentId = getIntent().getStringExtra("indentId");
        paiDanState = getIntent().getStringExtra("dstate");
        paiSongDanCommitBean = new PaiSongDanCommitBean();
        takeBean = new TakeOrderBean();
        gson = new Gson();
        topBarFinishLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addCar.setOnClickListener(this);
        jiayouPaytype.setOnClickListener(this);
        jiayouPaytype.setTitle("支付方式");
        jiayouPaytype.setContet("请选择支付方式 >");

//        receiveList.setOnItemLongClickListener(this);
        payNow.setOnClickListener(this);
        agreeOrder.setOnClickListener(this);
        disagreeOrder.setOnClickListener(this);
// SpeechUtils.getInstance(this).speakText();系统自带的语音播报
        amapTTSController = AmapTTSController.getInstance(getApplicationContext());
        amapTTSController.init();

        getDataFromServer();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerJDAdapter=new RecyclerAdapter(this, addedToCommitCarList);
        recyclerView.setAdapter(recyclerJDAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        jieDanAdapter = new PaiSongDanJieDanAdapter(this, addedToCommitCarList);
//        jieDanAdapter.setcalcuAmount(new PaiSongDanJieDanAdapter.calcuAmount() {
//            @Override
//            public void calAmount() {
//                calAllMoney();
//            }
//        });


//        recyclerView.setAdapter(new RecyclerAdapter(PaiSongDanJieDanUI.this,addedToCommitCarList));
        /****************************************************************************/
//        receiveList.setAdapter(jieDanAdapter);
        /****************************************************************************/

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
//                obtain.obj=bring;
//                obtain.what=101;

                switch (msg.what) {
                    case 101:

                        readyToAdd = (List<JiaYouJiRespBean.BringBean>) msg.obj;


                        if (readyToAdd.size() == 0) {
//                    直接 添加空条目
                            showLoadingDialog("数据获取中...");

//                    OilResponseBean


                            carNum carNum = new carNum();
                            carNum.setCarNum(GetUserInfoUtils.getUserInfo().getCarNum());

                            RequestBody requestBody = HttpManger.getHttpMangerInstance().getRequestBody(carNum);
                            HttpManger.getHttpMangerInstance().getServices().findOilTypeByCarNum(requestBody).enqueue(new retrofit2.Callback<PaisongDanItemBean>() {
                                @Override
                                public void onResponse(Call<PaisongDanItemBean> call, Response<PaisongDanItemBean> response) {
                                    disDialog();
                                    PaisongDanItemBean body = response.body();

                                    if (body != null) {
                                        PaisongDanItemBean.BringBean bring = body.getBring();

                                        if (bring != null) {

                                            parseItemBean(bring);


                                        } else {
                                            String message = body.getMessage();
                                            if (TextUtils.isEmpty(message)) {
                                                Toasty.error(PaiSongDanJieDanUI.this, "添加失败", Toast.LENGTH_SHORT, true).show();
                                            } else {
                                                Toasty.error(PaiSongDanJieDanUI.this, message, Toast.LENGTH_SHORT, true).show();
                                            }

                                            return;
                                        }

                                    } else {
                                        Toasty.error(PaiSongDanJieDanUI.this, "添加失败", Toast.LENGTH_SHORT, true).show();
                                        return;

                                    }

                                }

                                @Override
                                public void onFailure(Call<PaisongDanItemBean> call, Throwable t) {
                                    disDialog();
                                    Toasty.error(PaiSongDanJieDanUI.this, "添加失败", Toast.LENGTH_SHORT, true).show();
                                    return;
                                }
                            });

                        }
                        if (readyToAdd.size() == 1) {
//                    直接 添加进来 ==
                            JiaYouJiRespBean.BringBean jiaYouJibean = readyToAdd.get(0);
                            PaiSongDanCommitBean.CarListBean carListBean = new PaiSongDanCommitBean.CarListBean();
                            carListBean.setFinishTime(UIUtils.getCurrentTime());
                            carListBean.setOilType(jiaYouJibean.getOilType());
                            carListBean.setNationalStandard(jiaYouJibean.getNationalStandard());
                            carListBean.setTankSize(jiaYouJibean.getOilAmount());
                            carListBean.setOilBalance(jiaYouJibean.getOilPrice());
                            carListBean.setOilTotal(jiaYouJibean.getRealCost());
//                            jieDanAdapter.addOneItem(carListBean);


                            recyclerJDAdapter.addOneItem(carListBean);

                        }
                        if (readyToAdd.size() > 1) {
//                    弹框 让司机 选择  > 1


                            showInDialogist.clear();
                            showInDialogist.addAll(readyToAdd);

                            for (int i = 0; i < showInDialogist.size(); i++) {
                                showInDialogist.get(i).setChecked(false);
                            }

                            ChooseOilDataFragment dataFragment = new ChooseOilDataFragment();
                            dataFragment.show(getSupportFragmentManager(), "payDetailFragment");


                        }

                        break;


                    case 102:

//没有任何作用 只是为了 区分用

                        break;

                }

            }
        };


    }


    private void parseItemBean(final PaisongDanItemBean.BringBean bring) {

        if (mBringBean != null) {

            OilRequestBean oilRequestBean = new OilRequestBean();

            String areaCode = mBringBean.getAreaCode();

            oilRequestBean.setArea(areaCode);
            oilRequestBean.setNationalStandard(bring.getNationalStandard());
            oilRequestBean.setOilType(bring.getOilType());
            oilRequestBean.setTime(UIUtils.getCurrentTime());

            RequestBody requestBody = HttpManger.getHttpMangerInstance().getRequestBody(oilRequestBean);
            HttpManger.getHttpMangerInstance().getServices().findOilpriceByArea(requestBody).enqueue(new Callback<OilResponseBean>() {
                @Override
                public void onResponse(Call<OilResponseBean> call, Response<OilResponseBean> response) {
                    disDialog();
                    OilResponseBean body = response.body();

                    if (body != null) {

                        OilResponseBean.BringBean bodyBring = body.getBring();

                        if (bodyBring != null) {

                            String oilPrice = bodyBring.getOilPrice();

                            addToItem(bring, oilPrice);

                        } else {
                            disDialog();

                            String message = body.getMessage();

                            if (TextUtils.isEmpty(message)){
                                Toasty.error(PaiSongDanJieDanUI.this, "添加失败", Toast.LENGTH_SHORT, true).show();
                            }else {
                                Toasty.error(PaiSongDanJieDanUI.this, message, Toast.LENGTH_SHORT, true).show();
                            }

                            return;

                        }


                    } else {

                        disDialog();
                        Toasty.error(PaiSongDanJieDanUI.this, "添加失败", Toast.LENGTH_SHORT, true).show();
                        return;

                    }


                }

                @Override
                public void onFailure(Call<OilResponseBean> call, Throwable t) {
                    disDialog();
                    Toasty.error(PaiSongDanJieDanUI.this, "添加失败", Toast.LENGTH_SHORT, true).show();
                    return;
                }
            });


        } else {
            disDialog();
            Toasty.error(PaiSongDanJieDanUI.this, "添加失败", Toast.LENGTH_SHORT, true).show();
            return;
        }


    }


    public void addToItem(PaisongDanItemBean.BringBean bring, String oilPrice) {

        PaiSongDanCommitBean.CarListBean carListBean = new PaiSongDanCommitBean.CarListBean();
        carListBean.setFinishTime(UIUtils.getCurrentTime());
        carListBean.setOilType(bring.getOilType());
        carListBean.setNationalStandard(bring.getNationalStandard());
        carListBean.setOilBalance(oilPrice);

//        jieDanAdapter.addOneItem(carListBean);
        recyclerJDAdapter.addOneItem(carListBean);



    }

    public void addEmptyItem() {

        PaiSongDanCommitBean.CarListBean carListBean = new PaiSongDanCommitBean.CarListBean();
        carListBean.setFinishTime(UIUtils.getCurrentTime());
        carListBean.setOilType("");
        carListBean.setNationalStandard("");
        carListBean.setOilBalance("");

//        jieDanAdapter.addOneItem(carListBean);
        recyclerJDAdapter.addOneItem(carListBean);
    }


    /**
     * 获取两个List的不同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    private static List<JiaYouJiRespBean.BringBean> getDiffrent(List<JiaYouJiRespBean.BringBean> list1, List<JiaYouJiRespBean.BringBean> list2) {
        long st = System.nanoTime();
        List<JiaYouJiRespBean.BringBean> diff = new ArrayList<>();
        for (JiaYouJiRespBean.BringBean str : list1) {
            if (!list2.contains(str)) {
                diff.add(str);
            }
        }
        System.out.println("total times " + (System.nanoTime() - st));
        return diff;
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

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(carId));

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

        mBringBean = bringBean;
        final String deliveryAddress = bringBean.getDeliveryAddress();
        final String addressCoor = bringBean.getAddressCoor();
        String dstate = bringBean.getDstate();
        if (dstate.equals(Constant.JD_DAIJIEDAN)) {
//    只显示 是否接单
//    可选时间
//    隐匿加号
            bottomLlYesNo.setVisibility(View.VISIBLE);
            chooseTime.setVisibility(View.VISIBLE);

            chooseTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    {
                        DialogUtils.showDatePick(PaiSongDanJieDanUI.this, Gravity.CENTER, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDDHHMM, 0, new DialogUIDateTimeSaveListener() {
                            @Override
                            public void onSaveSelectedDate(int tag, String selectedDate) {

                                comContentChooseTime.setText(selectedDate);
                                takeBean.setEstimateArrivalTime(selectedDate);
                            }
                        }).show();
                    }


                }
            });


        }
        if (dstate.equals(Constant.JD_YIJIEDAN)) {
//    只显示 提交订单
            //    可选时间
//    显示加号
//            bottomLlYesNo.setVisibility(View.GONE);
            carLvRl.setVisibility(View.VISIBLE);
            addCar.setVisibility(View.VISIBLE);
            bottomLl.setVisibility(View.VISIBLE);

            peisongDizhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LatLng latLng;

                    if (TextUtils.isEmpty(addressCoor)) {
                        Toasty.error(PaiSongDanJieDanUI.this, "获取坐标点有误", Toast.LENGTH_SHORT, true).show();
                        return;
                    } else {
                        String[] split = addressCoor.split(",");
                        String jingDuStr = split[0];
                        String weiDuStr = split[1];

                        double jingDuDb = Double.parseDouble(jingDuStr);
                        double weiDuDb = Double.parseDouble(weiDuStr);

                        latLng = PointsConverToGD.convetToGD(PaiSongDanJieDanUI.this, new LatLng(weiDuDb, jingDuDb));

//                    Log.i(TAG, "onClick: 转转后坐标==》"+new Gson().toJson(latLng)+"；；qian"+addressCoor);
                    }

                    AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(null, null, new Poi(deliveryAddress, latLng, ""), AmapNaviType.DRIVER), PaiSongDanJieDanUI.this);


                }
            });


        }

        if (dstate.equals(Constant.JD_YIWANCHENG) || dstate.equals(Constant.JD_YIJUJUE)) {
//    不能选择时间
//    没有 是否接单 按钮
//    没有 提交按钮
//
//隐匿加号
            carLvRl.setVisibility(View.VISIBLE);

            List<DeliveryOrderRespBean.BringBean.CarListBean> carList = bringBean.getCarList();

            if (carList != null && carList.size() > 0) {

//                PaiSongDanShowAdapter showAdapter = new PaiSongDanShowAdapter(carList);
//                receiveList.setAdapter(showAdapter);
//                RecyclerAdapter recyclerAdapter=new RecyclerAdapter(carList);

                Log.i(TAG, "processData: 只展示 车辆");

            } else {
                carLvRl.setVisibility(View.GONE);
            }

            noteReceive.setFocusable(false);
            noteReceive.setFocusableInTouchMode(false);


        }


        orderNo.setTitle("派送单号");
        orderNo.setContet(bringBean.getIndentCode());
        orderTime.setTitle("下单时间");
        orderTime.setContet(bringBean.getOrderTime());

        caiyouLeixing.setTitle(bringBean.getNationalStandardName() + bringBean.getOilType());
        caiyouLeixing.setContet(bringBean.getOilAmount() + "L");
        peisongDizhi.setTitle("配送地址");
        peisongDizhi.setContet(bringBean.getDeliveryAddress());

        paidanBianhao.setTitle("派单编号");
        paidanBianhao.setContet(bringBean.getDeliveryCode());

        paidanRen.setTitle("派单人");
        paidanRen.setContet(bringBean.getSenderName());

        paidanDianhua.setTitle("派单人电话");
        paidanDianhua.setContet(bringBean.getSenderPhone());

        shouhuaRen.setTitle("收货人");
        shouhuaRen.setContet(bringBean.getReceiverName());
        shouhuaRenShoujihao.setTitle("收货人手机号");
        shouhuaRenShoujihao.setContet(bringBean.getOrderTel());

        zongJiayouLiang.setTitle("车牌号");
        zongJiayouLiang.setContet(bringBean.getCarNum());

        jiayouFeiyong.setTitle("送货人");
        jiayouFeiyong.setContet(bringBean.getDeliveryName());

        String remark = bringBean.getRemark();
        if (!TextUtils.isEmpty(remark)) {
            noteReceive.setText(remark);
        }

//        要提交的数据
        String u_user = bringBean.getU_user();
        if (TextUtils.isEmpty(u_user)) {
            paiSongDanCommitBean.setU_user("");
        } else {
            paiSongDanCommitBean.setU_user(u_user);
        }

        String remarkStr = noteReceive.getText().toString();

        if (TextUtils.isEmpty(remarkStr)) {

            paiSongDanCommitBean.setRemark("");

        } else {
            paiSongDanCommitBean.setRemark(remarkStr);
        }

        paiSongDanCommitBean.setId(bringBean.getId());


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
            case R.id.add_car:

//                Intent intent = new Intent(UIUtils.getContext(), GetJiaYouJiActivity.class);
//                startActivityForResult(intent, 1);

//                showLoadingDialog("获取加油机数据中...");
//
//                ChooseOilDataFragment dataFragment = new ChooseOilDataFragment();
//                dataFragment.show(getSupportFragmentManager(), "payDetailFragment");


//                getJiaYouDataFromServer();


                AddDataToItem();


                break;


            case R.id.jiayou_paytype:


                DialogUtils.showAlert(this, "选择支付方式", "", "", "", "现金支付", "在线支付", true, new DialogUIListener() {
                    @Override
                    public void onPositive() {
//                        deliverTime.setText("尽快派送");
//                        submitOrderBean.setDeliveryTime(null);
//                        submitOrderBean.setDelStatus("0");// 1 表示 有 预约时间 0 表示 没有预约时间

                        jiayouPaytype.setContet("现金支付");
                        paiSongDanCommitBean.setPayType("0");
                    }

                    @Override
                    public void onNegative() {

                        jiayouPaytype.setContet("在线支付");
                        paiSongDanCommitBean.setPayType("1");

                    }

                }).show();


                break;


            case R.id.pay_now:

                commitPaiSongDan();
                break;


//            agreeOrder.setOnClickListener(this);
//            disagreeOrder.setOnClickListener(this);


            case R.id.agree_order:


//                String timeChoose = comContentChooseTime.getText().toString().trim();
//
//                if (timeChoose.contains("选择时间")) {
//                    Toasty.info(PaiSongDanJieDanActivity.this, "请选择时间", Toast.LENGTH_SHORT, true).show();
//                    return;
//                }
//
                String arrivalTime = takeBean.getEstimateArrivalTime();

                if (arrivalTime == null) {

                    Toasty.info(PaiSongDanJieDanUI.this, "请选择时间", Toast.LENGTH_SHORT, true).show();
                    return;

                }


                String noteReceiveS = noteReceive.getText().toString().trim();

//                takeBean.setId(itemId);
                takeBean.setDstate("1"); // 同意
//                takeBean.setIndentId(indentId);
//                takeBean.setEstimateArrivalTime(timeChoose);

                if (TextUtils.isEmpty(noteReceiveS)) {
                    takeBean.setRemark("");
                } else {
                    takeBean.setRemark(noteReceiveS);
                }
                takeBean.setUserId(GetUserInfoUtils.getUserInfo().getUserId());


                commitAgreeAbleOrder(takeBean);
                break;
            case R.id.disagree_order:


                //                拒绝
                String noteReceiveStr = noteReceive.getText().toString().trim();

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


                commitAgreeAbleOrder(takeBean);
                break;


        }
    }

    private void AddDataToItem() {

        //                    直接 添加空条目
        showLoadingDialog("数据获取中...");

//                    OilResponseBean


        carNum carNum = new carNum();
        carNum.setCarNum(GetUserInfoUtils.getUserInfo().getCarNum());

        RequestBody requestBody = HttpManger.getHttpMangerInstance().getRequestBody(carNum);
        HttpManger.getHttpMangerInstance().getServices().findOilTypeByCarNum(requestBody).enqueue(new Callback<PaisongDanItemBean>() {
            @Override
            public void onResponse(Call<PaisongDanItemBean> call, Response<PaisongDanItemBean> response) {
                disDialog();
                PaisongDanItemBean body = response.body();

                if (body != null) {
                    PaisongDanItemBean.BringBean bring = body.getBring();

                    if (bring != null) {

                        parseItemBean(bring);


                    } else {

                        Toasty.error(PaiSongDanJieDanUI.this, "获取失败，请手动添加", Toast.LENGTH_SHORT, true).show();
                        addEmptyItem();
                        return;
                    }

                } else {
                    Toasty.error(PaiSongDanJieDanUI.this, "获取失败，请手动添加", Toast.LENGTH_SHORT, true).show();
                    addEmptyItem();
                    return;

                }

            }

            @Override
            public void onFailure(Call<PaisongDanItemBean> call, Throwable t) {
                disDialog();
                Toasty.error(PaiSongDanJieDanUI.this, "添加失败", Toast.LENGTH_SHORT, true).show();
                return;
            }
        });


    }


    public void commitPaiSongDan() {

//        paiSongDanCommitBean

        String payType = paiSongDanCommitBean.getPayType();

        if (payType == null) {
            Toasty.info(PaiSongDanJieDanUI.this, "请选择支付方式", Toast.LENGTH_SHORT, true).show();
            return;
        }
        showLoadingDialog("提交中...");


        Log.i(TAG, "commitPaiSongDan: 提交JSON" + new Gson().toJson(paiSongDanCommitBean));
        RequestBody requestBody = HttpManger.getHttpMangerInstance().getRequestBody(paiSongDanCommitBean);

        HttpManger.getHttpMangerInstance().getServices().submitDeliveryOrder(requestBody).enqueue(new Callback<EmptyBringGetOilBean>() {
            @Override
            public void onResponse(Call<EmptyBringGetOilBean> call, Response<EmptyBringGetOilBean> response) {
                disDialog();
                EmptyBringGetOilBean body = response.body();

                if (body != null) {
                    String bring = body.getBring();

                    if (bring != null) {

                        showMyDialog("提交成功");

                    }


                } else {
                    Toasty.info(PaiSongDanJieDanUI.this, "提交失败", Toast.LENGTH_SHORT, true).show();
                    return;
                }

            }

            @Override
            public void onFailure(Call<EmptyBringGetOilBean> call, Throwable t) {
                disDialog();
                Toasty.info(PaiSongDanJieDanUI.this, "提交失败", Toast.LENGTH_SHORT, true).show();
                return;

            }
        });

    }


    public void commitAgreeAbleOrder(TakeOrderBean takeBean) {
        showLoadingDialog("提交中...");
        String toJson = gson.toJson(takeBean);
        Log.i(TAG, "commitAgreeAbleOrder:提交的数据 " + toJson);

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
                        Toasty.info(PaiSongDanJieDanUI.this, "订单提交失败", Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                } else {
                    Toasty.info(PaiSongDanJieDanUI.this, "订单提交失败", Toast.LENGTH_SHORT, true).show();
                    return;
                }

            }

            @Override
            public void onFailure(Call<EmptyBringGetOilBean> call, Throwable t) {
                disDialog();
                Toasty.info(PaiSongDanJieDanUI.this, "订单提交失败", Toast.LENGTH_SHORT, true).show();
                Log.i(TAG, "onFailure: " + t.getMessage());
                return;

            }
        });
    }


//
//    public void callFragment() {
//        PayDetailFragment payDetailFragment = new PayDetailFragment();
//        payDetailFragment.show(getSupportFragmentManager(), "payDetailFragment");
//
//    }


    private void getJiaYouDataFromServer() {

        GetCarJiaYouJi getCarJiaYouJi = new GetCarJiaYouJi();
        getCarJiaYouJi.setCarNum(GetUserInfoUtils.getUserInfo().getCarNum());

        RequestBody requestBody = HttpManger.getHttpMangerInstance().getRequestBody(getCarJiaYouJi);

        HttpManger.getHttpMangerInstance().getServices().findFuelDiaryByCarNum(requestBody).enqueue(new Callback<JiaYouJiRespBean>() {
            @Override
            public void onResponse(Call<JiaYouJiRespBean> call, Response<JiaYouJiRespBean> response) {
                disDialog();
                JiaYouJiRespBean body = response.body();

                if (body != null) {
                    List<JiaYouJiRespBean.BringBean> bring = body.getBring();
                    if (bring != null) {
                        parseJiaYouBring(bring);
                    }
                }

            }

            @Override
            public void onFailure(Call<JiaYouJiRespBean> call, Throwable t) {
                disDialog();
                Toasty.error(PaiSongDanJieDanUI.this, "获取数据异常", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void parseJiaYouBring(List<JiaYouJiRespBean.BringBean> bring) {

        List<JiaYouJiRespBean.BringBean> bringNew = new ArrayList<>();
        Log.i(TAG, "parseJiaYouBring:之前 " + bring.get(0).toString());
        bring.removeAll(readyToAdd);
        Log.i(TAG, "parseJiaYouBring:之后" + bring.get(0).toString());

        for (int i = 0; i < bring.size(); i++) {

            JiaYouJiRespBean.BringBean bean = bring.get(i);

            for (int j = 0; j < readyToAdd.size(); j++) {
                JiaYouJiRespBean.BringBean beanAdd = readyToAdd.get(j);

                if (compareBean(bean, beanAdd)) {
                    Log.i(TAG, "parseJiaYouBring: 相同");
                } else {
                    Log.i(TAG, "parseJiaYouBring: 不相同");
                    bringNew.add(bean);
                }

//                if (bean.equals(beanAdd)) {
//                    Log.i(TAG, "parseJiaYouBring: 相同");
//                } else {
//                    Log.i(TAG, "parseJiaYouBring: 不相同");
//                    bringNew.add(bean);
//                }


            }

//            bringNew=bring;
        }

        if (readyToAdd.size() > 0) {
            Log.i(TAG, "parseJiaYouBring: ===");
            bring = bringNew;
        }


        Message obtain = Message.obtain();
        obtain.obj = bring;
        obtain.what = 101;
        mHandler.sendMessage(obtain);

    }


    public void calAllMoney() {
//        Double allPrice = 0.00;
//        List<PaiSongDanCommitBean.CarListBean> allItemData = jieDanAdapter.getAllItemData();
//
//        for (int i = 0; i < allItemData.size(); i++) {
//            Log.i(TAG, "calAllMoney: 每一个==》" + allItemData.toString());
//            String jinEMoney = allItemData.get(i).getJinEMoney();
//            Log.i(TAG, "calAllMoney: ==>每一笔=》" + jinEMoney);
//
//            if (!TextUtils.isEmpty(jinEMoney)) {
//                double v = Double.parseDouble(jinEMoney);
//                allPrice += v;
//            }
////            allPrice += v;
//
//        }
//        Log.i(TAG, "calAllMoney: 总金额是" + allPrice);
//
//        if ((allPrice != null) && (!allPrice.equals(""))) {
//            allPriceMoney.setText(String.valueOf(allPrice));
//            paiSongDanCommitBean.setPayAmount(String.valueOf(allPrice));
//        }
//        paiSongDanCommitBean.setCarList(allItemData);
        Toast.makeText(this, "计算总价", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "calAllMoney: 执行计算总价....");

    }


    public boolean compareBean(JiaYouJiRespBean.BringBean beanOri, JiaYouJiRespBean.BringBean beanNew) {

        boolean theSame = false;

        Log.i(TAG, "compareBean: 原始" + beanOri.toString() + ";hou;" + beanNew.toString());
        if (beanOri.toString().equals(beanNew.toString())) {
            theSame = true;
        } else {
            theSame = false;
        }
        Log.i(TAG, "compareBean:theSame=> " + theSame);
        return theSame;
    }

    @Override
    public void GetSelectedDataValue(List<JiaYouJiRespBean.BringBean> beanList) {

        if (beanList == null || beanList.size() == 0) {
            Toasty.info(PaiSongDanJieDanUI.this, "您未选择任何数据", Toast.LENGTH_SHORT, true).show();
            return;
        }
        for (int i = 0; i < beanList.size(); i++) {

            JiaYouJiRespBean.BringBean beanAdd = beanList.get(i);

            boolean checked = beanAdd.isChecked();
            if (checked) {
//                readyToAdd.add(beanAdd);
                Log.i(TAG, "GetSelectedDataValue: 进入" + i);
                PaiSongDanCommitBean.CarListBean carListBean = new PaiSongDanCommitBean.CarListBean();
                carListBean.setFinishTime(UIUtils.getCurrentTime());
                carListBean.setOilType(beanAdd.getOilType());
                carListBean.setNationalStandard(beanAdd.getNationalStandard());
                carListBean.setOilTotal(beanAdd.getOilAmount());
                carListBean.setOilBalance(beanAdd.getOilPrice());
                carListBean.setOilTotal(beanAdd.getRealCost());
//                jieDanAdapter.addOneItem(carListBean);

                recyclerJDAdapter.addOneItem(carListBean);

            }

        }

//        jieDanAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

        DialogUtils.showAlert(PaiSongDanJieDanUI.this, "", "确定要删除选中数据么", "", "", "确定", "取消", false, new DialogUIListener() {
            @Override
            public void onPositive() {

//                jieDanAdapter.deleteOneItem(i);
                recyclerJDAdapter.deleteOneItem(i);
            }

            @Override
            public void onNegative() {


            }

        }).show();

        return true;
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

    public class GetCarJiaYouJi {

        private String carNum;


        public String getCarNum() {
            return carNum;
        }

        public void setCarNum(String carNum) {
            this.carNum = carNum;
        }


        @Override
        public String toString() {
            return "GetCarJiaYouJi{" +
                    "carNum='" + carNum + '\'' +
                    '}';
        }
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


    public class carNum {
        private String carNum;

        public String getCarNum() {
            return carNum;
        }

        public void setCarNum(String carNum) {
            this.carNum = carNum;
        }

        @Override
        public String toString() {
            return "carNum{" +
                    "carNum='" + carNum + '\'' +
                    '}';
        }
    }

}
