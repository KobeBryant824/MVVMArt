package com.cxh.mvvmsample.model.api.entity.event;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class Event<T> {

    private String tag;
    private T data;

    /**
     * @param tag 只发送一个标记
     */
    public Event(String tag) {
        this.tag = tag;
    }

    /**
     * @param tag  标记
     * @param data 数据
     */
    public Event(String tag, T data) {
        this.tag = tag;
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public T getData() {
        return data;
    }

}