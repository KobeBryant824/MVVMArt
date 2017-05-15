package com.cxh.mvvmsample.model.api.entity.event;

/**
 * Desc:
 * Created by Hai (haigod7@gmail.com) on 2017/5/12 15:47.
 */
public class DataBindingViewModelEvent extends Event<String>{
    public static final String REPLY_COMMAND = "ReplyCommand";
    public static final String ONITEMCLICKLISTENER = "OnItemClickListener";

    public DataBindingViewModelEvent(String tag, String data) {
        super(tag, data);
    }
}
