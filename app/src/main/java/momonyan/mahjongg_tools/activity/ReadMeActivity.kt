package momonyan.mahjongg_tools.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.readme_layout.*
import momonyan.mahjongg_tools.R

class ReadMeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore = getSharedPreferences("MainData", Context.MODE_PRIVATE)
        var imageData :Int
        when (dataStore.getString("Theme", "Dark")) {
            "Dark" -> {
                setTheme(R.style.DarkTheme)
                imageData = R.drawable.readme_b
            }
            "Light" -> {
                setTheme(R.style.LightTheme)
                imageData = R.drawable.readme_w
            }
            "Mat" -> {
                setTheme(R.style.MatTheme)
                imageData = R.drawable.readme_mat
            }
            else -> error(2)
        }
        setContentView(R.layout.readme_layout)
        readmeImageView.setImageDrawable(getDrawable(imageData))

        readmeBackFloatingActionButton.setOnClickListener {
            finish()
        }
    }

}