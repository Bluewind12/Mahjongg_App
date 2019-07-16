package momonyan.mahjongg_tools.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import momonyan.mahjongg_tools.fragment.*

class TabAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return YakuList1HanFragment()
            }
            1 -> {
                return YakuList2HanFragment()
            }
            2 -> {
                return YakuList3HanFragment()
            }
            3 -> {
                return YakuList6HanFragment()
            }
            4 -> {
                return YakuListYakuManHanFragment()
            }
            else -> {
                return YakuList1HanFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "1飜"
            1 -> "2飜"
            2 -> "3飜"
            3 -> "6飜"
            4 ->"役満"
            else ->"E"
        }
    }
    override fun getCount(): Int {
        return 5
    }
}
