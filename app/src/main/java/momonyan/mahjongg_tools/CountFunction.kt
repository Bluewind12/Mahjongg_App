package momonyan.mahjongg_tools

import android.content.Intent
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import java.util.*

/**
 * カウントダウンの動作
 */
class CountFunction(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {
    lateinit var rightImage: ImageView
    lateinit var leftImage: ImageView
    lateinit var textView: TextView
    var rightDice = 0
    var leftDice = 0
    override fun onFinish() {
        var direction = ""
        when ((rightDice + leftDice) % 4) {
            1 -> direction = "自"
            2 -> direction = "右"
            3 -> direction = "対"
            0 -> direction = "左"
            else -> direction = ""
        }
        val sumDice = rightDice + leftDice
        textView.text = "賽：" + sumDice + "-" + direction
    }

    // インターバルで呼ばれる
    override fun onTick(millisUntilFinished: Long) {
        rightDice = Random().nextInt(6) + 1
        leftDice = Random().nextInt(6) + 1
        when (rightDice) {
            1 -> rightImage.setImageResource(R.drawable.number_1)
            2 -> rightImage.setImageResource(R.drawable.number_2)
            3 -> rightImage.setImageResource(R.drawable.number_3)
            4 -> rightImage.setImageResource(R.drawable.number_4)
            5 -> rightImage.setImageResource(R.drawable.number_5)
            6 -> rightImage.setImageResource(R.drawable.number_6)
        }
        when (leftDice) {
            1 -> leftImage.setImageResource(R.drawable.number_1)
            2 -> leftImage.setImageResource(R.drawable.number_2)
            3 -> leftImage.setImageResource(R.drawable.number_3)
            4 -> leftImage.setImageResource(R.drawable.number_4)
            5 -> leftImage.setImageResource(R.drawable.number_5)
            6 -> leftImage.setImageResource(R.drawable.number_6)
        }

    }
}