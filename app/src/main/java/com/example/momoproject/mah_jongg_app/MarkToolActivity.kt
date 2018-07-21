package com.example.momoproject.mah_jongg_app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import kotlinx.android.synthetic.main.mark_tool.*
import kotlin.math.floor

class MarkToolActivity : AppCompatActivity() {

    //和了
    private lateinit var completionSpinner: Spinner
    //雀頭
    private lateinit var headSpinner: Spinner
    //待ち
    private lateinit var waitSpinner: Spinner
    //順子
    private lateinit var shuntuTyuTyanSpinner: Spinner
    private lateinit var shuntuYaoThuSpinner: Spinner
    //明刻子
    private lateinit var koutuMeiTyuTyanSpinner: Spinner
    private lateinit var koutuMeiYaoThuSpinner: Spinner
    //暗刻子
    private lateinit var koutuAnTyuTyanSpinner: Spinner
    private lateinit var koutuAnYaoThuSpinner: Spinner
    //明槓子
    private lateinit var kantuMeiTyuTyanSpinner: Spinner
    private lateinit var kantuMeiYaoThuSpinner: Spinner
    //暗槓子
    private lateinit var kantuAnTyuTyanSpinner: Spinner
    private lateinit var kantuAnYaoThuSpinner: Spinner
    //計算
    private lateinit var resuluButton: Button
    private lateinit var resultText: TextView

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
        shuntuTyuTyanSpinner = findViewById(R.id.shunTanYao)
        shuntuYaoThuSpinner = findViewById(R.id.shunYaoThu)
        //明刻子
        koutuMeiTyuTyanSpinner = findViewById(R.id.kouMTanYao)
        koutuMeiYaoThuSpinner = findViewById(R.id.kouMYaoThu)
        //暗刻子
        koutuAnTyuTyanSpinner = findViewById(R.id.kouATanYao)
        koutuAnYaoThuSpinner = findViewById(R.id.kouAYaoThu)
        //明槓子
        kantuMeiTyuTyanSpinner = findViewById(R.id.kanMTanYao)
        kantuMeiYaoThuSpinner = findViewById(R.id.kanMYaoThu)
        //暗槓子
        kantuAnTyuTyanSpinner = findViewById(R.id.kanATanYao)
        kantuAnYaoThuSpinner = findViewById(R.id.kanAYaoThu)
        //計算
        resuluButton = findViewById(R.id.calculateButton)
        resultText = findViewById(R.id.markResultText)
    }

    private fun calculation() {
        headSum()
        waitSum()
        mentuSum()
        val resultUp = floor(resultInt / 10.0)
        if (selectCheck()) {
            getString(R.string.markResultC, resultUp.toInt())
        } else {
            resultText.text = getString(R.string.error)
        }
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

    private fun mentuSum() {
        val KoMTanYao = koutuMeiTyuTyanSpinner.selectedItem.toString().dropLast(1).toInt()
        val KoMYaoThu = koutuMeiYaoThuSpinner.selectedItem.toString().dropLast(1).toInt()
        val KoATanYao = koutuAnTyuTyanSpinner.selectedItem.toString().dropLast(1).toInt()
        val KoAYaoThu = koutuAnYaoThuSpinner.selectedItem.toString().dropLast(1).toInt()
        val KaMTanYao = kantuMeiTyuTyanSpinner.selectedItem.toString().dropLast(1).toInt()
        val KaMYaoThu = kantuMeiYaoThuSpinner.selectedItem.toString().dropLast(1).toInt()
        val KaATanYao = kantuAnTyuTyanSpinner.selectedItem.toString().dropLast(1).toInt()
        val KaAYaoThu = kantuAnYaoThuSpinner.selectedItem.toString().dropLast(1).toInt()

        resultInt += KoMTanYao * 2 + KoMYaoThu * 4
        +KoATanYao * 4 + KoAYaoThu * 8
        +KaMTanYao * 8 + KaMYaoThu * 16
        +KaATanYao * 16 + KaAYaoThu * 32
    }

    private fun selectCheck(): Boolean {
        val STanYao = shuntuTyuTyanSpinner.selectedItem.toString().dropLast(1).toInt()
        val SYaoThu = shuntuYaoThuSpinner.selectedItem.toString().dropLast(1).toInt()
        val KoMTanYao = koutuMeiTyuTyanSpinner.selectedItem.toString().dropLast(1).toInt()
        val KoMYaoThu = koutuMeiYaoThuSpinner.selectedItem.toString().dropLast(1).toInt()
        val KoATanYao = koutuAnTyuTyanSpinner.selectedItem.toString().dropLast(1).toInt()
        val KoAYaoThu = koutuAnYaoThuSpinner.selectedItem.toString().dropLast(1).toInt()
        val KaMTanYao = kantuMeiTyuTyanSpinner.selectedItem.toString().dropLast(1).toInt()
        val KaMYaoThu = kantuMeiYaoThuSpinner.selectedItem.toString().dropLast(1).toInt()
        val KaATanYao = kantuAnTyuTyanSpinner.selectedItem.toString().dropLast(1).toInt()
        val KaAYaoThu = kantuAnYaoThuSpinner.selectedItem.toString().dropLast(1).toInt()
        val sumResult = STanYao + SYaoThu + KoMTanYao + KoMYaoThu + KoATanYao + KoAYaoThu + KaMTanYao + KaMYaoThu + KaATanYao + KaAYaoThu
        //5つ以上の面子がないかの判定
        return sumResult <= 4

    }

}
