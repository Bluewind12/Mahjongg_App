package momonyan.mahjongg_tools

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import jp.co.runners.rateorfeedback.RateOrFeedback
import net.nend.android.NendAdInterstitial


class MainActivity : AppCompatActivity() {
    //配列を用いたもの
    private lateinit var playerButtons: Array<ImageButton>
    private lateinit var timesTexts: Array<TextView>
    private lateinit var boostTexts: Array<TextView>
    private lateinit var pointButtons: Array<ImageButton>
    //イメージボタン
    private lateinit var fieldButton: ImageButton
    //データ記録、呼び出し用
    private lateinit var dataStore: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    //数値管理
    private var fieldNum = 0
    private var firstPlayer = 0
    private var parent = 0
    private var times = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        //テーマ変更
        dataStore = getSharedPreferences("MainData", Context.MODE_PRIVATE)
        when (dataStore.getString("Theme", "Dark")) {
            "Dark" -> {
                setTheme(R.style.DarkTheme)
            }
            "Light" -> {
                setTheme(R.style.LightTheme)
            }
            "Mat" -> {
                setTheme(R.style.MatTheme)
            }
            else -> error(2)
        }

        super.onCreate(savedInstanceState)
        NendAdInterstitial.loadAd(applicationContext, "e3cf2a1284d0b2c5ba2cac11e5d0da50de7ce781", 922653)
        setContentView(R.layout.activity_main)
        //初期状態セット
        initialSetting()
        //回転セット
        rotateSetting()

        setFirstPlayer(firstPlayer)
        setState(parent)
        setFieldWind(fieldNum)
        //単推し
        for (i in 0 until playerButtons.size) {
            playerButtons[i].setOnClickListener {
                changeState(i, false)
            }
            playerButtons[i].setOnLongClickListener {
                changeState(i, true)
                true
            }
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
        //長押し
        fieldButton.setOnLongClickListener {
            fieldNum--
            if (fieldNum == -1) {
                fieldNum = 3
            }
            setFieldWind(fieldNum)
            editor.putInt("field", fieldNum)
            editor.apply()
            true
        }

        //ツールボタンクリック時
        pointButtons.forEach {
            it.setOnClickListener {
                val intent = Intent(this, ToolsActivity::class.java)
                startActivity(intent)
            }
        }

        //フリック
        val resetIntent = Intent(this, MainActivity::class.java)
        val flickView = window.decorView // Activity画面
        val adjustX = 150.0f
        val adjustY = 150.0f
        object : FlickCheck(flickView, adjustX, adjustY) {
            override fun getFlick(swipe: Int) {
                when (swipe) {
                    LEFT_FLICK -> {
                        Log.d("Theme", "左")
                        when (dataStore.getString("Theme", "Dark")) {
                            "Dark" -> editor.putString("Theme", "Light")
                            "Light" -> editor.putString("Theme", "Mat")
                            "Mat" -> editor.putString("Theme", "Dark")
                            else -> error(2)
                        }
                        editor.commit()
                        finish()
                        startActivity(resetIntent)
                    }
                    RIGHT_FLICK -> {
                        Log.d("Theme", "右")
                        when (dataStore.getString("Theme", "Dark")) {
                            "Dark" -> editor.putString("Theme", "Mat")
                            "Light" -> editor.putString("Theme", "Dark")
                            "Mat" -> editor.putString("Theme", "Light")
                            else -> error(2)
                        }
                        editor.commit()
                        finish()
                        startActivity(resetIntent)
                    }
                }
            }
        }
        setTheme(R.style.defaultTheme)
    }

    //初期設定
    private fun initialSetting() {
        playerButtons = arrayOf(findViewById(R.id.imageButton1), findViewById(R.id.imageButton2), findViewById(R.id.imageButton3), findViewById(R.id.imageButton4))
        timesTexts = arrayOf(findViewById(R.id.honba1), findViewById(R.id.honba2), findViewById(R.id.honba3), findViewById(R.id.honba4))
        boostTexts = arrayOf(findViewById(R.id.boost1), findViewById(R.id.boost2), findViewById(R.id.boost3), findViewById(R.id.boost4))
        fieldButton = findViewById(R.id.imageButtonF)
        pointButtons = arrayOf(findViewById(R.id.pointButton), findViewById(R.id.pointButton2), findViewById(R.id.pointButton3), findViewById(R.id.pointButton4))
        editor = dataStore.edit()
        editor.apply()

        fieldNum = dataStore.getInt("field", 0)
        firstPlayer = dataStore.getInt("firstPlayer", 0)
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
        boostTexts[0].rotation = 90f
        boostTexts[1].rotation = 0f
        boostTexts[2].rotation = -90f
        boostTexts[3].rotation = -180f
    }

    /**
     * 状態変更
     * @author BlueWind
     * @param touchButtonNum タッチされたプレイヤー番号
     * @param longFlag true:長押し false:単押
     */
    private fun changeState(touchButtonNum: Int, longFlag: Boolean) {


        if (!longFlag) {
            //単押(増加)
            if (parent == touchButtonNum) {
                times++
                setState(touchButtonNum)
            } else {
                parent = touchButtonNum
                times = 0
                setState(touchButtonNum)
            }
        } else {
            //長押(減少)
            if (parent == touchButtonNum) {
                if (times > 0) {
                    times--
                } else {
                    setFirstPlayer(touchButtonNum)
                    editor.putInt("firstPlayer", touchButtonNum)
                }
                setState(touchButtonNum)
            } else {
                parent = touchButtonNum
                times = 0
                setState(touchButtonNum)
            }
        }
        editor.putInt("times", times)
        editor.putInt("parent", parent)
        editor.apply()
        NendAdInterstitial.showAd(this)
    }

    //プレイヤー状態のセット
    private fun setState(setNum: Int) {
        viewAlertDialog()
        playerButtons[setNum % 4].setImageResource(R.drawable.hougaku1_higashi)
        playerButtons[(setNum + 1) % 4].setImageResource(R.drawable.hougaku3_minami)
        playerButtons[(setNum + 2) % 4].setImageResource(R.drawable.hougaku2_nishi)
        playerButtons[(setNum + 3) % 4].setImageResource(R.drawable.hougaku4_kita)
        timesTexts[setNum % 4].text = getString(R.string.place, times)
        timesTexts[(setNum + 1) % 4].text = " "
        timesTexts[(setNum + 2) % 4].text = " "
        timesTexts[(setNum + 3) % 4].text = " "
        setBoostWind()
    }

    private fun setFirstPlayer(setNum: Int) {
        if (dataStore.getString("Theme", "Dark") == "Light") {
            playerButtons[setNum % 4].background = resources.getDrawable(R.drawable.first_player_background_light)
        } else {
            playerButtons[setNum % 4].background = resources.getDrawable(R.drawable.first_player_background)
        }
        playerButtons[(setNum + 1) % 4].setBackgroundColor(Color.argb(0, 0, 0, 0))
        playerButtons[(setNum + 2) % 4].setBackgroundColor(Color.argb(0, 0, 0, 0))
        playerButtons[(setNum + 3) % 4].setBackgroundColor(Color.argb(0, 0, 0, 0))

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
        setState(parent)
        NendAdInterstitial.showAd(this)
    }

    private fun setBoostWind() {
        //0東, 1南, 2西 ,3北
        boostTexts[(parent + fieldNum) % 4].text = "場風"
        boostTexts[(parent + fieldNum + 1) % 4].text = ""
        boostTexts[(parent + fieldNum + 2) % 4].text = ""
        boostTexts[(parent + fieldNum + 3) % 4].text = ""
    }

    private fun viewAlertDialog() {
        RateOrFeedback(this)
                // レビュー用ストアURL
                .setPlayStoreUrl(getString(R.string.reviewUrl))
                // 改善点・要望の送信先メールアドレス
                .setFeedbackEmail(getString(R.string.reviewMail))
                // 一度、評価するか改善点を送信するを選択した場合、それ以降はダイアログが表示されません。
                // この値をインクリメントすることで再度ダイアログが表示されるようになります。
                .setReviewRequestId(0)
                // 前回ダイアログを表示してから次にダイアログを表示してよいタイミングまでの期間です。
                .setIntervalFromPreviousShowing(60 * 60 * 3)//3時間
                // アプリをインストールしてから、ここで指定された期間はダイアログを表示しません。
                .setNotShowTermSecondsFromInstall(60 * 60)//1時間
                .showIfNeeds()
    }

}
