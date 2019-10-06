package momonyan.mahjongg_tools.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            else -> Error("Menu Select Error")
        }
        return false
    }

}