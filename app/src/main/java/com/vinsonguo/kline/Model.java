package com.vinsonguo.kline;

/**
 * Created by dell on 2017/11/23.
 */

public class Model {
    private double Close;
    private double High;
    private double Low;
    private double Open;
    private int Vol;
    private String sDate;

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

    @Override
    public String toString() {
        return "Model{" +
                "Close=" + Close +
                ", High=" + High +
                ", Low=" + Low +
                ", Open=" + Open +
                ", Vol=" + Vol +
                ", sDate='" + sDate + '\'' +
                '}';
    }
}
