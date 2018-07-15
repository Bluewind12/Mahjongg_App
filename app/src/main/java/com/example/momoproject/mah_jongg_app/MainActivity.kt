package com.example.momoproject.mah_jongg_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var playerButton: Array<ImageButton>
    private lateinit var honbaText: Array<TextView>
    private lateinit var pointButton: Array<Button>

    private lateinit var fieldButton: ImageButton
    private var fieldNum = 0
    private var parent = 0
    private var honba = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playerButton = arrayOf(
                findViewById(R.id.imageButton1),
                findViewById(R.id.imageButton2),
                findViewById(R.id.imageButton3),
                findViewById(R.id.imageButton4))
        honbaText = arrayOf(
                findViewById(R.id.honba1),
                findViewById(R.id.honba2),
                findViewById(R.id.honba3),
                findViewById(R.id.honba4))
        fieldButton = findViewById(R.id.imageButtonF)
        pointButton = arrayOf(
                findViewById(R.id.pointButton),
                findViewById(R.id.pointButton2),
                findViewById(R.id.pointButton3),
                findViewById(R.id.pointButton4))

        playerButton[0].rotation = 90f
        playerButton[1].rotation = 0f
        playerButton[2].rotation = -90f
        playerButton[3].rotation = -180f
        honbaText[0].rotation = 90f
        honbaText[1].rotation = 0f
        honbaText[2].rotation = -90f
        honbaText[3].rotation = -180f

        changeImages(parent)
        honbaText[0].text = getString(R.string.place, 0)
        playerButton[0].setOnClickListener {
            if (parent == 0) {
                honba++
                honbaText[0].text = getString(R.string.place, honba)
            } else {
                parent = 0
                honba = 0
                changeImages(0)
                honbaText[0].text = getString(R.string.place, honba)
                honbaText[1].text = " "
                honbaText[2].text = " "
                honbaText[3].text = " "
            }
        }
        playerButton[1].setOnClickListener {
            if (parent == 1) {
                honba++
                honbaText[1].text = getString(R.string.place, honba)
            } else {
                parent = 1
                honba = 0
                changeImages(1)
                honbaText[0].text = " "
                honbaText[1].text = getString(R.string.place, honba)
                honbaText[2].text = " "
                honbaText[3].text = " "
            }
        }
        playerButton[2].setOnClickListener {
            if (parent == 2) {
                honba++
                honbaText[2].text = getString(R.string.place, honba)
            } else {
                parent = 2
                honba = 0
                changeImages(2)
                honbaText[0].text = " "
                honbaText[1].text = " "
                honbaText[2].text = getString(R.string.place, honba)
                honbaText[3].text = " "
            }
        }
        playerButton[3].setOnClickListener {
            if (parent == 3) {
                honba++
                honbaText[3].text = getString(R.string.place, honba)
            } else {
                parent = 3
                honba = 0
                changeImages(3)
                honbaText[0].text = " "
                honbaText[1].text = " "
                honbaText[2].text = " "
                honbaText[3].text = getString(R.string.place, honba)
            }
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
        pointButton[0].setOnClickListener {
            val intent = Intent(this, ToolsActivity::class.java)
            startActivity(intent)
        }
        pointButton[1].setOnClickListener {
            val intent = Intent(this, ToolsActivity::class.java)
            startActivity(intent)
        }
        pointButton[2].setOnClickListener {
            val intent = Intent(this, ToolsActivity::class.java)
            startActivity(intent)
        }
        pointButton[3].setOnClickListener {
            val intent = Intent(this, ToolsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun changeImages(touchButtonNum: Int) {
        playerButton[touchButtonNum].setImageResource(R.drawable.hougaku1_higashi)
        playerButton[(touchButtonNum + 1) % 4].setImageResource(R.drawable.hougaku3_minami)
        playerButton[(touchButtonNum + 2) % 4].setImageResource(R.drawable.hougaku2_nishi)
        playerButton[(touchButtonNum + 3) % 4].setImageResource(R.drawable.hougaku4_kita)

    }
}
