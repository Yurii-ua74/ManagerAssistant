package com.managerassistant.network;

import com.github.mikephil.charting.data.CandleEntry;

public class CustomCandleEntry extends CandleEntry {
    private final String date;

    public CustomCandleEntry(float x, float shadowH, float shadowL, float open, float close, String date) {
        super(x, shadowH, shadowL, open, close);
        this.date = date;
    }

    public CustomCandleEntry(float x, float shadowH, float shadowL, float open, float close, Object data, String date) {
        super(x, shadowH, shadowL, open, close, data);
        this.date = date;
    }

    public String getDate() {
        return date;
    }

}
