package momonyan.mahjongg_tools

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
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