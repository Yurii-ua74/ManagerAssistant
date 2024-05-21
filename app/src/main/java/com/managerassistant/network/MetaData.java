package com.managerassistant.network;

import com.google.gson.annotations.SerializedName;

public class MetaData {
    @SerializedName("1. Information")
    private String information;
    @SerializedName("2. Symbol")
    private String symbol;
    @SerializedName("3. Last Refreshed")
    private String lastRefreshed;
    @SerializedName("4. Output Size")
    private String outputSize;
    @SerializedName("5. Time Zone")
    private String timeZone;

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLastRefreshed() {
        return lastRefreshed;
    }

    public void setLastRefreshed(String lastRefreshed) {
        this.lastRefreshed = lastRefreshed;
    }

    public String getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(String outputSize) {
        this.outputSize = outputSize;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
