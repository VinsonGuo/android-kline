package com.vinsonguo.klinelib.model;

import java.util.ArrayList;
import java.util.List;

public class RSV {
    private ArrayList<Double> rsv;
    private int n;
    double high = 0.0;
    double low = 0.0;
    double close = 0.0;

    public RSV(List<HisData> OHLCData, int m) {
        n = m;
        rsv = new ArrayList<Double>();
        ArrayList<Double> r = new ArrayList<Double>();
        double rs = 0.0;

        if (OHLCData != null && OHLCData.size() > 0) {

            for (int i = OHLCData.size() - 1; i >= 0; i--) {
                HisData oHLCEntity = OHLCData.get(i);
                high = oHLCEntity.getHigh();
                low = oHLCEntity.getLow();
                close = oHLCEntity.getClose();
                if (OHLCData.size() - i < n) {
                    for (int j = 0; j < OHLCData.size() - i; j++) {
                        HisData oHLCEntity1 = OHLCData.get(i + j);
                        high = high > oHLCEntity1.getHigh() ? high : oHLCEntity1.getHigh();
                        low = low < oHLCEntity1.getLow() ? low : oHLCEntity1.getLow();
                    }
                } else {
                    for (int j = 0; j < n; j++) {
                        HisData oHLCEntity1 = OHLCData.get(i + j);
                        high = high > oHLCEntity1.getHigh() ? high : oHLCEntity1.getHigh();
                        low = low < oHLCEntity1.getLow() ? low : oHLCEntity1.getLow();
                    }
                }
                if (high != low) {
                    rs = (close - low) / (high - low) * 100;
                    r.add(rs);
                } else {
                    r.add(0.00);
                }
            }
            for (int i = r.size() - 1; i >= 0; i--) {
                rsv.add(r.get(i));
            }
        }
    }

    public List<Double> getRSV() {
        return rsv;
    }
}

