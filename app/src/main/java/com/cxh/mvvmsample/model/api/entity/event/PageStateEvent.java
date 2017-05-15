package com.cxh.mvvmsample.model.api.entity.event;

/**
 * Desc: 页面状态的 Event
 * Created by Hai (haigod7@gmail.com) on 2017/5/12 15:22.
 */
public class PageStateEvent extends Event {
    public static final String ON_SUCCESS = "onSuccess";
    public static final String ON_FAILED = "onFailed";

    public PageStateEvent(String tag) {
        super(tag);
    }
}
