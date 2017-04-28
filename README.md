## 简述
MVVM的目标和思想与MVP类似，利用数据绑定(Data Binding)、依赖属性(Dependency Property)、命令(Command)、路由事件(Routed Event)等新特性，打造了一个更加灵活高效的架构。

- View: 对应于Activity和XML，负责View的绘制以及与用户的交互。
- Model: 实体模型（数据的获取、存储、数据状态变化）
- ViewModel: 负责完成View与Model间的交互，负责业务逻辑。

## Libraries
- [PermissionsDispatcher](https://github.com/hotchemi/PermissionsDispatcher)
- [KLog](https://github.com/ZhaoKaiQiang/KLog)
- [FastJson](https://github.com/alibaba/fastjson)
- [EventBus](https://github.com/greenrobot/EventBus)
- [Fresco](https://github.com/facebook/fresco)
- [LRecyclerView](https://github.com/jdsjlzx/LRecyclerView)
- [RxBinding](https://github.com/JakeWharton/RxBinding)
- [RxJava2](https://github.com/ReactiveX/RxJava)
- [Retrofit](https://github.com/square/retrofit)
- [Leakcanary](https://github.com/square/leakcanary)
- [AndroidAutoLayout](https://github.com/hongyangAndroid/AndroidAutoLayout)
- [PageStateManager](https://github.com/hss01248/PageStateManager)


## Thanks
- [如何构建Android MVVM 应用框架](http://tech.meituan.com/android_mvvm.html)


## 弊端 ##
- 错误难定位，错误: 程序包xxx.xxx.databinding不存在，可能是数据绑定格式错误、语法错误

## License
   Apache-2.0
