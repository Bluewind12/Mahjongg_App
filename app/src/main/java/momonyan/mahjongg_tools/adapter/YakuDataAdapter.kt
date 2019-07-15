package momonyan.mahjongg_tools.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import momonyan.mahjongg_tools.R
import momonyan.mahjongg_tools.holder.YakuDataHolder

class YakuDataAdapter(private val mValues: Array<String>) : androidx.recyclerview.widget.RecyclerView.Adapter<YakuDataHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YakuDataHolder {
        //レイアウトの設定(inflate)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.yaku_list_card_layout, parent, false)
        //Holderの生成
        return YakuDataHolder(view)
    }

    override fun onBindViewHolder(holder: YakuDataHolder, position: Int) {
        val item = mValues[position]
        val stringData = item.split(",")
        holder.mTitle.text = stringData[0]
        holder.mRead.text = stringData[1]
        holder.mHansu.text = stringData[2]
        holder.mMachi.text = stringData[3]
        holder.mDescription.text = stringData[4]
        holder.mImage.setImageResource(getImageInt(stringData[5]))
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    private fun getImageInt(imageString: String): Int {
        val imageNum = imageString.toInt()
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
}