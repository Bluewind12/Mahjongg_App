package momonyan.mahjongg_tools.yakulist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.yaku_list_layout.*
import momonyan.mahjongg_tools.MainActivity
import momonyan.mahjongg_tools.R
import momonyan.mahjongg_tools.RecyclerLayoutCustomManager


class YakuListViewActivity : AppCompatActivity() {
    private var mDataList: ArrayList<YakuDataClass> = ArrayList()
    private lateinit var mDataImage: Array<Int>

    private var tabSelectFrag = true
    private var tabInt = 0

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
        mDataImage = arrayOf()
        val mStringData = resources.getStringArray(R.array.yaku_names)
        // データ作成
        for (i in 0 until mStringData.size) {
            mDataList.add(
                    YakuDataClass(
                            mStringData[i],
                            getImageInt(mStringData[i].split(",")[5].toInt()),
                            when (mStringData[i].split(",")[2]) {
                                "1飜", "3飜", "役満" -> getDrawable(R.drawable.background_yaku_list_card_black)!!
                                "2飜", "6飜" -> getDrawable(R.drawable.background_yaku_list_card_white)!!
                                else -> getDrawable(R.drawable.background_yaku_list_card)!!
                            })
            )
        }
        // Adapter作成
        val adapter = YakuDataAdapter(mDataList)

        // RecyclerViewにAdapterとLayoutManagerの設定
        yakuRecyclerView.adapter = adapter
        yakuRecyclerView.layoutManager = RecyclerLayoutCustomManager(this)
        yakuRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val manager = recyclerView.layoutManager as LinearLayoutManager?
                val firstVisibleItem = manager!!.findFirstVisibleItemPosition()
                if (tabSelectFrag) {
                    when (firstVisibleItem) {
                        0 -> tabLayout.getTabAt(0)!!.select()
                        11 -> tabLayout.getTabAt(1)!!.select()
                        22 -> tabLayout.getTabAt(2)!!.select()
                        25 -> tabLayout.getTabAt(3)!!.select()
                        26 -> tabLayout.getTabAt(4)!!.select()
                    }
                }
                if (tabInt == firstVisibleItem) {
                    tabSelectFrag = true
                }
            }
        })


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                tabSelectFrag = false
                when (p0!!.position) {
                    0 -> {
                        yakuRecyclerView.smoothScrollToPosition(0)
                        tabInt = 0
                    }
                    1 -> {
                        yakuRecyclerView.smoothScrollToPosition(11)
                        tabInt = 11
                    }
                    2 -> {
                        yakuRecyclerView.smoothScrollToPosition(22)
                        tabInt = 22
                    }
                    3 -> {
                        yakuRecyclerView.smoothScrollToPosition(25)
                        tabInt = 25
                    }
                    4 -> {
                        yakuRecyclerView.smoothScrollToPosition(26)
                        tabInt = 26
                    }
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }
        })
        //0,11,22,25,26
    }

    private fun getImageInt(imageNum: Int): Int {
        return when (imageNum) {
            0 -> R.drawable.non_image
            11 -> R.drawable.yaku_11
            12 -> R.drawable.yaku_12
            13 -> R.drawable.yaku_13
            14 -> R.drawable.yaku_14
            21 -> R.drawable.yaku_21
            22 -> R.drawable.yaku_22
            23 -> R.drawable.yaku_23
            24 -> R.drawable.yaku_24
            25 -> R.drawable.yaku_25
            26 -> R.drawable.yaku_26
            27 -> R.drawable.yaku_27
            28 -> R.drawable.yaku_28
            29 -> R.drawable.yaku_29
            210 -> R.drawable.yaku_210
            31 -> R.drawable.yaku_31
            32 -> R.drawable.yaku_32
            33 -> R.drawable.yaku_33
            61 -> R.drawable.yaku_61
            91 -> R.drawable.yaku_91
            92 -> R.drawable.yaku_92
            93 -> R.drawable.yaku_93
            94 -> R.drawable.yaku_94
            95 -> R.drawable.yaku_95
            96 -> R.drawable.yaku_96
            97 -> R.drawable.yaku_97
            98 -> R.drawable.yaku_98
            99 -> R.drawable.yaku_99
            910 -> R.drawable.yaku_910
            else -> R.drawable.non_image
        }
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
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
            else -> Error("Menu Select Error")
        }
        return false
    }
}