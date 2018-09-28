package com.vinsonguo.klinelib.model;

import java.util.ArrayList;
import java.util.List;


public class MACD {

    private List<Double> DEAs;
    private List<Double> DIFs;
    private List<Double> MACDs;

    /**
     * 得到MACD数据
     *
     * @param kLineBeen
     */
    public MACD(List<HisData> kLineBeen) {
        DEAs = new ArrayList<>();
        DIFs = new ArrayList<>();
        MACDs = new ArrayList<>();

        List<Double> dEAs = new ArrayList<>();
        List<Double> dIFs = new ArrayList<>();
        List<Double> mACDs = new ArrayList<>();

        double eMA12 = 0.0f;
        double eMA26 = 0.0f;
        double close = 0f;
        double dIF = 0.0f;
        double dEA = 0.0f;
        double mACD = 0.0f;
        if (kLineBeen != null && kLineBeen.size() > 0) {
            for (int i = 0; i < kLineBeen.size(); i++) {
                close = kLineBeen.get(i).getClose();
                if (i == 0) {
                    eMA12 = close;
                    eMA26 = close;
                } else {
                    eMA12 = eMA12 * 11 / 13 + close * 2 / 13;
                    eMA26 = eMA26 * 25 / 27 + close * 2 / 27;
                }
                dIF = eMA12 - eMA26;
                dEA = dEA * 8 / 10 + dIF * 2 / 10;
                mACD = dIF - dEA;
                dEAs.add(dEA);
                dIFs.add(dIF);
                mACDs.add(mACD);
            }

            for (int i = 0; i < dEAs.size(); i++) {
                DEAs.add(dEAs.get(i));
                DIFs.add(dIFs.get(i));
                MACDs.add(mACDs.get(i));
            }

        }

    }

    public List<Double> getDEA() {
        return DEAs;
    }

    public List<Double> getDIF() {
        return DIFs;
    }

    public List<Double> getMACD() {
        return MACDs;
    }

}

