package com.library.flowlayout;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangcheng on 17/6/2.
 * 打造一款具有滑动，并且不同高度的view会居中显示的流式布局
 */

public class FlowLayout extends ViewGroup implements FlowNotification {

    private static final String TAG = FlowLayout.class.getSimpleName();
    //主要用来处理滑动用的,正直才可以滑动的
    protected int verticalOffset;

    protected int width, height;

    private LineDes row = new LineDes();

    protected int totalHeight;

    //用来存储每一行的高度，主要让子类去实现几行的流式布局
    protected SparseArray<Float> heightLines = new SparseArray<>();

    private FlowAdapter flowAdapter;

    private boolean isLineCenter;

    private static final boolean default_line_center = false;

    @Override
    public void onChange() {
        if (flowAdapter != null) {
            removeAllViews();
            for (int i = 0; i < flowAdapter.getCount(); i++) {
                View view = View.inflate(getContext(), flowAdapter.generateLayout(i), null);
                flowAdapter.getView(i, view);
                addView(view, new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }
        }
    }

    //用来存储行信息的
    private class LineDes {
        //当前行的顶点坐标
        float cuLineTop;
        //行高度以最高的view为基准
        int lineHeight;
        //一行收集到的item
        List<ItemView> views = new ArrayList<>();

        //用完了该行的信息后进行清除的操作
        public void clearLineDes() {
            if (views.size() > 0) {
                views.clear();
            }
            lineHeight = 0;
            cuLineTop = 0;
        }
    }

    private class ItemView {
        View view;
        int heightArea;

        public ItemView(View view, int heightArea) {
            this.view = view;
            this.heightArea = heightArea;
        }
    }

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineFlowLayout);
        isLineCenter = array.getBoolean(R.styleable.LineFlowLayout_is_line_center, default_line_center);
        array.recycle();
        initArgus(context, attrs);
    }

    protected void initArgus(Context context, AttributeSet attrs) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = measureWidth;
        } else {
            //以实际屏宽为标准
            width = getContext().getResources().getDisplayMetrics().widthPixels;
        }

        Log.d(TAG, "屏高:" + getContext().getResources().getDisplayMetrics().heightPixels);

        int left = getPaddingLeft();
        int right = getPaddingRight();
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        int count = getChildCount();
        //整个view占据的高度
        totalHeight = 0;
        //当前行的高度
        int lineHeight = 0;
        int start = left;
        //当前行的起点，这个是换行的依据
        int cuLineStart = start;
        int end = width - right;
        int lineIndex = 0;
        //水平方向可用的宽度，主要考虑内边距
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidthArea = childWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            int childHeightArea = childHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            //如果当前行的起点再加上当前child的宽度还小于end坐标时
            if (cuLineStart + childWidthArea <= end) {
                cuLineStart += childWidthArea;
                lineHeight = Math.max(lineHeight, childHeightArea);
                heightLines.put(lineIndex, (float) lineHeight);
            } else {//换行了
                lineIndex++;
                cuLineStart = start;
                //换行的时候需要加上上一行的行高
                totalHeight += lineHeight;
                lineHeight = childHeightArea;
                heightLines.put(lineIndex, (float) lineHeight);
                cuLineStart += childWidthArea;
            }
            if (i == getChildCount() - 1) {
                totalHeight += lineHeight;
            }
        }
        totalHeight = totalHeight + top + bottom;

        calculateHeight(heightMode, measureHeight, totalHeight);
    }

    private void calculateHeight(int heightMode, int measureHeight, int totalHeight) {
        if (heightMode == MeasureSpec.EXACTLY) {
            height = measureHeight;
            Log.d(TAG, "规则的");
        } else {
            //以实际屏高为标准
            Log.d(TAG, "不规则的");//这里就去
            height = totalHeight;
        }

        Log.d(TAG, "totalHeight:" + totalHeight);
        Log.d(TAG, "height:" + height);
        Log.d(TAG, "verticalOffset:" + verticalOffset);
        setMeasuredDimension(width, height);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int right = getPaddingRight();
        //左边的起始坐标
        int start = left;
        int cuLineStart = start;
        int end = width - right;
        int cuLineTop = getPaddingTop();
        row.cuLineTop = cuLineTop;
        //当前行的高度
        int lineHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidthArea = childWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            int childHeightArea = childHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            if (cuLineStart + childWidthArea <= end) {
                child.layout(cuLineStart, cuLineTop, cuLineStart + childWidth, cuLineTop + childHeight);
                cuLineStart += childWidthArea;
                lineHeight = Math.max(lineHeight, childHeightArea);
                //当前行的高度保存的是该行最高的高度
                row.lineHeight = lineHeight;
                row.views.add(new ItemView(child, childHeightArea));
            } else {//换行
                //对上一行没居中显示的item进行修正
                if (isLineCenter) {
                    formatAboveLine();
                }
                cuLineStart = start;
                cuLineTop += lineHeight;
                child.layout(cuLineStart, cuLineTop, cuLineStart + childWidth, cuLineTop + childHeight);
                cuLineStart += childWidthArea;
                lineHeight = childHeightArea;
                //换行的item也需要加到行信息中
                row.lineHeight = lineHeight;
                row.views.add(new ItemView(child, childHeightArea));
                row.cuLineTop = cuLineTop;
            }
            if (i == getChildCount() - 1) {
                if (isLineCenter) {
                    formatAboveLine();
                }
            }
        }
    }

    private void formatAboveLine() {
        List<ItemView> views = row.views;
        for (int i = 0; i < views.size(); i++) {
            View view = views.get(i).view;
            ItemView itemView = views.get(i);
            //如果是当前行的高度大于了该view的高度话，此时需要重新放该view了
            if (itemView.heightArea < row.lineHeight) {
                float viewTop = row.cuLineTop + (row.lineHeight - view.getMeasuredHeight()) / 2;
                view.layout(view.getLeft(), (int) viewTop, view.getRight(),
                        (int) viewTop + view.getMeasuredHeight());
            }
        }
        row.clearLineDes();
    }

    //采用适配器模式
    public void setAdapter(FlowAdapter flowAdapter) {
        Log.d(TAG, "setAdapter");
        if (flowAdapter == null) {
            return;
        }
        this.flowAdapter = flowAdapter;
        flowAdapter.setFlowNotification(this);
        for (int i = 0; i < flowAdapter.getCount(); i++) {
            View view = View.inflate(getContext(), flowAdapter.generateLayout(i), null);
            flowAdapter.getView(i, view);
            addView(view, new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
    }
}
