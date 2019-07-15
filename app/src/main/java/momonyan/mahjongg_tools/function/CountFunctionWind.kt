package momonyan.mahjongg_tools.function

import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import momonyan.mahjongg_tools.R
import java.util.*

class CountFunctionWind(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {
    lateinit var windImage: ImageView
    lateinit var textView: TextView
    var windNum = 0

    //終了時
    override fun onFinish() {
        textView.text = when (windNum) {
            0 -> "東"
            1 -> "南"
            2 -> "西"
            3 -> "北"
            else -> ""
        }
    }

    // インターバルで呼ばれる
    override fun onTick(millisUntilFinished: Long) {
        windNum = Random().nextInt(4)
        when (windNum) {
            0 -> windImage.setImageResource(R.drawable.hougaku1_higashi)
            1 -> windImage.setImageResource(R.drawable.hougaku3_minami)
            2 -> windImage.setImageResource(R.drawable.hougaku2_nishi)
            3 -> windImage.setImageResource(R.drawable.hougaku4_kita)
        }
    }
}