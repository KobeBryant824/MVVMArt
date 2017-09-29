package com.cxh.mvvmart.viewmodel;

import android.app.Activity;
import android.databinding.ObservableField;

import com.cxh.mvvmart.App;
import com.cxh.mvvmart.base.BaseViewModel;
import com.cxh.mvvmart.bindingadapter.ReplyCommand;
import com.cxh.mvvmart.rx.RxActivityObserver;
import com.cxh.mvvmart.rx.RxScheduler;
import com.cxh.mvvmart.rx.function.HttpResultFunc;
import com.cxh.mvvmart.ui.activity.UserActivity;
import com.cxh.mvvmart.util.ToastUtils;

import javax.inject.Inject;

import io.reactivex.functions.Action;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class UserViewModel implements BaseViewModel {

    public final ObservableField<String> mPath = new ObservableField<>();
    public final ObservableField<String> mStrarsStr = new ObservableField<>();

    private static int sIndex = 0;
    private String mData;
    private UserActivity mUserActivity;

    @Inject
    UserViewModel(Activity activity) {
        mUserActivity = (UserActivity) activity;
        loadData();
    }

    @Override
    public void loadData() {
        App.RestfulApi()
                .getStargazers()
                .onErrorResumeNext(new HttpResultFunc<>())
                .compose(RxScheduler.switchSchedulers(mUserActivity))
                .subscribe(new RxActivityObserver<String, UserActivity>(mUserActivity) {
                    @Override
                    protected void refreshUI(String data) {
                        mData = data;
                        sIndex ++;
                        if (sIndex % 2 == 0){
                            mPath.set("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-02-926821_1453024764952889_775781470_n.jpg");
                        }else {
                            mPath.set("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-28-18094719_120129648541065_8356500748640452608_n.jpg");
                        }
                        mStrarsStr.set(data);
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
