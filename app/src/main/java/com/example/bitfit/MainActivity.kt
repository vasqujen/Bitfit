package com.example.bitfit

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateTV:TextView = findViewById(R.id.date_tv)
        val calAvg:TextView = findViewById(R.id.avg_cal_tv)
        val addFoodBtn:Button = findViewById(R.id.add_food_btn)

        val listData = ArrayList<Model>()
        val db = DBHelper(this, null)
        val rView = findViewById<RecyclerView>(R.id.recyclerView)
        rView.layoutManager = LinearLayoutManager(this)

        // set date
        val currentLDT = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
        dateTV.text = "\t\t\tDate: " + currentLDT.format(formatter)

        // date for calcing stuph
        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        Log.i("DATESTUFF", dayOfYear.toString())

        // populate RV
        val cursor = db.getFood()
        var avgCal = 0
        var entryCount = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                listData.add(
                    Model(
                        cursor.getString(cursor.getColumnIndex("food_name")),
                        cursor.getInt(cursor.getColumnIndex("calorie_count"))
                    )
                )

                // sum of calories today
                if (cursor.getInt(cursor.getColumnIndex("day_of_year")) == dayOfYear){
                    avgCal += cursor.getInt(cursor.getColumnIndex("calorie_count"))
                    entryCount ++
                }

                val adapter = RVAdapter(listData)
                rView.adapter = adapter
            }
            if(entryCount > 0) calAvg.text = "Avg. Calories: " + (avgCal/entryCount)



        }

        if(windowManager.defaultDisplay.rotation == 90 || windowManager.defaultDisplay.rotation == 180) {
            val imgFragment: DailyImageFrag = DailyImageFrag()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.land_daily_img_frag, imgFragment, "DailyImageFragment")
                .commit()
        }

        addFoodBtn.setOnClickListener{
            val intent = Intent(this, UserInputActivity::class.java)
            startActivity(intent)
        }
    }
}
