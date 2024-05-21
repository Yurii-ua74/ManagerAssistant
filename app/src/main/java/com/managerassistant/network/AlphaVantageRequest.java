package com.managerassistant.network;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class AlphaVantageRequest {
    private static final String API_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&apikey=DOYOTUXGZB61C4NU";
    private TextView textView;

    public AlphaVantageRequest(TextView textView) {
        this.textView = textView;
    }


    @SuppressLint("SetTextI18n")
    public void fetchResponse() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    handler.post(() -> textView.setText("HTTP error code: " + responseCode));
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

                handler.post(() -> {
                    if (alphaVantageResponse != null && alphaVantageResponse.getMetaData() != null) {
                        StringBuilder displayText = new StringBuilder();
                        displayText.append(alphaVantageResponse.getMetaData().getInformation()).append("\n");
                        //textView.setText(alphaVantageResponse.getMetaData().getInformation());

                        // Додаємо інформацію про останні ціни та обсяги
                        if (alphaVantageResponse.getTimeSeriesDaily() != null && !alphaVantageResponse.getTimeSeriesDaily().isEmpty()) {
                            for (Map.Entry<String, DailyData> entry : alphaVantageResponse.getTimeSeriesDaily().entrySet()) {
                                String date = entry.getKey();
                                DailyData dailyData = entry.getValue();

                                displayText.append("Date:   ").append(date).append("\n")
                                           .append("Open:   ").append(dailyData.getOpen()).append("\n")
                                           .append("High:   ").append(dailyData.getHigh()).append("\n")
                                           .append("Low:    ").append(dailyData.getLow()).append("\n")
                                           .append("Close:  ").append(dailyData.getClose()).append("\n")
                                           .append("Volume: ").append(dailyData.getVolume()).append("\n\n");
                            }
                        }

                        textView.setText(displayText.toString());

                    } else {
                        textView.setText("Failed to fetch data");
                    }
                });

            } catch (Exception e) {
                handler.post(() -> {
                    textView.setText("Exception: " + e.getMessage());
                    e.printStackTrace();
                });
            }
        });
    }
}

