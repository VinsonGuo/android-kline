package com.guoziwei.klinelib.chart;

/**
 * Created by Administrator on 2016/2/1.
 */

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.guoziwei.klinelib.R;
import com.guoziwei.klinelib.util.DoubleUtil;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class LineChartYMarkerView extends MarkerView {

    private final double mTick;
    private TextView tvContent;

    public LineChartYMarkerView(Context context, double tick) {
        super(context, R.layout.view_mp_real_price_marker);
        mTick = tick;
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        float value = e.getY();
        tvContent.setText(DoubleUtil.getStringByTick(value, mTick));
        super.refreshContent(e, highlight);
    }

}
