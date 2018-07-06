package com.neocom.mobilerefueling.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.neocom.mobilerefueling.MainTransOilActivity;
import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.utils.UIUtils;
import com.neocom.mobilerefueling.view.RoundedTextView;
import com.neocom.mobilerefueling.view.TickView;

import android.graphics.Path;

import android.graphics.PathMeasure;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by admin on 2017/10/24.
 */

public class OrderSuccessActivity extends Activity implements TickView.onTickPreCentListener {
    private static final String TAG = "MainActivity";
    private TickView mTickView;
    private TextView mTvPaySuccess;
    private View mLyTickView;
    private ImageView mIvPaySuccess;
    private ValueAnimator mTickSlideInAnimator;
    private RoundedTextView toShouYe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_success_view);
        mLyTickView = findViewById(R.id.ly_tick);
        mIvPaySuccess = (ImageView) findViewById(R.id.icon_pay_success);

        mTvPaySuccess = (TextView) findViewById(R.id.tv_pay_success);
        mTickView = (TickView) findViewById(R.id.icon_tick);
        mTickView.setTickPreCentUpdateListener(this);

        toShouYe = (RoundedTextView) findViewById(R.id.layout_continue_look);
        toShouYe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UIUtils.getContext(), MainTransOilActivity.class));
                finish();
            }
        });

        findViewById(R.id.top_bar_finish_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onTickPreCent(final float tickPreCent) {
        Log.d(TAG, "[onTickPreCent --> tickPreCent] = " + "%" + tickPreCent * 100);
        mTvPaySuccess.setAlpha((int) (tickPreCent));
        if (tickPreCent == 1) {//动画已完成
            startTickSlideInAnimator();
        }
    }

    /**
     * 打钩view平移缩放动画
     */
    private void startTickSlideInAnimator() {
        final int[] startXY = new int[2];
        final int[] endXY = new int[2];
        mTickView.getLocationInWindow(startXY);
        mIvPaySuccess.getLocationInWindow(endXY);
        Log.d(TAG, "[mSlideImageView startXY] = " + startXY[0] + "," + startXY[1]);
        Log.d(TAG, "[mIvPaySuccess endXY] = " + endXY[0] + "," + endXY[1]);

        Path path = new Path();
        path.moveTo(startXY[0], startXY[1]);
        path.lineTo(endXY[0], 0.9f * endXY[1]);
        final PathMeasure slidePathMeasure = new PathMeasure(path, false);

        mTickSlideInAnimator = ValueAnimator.ofFloat(0, 1f);
        mTickSlideInAnimator.setStartDelay(500);//停顿下，留点视觉间隙给用户
        mTickSlideInAnimator.setDuration(300);
        mTickSlideInAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mTickSlideInAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float slidePreCent = (float) animation.getAnimatedValue();
                mTvPaySuccess.setVisibility(View.GONE);
                mLyTickView.getBackground().mutate().setAlpha((int) (255 * (1 - slidePreCent)));
                mTickView.setScaleX(1 - 0.7f * slidePreCent);
                mTickView.setScaleY(1 - 0.7f * slidePreCent);
                mTickView.setAlpha(1 - 0.4f * slidePreCent);
                final float pathLength = slidePathMeasure.getLength();
                float[] currXY = new float[2];
                slidePathMeasure.getPosTan(pathLength * slidePreCent, currXY, null);
                mTickView.setTranslationX(currXY[0] - startXY[0]);
                mTickView.setTranslationY(currXY[1] - startXY[1]);
            }
        });
        mTickSlideInAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mTickSlideInAnimator.removeAllListeners();
                mLyTickView.setVisibility(View.GONE);
            }
        });
        mTickSlideInAnimator.start();
    }
}
