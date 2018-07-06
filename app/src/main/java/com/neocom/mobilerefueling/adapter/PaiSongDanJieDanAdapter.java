package com.neocom.mobilerefueling.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.activity.PaiSongDanJieDanActivity;
import com.neocom.mobilerefueling.bean.CarNumRespInfoBean;
import com.neocom.mobilerefueling.bean.OilLXGBBean;
import com.neocom.mobilerefueling.bean.OilRequestBean;
import com.neocom.mobilerefueling.bean.OilResponseBean;
import com.neocom.mobilerefueling.bean.PaiSongDanCommitBean;
import com.neocom.mobilerefueling.net.HttpManger;
import com.neocom.mobilerefueling.utils.GetOrderStateUtil;
import com.neocom.mobilerefueling.utils.UIUtils;
import com.neocom.mobilerefueling.view.OrderConbindView;
import com.widget.jcdialog.DialogUtils;
import com.widget.jcdialog.listener.DialogUIDateTimeSaveListener;
import com.widget.jcdialog.widget.DateSelectorWheelView;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.neocom.mobilerefueling.activity.PaiSongDanJieDanActivity.addedToCommitCarList;
import static com.neocom.mobilerefueling.activity.PaiSongDanJieDanActivity.mBringBean;
import static com.neocom.mobilerefueling.activity.PaiSongDanJieDanActivity.oilGB;
import static com.neocom.mobilerefueling.activity.PaiSongDanJieDanActivity.oilType;

/**
 * Created by admin on 2017/12/7.
 */

public class PaiSongDanJieDanAdapter extends BaseAdapter {

    private Context context;
    private final int YPCHOOSE = 1;
    private final int GBCHOOSE = 2;

    //    List<PaiSongDanCommitBean.CarListBean> carListBeen;
//    PaiSongDanJieDanActivity.addedToCommitCarList;
    public PaiSongDanJieDanAdapter(Context context) {

        this.context = context;
    }

//    public PaiSongDanJieDanAdapter(Context context, List<PaiSongDanCommitBean.CarListBean> carListBeen) {
//        this.context = context;
//        this.carListBeen = carListBeen;
//    }

    public void addOneItem(PaiSongDanCommitBean.CarListBean beanItem) {

        if (beanItem != null) {
//            carListBeen.add(beanItem);
            PaiSongDanJieDanActivity.addedToCommitCarList.add(beanItem);
            notifyDataSetChanged();
        }
    }


    //    添加 列表数据
    public void addList(List<PaiSongDanCommitBean.CarListBean> carListdata) {
        if (carListdata != null) {
//            carListBeen.addAll(carListdata);
            PaiSongDanJieDanActivity.addedToCommitCarList.addAll(carListdata);

            notifyDataSetChanged();
        }
    }
// 删除一条数据

    public void deleteOneItem(int position) {
//        carListBeen.remove(position);
        PaiSongDanJieDanActivity.addedToCommitCarList.remove(position);
        notifyDataSetChanged();
    }


    public List<PaiSongDanCommitBean.CarListBean> getAllItemData() {

        return PaiSongDanJieDanActivity.addedToCommitCarList;
    }

    @Override
    public int getCount() {
        return PaiSongDanJieDanActivity.addedToCommitCarList == null ? 0 : PaiSongDanJieDanActivity.addedToCommitCarList.size();
    }

    @Override
    public PaiSongDanCommitBean.CarListBean getItem(int i) {
//        return carListBeen.get(i);
        return PaiSongDanJieDanActivity.addedToCommitCarList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


//        根据 position 和 tag 进行 判断

        final ViewHolder holder;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.paisongdan_item_data_layout);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        PaiSongDanCommitBean.CarListBean carListBean = carListBeen.get(position);
        PaiSongDanCommitBean.CarListBean carListBean = PaiSongDanJieDanActivity.addedToCommitCarList.get(position);
        holder.itemChepaihao.setText(carListBean.getVehicleCode());
        holder.itemChepaihao.setTag(position);
        holder.itemLianxiren.setText(carListBean.getPName());
        holder.itemLianxiren.setTag(position);
        holder.itemLianxidianhua.setText(carListBean.getTelphone());
        holder.itemLianxidianhua.setTag(position);
        holder.itemFinishTime.setText(carListBean.getFinishTime());
        holder.itemYoupinleixing.setText(GetOrderStateUtil.getOilType(carListBean.getOilType()));
        holder.itemGuobiao.setText(carListBean.getNationalStandard());
        holder.itemJiayouliang.setText(carListBean.getOilTotal());
        holder.itemJiayouliang.setTag(position);
        holder.itemDanjia.setTitle("单价");
        holder.itemDanjia.setContet(carListBean.getOilBalance());
        holder.itemFee.setTitle("本次费用(元)");
//        holder.itemFee.setContet(carListBean.getJinEMoney());
        holder.itemFee.setContet(carListBean.getOilTotal());
        holder.itemFee.setTag(position);









        if (TextUtils.isEmpty(carListBean.getFinishTime())) {
            holder.itemFinishTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    {
                        DialogUtils.showDatePick(context, Gravity.CENTER, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDDHHMM, 0, new DialogUIDateTimeSaveListener() {
                            @Override
                            public void onSaveSelectedDate(int tag, String selectedDate) {

                                holder.itemFinishTime.setText(selectedDate);

                            }
                        }).show();
                    }

                }
            });

        } else {
            holder.itemFinishTime.setText(carListBean.getFinishTime());
        }


        if (TextUtils.isEmpty(carListBean.getOilType())) {

            holder.itemYoupinleixing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showPopItem(holder.itemYoupinleixing, YPCHOOSE, oilType, position,holder.itemDanjia, holder.itemFee,holder.itemJiayouliang);

                }
            });

        } else {
            holder.itemYoupinleixing.setText(GetOrderStateUtil.getOilType(carListBean.getOilType()));
        }
//        holder.itemGuobiao.setText(carListBean.getNationalStandard());


        if (TextUtils.isEmpty(carListBean.getNationalStandard())) {

            holder.itemGuobiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopItem(holder.itemGuobiao, GBCHOOSE, oilGB, position,holder.itemDanjia, holder.itemFee,holder.itemJiayouliang);
                }
            });

        } else {


        }


//        holder.introDuce.setText("第"+(position+1)+"辆车加油数据");
//        holder.itemJiayouliang.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
////                String danJiaStr = holder.itemDanjia.getContent();
////                String shuLiangStr = holder.itemJiayouliang.getText().toString();
////                if (!TextUtils.isEmpty(danJiaStr) && !TextUtils.isEmpty(shuLiangStr)) {
//
//                try {
//
//                    Log.i("", "afterTextChanged: itemJiayouliang位置" + position + ";;;" + editable.toString());
//
//                    if (position == (int) holder.itemFee.getTag()) {
//
//                        String danJiaStr = holder.itemDanjia.getContent();
//                        String shuLiangStr = editable.toString();
//
//                        if (TextUtils.isEmpty(shuLiangStr)) {
//                            holder.itemFee.setContet("");
//
//                        } else {
//
//                            Log.i("", "afterTextChanged:itemJiayouliang标记 " + (int) holder.itemFee.getTag());
//                            double danJiaDouble = Double.parseDouble(danJiaStr);
//                            double shuLiangDouble = Double.parseDouble(shuLiangStr);
//
//                            double zongJia = danJiaDouble * shuLiangDouble;
//
//                            holder.itemFee.setContet(format3(zongJia));
//
////                                getItem(position).setJinEMoney(String.valueOf(zongJia));
//
//                        }
//
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
////                }
//
////                if (calcuAmount != null) {
////                    calcuAmount.calAmount();
////                }
//
//            }
//        });

        holder.btnCalMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {


                    if (position == (int) holder.itemFee.getTag()) {

                        String danJiaStr = holder.itemDanjia.getContent();
                        String shuLiangStr = holder.itemJiayouliang.getText().toString();
                        Log.i("==", "onClick:单价 " + danJiaStr + "；数量：" + shuLiangStr);

                        if (TextUtils.isEmpty(shuLiangStr)) {
//                            holder.itemFee.setContet("");
                            Toasty.info(context, "请输入加油量", Toast.LENGTH_SHORT, true).show();
                            return;
                        } else {

                            Log.i("", "afterTextChanged:itemJiayouliang标记 " + (int) holder.itemFee.getTag());
                            double danJiaDouble = Double.parseDouble(danJiaStr);
                            double shuLiangDouble = Double.parseDouble(shuLiangStr);

                            double zongJia = danJiaDouble * shuLiangDouble;

                            holder.itemFee.setContet(format3(zongJia));

//                          getItem(position).setJinEMoney(String.valueOf(zongJia));
                            getItem(position).setTankSize(shuLiangStr);
                            getItem(position).setOilTotal(String.valueOf(zongJia));


                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                }

                if (calcuAmount != null) {
                    calcuAmount.calAmount();
                }


            }
        });




        holder.btnCxCph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (position == (int) holder.itemChepaihao.getTag()) {


                    final String chePai = holder.itemChepaihao.getText().toString().trim();

                    carNum carNum = new carNum();
                    carNum.setCarNum(chePai);

                    RequestBody requestBody = HttpManger.getHttpMangerInstance().getRequestBody(carNum);
                    HttpManger.getHttpMangerInstance().getServices().findCarinfoByCarNum(requestBody).enqueue(new Callback<CarNumRespInfoBean>() {
                        @Override
                        public void onResponse(Call<CarNumRespInfoBean> call, Response<CarNumRespInfoBean> response) {

                            CarNumRespInfoBean body = response.body();
                            if (body != null) {

                                CarNumRespInfoBean.BringBean bring = body.getBring();

                                if (bring != null) {

                                    String name = bring.getName();

                                    String telephone = bring.getTelephone();

                                    getItem(position).setVehicleCode(chePai);
                                    holder.itemLianxiren.setText(name);
                                    getItem(position).setPName(name);
                                    holder.itemLianxidianhua.setText(telephone);
                                    getItem(position).setTelphone(telephone);

                                    Log.i("", "onResponse: " + getItem(position).toString());

//                                holder.itemLianxiren.setText(carListBean.getPName());
//                                holder.itemLianxidianhua.setText(carListBean.getTelphone());


                                } else {
                                    holder.itemLianxiren.setText("");
                                    getItem(position).setVehicleCode("");
                                    holder.itemLianxidianhua.setText("");
                                    getItem(position).setTelphone("");

                                    Toasty.info(context, "查询车牌号失败请手动输入", Toast.LENGTH_SHORT, true).show();
                                    return;
                                }

                            } else {

                                holder.itemLianxiren.setText("");
                                getItem(position).setVehicleCode("");
                                holder.itemLianxidianhua.setText("");
                                getItem(position).setTelphone("");

                                Toasty.info(context, "查询车牌号失败请手动输入", Toast.LENGTH_SHORT, true).show();
                                return;
                            }


                        }

                        @Override
                        public void onFailure(Call<CarNumRespInfoBean> call, Throwable t) {

                            holder.itemLianxiren.setText("");
                            getItem(position).setVehicleCode("");
                            holder.itemLianxidianhua.setText("");
                            getItem(position).setTelphone("");

                        }
                    });

                }


            }
        });


//        holder.saveTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String btnName = holder.saveTv.getText().toString().trim();
//
//                if (btnName.equals("编辑")) {
//                    holder.saveTv.setText("保存");
//
//                }
//                if (btnName.equals("保存")) {
//                    holder.saveTv.setText("编辑");
//
//                    String chePaiHaoStr = holder.itemChepaihao.getText().toString();
//                    String lianXiRenStr = holder.itemLianxiren.getText().toString();
//                    String dianHuaStr = holder.itemLianxidianhua.getText().toString();
//
//                    getItem(position).setVehicleCode(chePaiHaoStr);
//                    getItem(position).setVehicleCode(lianXiRenStr);
//                    getItem(position).setTelphone(dianHuaStr);
//
//                    if (calcuAmount != null) {
//                        calcuAmount.calAmount();
//                    }
//
//                }
//
//            }
//        });

        return convertView;
    }


    public static String format3(double value) {

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
 /*
  * setMinimumFractionDigits设置成2
  *
  * 如果不这么做，那么当value的值是100.00的时候返回100
  *
  * 而不是100.00
  */
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);
 /*
  * 如果想输出的格式用逗号隔开，可以设置成true
  */
        nf.setGroupingUsed(false);
        return nf.format(value);
    }


    public void setEditable(EditText editText, boolean editable) {
//        editText.setFocusable(false);和editText.setEnabled(false);
        editText.setFocusable(false);
        editText.setEnabled(false);
    }


    static class ViewHolder {
        @BindView(R.id.item_chepaihao)
        EditText itemChepaihao;
        @BindView(R.id.item_lianxiren)
        EditText itemLianxiren;
        @BindView(R.id.item_lianxidianhua)
        EditText itemLianxidianhua;
        @BindView(R.id.item_finish_time)
        TextView itemFinishTime;
        @BindView(R.id.item_youpinleixing)
        TextView itemYoupinleixing;
        @BindView(R.id.item_guobiao)
        TextView itemGuobiao;
        @BindView(R.id.item_jiayouliang)
        EditText itemJiayouliang;
        @BindView(R.id.item_danjia)
        OrderConbindView itemDanjia;
        @BindView(R.id.item_fee)
        OrderConbindView itemFee;
        //        @BindView(R.id.save_tv)
//        TextView saveTv;
        @BindView(R.id.btn_cx_cph)
        TextView btnCxCph;

        @BindView(R.id.btn_cal_money)
        TextView btnCalMoney;

        @BindView(R.id.introduce)
        TextView introDuce;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
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

    public void setcalcuAmount(calcuAmount calcuAmount) {
        this.calcuAmount = calcuAmount;
    }


    calcuAmount calcuAmount;

    public interface calcuAmount {

        void calAmount();

    }

    private PopupWindow popLeft;
    private View layoutLeft;
    private ListView menulistLeft;


//    oilType
//    oilGB;


    private void showPopItem(final TextView viewTv, final int typeChoose, final List<OilLXGBBean.BringBean> oilList, final int parentPos, final OrderConbindView itemDanjia, final OrderConbindView itemFee, final TextView itemJiayouliang) {

        if (popLeft != null && popLeft.isShowing()) {
            popLeft.dismiss();
        } else {

            layoutLeft = UIUtils.inflate(
                    R.layout.pop_menulist);
            menulistLeft = (ListView) layoutLeft
                    .findViewById(R.id.menulist);

            final OilTypeAdapter oilItemAdapterAdapter = new OilTypeAdapter(oilList);
            menulistLeft.setAdapter(oilItemAdapterAdapter);
            menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    OilLXGBBean.BringBean strItem = oilItemAdapterAdapter.getItem(position);

                    String dictCode = strItem.getDictCode();
                    String dictName = strItem.getDictName();
                    String dictValue = strItem.getDictValue();
                    Log.i("==>", "onItemClick: " + dictName + ";;" + dictValue);


//                    这里 搞事情

                    if (typeChoose == YPCHOOSE) {
//                        itemOrder.setOilType(dictValue);
                        PaiSongDanJieDanActivity.addedToCommitCarList.get(parentPos).setOilType(oilList.get(position).getDictValue());
                        viewTv.setText(oilList.get(position).getDictName());
                    }
                    if (typeChoose == GBCHOOSE) {
//                        itemOrder.setNationalStandard(dictValue);
                        PaiSongDanJieDanActivity.addedToCommitCarList.get(parentPos).setNationalStandard(oilList.get(position).getDictValue());
                        viewTv.setText(oilList.get(position).getDictName());
                    }
                    getOilPriceByArea(addedToCommitCarList,parentPos,itemDanjia);



                    // 隐藏弹出窗口
                    if (popLeft != null && popLeft.isShowing()) {
                        popLeft.dismiss();
                    }

//
//
//                    try {
//
//
//                        if (parentPos == (int) itemFee.getTag()) {
//
//                            String danJiaStr = itemDanjia.getContent();
//                            String shuLiangStr = itemJiayouliang.getText().toString();
//                            Log.i("==", "onClick:单价 " + danJiaStr + "；数量：" + shuLiangStr);
//
//                            if (TextUtils.isEmpty(shuLiangStr)) {
////                            holder.itemFee.setContet("");
//                                Toasty.info(context, "请输入加油量", Toast.LENGTH_SHORT, true).show();
//                                return;
//                            } else {
//
//                                Log.i("", "afterTextChanged:itemJiayouliang标记 " + (int) itemFee.getTag());
//                                double danJiaDouble = Double.parseDouble(danJiaStr);
//                                double shuLiangDouble = Double.parseDouble(shuLiangStr);
//
//                                double zongJia = danJiaDouble * shuLiangDouble;
//
//                                itemFee.setContet(format3(zongJia));
//
////                          getItem(position).setJinEMoney(String.valueOf(zongJia));
//                                getItem(position).setTankSize(shuLiangStr);
//                                getItem(position).setOilTotal(String.valueOf(zongJia));
//
//
//                            }
//
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
////                }
//
//                    if (calcuAmount != null) {
//                        calcuAmount.calAmount();
//                    }
//


                }
            });


            // 创建弹出窗口
            // 窗口内容为layoutLeft，里面包含一个ListView
            // 窗口宽度跟tvLeft一样
            popLeft = new PopupWindow(layoutLeft, viewTv.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            ColorDrawable cd = new ColorDrawable(Color.BLACK);
            popLeft.setBackgroundDrawable(cd);
            popLeft.setAnimationStyle(R.style.PopupAnimation);
            popLeft.update();
            popLeft.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popLeft.setTouchable(true); // 设置popupwindow可点击
            popLeft.setOutsideTouchable(true); // 设置popupwindow外部可点击
            popLeft.setFocusable(true); // 获取焦点

            // 设置popupwindow的位置（相对tvLeft的位置）
            int topBarHeight = viewTv.getBottom();
            popLeft.showAsDropDown(viewTv, 0,
                    (topBarHeight - viewTv.getHeight()) / 2);

            popLeft.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 如果点击了popupwindow的外部，popupwindow也会消失
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        popLeft.dismiss();
                        return true;
                    }
                    return false;
                }
            });

        }

    }


//    List<PaiSongDanCommitBean.CarListBean> addedToCommitCarList
public void getOilPriceByArea(List<PaiSongDanCommitBean.CarListBean> dataList , final int parentPosi, final OrderConbindView itemDanjia){

    OilRequestBean oilRequestBean = new OilRequestBean();

    String nationalStandard = dataList.get(parentPosi).getNationalStandard();

    if (TextUtils.isEmpty(nationalStandard)) {
        Toasty.info(context, "请选择国标获取油价", Toast.LENGTH_SHORT, true).show();
        return;
    }
    String oilType = dataList.get(parentPosi).getOilType();
    if (TextUtils.isEmpty(oilType)){
        Toasty.info(context, "请选择油品类型获取油价", Toast.LENGTH_SHORT, true).show();
        return;
    }

//    oilRequestBean.setArea(mBringBean.getAreaCode());
    oilRequestBean.setArea("370000");
    oilRequestBean.setNationalStandard(nationalStandard);
    oilRequestBean.setOilType(oilType);
    oilRequestBean.setTime(UIUtils.getCurrentTime());

    RequestBody requestBody = HttpManger.getHttpMangerInstance().getRequestBody(oilRequestBean);
    HttpManger.getHttpMangerInstance().getServices().findOilpriceByArea(requestBody).enqueue(new Callback<OilResponseBean>() {
        @Override
        public void onResponse(Call<OilResponseBean> call, Response<OilResponseBean> response) {

            OilResponseBean body = response.body();
            if (body != null) {
                OilResponseBean.BringBean bring = body.getBring();
                if (bring != null) {


                    itemDanjia.setContet(bring.getOilPrice());
                    addedToCommitCarList.get(parentPosi).setOilBalance(bring.getOilPrice());

                }else {
                    Toasty.info(context, "查询油价失败", Toast.LENGTH_SHORT, true).show();
                    itemDanjia.setContet("0");
                    addedToCommitCarList.get(parentPosi).setOilBalance("0");
                    return;
                }
            }

        }

        @Override
        public void onFailure(Call<OilResponseBean> call, Throwable t) {

        }
    });



}





}
