package com.neocom.mobilerefueling.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.utils.UIUtils;

/**
 * Created by admin on 2017/11/1.
 */

public class SplashActivity extends BaseActivity {

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            startActivity(new Intent(UIUtils.getContext(), APPLoginActivity.class));
            finish();
        }
    };

    @Override
    public void initContentView() {
        setContentView(R.layout.splash_layout);
    }

    @Override
    public void initView() {
        handler.sendEmptyMessageDelayed(0, 1500);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                new Intent(UIUtils.getContext(), APPLoginActivity.class);
//
//            }
//        }, 1500);

    }

    @Override
    public void initData() {

    }
}
