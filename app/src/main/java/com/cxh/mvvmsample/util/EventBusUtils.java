package com.cxh.mvvmsample.util;

import com.cxh.mvvmsample.model.api.entity.event.PageStateEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Desc:
 * Created by Hai (haigod7@gmail.com) on 2017/5/12 15:35.
 */
public class EventBusUtils {

    public static void post(Object object){
        EventBus.getDefault().post(object);
    }

    public static void postSuccessEvent() {
        EventBus.getDefault().post(new PageStateEvent(PageStateEvent.ON_SUCCESS));
    }

    public static void postFailedEvent() {
        EventBus.getDefault().post(new PageStateEvent(PageStateEvent.ON_FAILED));
    }

}
