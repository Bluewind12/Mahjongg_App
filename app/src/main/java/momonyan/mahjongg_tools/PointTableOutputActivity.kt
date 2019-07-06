package momonyan.mahjongg_tools

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.score_table.*

class PointTableOutputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //テーマ変更
        val dataStore = getSharedPreferences("MainData", Context.MODE_PRIVATE)
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
        setContentView(R.layout.score_table)
        when (intent.getStringExtra("image")) {
            "oya_point" -> {
                setParentPointText()
            }
            "ko_point" -> {
                setChildPointText()
            }
        }
        pointTableGridLayout.setOnClickListener {
            finish()
        }
    }

    private fun setParentPointText() {
        pointTextView_20_1.text = getString(R.string.pinf_title)
        pointTextView_25_1.text = getString(R.string.ti_toi_title)
        pointTextView_30_1.text = getString(R.string.parent_1500)
        pointTextView_40_1.text = getString(R.string.parent_2000)
        pointTextView_50_1.text = getString(R.string.parent_2400)
        pointTextView_60_1.text = getString(R.string.parent_2900)
        pointTextView_70_1.text = getString(R.string.parent_3400)
        pointTextView_80_1.text = getString(R.string.parent_3900)
        pointTextView_90_1.text = getString(R.string.parent_4400)
        pointTextView_100_1.text = getString(R.string.parent_4800)
        pointTextView_110_1.text = getString(R.string.parent_5300)

        pointTextView_20_2.text = getString(R.string.parent_700_all)
        pointTextView_25_2.text = getString(R.string.parent_2400_pin)
        pointTextView_30_2.text = getString(R.string.parent_2900)
        pointTextView_40_2.text = getString(R.string.parent_3900)
        pointTextView_50_2.text = getString(R.string.parent_4800)
        pointTextView_60_2.text = getString(R.string.parent_5800)
        pointTextView_70_2.text = getString(R.string.parent_6800)
        pointTextView_80_2.text = getString(R.string.parent_7700)
        pointTextView_90_2.text = getString(R.string.parent_8700)
        pointTextView_100_2.text = getString(R.string.parent_9600)
        pointTextView_110_2.text = getString(R.string.parent_10600)

        pointTextView_20_3.text = getString(R.string.parent_1300_all)
        pointTextView_25_3.text = getString(R.string.parent_4800)
        pointTextView_30_3.text = getString(R.string.parent_5800)
        pointTextView_40_3.text = getString(R.string.parent_7700)
        pointTextView_50_3.text = getString(R.string.parent_9600)
        pointTextView_60_3.text = getString(R.string.parent_11600)
        pointTextView_70_3.text = getString(R.string.parent_man)

        pointTextView_20_4.text = getString(R.string.parent_2600_all)
        pointTextView_25_4.text = getString(R.string.parent_9600)
        pointTextView_30_4.text = getString(R.string.parent_11600)
        pointTextView_40_4.text = getString(R.string.parent_man)

        pointHaneTextView.text = getString(R.string.parent_hane)
        pointBaiTextView.text = getString(R.string.parent_bai)
        pointSanBaiTextView.text = getString(R.string.parent_sanbai)
        pointYakuTextView.text = getString(R.string.parent_yaku)
    }

    private fun setChildPointText() {
        pointTextView_20_1.text = getString(R.string.pinf_title)
        pointTextView_25_1.text = getString(R.string.ti_toi_title)
        pointTextView_30_1.text = getString(R.string.child_1000)
        pointTextView_40_1.text = getString(R.string.child_1300)
        pointTextView_50_1.text = getString(R.string.child_1600)
        pointTextView_60_1.text = getString(R.string.child_2000)
        pointTextView_70_1.text = getString(R.string.child_2300)
        pointTextView_80_1.text = getString(R.string.child_2600)
        pointTextView_90_1.text = getString(R.string.child_2900)
        pointTextView_100_1.text = getString(R.string.child_3200)
        pointTextView_110_1.text = getString(R.string.child_3600)

        pointTextView_20_2.text = getString(R.string.child_4080)
        pointTextView_25_2.text = getString(R.string.child_1600_pin)
        pointTextView_30_2.text = getString(R.string.child_2000)
        pointTextView_40_2.text = getString(R.string.child_2600)
        pointTextView_50_2.text = getString(R.string.child_3200)
        pointTextView_60_2.text = getString(R.string.child_3900)
        pointTextView_70_2.text = getString(R.string.child_4500)
        pointTextView_80_2.text = getString(R.string.child_5200)
        pointTextView_90_2.text = getString(R.string.child_5800)
        pointTextView_100_2.text = getString(R.string.child_6400)
        pointTextView_110_2.text = getString(R.string.child_7100)

        pointTextView_20_3.text = getString(R.string.child_7013)
        pointTextView_25_3.text = getString(R.string.child_3200)
        pointTextView_30_3.text = getString(R.string.child_3900)
        pointTextView_40_3.text = getString(R.string.child_5200)
        pointTextView_50_3.text = getString(R.string.child_6400)
        pointTextView_60_3.text = getString(R.string.child_7700)
        pointTextView_70_3.text = getString(R.string.child_man)

        pointTextView_20_4.text = getString(R.string.child_1326)
        pointTextView_25_4.text = getString(R.string.child_6400)
        pointTextView_30_4.text = getString(R.string.child_7700)
        pointTextView_40_4.text = getString(R.string.child_man)

        pointHaneTextView.text = getString(R.string.child_hane)
        pointBaiTextView.text = getString(R.string.child_bai)
        pointSanBaiTextView.text = getString(R.string.child_sanbai)
        pointYakuTextView.text = getString(R.string.child_yaku)
    }
}