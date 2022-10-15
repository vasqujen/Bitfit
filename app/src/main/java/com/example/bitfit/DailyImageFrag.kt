package com.example.bitfit

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class DailyImageFrag : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.daily_img_frag, container, false)
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var paths = mutableListOf<String>()
        val image: ImageView = view.findViewById(R.id.daily_image)

        image.setOnClickListener() {
            val db = DBHelper(view.context, null)
            val cursor = db.getImg()

            var pathCount = 0
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    paths.add(cursor.getString(cursor.getColumnIndex("path")))
                    pathCount++
                }
            }
            var randRange = (0..pathCount).random()

            Log.i("IMGRES", paths.get(randRange))

            image.setImageResource(paths.get(randRange).toInt())
        }
    }

}