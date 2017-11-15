package com.guoziwei.kline.model;

/**
 * 图表数据model
 * 只需要Close High Low Open Vol sDate这几个字段就可以，其他的字段是通过计算得出的
 */

public class HisData {
    private double Close;
    private double High;
    private double Low;
    private double Open;
    private int Vol;
    private String sDate;
    private int amountVol;
    private double avePrice;
    private double total;
    private double ma5;
    private double ma10;
    private double ma20;
    private double ma30;

    public double getClose() {
        return Close;
    }

    public void setClose(double close) {
        Close = close;
    }

    public double getHigh() {
        return High;
    }

    public void setHigh(double high) {
        High = high;
    }

    public double getLow() {
        return Low;
    }

    public void setLow(double low) {
        Low = low;
    }

    public double getOpen() {
        return Open;
    }

    public void setOpen(double open) {
        Open = open;
    }

    public int getVol() {
        return Vol;
    }

    public void setVol(int vol) {
        Vol = vol;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public double getAvePrice() {
        return avePrice;
    }

    public void setAvePrice(double avePrice) {
        this.avePrice = avePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HisData hisData = (HisData) o;

        return sDate != null ? sDate.equals(hisData.sDate) : hisData.sDate == null;

    }

    @Override
    public int hashCode() {
        return sDate != null ? sDate.hashCode() : 0;
    }

    public int getAmountVol() {
        return amountVol;
    }

    public void setAmountVol(int amountVol) {
        this.amountVol = amountVol;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getMa5() {
        return ma5;
    }

    public void setMa5(double ma5) {
        this.ma5 = ma5;
    }

    public double getMa10() {
        return ma10;
    }

    public void setMa10(double ma10) {
        this.ma10 = ma10;
    }

    public double getMa20() {
        return ma20;
    }

    public void setMa20(double ma20) {
        this.ma20 = ma20;
    }

    public double getMa30() {
        return ma30;
    }

    public void setMa30(double ma30) {
        this.ma30 = ma30;
    }

    @Override
    public String toString() {
        return "HisData{" +
                "Close=" + Close +
                ", High=" + High +
                ", Low=" + Low +
                ", Open=" + Open +
                ", Vol=" + Vol +
                ", sDate='" + sDate + '\'' +
                ", amountVol=" + amountVol +
                ", avePrice=" + avePrice +
                ", total=" + total +
                ", ma5=" + ma5 +
                ", ma10=" + ma10 +
                ", ma20=" + ma20 +
                ", ma30=" + ma30 +
                '}';
    }
}
