package com.github.seisuke.akmk

import android.support.constraint.ConstraintSet
import android.view.View

fun _ConstraintLayout.constraints(horizontal: HorizontalConstraints.() -> Unit = {},
                                  vertical: VerticalConstraints.() -> Unit = {} ): ConstraintSet {
    return ConstraintSet().apply {
        clone(this@constraints)
        HorizontalConstraints(this).horizontal()
        VerticalConstraints(this).vertical()
        applyTo(this@constraints)
    }
}

class HorizontalConstraints(constraintSet: ConstraintSet) : BaseConstraints(constraintSet) {

    override val head = ConstraintSet.START
    override val tail = ConstraintSet.END
    override val antonymHead = ConstraintSet.TOP
    override val antonymTail = ConstraintSet.BOTTOM

    override val guidelineOrientation = ConstraintSet.VERTICAL_GUIDELINE

    override fun View.bias(bias: Float) = constraintSet.setHorizontalBias(this.id, bias)

    override fun createChain(leftId: ViewId, rightId: ViewId, viewIdList: IntArray, weightList: FloatArray, chainType: Int) {
        constraintSet.createHorizontalChain(leftId.id, ConstraintSet.LEFT, rightId.id, ConstraintSet.RIGHT, viewIdList, weightList, chainType)
    }

    fun View.connectBaseline(endView: View, margin: Int? = null) {
        connect(this.id, ConstraintSet.BASELINE, endView.id, ConstraintSet.BASELINE, margin)
    }

    fun View.connectBaseline(endView: ViewId, margin: Int? = null) {
        connect(this.id, ConstraintSet.BASELINE, endView.id, ConstraintSet.BASELINE, margin)
    }
}

class VerticalConstraints(constraintSet: ConstraintSet) : BaseConstraints(constraintSet) {

    override val head = ConstraintSet.TOP
    override val tail = ConstraintSet.BOTTOM
    override val antonymHead = ConstraintSet.START
    override val antonymTail = ConstraintSet.END

    override val guidelineOrientation = ConstraintSet.HORIZONTAL_GUIDELINE

    override fun View.bias(bias: Float) = constraintSet.setVerticalBias(this.id, bias)

    override fun createChain(leftId: ViewId, rightId: ViewId, viewIdList: IntArray, weightList: FloatArray, chainType: Int) {
        constraintSet.createVerticalChain(leftId.id, head, rightId.id, tail, viewIdList, weightList, chainType)
    }
}

