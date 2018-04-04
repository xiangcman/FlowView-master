package com.library.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/**
 * Created by xiangcheng on 17/6/6.
 */

public class LineFlowLayout extends FlowLayout {
    private int mLineCount;
    private static final int DEFAULT_COUNT = 2;

    public LineFlowLayout(Context context) {
        super(context);
    }

    public LineFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initArgus(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineFlowLayout);
        mLineCount = array.getInt(R.styleable.LineFlowLayout_flow_line_count, DEFAULT_COUNT);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        totalHeight = 0;
        int lineCount = Math.min(heightLines.size(), mLineCount);
        for (int i = 0; i < lineCount; i++) {
            totalHeight += heightLines.get(i);
        }
        totalHeight += getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(width, totalHeight);
    }
}
