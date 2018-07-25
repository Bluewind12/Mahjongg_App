package com.example.momoproject.mah_jongg_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    //配列を用いたもの
    private lateinit var playerButtons: Array<ImageButton>
    private lateinit var timesTexts: Array<TextView>
    private lateinit var pointButtons: Array<Button>
    //イメージボタン
    private lateinit var fieldButton: ImageButton
    //データ記録、呼び出し用
    private lateinit var dataStore: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    //数値管理
    private var fieldNum = 0
    private var parent = 0
    private var times = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //初期状態セット
        initialSetting()
        //回転セット
        rotateSetting()

        setState(parent)
        setFieldWind(fieldNum)
        playerButtons[0].setOnClickListener {
            changeState(0)
        }
        playerButtons[1].setOnClickListener {
            changeState(1)
        }
        playerButtons[2].setOnClickListener {
            changeState(2)
        }
        playerButtons[3].setOnClickListener {
            changeState(3)
        }

        //中央(場風)タッチ時
        fieldButton.setOnClickListener {
            fieldNum++

            if (fieldNum == 4) {
                fieldNum = 0
            }
            setFieldWind(fieldNum)
            editor.putInt("field", fieldNum)
            editor.apply()
        }

        //プレイヤーボタンタッチ時
        pointButtons[0].setOnClickListener {
            val intent = Intent(this, ToolsActivity::class.java)
            startActivity(intent)
        }
        pointButtons[1].setOnClickListener {
            val intent = Intent(this, ToolsActivity::class.java)
            startActivity(intent)
        }
        pointButtons[2].setOnClickListener {
            val intent = Intent(this, ToolsActivity::class.java)
            startActivity(intent)
        }
        pointButtons[3].setOnClickListener {
            val intent = Intent(this, ToolsActivity::class.java)
            startActivity(intent)
        }
    }

    //初期設定
    private fun initialSetting() {
        playerButtons = arrayOf(
                findViewById(R.id.imageButton1),
                findViewById(R.id.imageButton2),
                findViewById(R.id.imageButton3),
                findViewById(R.id.imageButton4))
        timesTexts = arrayOf(
                findViewById(R.id.honba1),
                findViewById(R.id.honba2),
                findViewById(R.id.honba3),
                findViewById(R.id.honba4))
        fieldButton = findViewById(R.id.imageButtonF)
        pointButtons = arrayOf(
                findViewById(R.id.pointButton),
                findViewById(R.id.pointButton2),
                findViewById(R.id.pointButton3),
                findViewById(R.id.pointButton4))
        dataStore = getSharedPreferences("MainData", Context.MODE_PRIVATE)
        editor = dataStore.edit()
        editor.apply()
        fieldNum = dataStore.getInt("field", 0)
        parent = dataStore.getInt("parent", 0)
        times = dataStore.getInt("times", 0)
    }

    //回転管理
    private fun rotateSetting() {
        playerButtons[0].rotation = 90f
        playerButtons[1].rotation = 0f
        playerButtons[2].rotation = -90f
        playerButtons[3].rotation = -180f
        timesTexts[0].rotation = 90f
        timesTexts[1].rotation = 0f
        timesTexts[2].rotation = -90f
        timesTexts[3].rotation = -180f
    }

    //状態変更
    private fun changeState(touchButtonNum: Int) {
        if (parent == touchButtonNum) {
            times++
            timesTexts[touchButtonNum].text = getString(R.string.place, times)
        } else {
            parent = touchButtonNum
            times = 0
            setState(touchButtonNum)
        }
        editor.putInt("times", times)
        editor.putInt("parent", parent)
        editor.apply()
    }

    //プレイヤー状態のセット
    private fun setState(setNum: Int) {
        playerButtons[setNum].setImageResource(R.drawable.hougaku1_higashi)
        playerButtons[(setNum + 1) % 4].setImageResource(R.drawable.hougaku3_minami)
        playerButtons[(setNum + 2) % 4].setImageResource(R.drawable.hougaku2_nishi)
        playerButtons[(setNum + 3) % 4].setImageResource(R.drawable.hougaku4_kita)
        timesTexts[setNum].text = getString(R.string.place, times)
        timesTexts[(setNum + 1) % 4].text = " "
        timesTexts[(setNum + 2) % 4].text = " "
        timesTexts[(setNum + 3) % 4].text = " "
    }

    //場のセット
    private fun setFieldWind(windNum: Int) {
        when (windNum) {
            0 -> fieldButton.setImageResource(R.drawable.hougaku1_higashi)
            1 -> fieldButton.setImageResource(R.drawable.hougaku3_minami)
            2 -> fieldButton.setImageResource(R.drawable.hougaku2_nishi)
            3 -> fieldButton.setImageResource(R.drawable.hougaku4_kita)
            else -> error("error")
        }
    }
}
