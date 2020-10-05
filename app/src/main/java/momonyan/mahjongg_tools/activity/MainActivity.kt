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
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
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
    private var chichaFrag = false

    //FireBase
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    //Ad
    private lateinit var mInterstitialAd: InterstitialAd

    //review
    private var isReview = false

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

        when (dataStore.getString("Theme", "Dark")) {
            "Light" -> {
                chichaButton.background = ResourcesCompat.getDrawable(resources, R.drawable.function_button_w, null)
                honbaButton.background = ResourcesCompat.getDrawable(resources, R.drawable.function_button_w, null)
                pointButton1.background = ResourcesCompat.getDrawable(resources, R.drawable.light_setting_button, null)
                pointButton2.background = ResourcesCompat.getDrawable(resources, R.drawable.light_setting_button, null)
            }
            "Dark", "Mat" -> {
                chichaButton.background = ResourcesCompat.getDrawable(resources, R.drawable.function_button, null)
                honbaButton.background = ResourcesCompat.getDrawable(resources, R.drawable.function_button, null)
                pointButton1.background = ResourcesCompat.getDrawable(resources, R.drawable.dark_setting_button, null)
                pointButton2.background = ResourcesCompat.getDrawable(resources, R.drawable.dark_setting_button, null)
            }
            else -> error(2)
        }

        //初期状態セット
        initialSetting()
        //回転セット
        rotateSetting()

        if (dataStore.getString("GameMode", "4") == "4") {
            //４人プレイ時
            if (firstPlayer != 5) {
                setFirstPlayer(firstPlayer)
            } else {
                playerButtons.forEach {
                    it.setBackgroundColor(Color.argb(0, 0, 0, 0))
                }
            }
            setState(parent)
            setFieldWind(fieldNum)
        } else {
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
        resetIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
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
            if (!chichaFrag) {
                chichaFrag = true
                playerButtons.forEach {
                    if (dataStore.getString("Theme", "Dark") == "Light") {
                        it.background = ResourcesCompat.getDrawable(resources, R.drawable.first_player_background_light, null)
                    } else {
                        it.background = ResourcesCompat.getDrawable(resources, R.drawable.first_player_background, null)
                    }
                }
                for (i in 0 until 4) {
                    playerButtons[i].setOnClickListener {
                        if (dataStore.getString("Theme", "Dark") == "Light") {
                            playerButtons[i % 4].background = ResourcesCompat.getDrawable(resources, R.drawable.first_player_background_light, null)
                        } else {
                            playerButtons[i % 4].background = ResourcesCompat.getDrawable(resources, R.drawable.first_player_background, null)
                        }
                        playerButtons[(i + 1) % 4].setBackgroundColor(Color.argb(0, 0, 0, 0))
                        playerButtons[(i + 2) % 4].setBackgroundColor(Color.argb(0, 0, 0, 0))
                        playerButtons[(i + 3) % 4].setBackgroundColor(Color.argb(0, 0, 0, 0))
                        dataStore.edit().putInt("firstPlayer", i).apply()
                        firstPlayer = i

                        for (j in 0 until 4) {
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
            } else {
                chichaFrag = false
                playerButtons.forEach {
                    it.setBackgroundColor(Color.argb(0, 0, 0, 0))
                }
                for (j in 0 until 4) {
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
            if ((parent + 1) % 4 == touchButtonNum && firstPlayer == touchButtonNum) {
                parent = touchButtonNum
                times = 0
                fieldNum = (fieldNum + 1) % 4
                setFieldWind(fieldNum)
                setState(touchButtonNum)
            } else if (parent == touchButtonNum) {
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
                    firstPlayer = touchButtonNum
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
        if(!isReview && Random.nextInt(100) <= 20) {
            viewAlertDialog()
        }
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
            playerButtons[setNum % 4].background = ResourcesCompat.getDrawable(resources, R.drawable.first_player_background_light, null)
        } else {
            playerButtons[setNum % 4].background = ResourcesCompat.getDrawable(resources, R.drawable.first_player_background, null)
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
        val manager = ReviewManagerFactory.create(this)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task: Task<ReviewInfo?> ->
            when {
                task.isSuccessful -> {
                    val reviewInfo = task.result
                    val flow = manager.launchReviewFlow(this, reviewInfo)
                }
            }
        }
        isReview = true
    }

}
