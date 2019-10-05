package momonyan.mahjongg_tools.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.media.AudioAttributes
import android.media.SoundPool
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.point_tools.*
import momonyan.mahjongg_tools.R
import momonyan.mahjongg_tools.function.CountFunctionDice
import momonyan.mahjongg_tools.function.CountFunctionWind


class ToolsActivity : AppCompatActivity() {
    private lateinit var audioAttributes: AudioAttributes
    private lateinit var soundPool: SoundPool

    //サイコロ
    private lateinit var countDice: CountFunctionDice
    private lateinit var countWind: CountFunctionWind
    private var sound = 0

    //役
    private var role: Int = 1
    private var mark: Int = 10


    override fun onCreate(savedInstanceState: Bundle?) {
        val dataStore = getSharedPreferences("MainData", Context.MODE_PRIVATE)
        when (dataStore.getString("Theme", "Dark")) {
            "Dark" -> setTheme(R.style.DarkThemeAction)
            "Light" -> setTheme(R.style.LightThemeAction)
            "Mat" -> setTheme(R.style.MatThemeAction)
            else -> error(2)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.point_tools)
        //初期化
        init()

        //Ad
        val adRequest = AdRequest.Builder().build()
        toolAd.loadAd(adRequest)

        toolAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                logoImageView.visibility = View.INVISIBLE

            }
        }

        //スピナーセット
        hanSpinner.onItemSelectedListener = null
        hanSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapterView: AdapterView<*>) {}
            //選択時
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (!hanSpinner.isFocusable) {
                    hanSpinner.isFocusable = true
                    return
                }
                role = getRole()
                countPoint(role + mark)
            }
        }

        markSpinner.onItemSelectedListener = null
        markSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapterView: AdapterView<*>) {}
            //選択時
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (!markSpinner.isFocusable) {
                    markSpinner.isFocusable = true
                    return
                }
                mark = getMarks()
                countPoint(role + mark)
            }
        }
        hanSpinner.isFocusable = false
        markSpinner.isFocusable = false


        //画面遷移系
        pointTableP.setOnClickListener {
            val intent = Intent(this, PointTableOutputActivity::class.java)
            intent.putExtra("image", "oya_point")
            startActivity(intent)
        }
        pointTableC.setOnClickListener {
            val intent = Intent(this, PointTableOutputActivity::class.java)
            intent.putExtra("image", "ko_point")
            startActivity(intent)
        }

        markTable.setOnClickListener {
            val intent = Intent(this, FuTableViewActivity::class.java)
            startActivity(intent)
        }

        //符計算ツール画面への遷移
        markToolButton.setOnClickListener {
            val intent = Intent(this, MarkToolActivity::class.java)
            startActivity(intent)
        }
        //役一覧画面への繊維
        yakuJumpButton.setOnClickListener {
            val intent = Intent(this, YakuListViewActivity::class.java)
            startActivity(intent)
        }

        //サイコロ初期化
        countDice = CountFunctionDice(1000, 100)
        countDice.leftImage = leftImage
        countDice.rightImage = rightImage
        countDice.textView = oyaDiceTextView

        //サイコロ機能
        diceButton.setOnClickListener {
            countDice.cancel()
            countDice.start()
            //効果音
            soundPool.play(sound, 1.0f, 1.0f, 0, 0, 1.0f)
        }

        //風初期化
        countWind = CountFunctionWind(1000, 100)
        countWind.windImage = windRandImage
        countWind.textView = windRandOutputTextView

        //サイコロ機能
        windRollButton.setOnClickListener {
            countWind.cancel()
            countWind.start()
            //効果音
            soundPool.play(sound, 1.0f, 1.0f, 0, 0, 1.0f)
        }

        //TextViewの長押し時の説明文の表示に関しての部分
        setTextDescription(pointCalculationTextView, "飜数と符数を入力して計算を押すと計算されます")
        setTextDescription(pointTextView, "計算結果が表示されます\n親の場合と子の場合の両方が表示されます")
        setTextDescription(tableViewTextView, "麻雀の点数表を表示します")
        setTextDescription(markPointJunpTextView, "符の計算ツール画面への移動ボタンです")
        setTextDescription(diceTextView, "サイコロが振れます\n左・右・対・自の表示もあります")
        setTextDescription(yakuTextView, "役の一覧表への移動ボタンです")
        setTextDescription(windRandTextView, "風がランダムで表示されます。\n東南西北が表示されます。")

        //nend.setListener(this)
    }

    private fun setTextDescription(text: TextView, des: String) {

        text.setOnClickListener {
            Toast.makeText(this, des, Toast.LENGTH_LONG).show()
        }
        text.paintFlags = text.paintFlags or Paint.UNDERLINE_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
    }


    //スピナーからの読み取り（翻）
    private fun getRole(): Int {
        return when (hanSpinner.selectedItem.toString()) {
            "1翻" -> 1
            "2翻" -> 2
            "3翻" -> 3
            "4翻" -> 4
            "5翻" -> 5
            "6翻" -> 6
            "7翻" -> 6
            "8翻" -> 7
            "9翻" -> 7
            "10翻" -> 7
            "11翻" -> 8
            "12翻" -> 8
            "13翻以上" -> 9
            else -> error("翻エラー")
        }
    }

    //スピナーからの読み取り（符）
    private fun getMarks(): Int {
        return when (markSpinner.selectedItem.toString()) {
            "平和ツモ" -> 10
            "七対子" -> 20
            "30符" -> 30
            "40符" -> 40
            "50符" -> 50
            "60符" -> 60
            "70符" -> 70
            "80符" -> 80
            "90符" -> 90
            "100符" -> 100
            "110符" -> 110
            else -> error("符エラー")
        }
    }

    //点数計算
    private fun countPoint(point: Int) {
        when (point % 10) {
            5 -> setPoint(80, 40, 20)
            6 -> setPoint(120, 60, 30)
            7 -> setPoint(160, 80, 40)
            8 -> setPoint(240, 120, 60)
            9 -> setPoint(320, 160, 80)
            else -> when (point) {
                //1翻
                11 -> setPoint(0, 0, 0)
                21 -> setPoint(0, 0, 0)
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
                else -> Log.e("CountError", "計算エラー？")
            }
        }
    }

    //出力
    private fun setPoint(base: Int, parent: Int, child: Int) {
        if (base == 0 || parent == 0 || child == 0) {
            basicText.text = "点数なし"
            parentText.text = "点数なし"
            childText.text = "点数なし"
        } else {
            when (base) {
                80 -> basicText.text = getString(R.string.pointAlias, "満貫", base * 100)
                120 -> basicText.text = getString(R.string.pointAlias, "跳満", base * 100)
                160 -> basicText.text = getString(R.string.pointAlias, "倍満", base * 100)
                240 -> basicText.text = getString(R.string.pointAlias, "三倍満", base * 100)
                320 -> basicText.text = getString(R.string.pointAlias, "数え役満", base * 100)
                else -> basicText.text = (base * 100).toString()
            }
            parentText.text = getString(R.string.parentPoint, (base + parent) * 100, parent * 100)
            childText.text = getString(R.string.childPoint, base * 100, parent * 100, child * 100)
        }
    }

    //初期化用
    private fun init() {
        audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build()
        soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(2).build()
        sound = soundPool.load(this, R.raw.dise, 1)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setTheme(R.style.defaultTheme)
        when (item.itemId) {
            R.id.menu0 -> {
                AlertDialog.Builder(this)
                        .setTitle("Webページを開きます").setMessage("[幻想乃桜工房のHP]\nを開いてもよろしいですか").setPositiveButton("はい") { _, _ ->
                            val uri = Uri.parse(getString(R.string.fantasy_url))
                            startActivity(Intent(Intent.ACTION_VIEW, uri))
                        }.setNegativeButton("いいえ", null)
                        .setIcon(R.drawable.fantasy).show()
                return true
            }
            R.id.menu1 -> {
                AlertDialog.Builder(this)
                        .setTitle("Webページを開きます").setMessage("[プライバシーポリシー]\n[利用素材]\nのページを開いてもよろしいですか").setPositiveButton("はい") { _, _ ->
                            val uri = Uri.parse(getString(R.string.privacy_URL))
                            startActivity(Intent(Intent.ACTION_VIEW, uri))
                        }.setNegativeButton("いいえ", null)
                        .setIcon(R.drawable.kizi).show()
                return true
            }
            R.id.menu_review -> {
                AlertDialog.Builder(this)
                        .setTitle("レビューページへ").setMessage("[レビュー]\nのページを開いてもよろしいですか").setPositiveButton("はい") { _, _ ->
                            val uri = Uri.parse(getString(R.string.reviewUrl))
                            startActivity(Intent(Intent.ACTION_VIEW, uri))
                        }.setNegativeButton("いいえ", null)
                        .setIcon(R.drawable.review_image).show()
                return true
            }
            R.id.menu_readme ->{
                startActivity(Intent(this, ReadMeActivity::class.java))
            }
            R.id.menu_home -> {
                finish()
                return true
            }
            else -> Error("Menu Select Error")
        }
        return false
    }


}