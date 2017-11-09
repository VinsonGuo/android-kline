package com.guoziwei.kline.model;

/**
 * <a:HisData>
 * <a:Close>45.37</a:Close>
 * <a:High>45.38</a:High>
 * <a:Low>45.35</a:Low>
 * <a:Open>45.35</a:Open>
 * <a:Vol>178</a:Vol>
 * <a:sDate>2017-07-07T06:00:00</a:sDate>
 * </a:HisData>
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
                '}';
    }
}
