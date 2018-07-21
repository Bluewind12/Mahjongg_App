package com.example.momoproject.mah_jongg_app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Spinner

class MarkToolActivity : AppCompatActivity() {

    //和了
    private lateinit var completionSpinner: Spinner
    //雀頭
    private lateinit var headSpinner: Spinner
    //待ち
    private lateinit var waitSpinner: Spinner
    //順子
    private lateinit var shuntuTyuTyanSpinner: Spinner
    private lateinit var shuntuTannYaoSpinner: Spinner
    //明刻子
    private lateinit var koutuMeiTyuTyanSpinner: Spinner
    private lateinit var koutuMeiTannYaoSpinner: Spinner
    //暗刻子
    private lateinit var koutuAnTyuTyanSpinner: Spinner
    private lateinit var koutuAnTannYaoSpinner: Spinner
    //明槓子
    private lateinit var kantuMeiTyuTyanSpinner: Spinner
    private lateinit var kantuMeiTannYaoSpinner: Spinner
    //暗槓子
    private lateinit var kantuAnTyuTyanSpinner: Spinner
    private lateinit var kantuAnTannYaoSpinner: Spinner
    //計算ボタン
    private lateinit var resuluButton: Button

    var resultInt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mark_tool)
        init()

        resuluButton.setOnClickListener {
            //初期化
            resultInt = 0

            val compS = completionSpinner.selectedItem.toString()
            when (compS) {
                "ロン：鳴きなし" -> {
                    resultInt += 20 + 10
                    calculation()
                }
                "ロン：鳴きあり" -> {
                    resultInt += 20
                    calculation()
                }
                "ツモ：鳴きなし" -> {
                    resultInt += 20 + 2
                    calculation()
                }
                "ツモ：鳴きあり" -> {
                    resultInt += 20 + 2
                    calculation()
                }
                "ツモ：リンシャン" -> {
                    resultInt += 20 + 0
                    calculation()
                }
                "ピンフ：ツモ" -> {
                    resultInt += 20 + 0
                }
                "ピンフ：ロン" -> {
                    resultInt += 20 + 10
                }
                "七対子：ツモ" -> {
                    resultInt += 20 + 5
                }
                "七対子：ロン" -> {
                    resultInt += 20 + 5
                }
            }

        }


    }

    private fun init() {
        //和了
        completionSpinner = findViewById(R.id.spinner)
        //雀頭
        headSpinner = findViewById(R.id.spinner2)
        //待ち
        waitSpinner = findViewById(R.id.spinner3)
        //順子
        shuntuTyuTyanSpinner = findViewById(R.id.shunTT)
        shuntuTannYaoSpinner = findViewById(R.id.shunTY)
        //明刻子
        koutuMeiTyuTyanSpinner = findViewById(R.id.kouMTT)
        koutuMeiTannYaoSpinner = findViewById(R.id.kouMTY)
        //暗刻子
        koutuAnTyuTyanSpinner = findViewById(R.id.kouATT)
        koutuAnTannYaoSpinner = findViewById(R.id.kouATY)
        //明槓子
        kantuMeiTyuTyanSpinner = findViewById(R.id.kanMTT)
        kantuMeiTannYaoSpinner = findViewById(R.id.kanMTY)
        //暗槓子
        kantuAnTyuTyanSpinner = findViewById(R.id.kanATT)
        kantuAnTannYaoSpinner = findViewById(R.id.kanATY)
        //計算ボタン
        resuluButton = findViewById(R.id.calculationButton)
    }

    private fun calculation() {
        headSum()
        waitSum()
    }

    private fun headSum() {
        val headS = headSpinner.selectedItem.toString()
        when (headS) {
            "数牌" -> {
                resultInt += 0
            }
            "三元牌" -> {
                resultInt += 2
            }
            "場風牌" -> {
                resultInt += 2
            }
            "自風牌" -> {
                resultInt += 2
            }
            "客風牌" -> {
                resultInt += 0
            }
            "場風・自風牌" -> {
                resultInt += 4
            }
        }
    }

    private fun waitSum() {
        val waitS = waitSpinner.selectedItem.toString()
        when (waitS) {
            "単騎待ち（ﾀﾝｷ）" -> {
                resultInt += 2
            }
            "辺張待ち（ﾍﾟﾝﾁｬﾝ）" -> {
                resultInt += 2
            }
            "嵌張待ち（ｶﾝﾁｬﾝ）" -> {
                resultInt += 2
            }
            "両面待ち（ﾘｬﾝﾒﾝ）" -> {
                resultInt += 0
            }
            "双碰待ち（ｼｬﾝﾎﾟﾝ）" -> {
                resultInt += 0
            }
        }
    }

}