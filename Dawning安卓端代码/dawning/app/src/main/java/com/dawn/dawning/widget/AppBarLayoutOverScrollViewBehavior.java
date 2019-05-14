package com.dawn.dawning.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dawn.dawning.R;

public class AppBarLayoutOverScrollViewBehavior extends AppBarLayout.Behavior {
    private static final String TAG = "AppBarLayoutOverScrollViewBehavior";

    private ImageView mImageView;
    private ConstraintLayout mConstrainLayout;
    private LinearLayout mLinearLayout;

    private int mAppbarHeight;//记录AppbarLayout原始高度
    private int mImageViewHeight;//记录ImageView原始高度

    private static final float MAX_SCALE_HEIGHT = 200;//放大最大高度
    private float mTotalDy;//手指在Y轴滑动的总距离
    private float mScaleValue;//图片缩放比例
    private int mLastBottom;//Appbar的变化高度

    private boolean isAnimate;//是否做动画标志

    public AppBarLayoutOverScrollViewBehavior() {

    }

    public AppBarLayoutOverScrollViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {
        boolean handled = super.onLayoutChild(parent, abl, layoutDirection);
        init(abl);
        return handled;
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
        // 开始滑动时，启用动画
        isAnimate = true;
        return true;
    }

    /**
     * 进行初始化操作，在这里获取到ImageView的引用，和Appbar的原始高度
     *
     * @param abl
     */
    private void init(AppBarLayout abl) {
        // 必须设置ClipChildren为false，这样目标View在放大时才能超出布局的范围
        abl.setClipChildren(false);
        mAppbarHeight = abl.getHeight();
        mImageView = (ImageView) abl.findViewById(R.id.collapsing_layout_image);
        mConstrainLayout = (ConstraintLayout) abl.findViewById(R.id.appBar_constrainLayout);
        mLinearLayout = (LinearLayout) abl.findViewById(R.id.program_detail_top);
        if (mImageView != null) {
            mImageViewHeight = mImageView.getHeight();
        }
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        // 1.mTargetView不为null
        // 2.是向下滑动，dy<0表示向下滑动
        // 3.AppBarLayout已经完全展开，child.getBottom() >= mParentHeight
        if (mImageView != null && ((dy < 0 && child.getBottom() >= mAppbarHeight) || (dy > 0 && child.getBottom() > mAppbarHeight)) && type == ViewCompat.TYPE_TOUCH) {
            scaleHeaderImageView(child, dy);
        } else {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }
    }

    /**
     * 对ImageView进行缩放处理，对AppbarLayout进行高度的设置
     */
    private void scaleHeaderImageView(AppBarLayout abl, int dy) {
        // 累加垂直方向上滑动的像素数
        mTotalDy += -dy;
        // 不能大于最大滑动距离
        mTotalDy = Math.min(mTotalDy, MAX_SCALE_HEIGHT);
        // 计算目标View缩放比例，不能小于1
        mScaleValue = Math.max(1f, 1f + mTotalDy / MAX_SCALE_HEIGHT);
        // 缩放目标View
        mImageView.setScaleX(mScaleValue);
        mImageView.setScaleY(mScaleValue);
        int changeHieght = (int) (mImageViewHeight / 2 * (mScaleValue - 1));
        mConstrainLayout.setTranslationY(changeHieght);
        mLinearLayout.setTranslationY(changeHieght);
        // 计算目标View放大后增加的高度
        mLastBottom = mAppbarHeight + changeHieght;
        // 修改AppBarLayout的高度
        abl.setBottom(mLastBottom);
    }

    //处理惯性滑动的情况
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
         //如果触发了快速滚动且垂直方向上速度大于100，则禁用动画
        if (velocityY > 100) {
            isAnimate = false;
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }


    /**
     * 滑动停止的时候，恢复AppbarLayout、ImageView的原始状态
     */
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {
        recovery(abl);
        super.onStopNestedScroll(coordinatorLayout, abl, target, type);
    }


    /**
     * 通过属性动画的形式，恢复AppbarLayout、ImageView的原始状态
     *
     * @param abl
     */
    private void recovery(final AppBarLayout abl) {
        if (mTotalDy > 0) {
            mTotalDy = 0;
            if (isAnimate) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(mScaleValue, 1f).setDuration(200);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        mImageView.setScaleX(value);
                        mImageView.setScaleY(value);
                        mConstrainLayout.setTranslationY((int)((mLastBottom-mAppbarHeight)-(mLastBottom - mAppbarHeight) * animation.getAnimatedFraction()));
                        mLinearLayout.setTranslationY((int)((mLastBottom-mAppbarHeight)-(mLastBottom - mAppbarHeight) * animation.getAnimatedFraction()));
                        abl.setBottom((int) (mLastBottom - (mLastBottom - mAppbarHeight) * animation.getAnimatedFraction()));
                    }
                });
                valueAnimator.start();
            } else {
                mImageView.setScaleX(1);
                mImageView.setScaleY(1);
                mConstrainLayout.setTranslationY(0);
                mLinearLayout.setTranslationY(0);
                abl.setBottom(mAppbarHeight);
            }
        }
    }
}
//package com.dawn.dawning.widget;
//
//import android.animation.ValueAnimator;
//import android.content.Context;
//import android.support.design.widget.AppBarLayout;
//import android.support.design.widget.CoordinatorLayout;
//import android.support.v4.view.ViewCompat;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.dawn.dawning.R;
//
//
///**
// * 头部下拉放大Behavior
// * Create by: chenwei.li
// * Date: 2018/5/26
// * Time: 下午9:54
// * Email: lichenwei.me@foxmail.com
// */
//public class AppBarLayoutOverScrollViewBehavior extends AppBarLayout.Behavior {
//
//    private ImageView mImageView;
//    private int mAppbarHeight;//记录AppbarLayout原始高度
//    private int mImageViewHeight;//记录ImageView原始高度
//
//    private static final float MAX_ZOOM_HEIGHT = 500;//放大最大高度
//    private float mTotalDy;//手指在Y轴滑动的总距离
//    private float mScaleValue;//图片缩放比例
//    private int mLastBottom;//Appbar的变化高度
//
//    private boolean isAnimate;//是否做动画标志
//
//
//    public AppBarLayoutOverScrollViewBehavior(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//    }
//
//    @Override
//    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {
//        boolean handled = super.onLayoutChild(parent, abl, layoutDirection);
//        init(abl);
//        return handled;
//    }
//
//    /**
//     * 进行初始化操作，在这里获取到ImageView的引用，和Appbar的原始高度
//     *
//     * @param abl
//     */
//    private void init(AppBarLayout abl) {
//        abl.setClipChildren(false);
//        mAppbarHeight = abl.getHeight();
//        mImageView = (ImageView) abl.findViewById(R.id.collapsing_layout_image);
//        if (mImageView != null) {
//            mImageViewHeight = mImageView.getHeight();
//        }
//    }
//
//    /**
//     * 是否处理嵌套滑动
//     *
//     * @param parent
//     * @param child
//     * @param directTargetChild
//     * @param target
//     * @param nestedScrollAxes
//     * @param type
//     * @return
//     */
//    @Override
//    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
//        isAnimate = true;
//        return true;
//    }
//
//    /**
//     * 在这里做具体的滑动处理
//     *
//     * @param coordinatorLayout
//     * @param child
//     * @param target
//     * @param dx
//     * @param dy
//     * @param consumed
//     * @param type
//     */
//    @Override
//    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
//        if (mImageView != null && child.getBottom() >= mAppbarHeight && dy < 0 && type == ViewCompat.TYPE_TOUCH) {
//            zoomHeaderImageView(child, dy);
//        } else {
//            if (mImageView != null && child.getBottom() > mAppbarHeight && dy > 0 && type == ViewCompat.TYPE_TOUCH) {
//                consumed[1] = dy;
//                zoomHeaderImageView(child, dy);
//            } else {
//                if (valueAnimator == null || !valueAnimator.isRunning()) {
//                    super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
//                }
//
//            }
//        }
//
//    }
//
//
//    /**
//     * 对ImageView进行缩放处理，对AppbarLayout进行高度的设置
//     *
//     * @param abl
//     * @param dy
//     */
//    private void zoomHeaderImageView(AppBarLayout abl, int dy) {
//        mTotalDy += -dy;
//        mTotalDy = Math.min(mTotalDy, MAX_ZOOM_HEIGHT);
//        mScaleValue = Math.max(1f, 1f + mTotalDy / MAX_ZOOM_HEIGHT);
//        ViewCompat.setScaleX(mImageView, mScaleValue);
//        ViewCompat.setScaleY(mImageView, mScaleValue);
//        mLastBottom = mAppbarHeight + (int) (mImageViewHeight / 2 * (mScaleValue - 1));
//        abl.setBottom(mLastBottom);
//    }
//
//
//    /**
//     * 处理惯性滑动的情况
//     *
//     * @param coordinatorLayout
//     * @param child
//     * @param target
//     * @param velocityX
//     * @param velocityY
//     * @return
//     */
//    @Override
//    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
//        if (velocityY > 100) {
//            isAnimate = false;
//        }
//        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
//    }
//
//
//    /**
//     * 滑动停止的时候，恢复AppbarLayout、ImageView的原始状态
//     *
//     * @param coordinatorLayout
//     * @param abl
//     * @param target
//     * @param type
//     */
//    @Override
//    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {
//        recovery(abl);
//        super.onStopNestedScroll(coordinatorLayout, abl, target, type);
//    }
//
//    ValueAnimator valueAnimator;
//
//    /**
//     * 通过属性动画的形式，恢复AppbarLayout、ImageView的原始状态
//     *
//     * @param abl
//     */
//    private void recovery(final AppBarLayout abl) {
//        if (mTotalDy > 0) {
//            mTotalDy = 0;
//            if (isAnimate) {
//                valueAnimator = ValueAnimator.ofFloat(mScaleValue, 1f).setDuration(220);
//                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        float value = (float) animation.getAnimatedValue();
//                        ViewCompat.setScaleX(mImageView, value);
//                        ViewCompat.setScaleY(mImageView, value);
//                        abl.setBottom((int) (mLastBottom - (mLastBottom - mAppbarHeight) * animation.getAnimatedFraction()));
//                    }
//                });
//                valueAnimator.start();
//            } else {
//                ViewCompat.setScaleX(mImageView, 1f);
//                ViewCompat.setScaleY(mImageView, 1f);
//                abl.setBottom(mAppbarHeight);
//            }
//        }
//    }
//}