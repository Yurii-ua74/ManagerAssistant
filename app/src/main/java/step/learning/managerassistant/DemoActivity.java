package step.learning.managerassistant;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import java.util.ArrayList;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demo);

        CandleStickChart candleStickChart = findViewById(R.id.candleStickChart);

        ArrayList<CandleEntry> entries = new ArrayList<>();
        entries.add(new CandleEntry(0, 210, 190, 200, 195));
        entries.add(new CandleEntry(1, 215, 180, 205, 185));
        entries.add(new CandleEntry(2, 220, 200, 215, 210));
        entries.add(new CandleEntry(3, 240, 190, 220, 235));
        entries.add(new CandleEntry(4, 240, 220, 230, 225));
        entries.add(new CandleEntry(5, 200, 170, 180, 195));
        entries.add(new CandleEntry(6, 205, 170, 195, 210));
        entries.add(new CandleEntry(7, 220, 200, 210, 218));
        entries.add(new CandleEntry(8, 242, 201, 218, 238));
        entries.add(new CandleEntry(9, 245, 195, 238, 250));
        entries.add(new CandleEntry(10,258, 222, 250, 220));

        CandleDataSet dataSet = new CandleDataSet(entries, "Candlestick Data");

        dataSet.setShadowColor(Color.DKGRAY);  // Колір тіні свічки
        dataSet.setShadowWidth(0.7f);           // Ширина тіні
        dataSet.setDecreasingColor(Color.RED); // Колір тіла свічки, коли ціна закриття нижче ціни відкриття
        dataSet.setDecreasingPaintStyle(android.graphics.Paint.Style.FILL);
        dataSet.setIncreasingColor(Color.GREEN); // Колір тіла свічки, коли ціна закриття вище ціни відкриття
        dataSet.setIncreasingPaintStyle(android.graphics.Paint.Style.FILL);
        dataSet.setNeutralColor(Color.BLUE);    // Колір тіла свічки, коли ціна закриття дорівнює ціні відкриття

        CandleData data = new CandleData(dataSet);
        candleStickChart.setData(data);
        candleStickChart.invalidate(); // refresh
    }
}