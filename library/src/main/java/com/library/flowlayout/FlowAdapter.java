package com.library.flowlayout;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by xiangcheng on 17/6/2.
 */

public abstract class FlowAdapter<T extends Object> {
    protected Context context;

    private List<T> list;

    private FlowNotification flowNotification;

    public void setFlowNotification(FlowNotification flowNotification) {
        this.flowNotification = flowNotification;
    }

    public FlowAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
    }

    public int getCount() {
        return list.size();
    }

    /**
     * 子类需要的item布局
     *
     * @return
     */
    protected abstract int generateLayout(int position);

    public void getView(int position, View parent) {
        getView(list.get(position), parent);
    }

    public void notifyDataChanged() {
        if (flowNotification != null) {
            flowNotification.onChange();
        }
    }

    protected abstract void getView(T o, View parent);
}
