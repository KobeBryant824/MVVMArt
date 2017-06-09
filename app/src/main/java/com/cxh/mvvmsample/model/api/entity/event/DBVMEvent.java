package com.cxh.mvvmsample.model.api.entity.event;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class DBVMEvent extends Event<String>{

    public static final String REPLY_COMMAND = "ReplyCommand";
    public static final String ONITEMCLICKLISTENER = "OnItemClickListener";

    public DBVMEvent(String tag, String data) {
        super(tag, data);
    }
}
