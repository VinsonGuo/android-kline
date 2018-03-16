# android-kline
**基于MPAndroidChart的专业K线图**

**联系方式** _email/qq: gzw19931217@qq.com_  _WeChat: buck_guo_

本项目通过继承的方式定制了最新版本的**MPAndroidChart**，没有修改MPAndroidChart的源代码，所以对已经使用了MPAndroidChart的童鞋不会造成影响。

- 解决了多图表手势同步的问题
- 解决多图表**highlight**联动的问题
- 使用简单，两行代码就可以实现专业K线效果


# Demo

![demo](art/new_chart.gif)

也可以[点击这里下载](https://github.com/gzw19931217/android-kline/raw/master/art/app-debug.apk)

# What's new

- 增加了MACD、KDJ指标的显示和切换
- 增加了五日的分时图
- 优化图表的缩放，可以进行放大和缩小操作（之前只支持在初始状态下的放大操作）
- 拼接图表的缩放功能（之前如果一个手指在K线图，另一个手指在交易量图的时候，图表是不可以缩放的）
- 全屏模式的示例
- 分时图增加涨跌幅的坐标，并且涨跌幅为0%时竖直居中显示
- K线图增加最大/最小值的显示

# 使用方式

在项目**build.gradle**中添加依赖：

```
 allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
   }
   dependencies {
        compile com.github.gzw19931217:android-kline:1.0.0'
   }
```

**xml**

```xml
    <com.guoziwei.klinelib.chart.KLineView
        android:id="@+id/kline"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```


```xml
    <com.guoziwei.klinelib.chart.TimeLineView
        android:id="@+id/timeline"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

**java**  
```
       KLineView  K线图控件
       public void showKdj() 显示kdj指标
       	public void showMacd() 显示macd指标
       	public void showVolume() 显示交易量指标
       public void initData(List<HisData> hisDatas) 初始化数据，获取到数据后调用
       public void addData(HisData hisData) 图表末尾增加一个数据
       public void refreshData(float price) 刷新最后一个点的价格（不增加数据）
       
       
       TimeLineView 分时图控件
       public void initData(List<HisData> hisDatas) 初始化数据，获取到数据后调用
       public void addData(HisData hisData) 图表末尾增加一个数据
       public void refreshData(float price) 刷新最后一个点的价格（不增加数据）
       public void initDatas(List<HisData>... hisDatas) 初始化多日的数据，比如说5日的数据，就传5个list过去
       public void setLastClose(double lastClose)  设置昨天的收盘价，用于计算涨跌幅的坐标
       
       两个类共同的api：
       public void setCount(int init, int max, int min) 设置图标的可见个数，分别是初始值，最大值，最小值。比如(100,300,50)就是开始的时候100个点，最小可以缩放到300个点，最大可以放大到50个点
       
```

**注意：** 这里需要接收一个**HisData**的List，HisData需要如下的几个数据（**开盘、收盘、最高、最低、买卖量、时间**），其他的指标会根据公式计算出来

在本项目中，时间戳**date**相当于唯一的id，如果重复的话无法将这个data添加到图表中

```
  public HisData(double open, double close, double high, double low,  int vol, long date)
```

**个性化**：如果需要配置颜色，到colors.xml中配置。



研究图表花费了很多时间和精力，所以我想通过知识付费的形式与有需要的人进行分享这方面的经验，有需要的童鞋可以加入
我的知识星球来向我提问和获取提供后续的技术支持。

![知识星球](art/zsxq.png)

