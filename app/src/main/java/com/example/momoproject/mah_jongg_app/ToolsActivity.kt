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
            else -> error("翻エラー")
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
            else -> error("符エラー")
        }
    }

    private fun countPoint(point: Int) {
        when (point % 10) {
            5 -> setPoint(80, 40, 20)
            6 -> setPoint(120, 60, 30)
            7 -> setPoint(160, 80, 40)
            8 -> setPoint(240, 120, 60)
            9 -> setPoint(320, 160, 80)
            else -> when (point) {
            //1翻
                31 -> setPoint(10, 5, 3)
                41 -> setPoint(13, 7, 4)
                51 -> setPoint(16, 8, 4)
                61 -> setPoint(20, 10, 5)
                71 -> setPoint(23, 12, 6)
                81 -> setPoint(26, 13, 7)
                91 -> setPoint(29, 15, 8)
                101 -> setPoint(32, 16, 9)
                111 -> setPoint(36, 18, 9)
            //2翻
                12 -> setPoint(13, 7, 4)
                22 -> setPoint(16, 8, 4)
                32 -> setPoint(20, 10, 5)
                42 -> setPoint(26, 13, 7)
                52 -> setPoint(32, 16, 8)
                62 -> setPoint(39, 20, 10)
                72 -> setPoint(45, 23, 12)
                82 -> setPoint(52, 26, 13)
                92 -> setPoint(58, 29, 15)
                102 -> setPoint(64, 32, 16)
                112 -> setPoint(71, 36, 18)
            //3翻
                13 -> setPoint(26, 13, 7)
                23 -> setPoint(32, 16, 8)
                33 -> setPoint(39, 20, 10)
                43 -> setPoint(52, 26, 13)
                53 -> setPoint(64, 32, 16)
                63 -> setPoint(77, 39, 20)
                73 -> setPoint(80, 40, 20)
                83 -> setPoint(80, 40, 20)
                93 -> setPoint(80, 40, 20)
                103 -> setPoint(80, 40, 20)
                113 -> setPoint(80, 40, 20)
            //4翻
                14 -> setPoint(52, 26, 13)
                24 -> setPoint(64, 32, 16)
                34 -> setPoint(77, 39, 20)
                44 -> setPoint(80, 40, 20)
                54 -> setPoint(80, 40, 20)
                64 -> setPoint(80, 40, 20)
                74 -> setPoint(80, 40, 20)
                84 -> setPoint(80, 40, 20)
                94 -> setPoint(80, 40, 20)
                104 -> setPoint(80, 40, 20)
                114 -> setPoint(80, 40, 20)
                else -> error(-1)
            }
        }
    }

    private fun setPoint(base: Int, parent: Int, child: Int) {

    }
}