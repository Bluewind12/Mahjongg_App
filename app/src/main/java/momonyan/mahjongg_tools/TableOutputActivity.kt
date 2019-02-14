package momonyan.mahjongg_tools

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.score_table.*

class TableOutputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score_table)
        when (intent.getStringExtra("image")) {
            "oya_point" -> tableImages.setImageResource(R.drawable.point_oya)
            "ko_point" -> tableImages.setImageResource(R.drawable.point_ko)
            "fu" -> tableImages.setImageResource(R.drawable.mark)
            else -> tableImages.setImageResource(R.drawable.hougaku4_kita)
        }
        tableImages.setOnClickListener {
            finish()
        }
    }
}