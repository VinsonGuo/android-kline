# android-kline
基于MPAndroidChart的专业K线图

qq交流群 363529464

# Demo

![demo](art/kchart.gif)


# 使用方式
讲klinelib导入到你的项目中，主项目和它建立依赖

xml

```xml
    <com.guoziwei.klinelib.chart.KLineView
        android:id="@+id/kline"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

java  init方法只能调用其中的一个
```
        KLineView kLineView = findViewById(R.id.kline);
        // 分时图
        kLineView.initChartPriceData(list);
        // K线图
        kLineView.initChartKData(list);
```

注意：这里需要接收一个HisData的List，HisData需要如下的几个数据，其他的数据会根据公式计算出来

```
 public HisData(double close, double high, double low, double open, int vol, long date) {
        this.close = close;
        this.high = high;
        this.low = low;
        this.open = open;
        this.vol = vol;
        this.date = date;
    }
```