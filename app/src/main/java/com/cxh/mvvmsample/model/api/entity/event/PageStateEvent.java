package com.cxh.mvvmsample.model.api.entity.event;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class PageStateEvent extends Event {

    public static final String ON_SUCCESS = "onSuccess";
    public static final String ON_FAILED = "onFailed";

    public PageStateEvent(String tag) {
        super(tag);
    }
}
