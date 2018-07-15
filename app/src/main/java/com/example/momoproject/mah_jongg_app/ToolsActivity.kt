package com.example.momoproject.mah_jongg_app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

class ToolsActivity : AppCompatActivity() {
    private lateinit var okButton: Button
    private lateinit var pointButton: Button
    private lateinit var markButton: Button

    private lateinit var basicPointTextView: TextView
    private lateinit var parentTextView: TextView
    private lateinit var childTextView: TextView

    private lateinit var translateSpinner: Spinner
    private lateinit var markSpinner: Spinner
    private var baseInt = 0
    private var parentInt = 0
    private var childInt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.point_tools)
        okButton = findViewById(R.id.calculationButton)
        pointButton = findViewById(R.id.pointTable)
        markButton = findViewById(R.id.markTable)
        basicPointTextView = findViewById(R.id.basicText)
        parentTextView = findViewById(R.id.pearentText)
        childTextView = findViewById(R.id.childText)
        translateSpinner = findViewById(R.id.hanSpinner)
        markSpinner = findViewById(R.id.markSpinner)

        okButton.setOnClickListener {
            val role = getRole()
            val mark = getMarks()

        }

    }

    private fun getRole(): Int {
        val roleString = translateSpinner.selectedItem.toString()
        when (roleString) {
            "1翻" -> return 1
            "2翻" -> return 2
            "3翻" -> return 3
            "4翻" -> return 4
            "5翻" -> return 5
            "6翻" -> return 6
            "7翻" -> return 6
            "8翻" -> return 7
            "9翻" -> return 7
            "10翻" -> return 7
            "11翻" -> return 8
            "12翻" -> return 8
            "13翻以上" -> return 9
            else -> return 0
        }
    }

    private fun getMarks(): Int {
        val markString = markSpinner.selectedItem.toString()
        when (markString) {
            "平和ツモ" -> return 10
            "七対子" -> return 20
            "30符" -> return 30
            "40符" -> return 40
            "50符" -> return 50
            "60符" -> return 60
            "70符" -> return 70
            "80符" -> return 80
            "90符" -> return 90
            "100符" -> return 100
            "110符" -> return 110
            else -> return 0
        }
    }

    private fun countPoint(point: Int) {
        when (point) {
            //1翻
            31 -> setPoint(1000,500,300)
            41 -> setPoint(1300,700,400)
            51 -> setPoint(1600,800,400)
            61 -> setPoint(2000,1000,500)
            71 -> setPoint(2300,1200,600)
            81 -> setPoint(2600,1300,700)
            91 -> setPoint(2900,1500,800)
            101 -> setPoint(3200,1600,900)
            111 -> setPoint(3600,1800,900)
            //2翻
            12 -> setPoint(1000,1500,500)
            22 -> setPoint(1000,1500,500)
            32 -> setPoint(1000,1500,500)
            42 -> setPoint(1000,1500,500)
            52 -> setPoint(1000,1500,500)
            62 -> setPoint(1000,1500,500)
            72 -> setPoint(1000,1500,500)
            82 -> setPoint(1000,1500,500)
            92 -> setPoint(1000,1500,500)
            102 -> setPoint(1000,1500,50)
            112 -> setPoint(1000,1500,50)
            //3翻
            13 -> setPoint(1000,1500,500)
            23 -> setPoint(1000,1500,500)
            33 -> setPoint(1000,1500,500)
            43 -> setPoint(1000,1500,500)
            53 -> setPoint(1000,1500,500)
            63 -> setPoint(1000,1500,500)
            73 -> setPoint(1000,1500,500)
            83 -> setPoint(1000,1500,500)
            93 -> setPoint(1000,1500,500)
            103 -> setPoint(1000,1500,50)
            113 -> setPoint(1000,1500,50)
            //4翻
            14 -> setPoint(1000,1500,500)
            24 -> setPoint(1000,1500,500)
            34 -> setPoint(1000,1500,500)
            44 -> setPoint(1000,1500,500)
            54 -> setPoint(1000,1500,500)
            64 -> setPoint(1000,1500,500)
            74 -> setPoint(1000,1500,500)
            84 -> setPoint(1000,1500,500)
            94 -> setPoint(1000,1500,500)
            104 -> setPoint(1000,1500,50)
            114 -> setPoint(1000,1500,50)
            else -> error(-1)
        }
    }
    private  fun setPoint(base:Int,parent:Int,child:Int) {

    }
}