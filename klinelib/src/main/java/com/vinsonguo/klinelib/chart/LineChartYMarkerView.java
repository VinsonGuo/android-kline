package com.vinsonguo.klinelib.chart;

/**
 * Created by Administrator on 2016/2/1.
 */

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.vinsonguo.klinelib.R;
import com.vinsonguo.klinelib.util.DoubleUtil;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class LineChartYMarkerView extends MarkerView {

    private final int digits;
    private TextView tvContent;

    public LineChartYMarkerView(Context context, int digits) {
        super(context, R.layout.view_mp_real_price_marker);
        this.digits = digits;
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        float value = e.getY();
        tvContent.setText(DoubleUtil.getStringByDigits(value, digits));
        super.refreshContent(e, highlight);
    }

}
