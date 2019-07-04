package momonyan.mahjongg_tools

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.fu_calculation_table.*

class FuTableViewActivity : AppCompatActivity() {
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
        setContentView(R.layout.fu_calculation_table)
        fuTableLayout.setOnClickListener {
            finish()
        }
    }
}