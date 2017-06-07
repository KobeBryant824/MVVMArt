package com.cxh.mvvmsample.base;


/**
 * View、Model 的中间层、控制中心，主要负责数据处理
 * Created by Hai (haigod7@gmail.com) on 2017/3/14 10:02.
 */
public interface BaseViewModel {

    void loadData(); // 获取 M 层请求的数据结果
}
