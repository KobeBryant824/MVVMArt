package com.cxh.mvvmsample.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.cxh.mvvmsample.base.BaseViewModel;
import com.cxh.mvvmsample.bindingadapter.ReplyCommand;
import com.cxh.mvvmsample.listener.OnRequestListener;
import com.cxh.mvvmsample.model.api.XXXApi;
import com.cxh.mvvmsample.model.repository.XXXDataRepository;
import com.cxh.mvvmsample.util.ToastUtils;
import com.cxh.mvvmsample.view.activity.XXXActivity;

import io.reactivex.functions.Action;

/**
 *  Created by Hai (haigod7@gmail.com) on 2017/4/26 15:28.
 */
public class XXXViewModel implements BaseViewModel {
    private Context mContext;
    public final ObservableField<String> path = new ObservableField<>();
    public final ObservableField<XXXApi.WelcomeEntity> mWelcomeEntity = new ObservableField<>();
    private static int index = 0;

    public XXXViewModel(Activity activity) {
        mContext = activity;
        // 请求M层数据
         new XXXDataRepository().requestData(new OnRequestListener<XXXApi.WelcomeEntity>() {
            @Override
            public void onSuccess(XXXApi.WelcomeEntity welcomeEntity) {
                ((XXXActivity) mContext).showContent(); // 违背了ViewModel只处理数据不操作UI，不嫌麻烦可以每个页面都添加PageLayout的三种展示状态

                index++;
                if (index % 2 == 0) {
                    mWelcomeEntity.set(welcomeEntity);
                    path.set("http://wallpaper.macappstore.net/d/file/2016/12-07/ff2cbf8632ee23503fc9ce7be6b89314.jpg"); // 假设这个URL是从服务器获取的

                } else {
                    XXXApi.WelcomeEntity entity = JSON.parseObject("{\"code\":100,\"msg\":\"请求失败\",\"data\":{\"time\":\"2016-12-12\",\"noUpDate\":0,\"name\":\"阳光采购\",\"versionId\":\"1.2\",\"downUrl\":\"http://210.51.183.101:8081/group2/M00/00/00/ygcg.apk\"}}", XXXApi.WelcomeEntity.class);
                    mWelcomeEntity.set(entity);
                    path.set("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-25-13651793_897557617014845_571817176_n.jpg");
                }

            }

            @Override
            public void onFailed() {
                ((XXXActivity) mContext).showError();
            }
        });
    }

    public final ReplyCommand mReplyCommand = new ReplyCommand(new Action() {
        @Override
        public void run() throws Exception {
            ToastUtils.showToast(mContext, "click a replyCommand");
        }
    });

}
