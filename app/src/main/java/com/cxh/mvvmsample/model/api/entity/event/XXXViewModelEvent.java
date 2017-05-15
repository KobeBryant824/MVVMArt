package com.cxh.mvvmsample.model.api.entity.event;

/**
 * Desc:
 * Created by Hai (haigod7@gmail.com) on 2017/5/12 15:42.
 */
public class XXXViewModelEvent {
    private String data;

    public XXXViewModelEvent(String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
