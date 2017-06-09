package com.cxh.mvvmsample.util;

import com.cxh.mvvmsample.model.api.entity.event.PageStateEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
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
