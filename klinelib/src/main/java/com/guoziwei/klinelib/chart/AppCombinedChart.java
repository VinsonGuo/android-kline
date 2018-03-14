package com.guoziwei.klinelib.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

/**
 * Created by dell on 2017/6/22.
 */

public class AppCombinedChart extends CombinedChart {

    private IMarker mXMarker;

    public AppCombinedChart(Context context) {
        this(context, null);
    }

    public AppCombinedChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppCombinedChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        mRenderer = new AppCombinedChartRenderer(this, mAnimator, mViewPortHandler);
    }

    public void setXMarker(IMarker marker) {
        mXMarker = marker;
    }

    @Override
    public void setData(CombinedData data) {
        try {
            super.setData(data);
        }catch (ClassCastException e) {
            // ignore
        }
        ((AppCombinedChartRenderer) mRenderer).createRenderers();
        mRenderer.initBuffers();
    }

    @Override
    protected void drawMarkers(Canvas canvas) {
        if (mMarker == null || mXMarker == null || !isDrawMarkersEnabled() || !valuesToHighlight())
            return;

        for (int i = 0; i < mIndicesToHighlight.length; i++) {

            Highlight highlight = mIndicesToHighlight[i];

            IDataSet set = mData.getDataSetByIndex(highlight.getDataSetIndex());

            Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);
            int entryIndex = set.getEntryIndex(e);

            // make sure entry not null
            if (e == null || entryIndex > set.getEntryCount() * mAnimator.getPhaseX())
                continue;

            float[] pos = getMarkerPosition(highlight);

            // check bounds
            if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                continue;

            // callbacks to update the content
            mMarker.refreshContent(e, highlight);
            mXMarker.refreshContent(e, highlight);

            // draw the marker
//            if (mMarker instanceof LineChartYMarkerView) {
            LineChartYMarkerView yMarker = (LineChartYMarkerView) mMarker;
            LineChartXMarkerView xMarker = (LineChartXMarkerView) mXMarker;
            int width = yMarker.getMeasuredWidth();
            mMarker.draw(canvas, getMeasuredWidth() - width * 1.05f, pos[1] - yMarker.getMeasuredHeight() / 2);

            mXMarker.draw(canvas, pos[0] - (xMarker.getMeasuredWidth() / 2), getMeasuredHeight());
//            } else {
//                mMarker.draw(canvas, pos[0], pos[1]);
//            }
        }
    }

    @Override
    public void highlightValue(Highlight highlight) {
        super.highlightValue(highlight);
    }

}
