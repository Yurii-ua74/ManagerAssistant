package com.managerassistant.network;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class AlphaVantageResponse {
    @SerializedName("Meta Data")
    private MetaData metaData;

    @SerializedName("Time Series (Daily)")
    private Map<String, DailyData> timeSeriesDaily;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public Map<String, DailyData> getTimeSeriesDaily() {
        return timeSeriesDaily;
    }

    public void setTimeSeriesDaily(Map<String, DailyData> timeSeriesDaily) {
        timeSeriesDaily = timeSeriesDaily;
    }
}
