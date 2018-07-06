package com.neocom.mobilerefueling.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.bean.ReceiverOrderBeanBring;
import com.neocom.mobilerefueling.globle.Constant;
import com.neocom.mobilerefueling.net.HttpManger;
import com.neocom.mobilerefueling.utils.GetUserInfoUtils;
import com.neocom.mobilerefueling.utils.UIUtils;
import com.neocom.mobilerefueling.view.ListViewWithScroll;
import com.neocom.mobilerefueling.view.OrderConbindView;
import com.neocom.mobilerefueling.view.TopTitleBar;

import butterknife.BindView;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by admin on 2017/8/24.
 */

public class ReceiveFragment extends BaseFragment {


    @BindView(R.id.order_detail_tb)
    TopTitleBar orderDetailTb;
    @BindView(R.id.order_state_tv)
    TextView orderStateTv;
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
    @BindView(R.id.receive_send_car_no)
    OrderConbindView receiveSendCarNo;
    @BindView(R.id.receive_send_car_name)
    OrderConbindView receiveSendCarName;
    @BindView(R.id.send_time)
    TextView sendTime;
    @BindView(R.id.com_content_choose_time)
    TextView comContentChooseTime;
    @BindView(R.id.pay_cancle)
    Button payCancle;
    @BindView(R.id.pay_now)
    Button payNow;
    @BindView(R.id.bottom_ll)
    LinearLayout bottomLl;
    Unbinder unbinder;
    private View view;

    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = UIUtils.inflate(R.layout.receive_layout);
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        carNumBean carNumBean = new carNumBean();
        carNumBean.setCarNum(GetUserInfoUtils.getUserInfo().getCarNum());

        Gson gson = new Gson();
        String carNumJson = gson.toJson(carNumBean);


        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), carNumJson);

        Call<ReceiverOrderBeanBring> call = HttpManger.getHttpMangerInstance().getServices().deliveryOrderList(requestBody);
        call.enqueue(new Callback<ReceiverOrderBeanBring>() {
            @Override
            public void onResponse(Call<ReceiverOrderBeanBring> call, Response<ReceiverOrderBeanBring> response) {
                Log.i(TAG, "onResponse: =====");
            }

            @Override
            public void onFailure(Call<ReceiverOrderBeanBring> call, Throwable t) {
                Log.i(TAG, "onFailure: ==>>" + t.getMessage());
            }
        });


    }

    public class carNumBean {
        public String carNum;

        public String getCarNum() {
            return carNum;
        }

        public void setCarNum(String carNum) {
            this.carNum = carNum;
        }

        @Override
        public String toString() {
            return "carNumBean{" +
                    "carNum='" + carNum + '\'' +
                    '}';
        }
    }

}
