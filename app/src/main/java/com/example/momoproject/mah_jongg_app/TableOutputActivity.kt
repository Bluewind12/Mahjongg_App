package com.example.momoproject.mah_jongg_app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView

class TableOutputActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_view)
        imageView = findViewById(R.id.imageViewOutput)
        when (intent.getStringExtra("image")) {
            "oya_point" -> imageView.setImageResource(R.drawable.point_oya)
            "ko_point" -> imageView.setImageResource(R.drawable.point_ko)
            "fu" -> imageView.setImageResource(R.drawable.hougaku2_nishi)
            else -> imageView.setImageResource(R.drawable.hougaku4_kita)
        }
    }
}