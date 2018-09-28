package com.vinsonguo.kline.model;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class KModel {

    /**
     * time : 2017-08-02
     * price_o : 10526.00
     * price_c : 10469.34
     * price_h : 10557.47
     * price_l : 10461.95
     * volume : 248511557
     * volume_price : 3051亿
     * zf_bfb : 0.91%
     */

    private String time;
    private double price_o;
    private double price_c;
    private double price_h;
    private double price_l;
    private long volume;
    private String volume_price;
    private String zf_bfb;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice_o() {
        return price_o;
    }

    public void setPrice_o(double price_o) {
        this.price_o = price_o;
    }

    public double getPrice_c() {
        return price_c;
    }

    public void setPrice_c(double price_c) {
        this.price_c = price_c;
    }

    public double getPrice_h() {
        return price_h;
    }

    public void setPrice_h(double price_h) {
        this.price_h = price_h;
    }

    public double getPrice_l() {
        return price_l;
    }

    public void setPrice_l(double price_l) {
        this.price_l = price_l;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public String getVolume_price() {
        return volume_price;
    }

    public void setVolume_price(String volume_price) {
        this.volume_price = volume_price;
    }

    public String getZf_bfb() {
        return zf_bfb;
    }

    public void setZf_bfb(String zf_bfb) {
        this.zf_bfb = zf_bfb;
    }
}
