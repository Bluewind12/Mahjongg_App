package momonyan.mahjongg_tools

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.description_mentu.view.*
import kotlinx.android.synthetic.main.mark_tool.*
import net.nend.android.NendAdListener
import net.nend.android.NendAdView
import kotlin.math.ceil


class MarkToolActivity : AppCompatActivity(), NendAdListener {

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
    private lateinit var resultButton: Button
    private lateinit var resultText: TextView

    var resultInt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        val dataStore = getSharedPreferences("MainData", Context.MODE_PRIVATE)
        when (dataStore.getString("Theme", "Dark")) {
            "Dark" -> setTheme(R.style.DarkThemeAction)
            "Light" -> setTheme(R.style.LightThemeAction)
            "Mat" -> setTheme(R.style.MatThemeAction)
            else -> error(2)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.mark_tool)
        init()

        //計算ボタン
        resultButton.setOnClickListener {
            //初期化
            resultInt = 0
            var sumCheck = true

            val compS = completionSpinner.selectedItem.toString()
            when (compS) {
                "ロン：鳴きなし" -> {
                    resultInt += 20 + 10
                    syoukei1.text = getString(R.string.markSyoukei, 10)
                    calculation()
                }
                "ロン：鳴きあり" -> {
                    resultInt += 20
                    syoukei1.text = getString(R.string.markSyoukei, 0)
                    calculation()
                }
                "ツモ：鳴きなし" -> {
                    resultInt += 20 + 2
                    syoukei1.text = getString(R.string.markSyoukei, 2)
                    calculation()
                }
                "ツモ：鳴きあり" -> {
                    resultInt += 20 + 2
                    syoukei1.text = getString(R.string.markSyoukei, 2)
                    calculation()
                }
                "ツモ：リンシャン" -> {
                    resultInt += 20 + 0
                    syoukei1.text = getString(R.string.markSyoukei, 0)
                    calculation()
                }
                "ピンフ：ツモ" -> {
                    resultInt += 20 + 0
                    syoukei1.text = getString(R.string.markSyoukei, 20)
                    sumCheck = false
                }
                "ピンフ：ロン" -> {
                    resultInt += 20 + 10
                    syoukei1.text = getString(R.string.markSyoukei, 30)
                    sumCheck = false
                }
                "七対子：ツモ" -> {
                    resultInt += 20 + 5
                    syoukei1.text = getString(R.string.markSyoukei, 25)
                    sumCheck = false
                }
                "七対子：ロン" -> {
                    resultInt += 20 + 5
                    syoukei1.text = getString(R.string.markSyoukei, 25)
                    sumCheck = false
                }
            }
            if (sumCheck) {
                val resultUp = ceil(resultInt / 10.0)
                if (selectCheck()) {
                    syoukei5.text = getString(R.string.tumo_20)
                    resultText.text = getString(R.string.markResultC, resultUp.toInt() * 10, resultInt)
                } else {
                    resultText.text = getString(R.string.error)
                }
            } else {
                syoukei2.text = ""
                syoukei3.text = ""
                syoukei4.text = ""
                syoukei5.text = ""
                resultText.text = getString(R.string.markResultC, resultInt, resultInt)
            }
        }

        //罫線
        gridLayout.post {
            //横線
            //数値入れ
            spaceView1.layoutParams.width = gridLayout.width
            spaceView2.layoutParams.width = gridLayout.width
            spaceView3.layoutParams.width = gridLayout.width
            spaceView4.layoutParams.width = gridLayout.width
            spaceView5.layoutParams.width = gridLayout.width
            //セット
            spaceView1.requestLayout()
            spaceView2.requestLayout()
            spaceView3.requestLayout()
            spaceView4.requestLayout()
            spaceView5.requestLayout()

            //縦線
            //数値入れ
            spaceView6.layoutParams.height = gridLayout.height
            spaceView7.layoutParams.height = gridLayout.height
            //セット
            spaceView6.requestLayout()
            spaceView7.requestLayout()
        }

        //表についての説明表示
        val inflater = layoutInflater
        val descriptionView = inflater.inflate(R.layout.description_mentu, null)
        val toast = Toast(this)
        toast.duration = Toast.LENGTH_LONG
        mentuItemText.setOnClickListener {
            descriptionView.descriptionTitleText.text = getString(R.string.description1Title)
            descriptionView.descriptionTextView.text = getString(R.string.description1)
            descriptionView.descriptionImage.setImageResource(R.drawable.description_1)
            toast.view = descriptionView
            toast.show()
        }
        mentuItemText2.setOnClickListener {
            descriptionView.descriptionTitleText.text = getString(R.string.description2Title)
            descriptionView.descriptionTextView.text = getString(R.string.description2)
            descriptionView.descriptionImage.setImageResource(R.drawable.description_2)
            toast.view = descriptionView
            toast.show()
        }
        mentuItemText3.setOnClickListener {
            descriptionView.descriptionTitleText.text = getString(R.string.description3Title)
            descriptionView.descriptionTextView.text = getString(R.string.description3)
            descriptionView.descriptionImage.setImageResource(R.drawable.description_3)
            toast.view = descriptionView
            toast.show()
        }
        mentuItemText4.setOnClickListener {
            descriptionView.descriptionTitleText.text = getString(R.string.description4Title)
            descriptionView.descriptionTextView.text = getString(R.string.description4)
            descriptionView.descriptionImage.setImageResource(R.drawable.description_4)
            toast.view = descriptionView
            toast.show()
        }
        mentuItemText5.setOnClickListener {
            descriptionView.descriptionTitleText.text = getString(R.string.description5Title)
            descriptionView.descriptionTextView.text = getString(R.string.description5)
            descriptionView.descriptionImage.setImageResource(R.drawable.description_5)
            toast.view = descriptionView
            toast.show()
        }
        tyutyanTextView.setOnClickListener {
            descriptionView.descriptionTitleText.text = getString(R.string.description01Title)
            descriptionView.descriptionTextView.text = getString(R.string.description01)
            descriptionView.descriptionImage.setImageResource(R.drawable.description_01)
            toast.view = descriptionView
            toast.show()
        }
        kokyuTextView.setOnClickListener {
            descriptionView.descriptionTitleText.text = getString(R.string.description02Title)
            descriptionView.descriptionTextView.text = getString(R.string.description02)
            descriptionView.descriptionImage.setImageResource(R.drawable.description_02)
            toast.view = descriptionView
            toast.show()
        }
        //スピナーセレクト
        //雀頭
        var firstFlagHead = true
        val headView = layoutInflater.inflate(R.layout.description_mentu, null)
        val headToast = Toast(this)
        headSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //何も選択されなかった時の動作
            override fun onNothingSelected(adapterView: AdapterView<*>) {}

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (firstFlagHead) {
                    firstFlagHead = false
                } else {
                    when (position) {
                        0 -> {
                            headView.descriptionTitleText.text = getString(R.string.head1Title)
                            headView.descriptionTextView.text = getString(R.string.head1)
                            headView.descriptionImage.setImageResource(R.drawable.head_1)
                        }
                        1 -> {
                            headView.descriptionTitleText.text = getString(R.string.head2Title)
                            headView.descriptionTextView.text = getString(R.string.head2)
                            headView.descriptionImage.setImageResource(R.drawable.head_2)
                        }
                        2 -> {
                            headView.descriptionTitleText.text = getString(R.string.head3Title)
                            headView.descriptionTextView.text = getString(R.string.head3)
                            headView.descriptionImage.setImageResource(R.drawable.head_3)
                        }
                        3 -> {
                            headView.descriptionTitleText.text = getString(R.string.head4Title)
                            headView.descriptionTextView.text = getString(R.string.head4)
                            headView.descriptionImage.setImageResource(R.drawable.head_4)
                        }
                        4 -> {
                            headView.descriptionTitleText.text = getString(R.string.head5Title)
                            headView.descriptionTextView.text = getString(R.string.head5)
                            headView.descriptionImage.setImageResource(R.drawable.head_5)
                        }
                        5 -> {
                            headView.descriptionTitleText.text = getString(R.string.head6Title)
                            headView.descriptionTextView.text = getString(R.string.head6)
                            headView.descriptionImage.setImageResource(R.drawable.head_6)
                        }
                        else -> error("ポジションエラー$position")
                    }
                    headToast.view = headView
                    headToast.show()
                }
            }
        }

        //待ち方
        var firstFlagWait = true
        val waitView = layoutInflater.inflate(R.layout.description_mentu, null)
        val waitToast = Toast(this)
        waitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //何も選択されなかった時の動作
            override fun onNothingSelected(adapterView: AdapterView<*>) {}

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (firstFlagWait) {
                    firstFlagWait = false
                } else {
                    when (position) {
                        0 -> {
                            waitView.descriptionTitleText.text = getString(R.string.waitDest1Title)
                            waitView.descriptionTextView.text = getString(R.string.waitDest1)
                            waitView.descriptionImage.setImageResource(R.drawable.wait_dest1)
                        }
                        1 -> {
                            waitView.descriptionTitleText.text = getString(R.string.waitDest2Title)
                            waitView.descriptionTextView.text = getString(R.string.waitDest2)
                            waitView.descriptionImage.setImageResource(R.drawable.wait_dest2)
                        }
                        2 -> {
                            waitView.descriptionTitleText.text = getString(R.string.waitDest3Title)
                            waitView.descriptionTextView.text = getString(R.string.waitDest3)
                            waitView.descriptionImage.setImageResource(R.drawable.wait_dest3)
                        }
                        3 -> {
                            waitView.descriptionTitleText.text = getString(R.string.waitDest4Title)
                            waitView.descriptionTextView.text = getString(R.string.waitDest4)
                            waitView.descriptionImage.setImageResource(R.drawable.wait_dest4)
                        }
                        4 -> {
                            waitView.descriptionTitleText.text = getString(R.string.waitDest5Title)
                            waitView.descriptionTextView.text = getString(R.string.waitDest5)
                            waitView.descriptionImage.setImageResource(R.drawable.wait_dest5)
                        }
                        else -> error("ポジションエラー$position")
                    }
                    waitToast.view = waitView
                    waitToast.show()
                }
            }
        }

        nend2.setListener(this)
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
        resultButton = findViewById(R.id.calculateButton)
        resultText = findViewById(R.id.markResultText)
    }

    private fun calculation() {
        headSum()
        waitSum()
        mentuSum()

    }

    private fun headSum() {
        val headS = headSpinner.selectedItem.toString()
        when (headS) {
            "数牌" -> {
                resultInt += 0
                syoukei2.text = getString(R.string.markSyoukei, 0)
            }
            "三元牌" -> {
                resultInt += 2
                syoukei2.text = getString(R.string.markSyoukei, 2)
            }
            "場風牌" -> {
                resultInt += 2
                syoukei2.text = getString(R.string.markSyoukei, 2)
            }
            "自風牌" -> {
                resultInt += 2
                syoukei2.text = getString(R.string.markSyoukei, 2)
            }
            "客風牌" -> {
                resultInt += 0
                syoukei2.text = getString(R.string.markSyoukei, 0)
            }
            "場風・自風牌" -> {
                resultInt += 4
                syoukei2.text = getString(R.string.markSyoukei, 4)
            }
        }
    }

    private fun waitSum() {
        val waitS = waitSpinner.selectedItem.toString()
        when (waitS) {
            "単騎待ち（ﾀﾝｷ）" -> {
                resultInt += 2
                syoukei3.text = getString(R.string.markSyoukei, 2)
            }
            "辺張待ち（ﾍﾟﾝﾁｬﾝ）" -> {
                resultInt += 2
                syoukei3.text = getString(R.string.markSyoukei, 2)
            }
            "嵌張待ち（ｶﾝﾁｬﾝ）" -> {
                resultInt += 2
                syoukei3.text = getString(R.string.markSyoukei, 2)
            }
            "両面待ち（ﾘｬﾝﾒﾝ）" -> {
                resultInt += 0
                syoukei3.text = getString(R.string.markSyoukei, 0)
            }
            "双碰待ち（ｼｬﾝﾎﾟﾝ）" -> {
                resultInt += 0
                syoukei3.text = getString(R.string.markSyoukei, 0)
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

        val mentu = (KoMTanYao * 2 + KoMYaoThu * 4 + KoATanYao * 4 + KoAYaoThu * 8 + KaMTanYao * 8 + KaMYaoThu * 16 + KaATanYao * 16 + KaAYaoThu * 32)
        resultInt += mentu
        syoukei4.text = getString(R.string.markSyoukei, mentu)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.mark_option_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mark_back -> {
                finish()
                return true
            }
            R.id.mark_menu_home -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
            else -> Error("Menu Select Error")
        }
        return false
    }

    /** 受信エラー通知 */
    override fun onFailedToReceiveAd(nendAdView: NendAdView) {
    }

    /** クリック通知  */
    override fun onClick(nendAdView: NendAdView) {
    }

    /** 復帰通知  */
    override fun onDismissScreen(arg0: NendAdView) {
    }

    /** 受信成功通知  */
    override fun onReceiveAd(nendAdView: NendAdView) {
        imageView3.visibility = View.GONE
    }
}
