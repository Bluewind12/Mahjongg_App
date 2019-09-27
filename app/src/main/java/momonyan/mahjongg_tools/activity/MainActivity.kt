package momonyan.mahjongg_tools.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import jp.co.runners.rateorfeedback.RateOrFeedback
import kotlinx.android.synthetic.main.activity_main.*
import momonyan.mahjongg_tools.R
import momonyan.mahjongg_tools.function.FlickCheck
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    //配列を用いたもの
    private lateinit var playerButtons: Array<ImageButton>
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

    //FireBase
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    //Ad
    private lateinit var mInterstitialAd: InterstitialAd

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
        //Nend
//        NendAdInterstitial.loadAd(applicationContext, "e3cf2a1284d0b2c5ba2cac11e5d0da50de7ce781", 922653)

        //AD
        MobileAds.initialize(this, "ca-app-pub-6499097800180510~1231013049")
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-6499097800180510/7290769334"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        //FireBase
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)



        //FireBase
        val openParams = Bundle()
        openParams.putString("Event", "OpenEvent")
        openParams.putString("View", "Main")
        openParams.putString("full_text", "Main")
        firebaseAnalytics.logEvent("event_log", openParams)

        setContentView(R.layout.activity_main)
        //初期状態セット
        initialSetting()
        //回転セット
        rotateSetting()

        if (dataStore.getString("GameMode", "4") == "4") {
            //４人プレイ時
            setFirstPlayer(firstPlayer)
            setState(parent)
            setFieldWind(fieldNum)
        }else{
            //3人プレイ時

        }


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
                        val params = Bundle()
                        params.putString("Event", "ThemeChange")
                        params.putString("View", "Main")
                        params.putString("full_text", "ThemeChange")

                        Log.d("Theme", "左")
                        when (dataStore.getString("Theme", "Dark")) {
                            "Dark" -> {
                                editor.putString("Theme", "Light")
                                params.putInt("Theme", 0)
                            }
                            "Light" -> {
                                editor.putString("Theme", "Mat")
                                params.putInt("Theme", 1)
                            }
                            "Mat" -> {
                                editor.putString("Theme", "Dark")
                                params.putInt("Theme", 2)
                            }
                            else -> error(2)
                        }
                        firebaseAnalytics.logEvent("event_log", params)
                        editor.commit()
                        finish()
                        startActivity(resetIntent)
                    }
                    RIGHT_FLICK -> {
                        val params = Bundle()
                        params.putString("Event", "ThemeChange")
                        params.putString("View", "Main")
                        params.putString("full_text", "ThemeChange")
                        when (dataStore.getString("Theme", "Dark")) {
                            "Dark" -> {
                                editor.putString("Theme", "Mat")
                                params.putInt("Theme", 1)
                            }
                            "Light" -> {
                                editor.putString("Theme", "Dark")
                                params.putInt("Theme", 2)
                            }
                            "Mat" -> {
                                editor.putString("Theme", "Light")
                                params.putInt("Theme", 0)
                            }
                            else -> error(2)
                        }
                        editor.commit()
                        finish()
                        startActivity(resetIntent)
                    }
                }
            }
        }

        //本場ボタンタップ時の動作
        honbaButton.setOnClickListener {
            //単押(増加)
            times++
            honba1.text = getString(R.string.place, times)
            honba2.text = getString(R.string.place, times)
            editor.putInt("times", times)
            editor.apply()
        }
        honbaButton.setOnLongClickListener {
            if (times > 0) {
                times--
            }
            honba1.text = getString(R.string.place, times)
            honba2.text = getString(R.string.place, times)
            editor.putInt("times", times)
            editor.apply()
            true
        }



        chichaButton.setOnLongClickListener {
            playerButtons.forEach {
                it.setBackgroundColor(Color.argb(0, 0, 0, 0))
            }
            true
        }
        chichaButton.setOnClickListener {
            playerButtons.forEach {
                if (dataStore.getString("Theme", "Dark") == "Light") {
                    it.background = resources.getDrawable(R.drawable.first_player_background_light)
                } else {
                    it.background = resources.getDrawable(R.drawable.first_player_background)
                }
            }
            for(i in 0 until 4){

                playerButtons[i].setOnClickListener {
                    if (dataStore.getString("Theme", "Dark") == "Light") {
                        playerButtons[i % 4].background = resources.getDrawable(R.drawable.first_player_background_light)
                    } else {
                        playerButtons[i % 4].background = resources.getDrawable(R.drawable.first_player_background)
                    }
                    playerButtons[(i + 1) % 4].setBackgroundColor(Color.argb(0, 0, 0, 0))
                    playerButtons[(i + 2) % 4].setBackgroundColor(Color.argb(0, 0, 0, 0))
                    playerButtons[(i + 3) % 4].setBackgroundColor(Color.argb(0, 0, 0, 0))

                    for(j in 0 until 4){
                        playerButtons[j].setOnClickListener {
                            changeState(j, false)
                        }
                        playerButtons[j].setOnLongClickListener {
                            changeState(j, true)
                            true
                        }
                    }
                }
            }

        }


        setTheme(R.style.defaultTheme)
    }

    //初期設定
    private fun initialSetting() {
        playerButtons = arrayOf(findViewById(R.id.imageButton1), findViewById(R.id.imageButton2), findViewById(R.id.imageButton3), findViewById(R.id.imageButton4))
        boostTexts = arrayOf(findViewById(R.id.boost1), findViewById(R.id.boost2), findViewById(R.id.boost3), findViewById(R.id.boost4))
        fieldButton = findViewById(R.id.imageButtonF)
        pointButtons = arrayOf(findViewById(R.id.pointButton1), findViewById(R.id.pointButton2))
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
        honba1.rotation = 135f
        honba2.rotation = -45f
        boostTexts[0].rotation = 90f
        boostTexts[1].rotation = 0f
        boostTexts[2].rotation = -90f
        boostTexts[3].rotation = -180f

        chichaButton.rotation = 180f
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

        if (mInterstitialAd.isLoaded && Random.nextInt(100) <= 25) {
            mInterstitialAd.show()
        }
    }

    //プレイヤー状態のセット
    private fun setState(setNum: Int) {
        viewAlertDialog()
        playerButtons[setNum % 4].setImageResource(R.drawable.hougaku1_higashi)
        playerButtons[(setNum + 1) % 4].setImageResource(R.drawable.hougaku3_minami)
        playerButtons[(setNum + 2) % 4].setImageResource(R.drawable.hougaku2_nishi)
        playerButtons[(setNum + 3) % 4].setImageResource(R.drawable.hougaku4_kita)
        honba1.text = getString(R.string.place, times)
        honba2.text = getString(R.string.place, times)
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

    private fun reSetFirstPlayer() {

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

        //TODO AD
        //NendAdInterstitial.showAd(this)
        if (mInterstitialAd.isLoaded && Random.nextInt(100) <= 25) {
            mInterstitialAd.show()
        }
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
