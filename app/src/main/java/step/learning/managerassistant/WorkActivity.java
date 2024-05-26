package step.learning.managerassistant;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.managerassistant.database.database.DatabaseHelper;
import com.managerassistant.network.AlphaVantageRequest;


public class WorkActivity extends AppCompatActivity {
    private TextView textView;
    private String symbol = "IBM"; // Ініціалізація змінної symbol

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        //textView = findViewById(R.id.textView);

        //AlphaVantageRequest request = new AlphaVantageRequest(textView, symbol);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        AlphaVantageRequest request = new AlphaVantageRequest(databaseHelper, symbol);
        request.fetchResponse();



        //fetchStockData("AAPL,MSFT,GOOGL");
        //fetchStockData("IBM");
    }

   // private void fetchStockData(String symbol) {
        // Параметри для виклику методу getStockData
    //    String function = "TIME_SERIES_DAILY";
        //String symbol1 = "IBM";
    //    String apiKey = "DOYOTUXGZB61C4NU";

        //Log.d("MainActivity", "Fetching data for symbol: " + symbol);
        //Call<Object> call = apiService.getStockData("TIME_SERIES_DAILY", symbol, API_KEY);

        // Виклик методу getStockData
        //Call<Object> call = apiService.getStockData("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY", "IBM", "DOYOTUXGZB61C4NU");
        //call.enqueue(new Callback<Object>() {
        //    @SuppressLint("SetTextI18n")
        //    @Override
        //    public void onResponse(Call<Object> call, Response<Object> response) {
        //        //Log.d("MainActivity", "Response received: " + response);
        //        if (response.isSuccessful() && response.body() != null) {
        //            // Отримуємо всі дані з відповіді
        //            JsonObject responseObject = (JsonObject) response.body();
        //            JsonObject metaData = responseObject.getAsJsonObject("Meta Data");
        //            JsonObject timeSeries = responseObject.getAsJsonObject("Time Series (Daily)");

        //            // Отримуємо потрібні дані з Meta Data
        //            String symbol = metaData.get("2. Symbol").getAsString();
        //            String lastRefreshed = metaData.get("3. Last Refreshed").getAsString();
        //            String timeZone = metaData.get("5. Time Zone").getAsString();

        //            // Отримуємо дані про останні котирування
        //            JsonObject latestData = timeSeries.getAsJsonObject(lastRefreshed);
        //            String openPrice = latestData.get("1. open").getAsString();
        //            String highPrice = latestData.get("2. high").getAsString();
        //            String lowPrice = latestData.get("3. low").getAsString();
        //            String closePrice = latestData.get("4. close").getAsString();
        //            String volume = latestData.get("5. volume").getAsString();

        //            // Відображаємо дані на екрані
        //            textView.setText("Symbol: " + symbol + "\n" +
        //                    "Last Refreshed: " + lastRefreshed + "\n" +
        //                    "Time Zone: " + timeZone + "\n" +
        //                    "Open Price: " + openPrice + "\n" +
        //                    "High Price: " + highPrice + "\n" +
        //                    "Low Price: " + lowPrice + "\n" +
        //                    "Close Price: " + closePrice + "\n" +
        //                    "Volume: " + volume);
        //        } else {
        //            textView.setText("Failed to get data: " + response.code());
        //            Log.e("MainActivity", "Response failed: " + response.message());
        //        }
       //    }

       //    @Override
       //    public void onFailure(Call<Object> call, Throwable t) {
       //        textView.setText("Error: " + t.getMessage());
       //        Log.e("MainActivity", "Request failed", t);
       //    }
       //});
    //}
}