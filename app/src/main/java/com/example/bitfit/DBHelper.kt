package com.example.bitfit

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY, " +
                FOOD + " TEXT," +
                CALORIE + " INTEGER," +
                DAYOFYEAR + " INTEGER"
                +")")

        val imgQuery = ("CREATE TABLE IF NOT EXISTS " + IMG_TABLE_NAME + " ("
                + IMG_ID + " INTEGER PRIMARY KEY, " +
                IMG_PATH + " INTEGER"
                + ")")

        db.execSQL(query)
        db.execSQL(imgQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun addFood(foodName : String, calorieCount : String){
        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val values = ContentValues()
        val db = this.writableDatabase

        values.put(FOOD, foodName)
        values.put(CALORIE, calorieCount)
        values.put(DAYOFYEAR, dayOfYear)

        Log.i("DB_VAL", values.toString())


        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun addImg(imgPath:String){
        val values = ContentValues()
        val db = this.writableDatabase

        values.put(IMG_PATH, imgPath)

        db.insert(IMG_TABLE_NAME, null, values)
    }

    fun getFood(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)

    }

    fun getImg(): Cursor?{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $IMG_TABLE_NAME", null)
    }


    fun killImgTable(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $IMG_TABLE_NAME")
    }

    companion object{
        private val DATABASE_NAME = "BitFit"
        private val DATABASE_VERSION = 3

        val TABLE_NAME = "calorie_table"

        val ID = "id"
        val FOOD = "food_name"
        val CALORIE = "calorie_count"
        val DAYOFYEAR = "day_of_year"

        ////////////////////////////////

        val IMG_TABLE_NAME = "DailyImages"
        val IMG_ID = "id"
        val IMG_PATH = "path"
    }
}
