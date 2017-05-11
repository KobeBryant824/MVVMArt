package com.cxh.mvvmsample.viewmodel;

import android.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.cxh.mvvmsample.base.BaseViewModel;
import com.cxh.mvvmsample.bindingadapter.ReplyCommand;
import com.cxh.mvvmsample.listener.OnRequestListener;
import com.cxh.mvvmsample.model.api.XXXApi;
import com.cxh.mvvmsample.model.api.entity.Event;
import com.cxh.mvvmsample.model.repository.XXXDataRepository;
import com.cxh.mvvmsample.util.EventBusUtils;
import com.socks.library.KLog;

import io.reactivex.functions.Action;

import static com.cxh.mvvmsample.AppConstants.ON_FAILED;
import static com.cxh.mvvmsample.AppConstants.ON_SUCCESS;
import static com.cxh.mvvmsample.AppConstants.XXXVIEWMODEL_MREPLYCOMMAND;

/**
 * 数据处理中心，不处理UI，不持有Activity
 * Created by Hai (haigod7@gmail.com) on 2017/4/26 15:28.
 */
public class XXXViewModel implements BaseViewModel {

    public final ObservableField<String> mPath = new ObservableField<>();
    public final ObservableField<XXXApi.WelcomeEntity> mWelcomeEntity = new ObservableField<>();
    private static int index = 0;
    private String mData;

    public XXXViewModel() {
        getDataFromModel();
    }

    @Override
    public void getDataFromModel() {
        // 请求 M 层数据
        new XXXDataRepository().requestData(new OnRequestListener<XXXApi.WelcomeEntity>() {
            @Override
            public void onSuccess(XXXApi.WelcomeEntity welcomeEntity) {
                KLog.e(System.currentTimeMillis());

                EventBusUtils.post(ON_SUCCESS);

                mData = welcomeEntity.getData().toString();
                index++;
                if (index % 2 == 0) {
                    mWelcomeEntity.set(welcomeEntity);

                    // 报错：Crash: trying to use a recycled bitmap ，大图用fresco加载要进行剪裁，
                    mPath.set("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-02-926821_1453024764952889_775781470_n.jpg"); // 假设这个URL是从服务器获取的

                } else {
                    XXXApi.WelcomeEntity entity = JSON.parseObject("{\"code\":100,\"msg\":\"请求失败\",\"name\":{\"time\":\"2016-12-12\",\"noUpDate\":0,\"name\":\"阳光采购\",\"versionId\":\"1.2\",\"downUrl\":\"http://210.51.183.101:8081/group2/M00/00/00/ygcg.apk\"}}", XXXApi.WelcomeEntity.class);
                    mWelcomeEntity.set(entity);
                    mPath.set("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-28-18094719_120129648541065_8356500748640452608_n.jpg");
                }

            }

            @Override
            public void onFailed() {
                EventBusUtils.post(ON_FAILED);
            }
        });
    }

    public final ReplyCommand mReplyCommand = new ReplyCommand(new Action() {
        @Override
        public void run() throws Exception {
            EventBusUtils.post(new Event<>(XXXVIEWMODEL_MREPLYCOMMAND, mData));
        }
    });
}
