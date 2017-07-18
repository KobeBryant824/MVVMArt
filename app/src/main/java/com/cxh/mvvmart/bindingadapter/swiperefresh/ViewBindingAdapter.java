package com.cxh.mvvmart.bindingadapter.swiperefresh;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

import com.cxh.mvvmart.bindingadapter.ReplyCommand;


/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class ViewBindingAdapter {

    @BindingAdapter({"onRefreshCommand"})
    public static void onRefreshCommand(SwipeRefreshLayout swipeRefreshLayout, final ReplyCommand onRefreshCommand) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (onRefreshCommand != null) {
                onRefreshCommand.execute();
            }
        });
    }

}
