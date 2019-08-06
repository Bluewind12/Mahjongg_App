package momonyan.mahjongg_tools.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.yaku_list_layout.*
import momonyan.mahjongg_tools.R
import momonyan.mahjongg_tools.adapter.TabAdapter


class YakuListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val dataStore = getSharedPreferences("MainData", Context.MODE_PRIVATE)
        when (dataStore.getString("Theme", "Dark")) {
            "Dark" -> setTheme(R.style.DarkThemeAction)
            "Light" -> setTheme(R.style.LightThemeAction)
            "Mat" -> setTheme(R.style.MatThemeAction)
            else -> error(2)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.yaku_list_layout)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                yakuLogoImageView.visibility = View.INVISIBLE
            }
        }
        // Set
        container.adapter = TabAdapter(supportFragmentManager, this)
        tabLayout.setupWithViewPager(container)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

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
            else -> Log.e("D-Log", "Menu Select Error")
        }
        return false
    }
}