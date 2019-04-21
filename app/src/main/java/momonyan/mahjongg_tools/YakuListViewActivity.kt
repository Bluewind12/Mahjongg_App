package momonyan.mahjongg_tools

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.yaku_list_layout.*


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
                            getImageInt(mStringData[i].split(",")[5].toInt())
                    )
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
                if(tabInt == firstVisibleItem){
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
            else -> R.drawable.non_image
        }
    }
}