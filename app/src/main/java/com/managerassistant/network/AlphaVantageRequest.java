package com.managerassistant.network;

import com.google.gson.Gson;
import com.managerassistant.database.database.DatabaseHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlphaVantageRequest {
    //private static final String API_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&apikey=DOYOTUXGZB61C4NU";
    private static final String BASE_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=%s&apikey=DOYOTUXGZB61C4NU";
    //private TextView textView;
    private DatabaseHelper databaseHelper;
    private String smbl;

    public AlphaVantageRequest(DatabaseHelper databaseHelper, String smbl) {
        this.databaseHelper = databaseHelper;
        this.smbl = smbl;
    }

    private String getApiUrl() {
        return String.format(BASE_URL, smbl);
    }


   // @SuppressLint("SetTextI18n")
    public void fetchResponse() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {
                URL url = new URL(getApiUrl());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    return;
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                connection.disconnect();

                Gson gson = new Gson();
                AlphaVantageResponse alphaVantageResponse = gson.fromJson(response.toString(), AlphaVantageResponse.class);
                MetaData metaData = alphaVantageResponse.getMetaData();

                        //  інформація про останні ціни та обсяги
                        if (alphaVantageResponse.getTimeSeriesDaily() != null && !alphaVantageResponse.getTimeSeriesDaily().isEmpty()) {
                            for (Map.Entry<String, DailyData> entry : alphaVantageResponse.getTimeSeriesDaily().entrySet()) {
                                String date = entry.getKey();
                                DailyData dailyData = entry.getValue();

                                databaseHelper.insertDailyData(
                                        smbl,
                                        date,
                                        Double.parseDouble(dailyData.getOpen()),
                                        Double.parseDouble(dailyData.getHigh()),
                                        Double.parseDouble(dailyData.getLow()),
                                        Double.parseDouble(dailyData.getClose()),
                                        Long.parseLong(dailyData.getVolume())
                                );
                            }
                        }

            } catch (Exception e) {
                    e.printStackTrace();
            }
        });
    }
}

