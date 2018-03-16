# android-kline
**基于MPAndroidChart的专业K线图**

**联系方式** _email/qq gzw19931217@qq.com_ 

本项目通过继承的方式定制了最新版本的**MPAndroidChart**，没有修改MPAndroidChart的源代码，所以对已经使用了MPAndroidChart的童鞋不会造成影响。

- 丰富的个性化接口，支持图表个性化定制。
- 解决了多图表手势同步的问题
- 解决多图表**highlight**联动的问题
- 使用简单，两行代码就可以实现专业K线效果


# Demo

![demo](art/kchart.gif)


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
        compile com.github.gzw19931217:android-kline:0.1.3'
   }
```

**xml**

```xml
    <com.guoziwei.klinelib.chart.KLineView
        android:id="@+id/kline"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

**java**  init方法只能调用其中的一个
```
        KLineView kLineView = (KLineView) findViewById(R.id.kline);
        // 分时图
        kLineView.initChartPriceData(list);
        // K线图
        kLineView.initChartKData(list);
```

**注意：** 这里需要接收一个**HisData**的List，HisData需要如下的几个数据（**开盘、收盘、最高、最低、买卖量、时间**），其他的指标会根据公式计算出来

在本项目中，时间戳**date**相当于唯一的id，如果重复的话无法将这个data添加到图表中

```
  public HisData(double open, double close, double high, double low,  int vol, long date)
```

**个性化**：在你的项目中重新设置这些颜色的值
```
   <color name="marker_color">#be945c</color>
    <color name="marker_text_color">#333333</color>
    <color name="chart_grid_color">#333333</color>

    <color name="ma5">#823E66</color>
    <color name="ma10">#B99C7A</color>
    <color name="ma20">#3C7193</color>
    <color name="ma30">#7F9976</color>

    <color name="increasing_color">@color/main_color_red</color>
    <color name="decreasing_color">@color/main_color_green</color>
    <color name="normal_line_color">#be945c</color>
    <color name="ave_color">#bbbbbb</color>
    <color name="axis_color">#ffffff</color>
    <color name="chart_info_color">#88000000</color>
    <color name="chart_no_data_color">#be945c</color>
    <color name="highlight_color">#be945c</color>
    <color name="limit_color">#adadad</color>
```


本人利用业余时间完善了这个图表库，demo如下，也可以[点击这里下载](art/app-release.apk)


![demo](art/new_chart.gif)

目前图表库有很多不完善的地方，本人利用业余时间对以下方面进行了完善：

- 增加了MACD、KDJ指标的显示和切换
- 增加了五日的分时图
- 优化图表的缩放，可以进行放大和缩小操作（之前只支持在初始状态下的放大操作）
- 拼接图表的缩放功能（之前如果一个手指在K线图，另一个手指在交易量图的时候，图表是不可以缩放的）
- 全屏模式的示例
- 分时图增加涨跌幅的坐标，并且涨跌幅为0%时竖直居中显示
- K线图增加最大/最小值得显示

研究图表花费了很多时间和精力，所以我想通过知识付费的形式与有需要的人进行分享，有需要的童鞋可以加入
我的知识星球来获取完整的代码，并提供后续的技术支持。

![知识星球](art/zsxq.png)

