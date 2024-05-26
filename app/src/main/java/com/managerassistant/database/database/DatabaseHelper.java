package com.managerassistant.database.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Invest.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Створюємо таблицю для зберігання назв всіх створених таблиць
        //db.execSQL("CREATE TABLE IF NOT EXISTS table_list (table_name TEXT PRIMARY KEY)");

        String createTableListSQL = "CREATE TABLE IF NOT EXISTS table_list (table_name TEXT PRIMARY KEY)";
        db.execSQL(createTableListSQL);
    }

    ///////////////
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Отримуємо список всіх створених таблиць
        Cursor cursor = db.rawQuery("SELECT table_name FROM table_list", null);
        if (cursor.moveToFirst()) {
            do {
                String tableName = cursor.getString(0);
                db.execSQL(String.format("DROP TABLE IF EXISTS %s", tableName));
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Видаляємо таблицю table_list
        db.execSQL("DROP TABLE IF EXISTS table_list");

        // Створюємо таблиці заново
        onCreate(db);
    }

    public void createTableIfNotExists(String symbol) {
        SQLiteDatabase db = this.getWritableDatabase();
        String createTableSQL = String.format(
                "CREATE TABLE IF NOT EXISTS %s (date TEXT PRIMARY KEY, open REAL, high REAL, low REAL, close REAL, volume INTEGER)",
                symbol
        );
        db.execSQL(createTableSQL);

        // Додаємо назву таблиці до table_list, якщо її там ще немає
        ContentValues contentValues = new ContentValues();
        contentValues.put("table_name", symbol);
        db.insertWithOnConflict("table_list", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public void insertDailyData(String symbol, String date, double open, double high, double low, double close, long volume) {
        createTableIfNotExists(symbol);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("open", open);
        contentValues.put("high", high);
        contentValues.put("low", low);
        contentValues.put("close", close);
        contentValues.put("volume", volume);

        db.insertWithOnConflict(symbol, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

}
