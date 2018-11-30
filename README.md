# MVP框架
博客地址：https://www.jianshu.com/u/2ce061e11334                       
利用目前主流的开源库搭建的一套mvp开发模式，已经正式投入公司新项目开发，暂时没遇到什么问题，如果有问题，欢迎issues，其中有些库已经有新版本，如有业务需要，可以更新自行使用                   
![](https://github.com/tenney-tang/TestMVP/blob/master/picture/p4.png)
![](https://github.com/tenney-tang/TestMVP/blob/master/picture/p5.png)

项目用的是MVP + RxJava + Dagger2 + Retrofit + okhttp
# 说明:                        
1)所有网络请求限制了不能在activity里面和Fragment里面发起,必须在MVP的presenter里面进行网络请
求这样便于管理MVP的View与presenter的代码     
2)如果业务逻辑需要,一定要在Activity和Fragment里面发起网络请求,那么可以通过Application的
mAppComponent对象,获取data module的DataManager对象,调用网络请求的client      
# 工程结构:
	工程分为两个主要的module
	app module:主工程模块(包含各种基类\MVP\工具类\自定义view)
	    基类:                        
			普通Activity的基类 --> BaseActivity(这个基类里面限制了网络请求)                
				 
			普通Fragment的基类 --> BaseFragment            
				 
			封装了RecyclerView列表带上拉刷新下拉加载的Fragment基类 --> BaseRefreshRecyclerFragment             
				说明:封装的RecyclerView对象 --> mRecyclerView
			     	封装的下拉刷新对象 --> mRefreshLayout
			     	封装的Adapter对象 --> mAdapter
			     	封装的LayoutManager对象 --> mLayoutManager              
		 
	    MVP:
			关联了MVP的Fragment基类 --> MVPFragmentView   
				说明:示例代码参考工程Zipfragment类   
 			关联了MVP的Presenter基类 --> MVPFragmentPresenter   
                                说明:示例代码参考工程ZipPresenter类  
# APP层：使用开源框架 
* BaseRecyclerViewAdapterHelper       
BaseMultiItemQuickAdapter 可加载不同类型布局类型，  openLoadAnimation   加载动画        

* butterknife   
注解，替代findViewById，注解点击事件

* dagger2   
依赖注入 单例模式 @Singleton ， @Inject即可自动注入对象，@Component就是说当我们的类不属于各种归类的时候我们就可以使用@Component来标注这个类 ,Dagger就是用来创造一个容器，所有需要被依赖的对象在Dagger的容器中实例化，并通过Dagger注入到合适的地方，实现解耦，MVP框架就是为解耦而生，因此MVP和Dagger是非常完美

* rxlifecycle  
MVPFragmentPresenter在使用rxjava的时候，如果没有及时解除订阅，在退出activity的时候，异步线程还在执行，对activity还存在引用，此时就会产生内存泄漏，RxLifecycle就是为了解决rxjava导致的内存泄漏而产生的

* LeakCanary  
检查内存泄露

* Agentweb   
替代webview，比webview更强大  
# data 层：使用开源框架

* rxjava          
  核心Observable（被观察者）可以理解为事件的发送者
  Observer（观察者）Observer可以理解为事件的接收者，就好像快递的接收者
  Subscriber （订阅）Subscriber 绑定两者，请求接口和请求回来的数据

 * okhttp             
 用于网络请求
 * Retrofit            
 是把所有的网络请求封装起来 结合rxjava +okhttp  + Retrofit 

* com.facebook.stetho:stetho          
okhttp拦截器 直接在浏览器抓包chrome://inspect 要翻墙或者下载chrome inspect 离线调试-工具包

* klog      
用于日志输出类同 logger ,可现实日资打印具体位置
* LocalCache 文件，对象，等等等等

# 部分简单实用介绍

BaseMultiItemQuickAdapter 多类型的使用

RxJava+Retrofit 一次合并多个请求代码片段如下           
```
@Override
    public void refresh(int menuVersion, int menuSid, int settingsId, int cityId, int provinceId) {
        //创建一个被观察者
        Observable<BaseBean<ZipBean>> zipObservable = getAppComponent().getDataManeger().getDataStore().getMsgV3(cityId,provinceId);
        Observable<BaseBean<TestBean>> testObservable = getAppComponent().getDataManeger().getDataStore().requestTestData( menuVersion, menuSid, settingsId);

        Observable<ZipAndTestBean>zipAndTestBeanObservable =zipObservable.zipWith(testObservable, new Func2<BaseBean<ZipBean>, BaseBean<TestBean>, ZipAndTestBean>() {
            @Override
            public ZipAndTestBean call(BaseBean<ZipBean> zipBeanBaseBean, BaseBean<TestBean> testBeanBaseBean) {
                ZipAndTestBean zipAndTestBean = new ZipAndTestBean();
                        zipAndTestBean.zipBean = zipBeanBaseBean.data;
                        zipAndTestBean.testBean = testBeanBaseBean.data;
                        return zipAndTestBean;
            }
        });
        zipAndTestBeanObservable.compose(this.<ZipAndTestBean>bindToLifecycle())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZipAndTestBean>() {
                    @Override
                    public void onCompleted() {
                        mView.onFinishRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFinishError();
                    }

                    @Override
                    public void onNext(ZipAndTestBean zipAndTestBean) {
                         zipList.clear();
                         testList.clear();
                         zipList.addAll(zipAndTestBean.zipBean.dataList);
                         testList.addAll(zipAndTestBean.testBean.dataList);

                    }
                });
    }
```

rxbus 不同的fragment进行数据传递

多界面（如登录注册验证码， 设置之类）不用activity跳转，fragment自由切换 
（上述都有支持）

# 自定义控件有     
* ExactRefreshLayout    
  准确度高的下拉刷新,手势只有在往下的时候才出现下拉的球
* RecycleViewDivider         
  RecycleView自定义分割线 横向，纵向，图片，颜色，都支持
* SimpleToolbar     
  全局的toolbar，包括了，左边返回 中间标题，右边关闭，右边第二个图标
  （具体还可拓展，根据自己需求来，里面跟多精彩巧妙设计，等待大家去自行发掘）
 
  
  ## Android进阶汇Q群：515769388
  
   __终于写完了，第一次上传github，累死了，不过回头看看，再累也是值得滴~__





