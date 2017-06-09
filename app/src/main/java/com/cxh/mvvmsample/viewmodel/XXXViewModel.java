package com.cxh.mvvmsample.viewmodel;

import android.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.cxh.mvvmsample.base.BaseViewModel;
import com.cxh.mvvmsample.bindingadapter.ReplyCommand;
import com.cxh.mvvmsample.listener.OnRequestListener;
import com.cxh.mvvmsample.model.api.XXXApi;
import com.cxh.mvvmsample.model.api.entity.event.XXXVMEvent;
import com.cxh.mvvmsample.model.repository.XXXRepository;
import com.cxh.mvvmsample.util.EventBusUtils;
import com.socks.library.KLog;

import javax.inject.Inject;

import io.reactivex.functions.Action;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class XXXViewModel implements BaseViewModel {

    private final XXXRepository mXXXRepository;

    public final ObservableField<String> mPath = new ObservableField<>();
    public final ObservableField<XXXApi.WelcomeEntity> mWelcomeEntity = new ObservableField<>();

    private static int index = 0;
    private String mData;

    @Inject
    public XXXViewModel() {
        mXXXRepository = new XXXRepository();
        loadData();
    }

    @Override
    public void loadData() {
        mXXXRepository.requestData(new OnRequestListener<XXXApi.WelcomeEntity>() {

            @Override
            public void onSuccess(XXXApi.WelcomeEntity welcomeEntity) {
                EventBusUtils.postSuccessEvent();

                mData = welcomeEntity.getData().toString();
                index++;
                if (index % 2 == 0) {
                    mWelcomeEntity.set(welcomeEntity);

                    mPath.set("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-02-926821_1453024764952889_775781470_n.jpg"); // 假设这个URL是从服务器获取的

                } else {
                    XXXApi.WelcomeEntity entity = JSON.parseObject("{\"code\":100,\"msg\":\"请求失败\",\"name\":{\"time\":\"2016-12-12\",\"noUpDate\":0,\"name\":\"阳光采购\",\"versionId\":\"1.2\",\"downUrl\":\"http://210.51.183.101:8081/group2/M00/00/00/ygcg.apk\"}}", XXXApi.WelcomeEntity.class);
                    mWelcomeEntity.set(entity);
                    mPath.set("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-28-18094719_120129648541065_8356500748640452608_n.jpg");
                }

            }

            @Override
            public void onFailed() {
                EventBusUtils.postFailedEvent();
            }
        });
    }

    public final ReplyCommand mReplyCommand = new ReplyCommand(new Action() {

        @Override
        public void run() throws Exception {
            EventBusUtils.post(new XXXVMEvent(mData));
        }
    });
}
