package com.vinsonguo.klinelib.model;

import java.util.ArrayList;
import java.util.List;


public class KDJ {
    private ArrayList<Double> Ks;
    private ArrayList<Double> Ds;
    private ArrayList<Double> Js;
    private ArrayList<Double> rsv;

    private RSV mRSV;

    public KDJ(List<HisData> OHLCData) {
        Ks = new ArrayList<Double>();
        Ds = new ArrayList<Double>();
        Js = new ArrayList<Double>();

        ArrayList<Double> ks = new ArrayList<Double>();
        ArrayList<Double> ds = new ArrayList<Double>();
        ArrayList<Double> js = new ArrayList<Double>();

        mRSV = new RSV(OHLCData, 9);
        double k = 50.0;
        double d = 50.0;
        double j = 0.0;
        double rSV = 0.0;

        if (OHLCData != null && OHLCData.size() > 0) {
            rsv = (ArrayList<Double>) mRSV.getRSV();
            for (int i = OHLCData.size() - 1; i >= 0; i--) {
                rSV = rsv.get(i);
                k = (k * 2 + rSV) / 3;
                d = (d * 2 + k) / 3;
                j = 3 * k - 2 * d;
                ks.add(k);
                ds.add(d);
                js.add(j);
            }
            for (int i = ks.size() - 1; i >= 0; i--) {
                Ks.add(ks.get(i));
                Ds.add(ds.get(i));
                Js.add(js.get(i));
            }
        }
    }

    public ArrayList<Double> getK() {
        return Ks;
    }

    public ArrayList<Double> getD() {
        return Ds;
    }

    public ArrayList<Double> getJ() {
        return Js;
    }
}

