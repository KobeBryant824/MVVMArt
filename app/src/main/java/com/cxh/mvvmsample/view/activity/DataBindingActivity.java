package com.cxh.mvvmsample.view.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cxh.mvvmsample.BR;
import com.cxh.mvvmsample.R;
import com.cxh.mvvmsample.base.BaseAutoActivity;
import com.cxh.mvvmsample.bindingadapter.ReplyCommand;
import com.cxh.mvvmsample.databinding.MyBinding;
import com.cxh.mvvmsample.databinding.ViewStub1Binding;
import com.cxh.mvvmsample.listener.OkListener;
import com.cxh.mvvmsample.listener.OnItemClickListener;
import com.cxh.mvvmsample.model.api.entity.User;
import com.cxh.mvvmsample.util.ToastUtils;
import com.cxh.mvvmsample.view.adapter.XXXRecyclerViewAdapter;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass;

/**
 * Desc, databinding一些简单使用，偷懒没有写viewmodel
 * Created by Hai (haigod7@gmail.com) on 2017/4/28 13:58.
 */
public class DataBindingActivity extends BaseAutoActivity implements OkListener {

    private MyBinding mBinding;

    private OnItemClickListener listener = item -> ToastUtils.showToast(DataBindingActivity.this, item);

    public final ItemBinding<User> itemBinding = ItemBinding.<User>of(BR.item, R.layout.list_item).bindExtra(BR.listener, listener);

    public final ObservableList<User> items = new ObservableArrayList<>();

    public final XXXRecyclerViewAdapter<Object> adapter = new XXXRecyclerViewAdapter<>();

    /**
     * Items merged with a header on top and footer on bottom.
     */
    public final MergeObservableList<Object> headerFooterItems = new MergeObservableList<>()
            .insertItem("Header")
            .insertList(items)
            .insertItem("Footer");

    public final OnItemBindClass<Object> multipleItems = new OnItemBindClass<>()
            .map(String.class, BR.item, R.layout.list_item_header)
            .map(User.class, BR.item, R.layout.list_item);

    @Override
    protected void setContentView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
    }

    @Override
    protected void RetryEvent() {

    }

    @Override
    protected void initViewsAndEvents() {
        mPageStateManager.showContent();
        User user = new User("Kobe", "Bryant", 37);
        mBinding.setUser(user);
        mBinding.setOkText("hello点我");
        mBinding.setListener(this);
        mBinding.setActivity(this);

        for (int i = 0; i < 3; i++) {
            items.add(new User("Kobe" + i, "Bryant"));
        }

        mBinding.viewStub.setOnInflateListener((stub, inflated) -> {
            ViewStub1Binding binding = DataBindingUtil.bind(inflated);
            binding.setUser(user);
        });

    }


    @Override
    public void onClickOk(View view) {
        ToastUtils.showToast(this, "OkListener");
    }

    public void addItem() {
        items.add(new User("New", "Bryant"));
    }

    public void removeItem() {
        if (items.size() > 1) {
            items.remove(items.size() - 1);
        }
    }

    public void inflateViewStub(View view) {
        if (!mBinding.viewStub.isInflated()) {
            mBinding.viewStub.getViewStub().inflate();
        }
    }

    public final ReplyCommand mReplyCommand = new ReplyCommand(() -> ToastUtils.showToast(DataBindingActivity.this, "ReplyCommand"));

    public final BindingRecyclerViewAdapter.ViewHolderFactory viewHolder = binding -> new MyAwesomeViewHolder(binding.getRoot());

   /**
    * 自定义ViewHolder
    */
    private static class MyAwesomeViewHolder extends RecyclerView.ViewHolder {
        public MyAwesomeViewHolder(View itemView) {
            super(itemView);
        }
    }
}
