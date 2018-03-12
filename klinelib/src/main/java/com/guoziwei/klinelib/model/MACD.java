package com.guoziwei.klinelib.model;

import java.util.ArrayList;
import java.util.List;


public class MACD {

    private List<Double> DEAs;
    private List<Double> DIFs;
    private List<Double> BARs;

    private List<Double> EMA12;
    private List<Double> EMA26;
    private EMAEntity mEMAEntity9;
    private EMAEntity mEMAEntity12;
    private EMAEntity mEMAEntity26;

    public MACD(List<HisData> OHLCData) {
        if (OHLCData != null && OHLCData.size() > 0) {
            mEMAEntity9 = new EMAEntity(OHLCData, 9);
            mEMAEntity12 = new EMAEntity(OHLCData, 12);
            mEMAEntity26 = new EMAEntity(OHLCData, 26);
            EMA12 = mEMAEntity12.getnEMA();
            EMA26 = mEMAEntity26.getnEMA();
        }

        DEAs = new ArrayList<Double>();
        DIFs = new ArrayList<Double>();
        BARs = new ArrayList<Double>();


        List<Double> dEAs = new ArrayList<Double>();
        List<Double> dIFs = new ArrayList<Double>();
        List<Double> mACDs = new ArrayList<Double>();
        List<Double> mBARs = new ArrayList<Double>();

        double dIF = 0.0;
        double dEA = 0.0;
        double mBAR = 0.0;
        if (OHLCData != null && OHLCData.size() > 0) {

            for (int i = OHLCData.size() - 1; i >= 0; i--) {
                dIF = EMA12.get(OHLCData.size() - i - 1) - EMA26.get(OHLCData.size() - i - 1);
                if (i == OHLCData.size() - 1) {
                    dEA = 0 + dIF * (2 / 10);
                    mBAR = 0.0;
                } else {
                    dEA = dEA * 0.8 + dIF * 0.2;
                    mBAR = 2 * (dIF - dEA);
                }
                dEAs.add(dEA);
                dIFs.add(dIF);
                mBARs.add(mBAR);
            }

            for (int i = dEAs.size() - 1; i >= 0; i--) {
                DEAs.add(dEAs.get(i));
                DIFs.add(dIFs.get(i));
                BARs.add(mBARs.get(i));
            }
        }

    }

    public List<Double> getDEA() {
        return DEAs;
    }

    public List<Double> getDIF() {
        return DIFs;
    }

    public List<Double> getBAR() {
        return BARs;
    }

}

