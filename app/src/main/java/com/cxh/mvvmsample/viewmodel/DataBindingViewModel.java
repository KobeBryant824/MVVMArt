package com.cxh.mvvmsample.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cxh.mvvmsample.BR;
import com.cxh.mvvmsample.R;
import com.cxh.mvvmsample.base.BaseViewModel;
import com.cxh.mvvmsample.bindingadapter.ReplyCommand;
import com.cxh.mvvmsample.listener.OnItemClickListener;
import com.cxh.mvvmsample.model.api.entity.User;
import com.cxh.mvvmsample.model.api.entity.event.DataBindingViewModelEvent;
import com.cxh.mvvmsample.model.api.entity.event.PageStateEvent;
import com.cxh.mvvmsample.util.EventBusUtils;
import com.cxh.mvvmsample.view.adapter.XXXRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass;

import static com.cxh.mvvmsample.model.api.entity.event.DataBindingViewModelEvent.ONITEMCLICKLISTENER;
import static com.cxh.mvvmsample.model.api.entity.event.DataBindingViewModelEvent.REPLY_COMMAND;

/**
 * Desc:
 * Created by Hai (haigod7@gmail.com) on 2017/5/11 15:01.
 */
public class DataBindingViewModel implements BaseViewModel {
    private boolean first = true;

    private OnItemClickListener listener = msg -> EventBusUtils.post(new DataBindingViewModelEvent(ONITEMCLICKLISTENER, msg));

    // 单一item
    public final ItemBinding<User> itemBinding = ItemBinding.<User>of(BR.item, R.layout.list_item).bindExtra(BR.listener, listener);

    public final ObservableList<User> items0 = new ObservableArrayList<>();
    public final ObservableList<User> items = new ObservableArrayList<>();

    public final XXXRecyclerViewAdapter<Object> adapter = new XXXRecyclerViewAdapter<>();

    /**
     * Items merged with a header on top and footer on bottom.
     */
    public final MergeObservableList<Object> headerFooterItems = new MergeObservableList<>()
            .insertItem("Header")
            .insertList(items0)
            .insertItem("Footer");

    // 添加多种itemstyle，类型必须对应上
    public final OnItemBindClass<Object> multipleItems = new OnItemBindClass<>()
            .map(String.class, BR.item, R.layout.list_item_header)
            .map(User.class, BR.item, R.layout.list_item);

    public final ViewStyle viewStyle = new ViewStyle();

    public class ViewStyle {
        public final ObservableBoolean isRefreshing = new ObservableBoolean(true);
    }

    public DataBindingViewModel() {
        getDataFromModel();
    }

    @Override
    public void getDataFromModel() {
        for (int i = 0; i < 3; i++) {
            items0.add(new User("Kobe" + i, "Bryant"));
        }

        User user = new User("Kobe", "Bryant", 37);
        EventBusUtils.post(user);

        loadData();
    }

    /**
     * 假设从 Model 获取到的数据
     */
    private void loadData() {
        viewStyle.isRefreshing.set(true);
        items.clear();
        // 模拟网络请求
        Observable
                .create((ObservableOnSubscribe<List<User>>) emitter -> {
                    List<User> userList = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        userList.add(new User("Kobe" + i, "Bryant"));
                    }
                    emitter.onNext(userList);
                })
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    items.addAll(list);
                    viewStyle.isRefreshing.set(false);
                    if (first) {
                        first = false;
                        EventBusUtils.postSuccessEvent();
                    }
                });

    }

    private void loadMoreData(){
        Observable
                .create((ObservableOnSubscribe<List<User>>) emitter -> {
                    List<User> userList = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        userList.add(new User("More" + i, "Bryant"));
                    }
                    emitter.onNext(userList);
                })
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items::addAll);
    }

    public void addItem() {
        items.add(new User("New Data", "Bryant"));
    }

    public void removeItem() {
        if (items.size() > 1) {
            items.remove(items.size() - 1);
        }
    }

    public final ReplyCommand mReplyCommand = new ReplyCommand(() -> EventBusUtils.post(new DataBindingViewModelEvent(REPLY_COMMAND, "ReplyCommand")));

    public final ReplyCommand onRefreshCommand = new ReplyCommand(DataBindingViewModel.this::loadData);

    public final ReplyCommand<Integer> onLoadMoreCommand = new ReplyCommand<>(integer -> loadMoreData());

    public final BindingRecyclerViewAdapter.ViewHolderFactory viewHolder = binding -> new MyAwesomeViewHolder(binding.getRoot());

    /**
     * 自定义 ViewHolder
     */
    private static class MyAwesomeViewHolder extends RecyclerView.ViewHolder {
        MyAwesomeViewHolder(View itemView) {
            super(itemView);
        }
    }
}
