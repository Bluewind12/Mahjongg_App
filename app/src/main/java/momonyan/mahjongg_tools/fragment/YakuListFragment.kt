package momonyan.mahjongg_tools.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.yaku_list_recycler_layout.view.*
import momonyan.mahjongg_tools.R
import momonyan.mahjongg_tools.adapter.YakuDataAdapter
import momonyan.mahjongg_tools.manager.RecyclerLayoutCustomManager

class YakuList1HanFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.yaku_list_recycler_layout, container, false)
        val stringData = resources.getStringArray(R.array.yaku_1_names)
        val act = activity!!
        setRecyclerList(view.yakuRecyclerView, stringData, act)
        return view
    }
}

class YakuList2HanFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.yaku_list_recycler_layout, container, false)
        val stringData = resources.getStringArray(R.array.yaku_2_names)
        val act = activity!!
        setRecyclerList(view.yakuRecyclerView, stringData, act)
        return view

    }
}

class YakuList3HanFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.yaku_list_recycler_layout, container, false)
        val stringData = resources.getStringArray(R.array.yaku_3_names)
        val act = activity!!
        setRecyclerList(view.yakuRecyclerView, stringData, act)
        return view
    }
}

class YakuList6HanFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.yaku_list_recycler_layout, container, false)
        val stringData = resources.getStringArray(R.array.yaku_6_names)
        val act = activity!!
        setRecyclerList(view.yakuRecyclerView, stringData, act)
        return view
    }
}

class YakuListYakuManHanFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.yaku_list_recycler_layout, container, false)
        val stringData = resources.getStringArray(R.array.yaku_yakuman_names)
        val act = activity!!
        setRecyclerList(view.yakuRecyclerView, stringData, act)
        return view
    }
}

fun setRecyclerList(recyclerView: RecyclerView, data: Array<String>, act: FragmentActivity) {
    val adapter = YakuDataAdapter(data)
    // RecyclerViewにAdapterとLayoutManagerの設定
    recyclerView.adapter = adapter
    recyclerView.layoutManager = RecyclerLayoutCustomManager((act))

}