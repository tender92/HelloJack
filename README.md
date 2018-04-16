<h1 align="center">
  <center><font size="6">HelloTender是我从接触Android到现今所学知识的汇总</font></center>
	<br/>
</h1>



### 整体架构
- 基础框架：MVP + RxJava框架。MVP框架使UI逻辑和业务逻辑分离，并且提取接口至Contract层，使代码清晰明了。
- 业务框架：部分模块发开发（未完全解耦），包括业务模块、基础公共模块、百度语音模块、百度定位模块、极光推送模块、路由模块、友盟分享和统计模块。
- 使用Android Studio的自定义模版开发，对于多人开发可提高效率。
- 沉浸式状态栏实现。

### 业务模块主要实现功能
#### 微信6.5.7大部位UI实现
- BottomNavigationBar配合ViewPager实现底部导航栏加左右滑动。
- Realm实现我的好友数据库CRUD操作；自定义QuickIndexBar实现快速导航栏。
- 好友聊天界面;引入emoji_library.aar实现表情包聊天；语音聊天动画效果；待完善。
- ZXingView扫一扫；生成二维码。
- 设置页面采用DrawerLayout + NavigationView实现左滑菜单栏效果。
- 仿微信九宫格图片选择ImagePicker；图片在线本地加载ImageLoader；图片缩放PhotoView。
- CityPicker省市区联动选择器；CityPicker单个城市选择。
#### Todo-Task实现
- Realm数据库任务列表、任务详情的CRUD操作。
- FloatingActionBar实现折叠式悬浮按钮。
- LQRRecyclerView实现任务列表集和列表任务集，LQRAdapterForRecyclerView简单适配。
- MenuItem实现标题可在Toolbar上动态编辑。
#### 咕咚翻译实现
- 使用Retrofit2作为网络请求框架。
- 集成百度翻译、Google翻译、金山翻译、有道翻译。
#### 零零散散
- Once一次性使用框架。
- CoordinatorLayout + AppBarLayout + CollapsingToolbarLayout + Toolbar 实现标题栏可折叠。
- BottomSheet实现底部弹出框。
- TabLayout实现顶部选项卡。
- 自定义SwipeListView，实现上拉加载更多、下拉刷新、左滑实现删除效果、下滑多个Item数出现返回顶部按钮；引导动画实现。
- Dagger2框架简单实现。
