package momonyan.mahjongg_tools

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton

class TableOutputActivity : AppCompatActivity() {
    private lateinit var imageView: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        val dataStore = getSharedPreferences("MainData", Context.MODE_PRIVATE)
        when (dataStore.getString("Theme", "Dark")) {
            "Dark" -> setTheme(R.style.DarkTheme)
            "Light" -> setTheme(R.style.LightTheme)
            "Mat" -> setTheme(R.style.MatTheme)
            else -> error(2)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_view)
        imageView = findViewById(R.id.imageViewOutput)
        when (intent.getStringExtra("image")) {
            "oya_point" -> imageView.setImageResource(R.drawable.point_oya)
            "ko_point" -> imageView.setImageResource(R.drawable.point_ko)
            "fu" -> imageView.setImageResource(R.drawable.mark)
            else -> imageView.setImageResource(R.drawable.hougaku4_kita)
        }
        imageView.setOnClickListener {
            finish()
        }
    }
}