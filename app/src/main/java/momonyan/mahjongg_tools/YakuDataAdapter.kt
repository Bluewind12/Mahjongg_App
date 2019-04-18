package momonyan.mahjongg_tools

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class YakuDataAdapter(private val mValues: ArrayList<YakuDataClass>) : RecyclerView.Adapter<YakuDataHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YakuDataHolder {
        //レイアウトの設定(inflate)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.yaku_list_card_layout, parent, false)
        //Holderの生成
        return YakuDataHolder(view)
    }

    override fun onBindViewHolder(holder: YakuDataHolder, position: Int) {
        val item = mValues[position]
        val stringData = item.dataString.split(",")
        holder.mTitle.text = stringData[0]
        holder.mRead.text = stringData[1]
        holder.mHansu.text = stringData[2]
        holder.mMachi.text = stringData[3]
        holder.mDescription.text = stringData[4]
        holder.mImage.setImageResource(item.image)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }
}