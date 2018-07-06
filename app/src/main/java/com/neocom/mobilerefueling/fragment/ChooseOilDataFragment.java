package com.neocom.mobilerefueling.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.activity.PaiSongDanJieDanActivity;
import com.neocom.mobilerefueling.adapter.JiaYouJiAdapter;
import com.neocom.mobilerefueling.bean.JiaYouJiRespBean;

import java.util.List;

/**
 * Created by admin on 2017/12/7.
 */

public class ChooseOilDataFragment extends DialogFragment {

    private ListView oilListDataView;
    private ImageView closeOne;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_lv_layout);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 5;
        window.setAttributes(lp);

        initView(dialog);

        return dialog;
    }

    private void initView(Dialog dialog) {

        oilListDataView = dialog.findViewById(R.id.data_lv);
        closeOne = dialog.findViewById(R.id.close_one);
        closeOne.setOnClickListener(onClickListener);
        Button okBtn = dialog.findViewById(R.id.pay_now);
        okBtn.setOnClickListener(onClickListener);

//        showInDialogist


        JiaYouJiAdapter youJiAdapter=new JiaYouJiAdapter(getActivity(), PaiSongDanJieDanActivity.showInDialogist);
        oilListDataView.setAdapter(youJiAdapter);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.close_one:
                    getDialog().dismiss();
                    break;

                case R.id.pay_now:

                    getDialog().dismiss();

                    selectedDataCallBack.GetSelectedDataValue(PaiSongDanJieDanActivity.showInDialogist);

                    break;

            }

        }
    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象

        selectedDataCallBack= (GetSelectedDataCallBack) getActivity();

    }





    GetSelectedDataCallBack selectedDataCallBack;


//    定义一个接口 获取 选中的数据

    public interface GetSelectedDataCallBack{
        void GetSelectedDataValue(List<JiaYouJiRespBean.BringBean> beanList); // 返回选中的 集合数据

    }


}
