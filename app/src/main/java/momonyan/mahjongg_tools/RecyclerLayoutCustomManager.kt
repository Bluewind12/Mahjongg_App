package momonyan.mahjongg_tools

import android.content.Context
import android.graphics.PointF
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView


class RecyclerLayoutCustomManager : LinearLayoutManager {

    constructor(context: Context) : super(context, VERTICAL, false)

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?,
                                        position: Int) {
        val smoothScroller = TopSnappedSmoothScroller(recyclerView.context)
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    private inner class TopSnappedSmoothScroller(context: Context) : LinearSmoothScroller(context) {

        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return this@RecyclerLayoutCustomManager
                    .computeScrollVectorForPosition(targetPosition)
        }

        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }
}