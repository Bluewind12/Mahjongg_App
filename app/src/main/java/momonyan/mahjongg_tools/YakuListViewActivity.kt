package momonyan.mahjongg_tools

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.yaku_list_layout.*

class YakuListViewActivity : AppCompatActivity() {
    private var mDataList: ArrayList<YakuDataClass> = ArrayList()
    private lateinit var mDataImage: Array<Int>

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
                            R.drawable.hougaku1_higashi
                    )
            )
        }
        // Adapter作成
        val adapter = YakuDataAdapter(mDataList)

        // RecyclerViewにAdapterとLayoutManagerの設定
        yakuRecyclerView.adapter = adapter
        yakuRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        yakuRecyclerView.layoutManager = RecyclerLayoutCustomManager(this)

    }
}