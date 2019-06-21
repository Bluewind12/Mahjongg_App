package momonyan.mahjongg_tools

import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class CountFunctionDice(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {
    lateinit var rightImage: ImageView
    lateinit var leftImage: ImageView
    lateinit var textView: TextView
    var rightDice = 0
    var leftDice = 0
    override fun onFinish() {
        val direction: String = when ((rightDice + leftDice) % 4) {
            1 -> "自"
            2 -> "右"
            3 -> "対"
            0 -> "左"
            else -> ""
        }
        val sumDice = rightDice + leftDice
        textView.text = "$sumDice：$direction"
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