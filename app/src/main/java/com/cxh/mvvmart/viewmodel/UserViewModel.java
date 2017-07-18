package com.cxh.mvvmart.viewmodel;

import android.app.Activity;
import android.databinding.ObservableField;

import com.cxh.mvvmart.base.BaseViewModel;
import com.cxh.mvvmart.bindingadapter.ReplyCommand;
import com.cxh.mvvmart.callback.OnRequestListener;
import com.cxh.mvvmart.model.entity.WelcomeEntity;
import com.cxh.mvvmart.model.repository.UserRepository;
import com.cxh.mvvmart.ui.activity.UserActivity;
import com.cxh.mvvmart.util.ToastUtils;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.functions.Action;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class UserViewModel implements BaseViewModel {

    public final ObservableField<String> mPath = new ObservableField<>();
    public final ObservableField<WelcomeEntity> mWelcomeEntity = new ObservableField<>();

    private static int sIndex = 0;
    private String mData;
    private final UserRepository mUserRepository;
    private UserActivity mUserActivity;

    @Inject
    UserViewModel(Activity activity) {
        mUserActivity = (UserActivity) activity;
        mUserRepository = new UserRepository();
        loadData();
    }

    @Override
    public void loadData() {
        mUserRepository.requestData(mUserActivity, new OnRequestListener<WelcomeEntity>() {

            @Override
            public void onSuccess(WelcomeEntity welcomeEntity) {
                mUserActivity.showContent();

                mData = welcomeEntity.getData().toString();
                sIndex++;
                if (sIndex % 2 == 0) {
                    mWelcomeEntity.set(welcomeEntity);
                    mPath.set("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-02-926821_1453024764952889_775781470_n.jpg"); // 假设这个URL是从服务器获取的

                } else {
                    Gson gson = new Gson();
                    WelcomeEntity entity = gson.fromJson("{\"code\":100,\"msg\":\"请求失败\",\"name\":{\"time\":\"2016-12-12\",\"noUpDate\":0,\"name\":\"阳光采购\",\"versionId\":\"1.2\",\"downUrl\":\"http://210.51.183.101:8081/group2/M00/00/00/ygcg.apk\"}}", WelcomeEntity.class);
                    mWelcomeEntity.set(entity);
                    mPath.set("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-28-18094719_120129648541065_8356500748640452608_n.jpg");
                }

            }

            @Override
            public void onFailed() {
                mUserActivity.showError();
            }
        });
    }

    public final ReplyCommand mReplyCommand = new ReplyCommand(new Action() {

        @Override
        public void run() throws Exception {
            ToastUtils.show(mData);
        }
    });
}
