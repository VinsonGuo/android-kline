# android-kline
基于MPAndroidChart的专业K线图

qq交流群 363529464

本项目通过继承的方式定制了最新版本的MPAndroidChart，没有修改MPAndroidChart的源代码，所以对已经使用了MPAndroidChart的童鞋不会造成影响。

丰富的定制化接口，支持图表的个性化定制。

# Demo

![demo](art/kchart.gif)


# 使用方式

在项目build.gradle中添加依赖：

```
 allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
   }
   dependencies {
        compile com.github.gzw19931217:android-kline:0.1.1'
   }
```

xml

```xml
    <com.guoziwei.klinelib.chart.KLineView
        android:id="@+id/kline"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

java  init方法只能调用其中的一个
```
        KLineView kLineView = (KLineView) findViewById(R.id.kline);
        // 分时图
        kLineView.initChartPriceData(list);
        // K线图
        kLineView.initChartKData(list);
```

注意：这里需要接收一个HisData的List，HisData需要如下的几个数据，其他的指标会根据公式计算出来

```
  public HisData(double open,double close, double high, double low,  int vol, long date)
```

个性化：在你的项目中重新设置这些颜色的值
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

自定义悬浮的信息提示：
集成`ChartInfoView`，然后在init方法前调用`KLineView`的`setChartInfoView`方法。
此外，还可以通过下面的方法对图表和坐标进行个性化的定制。
```
    public AppCombinedChart getChartPrice() 

    public AppCombinedChart getChartVolume() 

    public XAxis getxAxisPrice() 

    public YAxis getAxisRightPrice() 

    public YAxis getAxisLeftPrice() 

    public XAxis getxAxisVolume() 

    public YAxis getAxisRightVolume() 

    public YAxis getAxisLeftVolume() 
```

如果还有更复杂的需求，可以选择继承KLineView。