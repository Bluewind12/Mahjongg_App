package momonyan.mahjongg_tools.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.yaku_list_card_layout.view.*


class YakuDataHolder(mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {
    val mTitle: TextView = mView.yakuTitleTextView //役名
    val mRead: TextView = mView.yakuTitleReadTextView //役名
    val mDescription: TextView = mView.yakuDescriptionTextView //説明文
    val mHansu: TextView = mView.yakuHansuTextView //飜数
    val mMachi: TextView = mView.yakuMachiTextView //待ち方
    val mImage: ImageView = mView.yakuDescriptionImageView //Image
}