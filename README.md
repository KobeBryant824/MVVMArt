## MVVM
![screenshot_1](./images/mvvm.png)
![screenshot_1](./images/databinding.png)

- View: 对应于 Activity 和 XML，负责 View 的绘制以及与用户的交互。
- ViewModel: 负责完成 View 与 Model 间的交互，负责业务逻辑。
- Model: 实体模型（数据的获取、存储、数据状态变化）
   
MVVM 的目标和思想与 MVP 类似，利用数据绑定 (Data Binding)、依赖属性 (Dependency Property)、命令 (Command)、路由事件 (Routed Event)等新特性，打造了一个更加灵活高效的架构。


## 弊端 ##
- 数据绑定使得 Bug 很难被调试，错误位置难定位
- 数据双向绑定不利于代码重用。客户端开发最常用的重用是 View，但是数据双向绑定技术，让你在一个 View 都绑定了一个 Model，不同模块的 Model 都不同，那就不能简单重用 View 了。 


## Libs
- [PageStateManager](https://github.com/hss01248/PageStateManager)
- [KLog](https://github.com/ZhaoKaiQiang/KLog)
- [Gson](https://github.com/google/gson)
- [EventBus](https://github.com/greenrobot/EventBus)
- [Fresco](https://github.com/facebook/fresco)
- [Retrofit](https://github.com/square/retrofit)
- [RxJava2](https://github.com/ReactiveX/RxJava)
- [RxLifecycle](https://github.com/trello/RxLifecycle)
- [RxPermissions](https://github.com/tbruyelle/RxPermissions)
- [RxBinding](https://github.com/JakeWharton/RxBinding)
- [binding-collection-adapter](https://github.com/evant/binding-collection-adapter)
- [Dagger2](https://github.com/google/dagger)
- [Leakcanary](https://github.com/square/leakcanary)


## Thanks
- [Data Binding Library](https://developer.android.com/topic/libraries/data-binding/index.html)
- [Data Binding 中文翻译](http://www.jianshu.com/p/b1df61a4df77)
- [如何构建 Android MVVM 应用框架](http://tech.meituan.com/android_mvvm.html)


## License
```
Copyright 2017 XinHai Chen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
