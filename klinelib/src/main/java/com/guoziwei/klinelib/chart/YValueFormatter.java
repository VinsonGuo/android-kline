package com.guoziwei.klinelib.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.guoziwei.klinelib.util.DoubleUtil;

/**
 * Created by guoziwei on 2017/10/28.
 */

public class YValueFormatter implements IAxisValueFormatter {
    private int mDigits;

    /**
     * @param digits 浮点数保留的位数
     */
    public YValueFormatter(int digits) {
        mDigits = digits;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return DoubleUtil.getStringByDigits(value, mDigits);
    }
}
