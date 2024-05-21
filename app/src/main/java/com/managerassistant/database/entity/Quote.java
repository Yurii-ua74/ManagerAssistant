package com.managerassistant.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "quote")
public class Quote {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String symbol;
    public float open;
    public float high;
    public float low;
    public float close;
    public long timestamp;
}
