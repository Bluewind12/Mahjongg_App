package com.example.momoproject.mah_jongg_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private lateinit var playerButton: Array<ImageButton>

    private lateinit var fieldButton: ImageButton
    private var fieldNum = 0
    private lateinit var pointButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playerButton = arrayOf(
                findViewById(R.id.imageButton1),
                findViewById(R.id.imageButton2),
                findViewById(R.id.imageButton3),
                findViewById(R.id.imageButton4))
        fieldButton = findViewById(R.id.imageButtonF)
        pointButton = findViewById(R.id.pointButton)

        playerButton[0].rotation = 90f
        playerButton[1].rotation = 0f
        playerButton[2].rotation = -90f
        playerButton[3].rotation = -180f

        playerButton[0].setOnClickListener {
            changeImages(0)
        }
        playerButton[1].setOnClickListener {
            changeImages(1)
        }
        playerButton[2].setOnClickListener {
            changeImages(2)
        }
        playerButton[3].setOnClickListener {
            changeImages(3)
        }

        fieldButton.setOnClickListener {
            when (fieldNum % 4) {
                0 -> fieldButton.setImageResource(R.drawable.hougaku1_higashi)
                1 -> fieldButton.setImageResource(R.drawable.hougaku3_minami)
                2 -> fieldButton.setImageResource(R.drawable.hougaku2_nishi)
                3 -> fieldButton.setImageResource(R.drawable.hougaku4_kita)
                else -> error("error")
            }
            fieldNum++
        }
    }

    private fun changeImages(touchButtonNum: Int) {
        playerButton[touchButtonNum].setImageResource(R.drawable.hougaku1_higashi)
        playerButton[(touchButtonNum + 1) % 4].setImageResource(R.drawable.hougaku3_minami)
        playerButton[(touchButtonNum + 2) % 4].setImageResource(R.drawable.hougaku2_nishi)
        playerButton[(touchButtonNum + 3) % 4].setImageResource(R.drawable.hougaku4_kita)
    }
}
