package com.library.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.EdgeEffect;
import android.widget.Scroller;

/**
 * Created by xiangcheng on 17/6/3.
 * 自带有滑动效果的流式布局
 */

public class ScrollFlowLayout extends FlowLayout {

    private static final String TAG = ScrollFlowLayout.class.getSimpleName();
    private int mScaleTouchSlop;//点击和滑动的临界值

    private int mMaxVelocity;

    private VelocityTracker mVelocityTracker;//滑动速度跟踪器

    private Scroller scroller;

    private EdgeEffect edgeEffectTop, edgeEffectBottom;//加入滑动到边界的阴影效果

    private static final int defaultEffectColor = Color.GRAY;

    private int topEffectColor;//上边界阴影颜色
    private int bottomEffectColor;//下边界阴影颜色

    private boolean enableEffect;

    // 对于fling，仅吸收到达边缘时的速度
    private boolean hasAbsorbTop, hasAbsorbBottom;

//    private Paint scrollBarPaint;

    public ScrollFlowLayout(Context context) {
        super(context);
    }

    public ScrollFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initArgus(Context context, AttributeSet attrs) {
        mScaleTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        scroller = new Scroller(context);
        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScrollFlowLayout);
            topEffectColor = typedArray.getColor(R.styleable.ScrollFlowLayout_effect_top_color, defaultEffectColor);
            bottomEffectColor = typedArray.getColor(R.styleable.ScrollFlowLayout_effect_bottom_color, defaultEffectColor);
            enableEffect = typedArray.getBoolean(R.styleable.ScrollFlowLayout_need_effect, false);
        } finally {
            typedArray.recycle();
        }
        initEffect(context);
        setWillNotDraw(false);
    }

    private void initEffect(Context context) {
        edgeEffectTop = new EdgeEffect(context);
        edgeEffectBottom = new EdgeEffect(context);
        Utils.trySetColorForEdgeEffect(edgeEffectTop, topEffectColor);
        Utils.trySetColorForEdgeEffect(edgeEffectBottom, bottomEffectColor);
//        scrollBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        scrollBarPaint.setStyle(Paint.Style.FILL);
//        scrollBarPaint.setColor(0x88888888);
    }

    float startY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        float detaY;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                startY = ev.getY(0);
                break;
            case MotionEvent.ACTION_MOVE:
                detaY = ev.getY() - startY;
                //如果是属于滑动事件的话，拦截子类的ontouch
                if (Math.abs(detaY) > mScaleTouchSlop) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                detaY = ev.getY() - startY;
                //如果是属于滑动事件的话，拦截子类的ontouch
                if (Math.abs(detaY) > mScaleTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private int mPointerId;//多点触摸只算第一根手指的速度

    private float scrollBarDy;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        float detaY;
        acquireVelocityTracker(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN");
                scroller.abortAnimation();
//                mVelocityTracker.clear();
                startY = ev.getY();
                mPointerId = ev.getPointerId(0);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                startY = ev.getY(0);
                break;
            case MotionEvent.ACTION_MOVE:
                if (verticalOffset > 0) {
                    float currentY = ev.getY();
                    detaY = startY - currentY;
                    scrollBarDy = detaY;
                    if (getScrollY() < 0 && detaY < 0) {//往下拉
                        detaY = 0;
                        scrollTo(0, 0);
                    } else if (getScrollY() > verticalOffset && detaY > 0) {//往上拉
                        detaY = 0;
                    }
                    scrollBy(0, (int) detaY);

                    if (getScrollY() >= verticalOffset) {
                        scrollTo(0, verticalOffset);
                    } else if (getScrollY() <= 0) {
                        scrollTo(0, 0);
                    }

                    startY = ev.getY();

                    if (enableEffect) {
                        //到了上边缘
                        if (getScrollY() == 0 && detaY < 0) {
                            //这里传进去的是一个滑动的比例
                            edgeEffectTop.onPull(Math.abs(detaY) / width);
                        } else if (getScrollY() >= verticalOffset && detaY > 0) {
                            edgeEffectBottom.onPull(Math.abs(Math.abs(detaY) / width));
                        }
                    }
//                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP");
                if (verticalOffset > 0) {
                    mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                    int initialVelocity = (int) mVelocityTracker.getYVelocity(mPointerId);
//                    mVelocityTracker.clear();
                    startY = ev.getY();
//                    Log.d(TAG, "initialVelocity:" + initialVelocity);
                    if (getScrollY() >= 0 && getScrollY() <= verticalOffset) {
                        //在快速滑动松开的基础上开始惯性滚动，滚动距离取决于fling的初速度，快速滑动的时候，需要移动的距离
                        scroller.fling((int) ev.getX(), (int) ev.getY(), 0,
                                -initialVelocity, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
//                        scroller.extendDuration(50);
//                        invalidate();
                    } else {
                        edgeEffectTop.onRelease();
                        edgeEffectBottom.onRelease();
                    }
                }
                releaseVelocityTracker();
                break;
        }
        return true;
    }

    private void acquireVelocityTracker(final MotionEvent event) {
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {//是否已经滚动完成
//            Log.d(TAG, "startY之前:" + startY);
//            Log.d(TAG, "mScroller.getCurrY() " + scroller.getCurrY());
            int dataY = (int) (scroller.getCurrY() - startY);
            Log.d(TAG, "dataY:" + dataY);
            if (getScrollY() <= 0) {
                dataY = 0;
            } else if (getScrollY() >= verticalOffset) {
                dataY = 0;
            }
            scrollBy(0, dataY);//获取当前值，startScroll（）初始化后，调用就能获取区间值
            if (getScrollY() >= verticalOffset) {
                scrollTo(0, verticalOffset);
            } else if (getScrollY() <= 0) {
                scrollTo(0, 0);
            }
            startY = scroller.getCurrY();

            postInvalidate();
//            Log.d(TAG, "getScrollY():" + getScrollY());
//            Log.d(TAG, "verticalOffset:" + verticalOffset);

            if (enableEffect) {
                if (!hasAbsorbTop && getScrollY() == 0) {
                    hasAbsorbTop = true;
                    //这里是为了实现快速滑动的一个阴影效果，传进去的是当前的速率
                    edgeEffectTop.onAbsorb((int) scroller.getCurrVelocity());
                } else if (!hasAbsorbBottom && getScrollY() == verticalOffset) {
                    hasAbsorbBottom = true;
                    edgeEffectBottom.onAbsorb((int) scroller.getCurrVelocity());
                }
            }
        } else {
            hasAbsorbTop = false;
            hasAbsorbBottom = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (enableEffect) {
            if (!edgeEffectTop.isFinished()) {
                edgeEffectTop.setSize(width, width);
                if (edgeEffectTop.draw(canvas)) {
                    postInvalidate();
                }
            }
            if (!edgeEffectBottom.isFinished()) {
                canvas.save();
                canvas.translate(0, verticalOffset);
                canvas.rotate(-180, width / 2, height / 2);
                edgeEffectBottom.setSize(width, width);
                if (edgeEffectBottom.draw(canvas)) {
                    postInvalidate();
                }
                canvas.restore();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = measureHeight;
            Log.d(TAG, "规则的");
        } else {
            //以实际屏高为标准
            height = measureHeight;
        }
        //计算偏移量
        verticalOffset = totalHeight + getPaddingTop() + getPaddingBottom() - height;//如果是正直，说明内容的高度比我们的实际高度高，如果是负值说明还没填满
        setMeasuredDimension(width, height);
    }
}
