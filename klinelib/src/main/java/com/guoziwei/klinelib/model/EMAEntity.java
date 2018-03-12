package com.guoziwei.klinelib.model;

import com.guoziwei.klinelib.model.HisData;

import java.util.ArrayList;
import java.util.List;

public class EMAEntity {
    private List<Double> EMAs;

    public EMAEntity(List<HisData> OHLCData, int n) {
        EMAs = new ArrayList<Double>();
        if (OHLCData != null && OHLCData.size() > 0) {
            double close = 0;
            double EMA12 = 0.0;
            HisData oHLCEntity;
            for (int i = OHLCData.size() - 1; i >= 0; i--) {
                close = OHLCData.get(i).getClose();
                oHLCEntity = OHLCData.get(i);
                if (i == OHLCData.size() - 1) {
                    EMA12 = oHLCEntity.getClose();
                    EMAs.add(EMA12);
                } else {
                    EMA12 = EMAs.get(EMAs.size() - 1) * (n - 1) / (n + 1) + close * (2) / (n + 1);
                    EMAs.add(EMA12);
                }
            }

        }
    }

    public List<Double> getnEMA() {
        return EMAs;
    }
}
