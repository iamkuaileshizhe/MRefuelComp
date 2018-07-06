package com.neocom.mobilerefueling.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.bean.PiciDetailBean;
import com.neocom.mobilerefueling.net.HttpManger;
import com.neocom.mobilerefueling.view.OrderConbindView;

import butterknife.BindView;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/10/25.
 */

public class CheckBatchDetailActivity extends BaseActivity {
    @BindView(R.id.top_bar_finish_ll)
    LinearLayout topBarFinishLl;
    @BindView(R.id.top_bar_title_tv)
    TextView topBarTitleTv;
    @BindView(R.id.top_title_bar)
    LinearLayout topTitleBar;
    @BindView(R.id.pici_no)
    OrderConbindView piciNo;
    @BindView(R.id.pici_oil_type)
    OrderConbindView piciOilType;
    @BindView(R.id.pici_amount_oil)
    OrderConbindView piciAmountOil;
    @BindView(R.id.pici_amount_oil_get)
    OrderConbindView piciAmountOilGet;
    @BindView(R.id.pici_provide)
    OrderConbindView piciProvide;
    @BindView(R.id.pici_state)
    OrderConbindView piciState;
    @BindView(R.id.pici_price)
    OrderConbindView piciPrice;
    @BindView(R.id.pici_payment)
    OrderConbindView piciPayment;
    @BindView(R.id.pici_amount_oil_get_time)
    OrderConbindView piciAmountOilGetTime;
    @BindView(R.id.pici_getter)
    OrderConbindView piciGetter;
    @BindView(R.id.pici_getter_phone)
    OrderConbindView piciGetterPhone;
    @BindView(R.id.pici_report)
    OrderConbindView piciReport;
    @BindView(R.id.more)
    ImageView more;
    @BindView(R.id.pici_remark)
    OrderConbindView piciRemark;
    private String batchId;

    @Override
    public void initContentView() {
        setContentView(R.layout.check_batch_detail_layout);
    }

    @Override
    public void initView() {

        Intent intent = getIntent();
        batchId = intent.getStringExtra("batchId");
        topBarFinishLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        piciNo.setTitle("批次号");
        piciOilType.setTitle("油品型号");
        piciAmountOil.setTitle("总油量");
        piciAmountOilGet.setTitle("已提油量");
        piciProvide.setTitle("供油商");
        piciState.setTitle("批次状态");
        piciPrice.setTitle("采购单价");
        piciPayment.setTitle("付款状况");
        piciAmountOilGetTime.setTitle("采购时间");
        piciGetter.setTitle("采购人");
        piciGetterPhone.setTitle("采购电话");
        piciReport.setTitle("采购报告");
        piciRemark.setTitle("备注信息");
    }

    @Override
    public void initData() {

    }


    @Override
    protected void onStart() {
        super.onStart();

        getDataFronServer();
    }

    private void getDataFronServer() {
        BatchId batchIdBean = new BatchId();
        batchIdBean.setId(batchId);
        RequestBody body = HttpManger.getHttpMangerInstance().getRequestBody(batchIdBean);

        HttpManger.getHttpMangerInstance().getServices().batchDetail(body).enqueue(new Callback<PiciDetailBean>() {
            @Override
            public void onResponse(Call<PiciDetailBean> call, Response<PiciDetailBean> response) {


                PiciDetailBean body = response.body();

                if (body != null) {

                    PiciDetailBean.BringBean bring = body.getBring();
                    if (bring!=null){

                        parseBringBean(bring);

                    }
                }

            }

            @Override
            public void onFailure(Call<PiciDetailBean> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void parseBringBean(PiciDetailBean.BringBean bring) {



        piciNo.setContet(bring.getBatchNum());
        piciOilType.setContet(bring.getFuelModelName());
        piciAmountOil.setContet(bring.getFuelTotal());
        piciAmountOilGet.setContet(bring.getFuelDone());
        piciProvide.setContet(bring.getSupplyName());
//        piciState.setTitle("批次状态");
        piciState.setContet("批次状态");
        piciPrice.setContet(bring.getPrice());
        piciPayment.setContet(bring.getPayStatus());
        piciAmountOilGetTime.setContet(bring.getBuyTime());
        piciGetter.setContet(bring.getBuyer());
        piciGetterPhone.setContet(bring.getBuyerTel());
        piciReport.setContet(bring.getFileList().get(0).getFileOldname()==null?"":bring.getFileList().get(0).getFileOldname());
        piciRemark.setContet(bring.getRemark());



    }

    public class BatchId {
        private String id;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        @Override
        public String toString() {
            return "batchId{" +
                    "id='" + id + '\'' +
                    '}';
        }

    }


}
