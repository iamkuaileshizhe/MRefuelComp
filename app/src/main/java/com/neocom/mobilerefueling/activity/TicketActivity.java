package com.neocom.mobilerefueling.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.adapter.TicketTypeAdapter;
import com.neocom.mobilerefueling.bean.TicketSubmitBean;
import com.neocom.mobilerefueling.utils.UIUtils;
import com.neocom.mobilerefueling.view.OrderComEdittextTicket;

import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by admin on 2017/9/27.
 * <p>
 * 这句话有两个读法：“你可知这百年，爱人只能陪半途。”
 * 更动人的读法是，“你可知这百年爱人，只能陪半途。”
 */

public class TicketActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ticket_choose_gv)
    GridView ticketChooseGv;
    @BindView(R.id.ticket_choose_gv_kind)
    GridView ticketChooseKind;
    @BindView(R.id.ticket_content_fl)
    FrameLayout ticketContentFl;
    @BindView(R.id.ticket_kind)
    CardView ticketKind;
    @BindView(R.id.pay_now)
    Button okBtn;
    @BindView(R.id.top_bar_finish_ll)
    LinearLayout topBarFinishLl;
    private TicketTypeAdapter ticketTypeAdapter;
    private TicketTypeAdapter ticketTypeKindAdapter;
    private String ticketTtypes[] = {"不开发票", "普通发票", "增值税发票"};
    private String ticketTypesKind[] = {"个人抬头", "单位抬头"};
    private View personalView;
    private View companyTicketView;
    private View zengZhiShuiTicketView;

    private int CURRENT_TICKET_STATE = 0;

    private int ORDINARY_TICKET_NO = 10;
    private int ORDINARY_TICKET = 100;
    private int ZENGZHISHUI_TICKET = 101;
    private int PERSON_TICKET = 200;
    private int COMPANY_TICKET = 201;
    private int CURRENT_TICKET_KIND = 1;
    private OrderComEdittextTicket personalTicketHead;
    private OrderComEdittextTicket personalTicketReceiveName;
    private OrderComEdittextTicket personalTicketReceiveAddress;
    private OrderComEdittextTicket personalTicketReceivePhone;
    private OrderComEdittextTicket companyTicketHead;
    private OrderComEdittextTicket companyTicketTaxNo;
    private OrderComEdittextTicket companyTicketReceiveName;
    private OrderComEdittextTicket companyTicketReceiveAddress;
    private OrderComEdittextTicket companyTicketReceiveTelphone;
    private OrderComEdittextTicket zengZhiShuiTicketCompName;
    private OrderComEdittextTicket zengZhiShuiTicketCompNo;
    private OrderComEdittextTicket zengZhiShuiTicketCompAddress;
    private OrderComEdittextTicket zengZhiShuiTicketCompTelphone;
    private OrderComEdittextTicket zengZhiShuiTicketCompBank;
    private OrderComEdittextTicket zengZhiShuiTicketCompBankAccount;
    private OrderComEdittextTicket zengZhiShuiTicketCompReceiveName;
    private OrderComEdittextTicket zengZhiShuiTicketCompReceiveAddress;
    private OrderComEdittextTicket zengZhiShuiTicketCompReceivePhone;

    @Override
    public void initContentView() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.ticket_choose_layout);
    }

    @Override
    public void initView() {

        initTicketType();
        initTicketKind();
        inFlateTicketView();
        okBtn.setOnClickListener(this);
        topBarFinishLl.setOnClickListener(this);
    }

    private void inFlateTicketView() {

        personalView = UIUtils.inflate(R.layout.personal_layout);
        companyTicketView = UIUtils.inflate(R.layout.company_ticket_layout);
        zengZhiShuiTicketView = UIUtils.inflate(R.layout.zengzhihshui_ticket_layout);
        initPersonTicketView();
        initCompanyTicketView();
        initZengZhiShuiTicketView();
    }

    private void initPersonTicketView() {
        personalTicketHead = personalView.findViewById(R.id.ticket_head);
        personalTicketReceiveName = personalView.findViewById(R.id.ticket_receive_name);
        personalTicketReceiveAddress = personalView.findViewById(R.id.ticket_receive_address);
        personalTicketReceivePhone = personalView.findViewById(R.id.ticket_receive_phone);
        personalTicketHead.setEditTitle("发票抬头");
        personalTicketReceiveName.setEditTitle("收票人姓名");
        personalTicketReceiveAddress.setEditTitle("收票人地址");
        personalTicketReceivePhone.setEditTitle("收票人电话");

    }

    public TicketSubmitBean getPersonTicketContent() {
        TicketSubmitBean ticketSubmitBean = new TicketSubmitBean();
        ticketSubmitBean.setInvoiceType("0");//普通发票
        String personalTicketHeadStr = personalTicketHead.getEditContent();
        if (TextUtils.isEmpty(personalTicketHeadStr)) {
            Toast.makeText(this, "请输入发票抬头", Toast.LENGTH_SHORT).show();
            return null;
        }
        ticketSubmitBean.setTitle(personalTicketHeadStr);

        String personalTicketReceiveNameStr = personalTicketReceiveName.getEditContent();

        if (personalTicketReceiveNameStr == null) {
            Toast.makeText(this, "请输入收票人姓名", Toast.LENGTH_SHORT).show();
            return null;
        }

        ticketSubmitBean.setInvoiceName(personalTicketReceiveNameStr);

        String personalTicketReceiveAddressStr = personalTicketReceiveAddress.getEditContent();

        if (personalTicketReceiveAddressStr == null) {
            Toast.makeText(this, "请输入收票人地址", Toast.LENGTH_SHORT).show();
            return null;
        }

        ticketSubmitBean.setInvAddress(personalTicketReceiveAddressStr);

        String personalTicketReceivePhoneStr = personalTicketReceivePhone.getEditContent();

        if (personalTicketReceivePhoneStr == null) {
            Toast.makeText(this, "请输入收票人电话", Toast.LENGTH_SHORT).show();
            return null;
        }

        ticketSubmitBean.setInvtelphone(personalTicketReceivePhoneStr);
        return ticketSubmitBean;
    }


    private void initCompanyTicketView() {

        companyTicketHead = companyTicketView.findViewById(R.id.cop_ticket_head);
        companyTicketTaxNo = companyTicketView.findViewById(R.id.cop_ticket_tax_no);
        companyTicketReceiveName = companyTicketView.findViewById(R.id.cop_ticket_receive_name);
        companyTicketReceiveAddress = companyTicketView.findViewById(R.id.cop_ticket_receive_address);
        companyTicketReceiveTelphone = companyTicketView.findViewById(R.id.cop_ticket_receive_telphone);
        companyTicketHead.setEditTitle("发票抬头");
        companyTicketTaxNo.setEditTitle("纳税人识别码");
        companyTicketReceiveName.setEditTitle("收票人姓名");
        companyTicketReceiveAddress.setEditTitle("收票人地址");
        companyTicketReceiveTelphone.setEditTitle("收票人电话");

    }


    public TicketSubmitBean getCompanyTicketContent() {

        TicketSubmitBean ticketSubmitBean = new TicketSubmitBean();
        ticketSubmitBean.setInvoiceType("1");//电子发票

        String companyTicketHeadStr = companyTicketHead.getEditContent();
        if (companyTicketHeadStr == null) {
            Toast.makeText(this, "请输入发票抬头", Toast.LENGTH_SHORT).show();
            return null;
        }
        ticketSubmitBean.setTitle(companyTicketHeadStr);

        String companyTicketTaxNoStr = companyTicketTaxNo.getEditContent();
        if (companyTicketTaxNoStr == null) {
            Toast.makeText(this, "请输入纳税人识别码", Toast.LENGTH_SHORT).show();
            return null;
        }
        ticketSubmitBean.setTaxCode(companyTicketTaxNoStr);

        String companyTicketReceiveNameStr = companyTicketReceiveName.getEditContent();

        if (companyTicketReceiveNameStr == null) {
            Toast.makeText(this, "请输入收票人姓名", Toast.LENGTH_SHORT).show();
            return null;
        }
        ticketSubmitBean.setInvoiceName(companyTicketReceiveNameStr);


        String companyTicketReceiveAddressStr = companyTicketReceiveAddress.getEditContent();

        if (companyTicketReceiveAddressStr == null) {
            Toast.makeText(this, "请输入收票人地址", Toast.LENGTH_SHORT).show();
            return null;
        }

        ticketSubmitBean.setInvAddress(companyTicketReceiveAddressStr);

        String companyTicketReceiveTelphoneStr = companyTicketReceiveTelphone.getEditContent();

        if (companyTicketReceiveTelphoneStr == null) {
            Toast.makeText(this, "请输入收票人电话", Toast.LENGTH_SHORT).show();
            return null;
        }

        ticketSubmitBean.setInvtelphone(companyTicketReceiveTelphoneStr);
        return ticketSubmitBean;
    }


    private void initZengZhiShuiTicketView() {

        zengZhiShuiTicketCompName = zengZhiShuiTicketView.findViewById(R.id.cop_ticket_comp_name);
        zengZhiShuiTicketCompNo = zengZhiShuiTicketView.findViewById(R.id.cop_ticket_comp_no);
        zengZhiShuiTicketCompAddress = zengZhiShuiTicketView.findViewById(R.id.cop_ticket_comp_address);
        zengZhiShuiTicketCompTelphone = zengZhiShuiTicketView.findViewById(R.id.cop_ticket_comp_telphone);
        zengZhiShuiTicketCompBank = zengZhiShuiTicketView.findViewById(R.id.cop_ticket_comp_bank);
        zengZhiShuiTicketCompBankAccount = zengZhiShuiTicketView.findViewById(R.id.cop_ticket_comp_bank_account);
        zengZhiShuiTicketCompReceiveName = zengZhiShuiTicketView.findViewById(R.id.cop_ticket_comp_receive_name);
        zengZhiShuiTicketCompReceiveAddress = zengZhiShuiTicketView.findViewById(R.id.cop_ticket_comp_receive_address);
        zengZhiShuiTicketCompReceivePhone = zengZhiShuiTicketView.findViewById(R.id.cop_ticket_comp_receive_phone);

        zengZhiShuiTicketCompName.setEditTitle("公司名称");
        zengZhiShuiTicketCompNo.setEditTitle("纳税人识别码");
        zengZhiShuiTicketCompAddress.setEditTitle("注册地址");
        zengZhiShuiTicketCompTelphone.setEditTitle("注册电话");
        zengZhiShuiTicketCompBank.setEditTitle("开户银行");
        zengZhiShuiTicketCompBankAccount.setEditTitle("开户账号");
        zengZhiShuiTicketCompReceiveName.setEditTitle("收票人姓名");
        zengZhiShuiTicketCompReceiveAddress.setEditTitle("收票人地址");
        zengZhiShuiTicketCompReceivePhone.setEditTitle("收票人电话");
    }


    public TicketSubmitBean getZengZhiShuiTicketContent() {

        TicketSubmitBean ticketSubmitBean = new TicketSubmitBean();
        ticketSubmitBean.setInvoiceType("2");//增值税发票

        String zengZhiShuiTicketCompNameStr = zengZhiShuiTicketCompName.getEditContent();
        if (zengZhiShuiTicketCompNameStr == null) {
            Toast.makeText(this, "请输入公司的名称", Toast.LENGTH_SHORT).show();
            return null;
        }
        ticketSubmitBean.setCompanyName(zengZhiShuiTicketCompNameStr);

        String zengZhiShuiTicketCompNoStr = zengZhiShuiTicketCompNo.getEditContent();

        if (zengZhiShuiTicketCompNoStr == null) {
            Toast.makeText(this, "请输入纳税人识别码", Toast.LENGTH_SHORT).show();
            return null;
        }
        ticketSubmitBean.setTaxCode(zengZhiShuiTicketCompNoStr);


        String zengZhiShuiTicketCompAddressStr = zengZhiShuiTicketCompAddress.getEditContent();
        if (zengZhiShuiTicketCompAddressStr == null) {
            Toast.makeText(this, "请输入注册地址", Toast.LENGTH_SHORT).show();
            return null;
        }
        ticketSubmitBean.setInvAddress(zengZhiShuiTicketCompAddressStr);

        String zengZhiShuiTicketCompTelphoneStr = zengZhiShuiTicketCompTelphone.getEditContent();
        if (zengZhiShuiTicketCompTelphoneStr == null) {
            Toast.makeText(this, "请输入注册电话", Toast.LENGTH_SHORT).show();
            return null;
        }
        ticketSubmitBean.setCompanyPhone(zengZhiShuiTicketCompTelphoneStr);

        String zengZhiShuiTicketCompBankStr = zengZhiShuiTicketCompBank.getEditContent();
        if (zengZhiShuiTicketCompBankStr == null) {
            Toast.makeText(this, "请输入开户行", Toast.LENGTH_SHORT).show();
            return null;
        }
        ticketSubmitBean.setBankName(zengZhiShuiTicketCompBankStr);

        String zengZhiShuiTicketCompReceiveNameStr = zengZhiShuiTicketCompReceiveName.getEditContent();
        if (zengZhiShuiTicketCompReceiveNameStr == null) {
            Toast.makeText(this, "请输入收票人姓名", Toast.LENGTH_SHORT).show();
            return null;
        }
        ticketSubmitBean.setInvoiceName(zengZhiShuiTicketCompReceiveNameStr);

        String zengZhiShuiTicketCompReceiveAddressStr = zengZhiShuiTicketCompReceiveAddress.getEditContent();
        if (zengZhiShuiTicketCompReceiveAddressStr == null) {
            Toast.makeText(this, "请输入收票人地址", Toast.LENGTH_SHORT).show();
            return null;
        }
        ticketSubmitBean.setInvAddress(zengZhiShuiTicketCompReceiveAddressStr);

        String zengZhiShuiTicketCompReceivePhoneStr = zengZhiShuiTicketCompReceivePhone.getEditContent();
        if (zengZhiShuiTicketCompReceivePhoneStr == null) {
            Toast.makeText(this, "请输入收票人电话", Toast.LENGTH_SHORT).show();
            return null;
        }

        return ticketSubmitBean;
    }

    private void initTicketKind() {
        ticketTypeKindAdapter = new TicketTypeAdapter(this, Arrays.asList(ticketTypesKind));
        ticketChooseKind.setSelector(new ColorDrawable(Color.TRANSPARENT));
        ticketChooseKind.setAdapter(ticketTypeKindAdapter);
        ticketChooseKind.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (ticketTypeAdapter.getCheckedItem() == -1) {
                    Toast.makeText(TicketActivity.this, "请选择发票类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ticketTypeAdapter.getCheckedItem() == 0) {
                    Toast.makeText(TicketActivity.this, "您已选择不开发票", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (i == 0) {
//                    ticketContentFl.removeAllViews();
//                    ticketContentFl.addView(personalView);
                    contentAddView(personalView);
                    CURRENT_TICKET_KIND = PERSON_TICKET;

                }
                if (i == 1) {
//                    ticketContentFl.removeAllViews();
//                    ticketContentFl.addView(companyTicketView);
                    contentAddView(companyTicketView);
                    CURRENT_TICKET_KIND = COMPANY_TICKET;
                }

                ticketTypeKindAdapter.setCheckItem(i);
//                Toast.makeText(TicketActivity.this, "点击啦啦啦" + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void contentAddView(View view) {
        ticketContentFl.removeAllViews();
        ticketContentFl.addView(view);
    }


    private void initTicketType() {
        ticketTypeAdapter = new TicketTypeAdapter(this, Arrays.asList(ticketTtypes));
        ticketChooseGv.setSelector(new ColorDrawable(Color.TRANSPARENT));
        ticketChooseGv.setAdapter(ticketTypeAdapter);
        ticketChooseGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ticketTypeAdapter.setCheckItem(i);

                if (i == 0) {
                    CURRENT_TICKET_STATE = ORDINARY_TICKET_NO;
                    ticketTypeKindAdapter.setCheckItem(-1);
                    ticketContentFl.removeAllViews();
                    if (ticketKind.getVisibility() == View.GONE) {
                        ticketKind.setVisibility(View.VISIBLE);
                    }

                }

                if (i == 1) {
                    CURRENT_TICKET_STATE = ORDINARY_TICKET;
                    if (ticketKind.getVisibility() == View.GONE) {
                        ticketKind.setVisibility(View.VISIBLE);
                        ticketContentFl.removeAllViews();
                    }

                }

                if (i == 2) {
                    CURRENT_TICKET_STATE = ZENGZHISHUI_TICKET;
                    ticketKind.setVisibility(View.GONE);
//                    ticketContentFl.removeAllViews();
//                    ticketContentFl.addView(personalView);
                    contentAddView(zengZhiShuiTicketView);

                }
//                Toast.makeText(TicketActivity.this, "点击" + i, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.pay_now:

                TicketSubmitBean ticketContent = null;

                Log.i(TAG, "onClick: CURRENT_TICKET_STATE==》" + CURRENT_TICKET_STATE + ";CURRENT_TICKET_KIND;" + CURRENT_TICKET_KIND);
                if (CURRENT_TICKET_STATE == ORDINARY_TICKET_NO) {
//                    Toast.makeText(this, "不开发票", Toast.LENGTH_SHORT).show();
                }
                if (CURRENT_TICKET_STATE == ORDINARY_TICKET) {
//                    Toast.makeText(this, "普通发票", Toast.LENGTH_SHORT).show();
                    if (CURRENT_TICKET_KIND == PERSON_TICKET) {

                        ticketContent = getPersonTicketContent();

//                        Toast.makeText(this, "个人抬头", Toast.LENGTH_SHORT).show();
                    } else if (CURRENT_TICKET_KIND == COMPANY_TICKET) {
//                        Toast.makeText(this, "单位抬头", Toast.LENGTH_SHORT).show();
                        ticketContent = getCompanyTicketContent();
                    }
                }
                if (CURRENT_TICKET_STATE == ZENGZHISHUI_TICKET) {
//                    Toast.makeText(this, "增值税发票", Toast.LENGTH_SHORT).show();
                    ticketContent = getZengZhiShuiTicketContent();
                }

                Log.i(TAG, "onClick: =发票信息是=>>" + new Gson().toJson(ticketContent));
                String ticketJson = new Gson().toJson(ticketContent);
                Intent intent = new Intent();
                intent.putExtra("ticketJsonData", ticketJson);

                setResult(12, intent);
                finish();
                break;


            case R.id.top_bar_finish_ll:
                finish();
                break;

        }
    }

    @Override
    public void initData() {

    }
}
