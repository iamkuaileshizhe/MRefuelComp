package com.neocom.mobilerefueling.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.bean.GCarListBean;
import com.neocom.mobilerefueling.utils.UIUtils;
import com.neocom.mobilerefueling.view.OrderConbindView;

import java.util.List;

/**
 * Created by admin on 2017/8/25.
 */

public class ReceiveListDetailAdapter extends BaseAdapter {


    List<GCarListBean> carListTake;

    public ReceiveListDetailAdapter(List<GCarListBean> carListTake) {
        this.carListTake = carListTake;
    }

    @Override
    public int getCount() {
        return carListTake == null ? 0 : carListTake.size();
    }

    @Override
    public Object getItem(int i) {
        return carListTake.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = UIUtils.inflate(R.layout.receive_list_detail_item_layout);
//            holder.title = convertView.findViewById(R.id.title);

//            holder.content = convertView.findViewById(R.id.content);


            holder.vehicleCode = convertView.findViewById(R.id.car_num_oc);
            holder.pName = convertView.findViewById(R.id.car_driver_oc);
            holder.telphone = convertView.findViewById(R.id.car_driver_phone_oc);
            holder.oilType = convertView.findViewById(R.id.car_oil_type_oc);
            holder.tankSize = convertView.findViewById(R.id.car_tank_size_oc);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GCarListBean item = (GCarListBean) getItem(position);

        holder.vehicleCode.setTitle("车牌号");
        holder.vehicleCode.setContet(item.getVehicleCode());

        holder.pName.setTitle("车辆联系人");
        holder.pName.setContet(item.getpName());

        holder.telphone.setTitle("车辆联系电话");
        holder.telphone.setContet(item.getTelphone());

        holder.oilType.setTitle("燃油类型");
        holder.oilType.setContet(item.getOilType());

        holder.tankSize.setTitle("油箱大小");
        holder.tankSize.setContet(item.getTankSize());
//        holder.title.setText(getItem(position).toString());


//        holder.content.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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
//                if (getItemContentListener != null) {
//
//                    //使用回调将数据传递出去
//                    getItemContentListener.getItemContent(position, editable.toString());
//                }
//
//            }
//        });


        return convertView;
    }

    static class ViewHolder {

//        TextView title;
//        EditText content;

        OrderConbindView vehicleCode;
        OrderConbindView pName;
        OrderConbindView telphone;
        OrderConbindView oilType;
        OrderConbindView tankSize;


    }


    public void setOnGetItenContentListener(OnGetItemContentListener getItenContentListener) {

        this.getItemContentListener = getItenContentListener;


    }


    public interface OnGetItemContentListener {

        void getItemContent(int position, String conten);

    }

    OnGetItemContentListener getItemContentListener;
}
