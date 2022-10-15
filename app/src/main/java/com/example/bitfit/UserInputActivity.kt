package com.example.bitfit

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class UserInputActivity : AppCompatActivity(){
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_input)

        val db = DBHelper(this, null)
        val getFood: EditText = findViewById(R.id.food_ET)
        val getCal: EditText = findViewById(R.id.cal_ET)
        val saveBTN: Button = findViewById(R.id.save_food)

        saveBTN.setOnClickListener {
            val foodName = getFood.text.toString()
            val calCount = getCal.text.toString()

            db.addFood(foodName, calCount)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}