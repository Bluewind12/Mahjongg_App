package momonyan.mahjongg_tools

import android.content.Context
import android.graphics.PointF
import androidx.recyclerview.widget.RecyclerView


class RecyclerLayoutCustomManager(context: Context) : androidx.recyclerview.widget.LinearLayoutManager(context, RecyclerView.VERTICAL, false) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?,
                                        position: Int) {
        val smoothScroller = TopSnappedSmoothScroller(recyclerView.context)
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    private inner class TopSnappedSmoothScroller(context: Context) : androidx.recyclerview.widget.LinearSmoothScroller(context) {

        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return this@RecyclerLayoutCustomManager
                    .computeScrollVectorForPosition(targetPosition)
        }

        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }
}