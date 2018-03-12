package com.guoziwei.klinelib.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

import static com.github.mikephil.charting.utils.Utils.convertDpToPixel;

/**
 * 为每个label设置颜色，把两端的label绘制在内容区域内
 */

public class ColorContentYAxisRenderer extends YAxisRenderer {
    private int[] mLabelColorArray;
    private boolean mLabelInContent = false;
    private boolean mUseDefaultLabelXOffset = true;
    private boolean mUseDefaultLimitLineLabelXOffset=true;

    public ColorContentYAxisRenderer(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer trans) {
        super(viewPortHandler, yAxis, trans);
    }

    /**
     * 给每个label单独设置颜色
     */
    public void setLabelColor(int[] labelColorArray) {
        mLabelColorArray = labelColorArray;
    }

    public void setLabelInContent(boolean flag) {
        mLabelInContent = flag;
    }

    public void setUseDefaultLabelXOffset(boolean flag) {
        mUseDefaultLabelXOffset = flag;
    }

    public void setUseDefaultLimitLineLabelXOffset(boolean flag) {
        mUseDefaultLimitLineLabelXOffset = flag;
    }

    @Override
    public void renderAxisLabels(Canvas c) {
        if (!mYAxis.isEnabled() || !mYAxis.isDrawLabelsEnabled())
            return;

        float[] positions = getTransformedPositions();

        mAxisLabelPaint.setTypeface(mYAxis.getTypeface());
        mAxisLabelPaint.setTextSize(mYAxis.getTextSize());
        mAxisLabelPaint.setColor(mYAxis.getTextColor());

        float xoffset = mYAxis.getXOffset();
        float yoffset = Utils.calcTextHeight(mAxisLabelPaint, "A") / 2.5f + mYAxis.getYOffset();

        YAxis.AxisDependency dependency = mYAxis.getAxisDependency();
        YAxis.YAxisLabelPosition labelPosition = mYAxis.getLabelPosition();

        float xPos = 0f;

        if (dependency == YAxis.AxisDependency.LEFT) {

            if (labelPosition == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
                mAxisLabelPaint.setTextAlign(Paint.Align.RIGHT);
                xPos = mViewPortHandler.offsetLeft() - xoffset;
            } else {
                mAxisLabelPaint.setTextAlign(Paint.Align.LEFT);
                xPos = mViewPortHandler.offsetLeft() + xoffset;
            }

        } else {

            if (labelPosition == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
                mAxisLabelPaint.setTextAlign(Paint.Align.LEFT);
                xPos = mViewPortHandler.contentRight() + xoffset;
            } else {
                mAxisLabelPaint.setTextAlign(Paint.Align.RIGHT);
                xPos = mViewPortHandler.contentRight() - xoffset;
            }
        }

        drawYLabels(c, xPos, positions, yoffset);
    }

    @Override
    protected void drawYLabels(Canvas c, float fixedPosition, float[] positions, float offset) {

        final int from = mYAxis.isDrawBottomYLabelEntryEnabled() ? 0 : 1;
        final int to = mYAxis.isDrawTopYLabelEntryEnabled()
                ? mYAxis.mEntryCount
                : (mYAxis.mEntryCount - 1);

        int originalColor = mAxisLabelPaint.getColor();

        float textHeight = Utils.calcTextHeight(mAxisLabelPaint, "A");
        float yoffset = textHeight / 2.5f + mYAxis.getYOffset();
        float space = Utils.convertDpToPixel(1f);
        if (!mUseDefaultLabelXOffset) {
            if (mYAxis.getAxisDependency() == YAxis.AxisDependency.LEFT) {
                fixedPosition -= mYAxis.getXOffset();
            } else {
                fixedPosition += mYAxis.getXOffset();
            }
        }

        // draw
        for (int i = from; i < to; i++) {

            if (mLabelColorArray != null && i >= 0 && i < mLabelColorArray.length) {
                int labelColor = mLabelColorArray[i];
                mAxisLabelPaint.setColor(labelColor);
            } else {
                mAxisLabelPaint.setColor(originalColor);
            }
            String text = mYAxis.getFormattedLabel(i);

            float y = positions[i * 2 + 1] + offset;

            if (mLabelInContent) {
                if (i == from) {
                    y = y - offset - space-1F;
                } else if (i == (to - 1)) {
                    y = y - yoffset + textHeight + space+1F;
                }
            }

            c.drawText(text, fixedPosition, y, mAxisLabelPaint);
        }
        mAxisLabelPaint.setColor(originalColor);
    }

    @Override
    public void renderLimitLines(Canvas c) {

        List<LimitLine> limitLines = mYAxis.getLimitLines();

        if (limitLines == null || limitLines.size() <= 0)
            return;

        float[] pts = mRenderLimitLinesBuffer;
        pts[0] = 0;
        pts[1] = 0;
        Path limitLinePath = mRenderLimitLines;
        limitLinePath.reset();

        for (int i = 0; i < limitLines.size(); i++) {

            LimitLine l = limitLines.get(i);

            if (!l.isEnabled())
                continue;

            int clipRestoreCount = c.save();
            mLimitLineClippingRect.set(mViewPortHandler.getContentRect());
            mLimitLineClippingRect.inset(0.f, -l.getLineWidth());
            c.clipRect(mLimitLineClippingRect);

            mLimitLinePaint.setStyle(Paint.Style.STROKE);
            mLimitLinePaint.setColor(l.getLineColor());
            mLimitLinePaint.setStrokeWidth(l.getLineWidth());
            mLimitLinePaint.setPathEffect(l.getDashPathEffect());

            pts[1] = l.getLimit();

            mTrans.pointValuesToPixel(pts);

            limitLinePath.moveTo(mViewPortHandler.contentLeft(), pts[1]);
            limitLinePath.lineTo(mViewPortHandler.contentRight(), pts[1]);

            c.drawPath(limitLinePath, mLimitLinePaint);
            limitLinePath.reset();
            // c.drawLines(pts, mLimitLinePaint);

            String label = l.getLabel();

            // if drawing the limit-value label is enabled
            if (label != null && !label.equals("")) {

                mLimitLinePaint.setStyle(l.getTextStyle());
                mLimitLinePaint.setPathEffect(null);
                mLimitLinePaint.setColor(l.getTextColor());
                mLimitLinePaint.setTypeface(l.getTypeface());
                mLimitLinePaint.setStrokeWidth(0.5f);
                mLimitLinePaint.setTextSize(l.getTextSize());

                final float labelLineHeight = Utils.calcTextHeight(mLimitLinePaint, label);
                float xOffset = convertDpToPixel(4f) + l.getXOffset();
                float yOffset = l.getLineWidth() + labelLineHeight + l.getYOffset();

                final LimitLine.LimitLabelPosition position = l.getLabelPosition();

                if (!mUseDefaultLimitLineLabelXOffset) {
                    xOffset= convertDpToPixel(1f);
                }

                if (position == LimitLine.LimitLabelPosition.RIGHT_TOP) {

                    mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                    c.drawText(label,
                            mViewPortHandler.contentRight() - xOffset,
                            pts[1] - yOffset + labelLineHeight, mLimitLinePaint);

                } else if (position == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {

                    mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                    c.drawText(label,
                            mViewPortHandler.contentRight() - xOffset,
                            pts[1] + yOffset, mLimitLinePaint);

                } else if (position == LimitLine.LimitLabelPosition.LEFT_TOP) {

                    mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                    c.drawText(label,
                            mViewPortHandler.contentLeft() + xOffset,
                            pts[1] - yOffset + labelLineHeight, mLimitLinePaint);

                } else {

                    mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                    c.drawText(label,
                            mViewPortHandler.offsetLeft() + xOffset,
                            pts[1] + yOffset, mLimitLinePaint);
                }
            }

            c.restoreToCount(clipRestoreCount);
        }
    }
}
