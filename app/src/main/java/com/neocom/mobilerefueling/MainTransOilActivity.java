package com.neocom.mobilerefueling;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.lzy.widget.AlphaIndicator;
import com.neocom.mobilerefueling.activity.BaseWithoutMapActivity;
import com.neocom.mobilerefueling.activity.ExampleActivity;
import com.neocom.mobilerefueling.fragment.GetOilListFragmen;
import com.neocom.mobilerefueling.fragment.HomeFragment;
import com.neocom.mobilerefueling.fragment.MeFragment;
import com.neocom.mobilerefueling.fragment.SureFragment;
import com.neocom.mobilerefueling.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


public class MainTransOilActivity extends BaseWithoutMapActivity implements View.OnClickListener {

    //    private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();


    private ViewPager viewPagerContent;

    private ContentFragmentAdapter mAdapter;
    private LinearLayout mTtitleBar;
    private TextView mainTitle;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
////        initView();
////        initDatas();
//        ImmersionBar.with(this).titleBar(mTtitleBar).init();
//
//        viewPagerContent.setAdapter(mAdapter);
//
//        initEvent();
//    }

    /**
     * 初始化所有事件
     */
    private void initEvent() {

//        viewPagerContent.setOnPageChangeListener(this);

    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_oil_main);
//        initView();
//        initDatas();

        tempUI();

    }

    private void tempUI() {

        AppCompatButton ordinaryUserBtn = (AppCompatButton) findViewById(R.id.ordinary_user);
        AppCompatButton trasferUserBtn = (AppCompatButton) findViewById(R.id.transfer_user);
        AppCompatButton trasOilUserBtn = (AppCompatButton) findViewById(R.id.trans_oil_user);

        ordinaryUserBtn.setOnClickListener(this);
        trasOilUserBtn.setClickable(false);
        trasferUserBtn.setOnClickListener(this);
        trasOilUserBtn.setOnClickListener(this);
    }

    public void initView() {
        viewPagerContent = (ViewPager) findViewById(R.id.viewpager_content);
        mAdapter = new ContentFragmentAdapter(getSupportFragmentManager());

        viewPagerContent.setAdapter(mAdapter);
        AlphaIndicator alphaIndicator = (AlphaIndicator) findViewById(R.id.alphaIndicator);
        alphaIndicator.setViewPager(viewPagerContent);
//        AlphaView one= (AlphaView) findViewById(R.id.indicator_one);
//        AlphaView two= (AlphaView) findViewById(R.id.indicator_two);
//        AlphaView three= (AlphaView) findViewById(R.id.indicator_three);
//        AlphaView four= (AlphaView) findViewById(R.id.indicator_four);
        mTtitleBar = (LinearLayout) findViewById(R.id.main_title_bar);
        mainTitle = mTtitleBar.findViewById(R.id.main_title);
        ImmersionBar.with(this).titleBar(mTtitleBar).init();
        viewPagerContent.setOffscreenPageLimit(3);


//        ChangeColorIconWithText one = (ChangeColorIconWithText) findViewById(R.id.id_indicator_one);
//        mTabIndicators.add(one);
//        ChangeColorIconWithText two = (ChangeColorIconWithText) findViewById(R.id.id_indicator_two);
//        mTabIndicators.add(two);
//        ChangeColorIconWithText three = (ChangeColorIconWithText) findViewById(R.id.id_indicator_three);
//        mTabIndicators.add(three);
//        ChangeColorIconWithText four = (ChangeColorIconWithText) findViewById(R.id.id_indicator_four);
//        mTabIndicators.add(four);

//        mTabIndicators.add(one);
//        mTabIndicators.add(two);
//        mTabIndicators.add(three);
//        mTabIndicators.add(four);


//        one.setOnClickListener(this);
//        two.setOnClickListener(this);
//        three.setOnClickListener(this);
//        four.setOnClickListener(this);
        mTtitleBar.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTransOilActivity.this, ExampleActivity.class);

                startActivity(intent);


            }
        });

//
//        one.setIconAlpha(1.0f);

//        setMainTitle(0);

        initEvent();

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.ordinary_user:
                startActivity(new Intent(UIUtils.getContext(), MainActivity.class));
                finish();
                break;

            case R.id.transfer_user:
                startActivity(new Intent(UIUtils.getContext(), MainTransActivity.class));
                finish();
                break;

            case R.id.trans_oil_user:

                break;

        }

    }


    public class ContentFragmentAdapter extends FragmentPagerAdapter {


        public ContentFragmentAdapter(FragmentManager fm) {
            super(fm);

            mFragments.add(new HomeFragment());
//            mFragments.add(new GetOilOrder());
            mFragments.add(new GetOilListFragmen());
            mFragments.add(new SureFragment());
            mFragments.add(new MeFragment());

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position);
        }

        @Override
        public Fragment getItem(int position) {
//            return FragmentFactory.createFragment(position);
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }


//    @Override
//    public void onClick(View v) {
////        clickTab(v);
//
//    }

    /**
     * 点击Tab按钮
     *
     * @param v
     */
//    private void clickTab(View v) {
////        resetOtherTabs();
//
//        switch (v.getId()) {
//            case R.id.indicator_one:
////                mTabIndicators.get(0).setIconAlpha(1.0f);
////                viewPagerContent.setCurrentItem(0, false);
//                setMainTitle(0);
//                break;
//            case R.id.indicator_two:
////                mTabIndicators.get(1).setIconAlpha(1.0f);
////                viewPagerContent.setCurrentItem(1, false);
//                setMainTitle(1);
//                break;
//            case R.id.indicator_three:
////                mTabIndicators.get(2).setIconAlpha(1.0f);
////                viewPagerContent.setCurrentItem(2, false);
//                setMainTitle(2);
//                break;
//            case R.id.indicator_four:
////                mTabIndicators.get(3).setIconAlpha(1.0f);
////                viewPagerContent.setCurrentItem(3, false);
//                setMainTitle(3);
//                break;
//        }
//    }


//    public void setMainTitle(int position) {
//        switch (position) {
//            case 0:
//                mainTitle.setText("首页");
//                break;
//            case 1:
//                mainTitle.setText("加油");
//                break;
//            case 2:
//                mainTitle.setText("订单");
//                break;
//            case 3:
//                mainTitle.setText("我的");
//                break;
//        }
//
//
//    }


    /**
     * 重置其他的TabIndicator的颜色
     */
//    private void resetOtherTabs() {
//        for (int i = 0; i < mTabIndicators.size(); i++) {
//            mTabIndicators.get(i).setIconAlpha(0);
//        }
//    }

//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        if (positionOffset > 0) {
//            ChangeColorIconWithText left = mTabIndicators.get(position);
//            ChangeColorIconWithText right = mTabIndicators.get(position + 1);
//            left.setIconAlpha(1 - positionOffset);
//            right.setIconAlpha(positionOffset);
//        }
//
//    }
//
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//
//        setMainTitle(position);
//
//    }
    public void setOnShowAddressListener(OnShowAddressListener addressListener) {

        this.addressListener = addressListener;
    }


    public interface OnShowAddressListener {

        public boolean showAddress();

    }

    private OnShowAddressListener addressListener;


    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
