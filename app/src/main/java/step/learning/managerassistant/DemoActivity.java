package step.learning.managerassistant;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.managerassistant.database.database.DatabaseHelper;
import com.managerassistant.network.CustomCandleEntry;

import java.util.ArrayList;

public class DemoActivity extends AppCompatActivity {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;
    private TextView textView;
    private String symbol = "GOLD"; // Ініціалізація змінної symbol
    private EditText firstInputPrice;
    private EditText secondInputPrice;
    private Button searchPriceButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demo);

        CandleStickChart candleStickChart = findViewById(R.id.candleStickChart);
        textView = findViewById(R.id.textView);
        //
        firstInputPrice = findViewById(R.id.firstInputPrice);
        secondInputPrice = findViewById(R.id.secondInputPrice);
        searchPriceButton = findViewById(R.id.searchPriceButton);
        databaseHelper = new DatabaseHelper(this);
        //

        DatabaseHelper databaseHelper = new DatabaseHelper(this); // Ініціалізація бази даних

        // Отримання даних з бази даних
        ArrayList<CandleEntry> entries = getEntriesFromDatabase(databaseHelper, symbol);

        // Створення CandleDataSet
        CandleDataSet dataSet = new CandleDataSet(entries, "Candlestick Data");
        dataSet.setShadowColor(Color.DKGRAY);  // Колір тіні свічки
        dataSet.setShadowWidth(0.7f);           // Ширина тіні
        dataSet.setDecreasingColor(Color.RED); // Колір тіла свічки, коли ціна закриття нижче ціни відкриття
        dataSet.setDecreasingPaintStyle(android.graphics.Paint.Style.FILL);
        dataSet.setIncreasingColor(Color.GREEN); // Колір тіла свічки, коли ціна закриття вище ціни відкриття
        dataSet.setIncreasingPaintStyle(android.graphics.Paint.Style.FILL);
        dataSet.setNeutralColor(Color.BLUE);    // Колір тіла свічки, коли ціна закриття дорівнює ціні відкриття

        // Створення CandleData
        CandleData data = new CandleData(dataSet);

        // Встановлення CandleData у CandleStickChart
        candleStickChart.setData(data);
        candleStickChart.invalidate(); // refresh


        searchPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstPriceStr = firstInputPrice.getText().toString();
                String secondPriceStr = secondInputPrice.getText().toString();

                if (firstPriceStr.isEmpty() || secondPriceStr.isEmpty()) {
                    Toast.makeText(DemoActivity.this, "Please enter both prices", Toast.LENGTH_SHORT).show();
                    return;
                }

                double firstPrice = Double.parseDouble(firstPriceStr);
                double secondPrice = Double.parseDouble(secondPriceStr);
                ArrayList<CandleEntry> datePrice = new ArrayList<>();
                datePrice = getEntriesFromDatabase(databaseHelper, symbol);
                if (!datePrice.isEmpty()) {
                    // перевірка дозволу на відправлення повідомлень
                    if (ContextCompat.checkSelfPermission(DemoActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(DemoActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
                    }
                    if (checkPrice(datePrice, firstPrice, secondPrice)) {
                        sendPushNotification("Price Alert", "The price you're waiting for has success");
                    } else {
                        Toast.makeText(DemoActivity.this, "Waiting for new prices", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DemoActivity.this, "Waiting for new prices", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //@Override
    //public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    //    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //    if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
    //        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
    //            sendPushNotification("Price Alert", "The price you're waiting for has success");
    //        } else {
    //            Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
    //        }
    //    }
    //}

    private void sendPushNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "price_alert_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Price Alerts", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true);

        notificationManager.notify(1, notificationBuilder.build());
    }

    private ArrayList<CandleEntry> getEntriesFromDatabase(DatabaseHelper databaseHelper, String symbol) {
        ArrayList<CandleEntry> entries = new ArrayList<>();

        // Отримання даних з бази даних для вказаного символу (наприклад, "GOLD")
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(symbol, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int dateIndex = cursor.getColumnIndex("date");
            int openIndex = cursor.getColumnIndex("open");
            int highIndex = cursor.getColumnIndex("high");
            int lowIndex = cursor.getColumnIndex("low");
            int closeIndex = cursor.getColumnIndex("close");
            int volumeIndex = cursor.getColumnIndex("volume");

            do {
                String date = cursor.getString(dateIndex);
                float open = cursor.getFloat(openIndex);
                float high = cursor.getFloat(highIndex);
                float low = cursor.getFloat(lowIndex);
                float close = cursor.getFloat(closeIndex);
                CustomCandleEntry entry = new CustomCandleEntry(entries.size(), high, low, open, close, date);
                entries.add(entry);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return entries;
    }

    private boolean checkPrice(ArrayList<CandleEntry> datePrice, double firstPrice, double secondPrice ) {
        //ArrayList<CandleEntry> datePrice = new ArrayList<>();
        boolean foundFirstPrice = false;

        for (CandleEntry entry : datePrice) {
            int closePrice = (int) entry.getClose(); // Отримуємо ціну закриття

            if (!foundFirstPrice) {
                // Якщо ми ще не знайшли першу ціну, перевіряємо її
                if (closePrice == firstPrice) {
                    foundFirstPrice = true;
                }
            } else {
                // Якщо ми вже знайшли першу ціну, перевіряємо другу
                if (closePrice == secondPrice) {
                    return true;
                }
            }
        }


        return false;
    }
}