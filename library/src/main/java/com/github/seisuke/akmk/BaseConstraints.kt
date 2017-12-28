package com.github.seisuke.akmk

import android.os.Build
import android.support.annotation.FloatRange
import android.support.constraint.Barrier
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.constraint.Guideline
import android.view.View
import org.jetbrains.anko.custom.ankoView

abstract class BaseConstraints(protected val constraintSet: ConstraintSet) {

    abstract val head: Int
    abstract val tail: Int
    abstract val antonymHead: Int
    abstract val antonymTail: Int
    abstract val guidelineOrientation: Int

    val parent: BaseConstraints.Parent by lazy { BaseConstraints.Parent() }

    interface WithMargin {
        val margin: Pair<Int?, Int?>
    }

    open class ViewId(val id: Int)

    operator fun ViewId.minus(viewId: ViewId): ViewId {
        connect(this, viewId)
        return viewId
    }

    operator fun ViewId.minus(view: View) = this.minus(ViewId(view.id))
    operator fun View.minus(view: View) = ViewId(this.id).minus(ViewId(view.id))
    operator fun View.minus(view: ViewId) = ViewId(this.id).minus(view)

    operator fun ViewId.minus(chain: Chain) = ChainWithLeftViewId(chain, this)
    operator fun View.minus(chain: Chain) = ViewId(this.id).minus(chain)

    operator fun ViewId.get(margin: Pair<Int?, Int?>) = ViewIdWithMargin(this.id, margin)
    operator fun View.get(margin: Pair<Int?, Int?>) = ViewId(this.id).get(margin)
    operator fun ViewId.get(margin: Int) = ViewIdWithMargin(this.id, margin to null)
    operator fun View.get(margin: Int) = ViewId(this.id).get(margin to null)

    open class Parent : ViewId(ConstraintSet.PARENT_ID)
    open class ViewIdWithMargin(viewId: Int, _margin: Pair<Int?, Int?>) : ViewId(viewId), WithMargin {
        override val margin = _margin
    }

    abstract fun View.bias(@FloatRange(from = 0.0, to = 1.0) bias: Float)

    abstract protected fun createChain(leftId: ViewId, rightId: ViewId, viewIdList: IntArray, weightList: FloatArray, chainType: Int)

    fun chainSpread(vararg views: View, weightList: List<Float>? = null) = Chain(ConstraintSet.CHAIN_SPREAD, *views, weightList = weightList)

    fun chainSpreadInside(vararg views: View, weightList: List<Float>? = null) = Chain(ConstraintSet.CHAIN_SPREAD_INSIDE, *views, weightList = weightList)

    fun chainPacked(vararg views: View) = Chain(ConstraintSet.CHAIN_PACKED, *views, weightList = null)

    fun _ConstraintLayout.barrierHead(vararg views: View) = barrier(head, *views) {
        id = generateViewId()
    }

    fun _ConstraintLayout.barrierTail(vararg views: View) = barrier(tail, *views) {
        id = generateViewId()
    }

    fun _ConstraintLayout.guidelineBegin(guide: Int): Guideline {
        return guideline {
            this.id = generateViewId()
        }.lparams {
            this.orientation = guidelineOrientation
            this.guideBegin = guide
        }
    }

    fun _ConstraintLayout.guidelineEnd(guide: Int): Guideline {
        return guideline {
            this.id = generateViewId()
        }.lparams {
            this.orientation = guidelineOrientation
            this.guideEnd = guide
        }
    }

    fun _ConstraintLayout.guidelinePercent(@FloatRange(from = 0.0, to = 1.0) guide: Float): Guideline {
        return guideline {
            this.id = generateViewId()
        }.lparams {
            this.orientation = guidelineOrientation
            this.guidePercent = guide
        }
    }

    fun ConstraintLayout.barrier(side: Int, vararg views: View, init: (Barrier).() -> Unit = {}): Barrier {
        return ankoView(::Barrier, theme = 0) {
            type = side
            referencedIds = views.map(View::getId).toIntArray()
            init()
        }
    }

    fun View.alignWith(headView: View? = null, tailView: View? = null) {
        headView?.let {
            constraintSet.connect(this.id, antonymHead, headView.id, antonymHead )
        }

        tailView?.let {
            constraintSet.connect(this.id, antonymTail, tailView.id, antonymTail )
        }
    }

    fun alignHead(vararg views: View) {
        val first = views.first()
        views.drop(1).forEach {
            constraintSet.connect(it.id, antonymHead, first.id, antonymHead )
        }
    }

    fun alignTail(vararg views: View) {
        val first = views.first()
        views.drop(1).forEach {
            constraintSet.connect(it.id, antonymTail, first.id, antonymTail )
        }
    }

    fun View.ratio(ratio: String) {
        constraintSet.setDimensionRatio(this.id, ratio)
    }

    protected fun connect(startId: Int, startSide: Int, endId: Int, endSide: Int, margin: Int?) {
        if (margin != null) {
            constraintSet.connect(startId, startSide, endId, endSide, margin)
        } else {
            constraintSet.connect(startId, startSide, endId, endSide)
        }
    }

    inner class Chain(val chainType: Int, private vararg val views: View, val weightList: List<Float>?) {
        val viewIdList: List<Int> = views.map(View::getId)

        fun alignHead() : Chain {
            this@BaseConstraints.alignHead(*views)
            return this
        }

        fun alignTail() : Chain {
            this@BaseConstraints.alignTail(*views)
            return this
        }
    }

    inner class ChainWithLeftViewId(private val chain: Chain, leftView: ViewId) : ViewId(leftView.id) {
        operator fun minus(view: ViewId) {
            val weightArray = chain.weightList?.let {
                it.toFloatArray()
            } ?: FloatArray(chain.viewIdList.size)

            createChain(this, view, chain.viewIdList.toIntArray(), weightArray, chain.chainType)
        }

        operator fun minus(view: View) = minus(ViewId(view.id))
    }

    private fun Pair<Int?, Int?>.existNext() = this.second != null
    private fun Pair<Int?, Int?>.existPrev() = this.first != null

    private fun connect(startView: ViewId, endView: ViewId) {
        val (left, right) = when {
            startView is Parent -> head to head
            endView is Parent -> tail to tail
            startView is WithMargin && startView.margin.existNext() -> tail to head
            else -> head to tail
        }

        val (startId, endId) = when {
            endView is Parent -> startView.id to endView.id
            startView is WithMargin && startView.margin.existNext() -> startView.id to endView.id
            else -> endView.id to startView.id
        }

        val margin = when {
            startView is WithMargin && startView.margin.existNext() -> startView.margin.second
            endView is WithMargin && endView.margin.existPrev() -> endView.margin.first
            else -> null
        }

        connect(startId, left, endId, right, margin)
    }

    private fun generateViewId() : Int {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.hashCode()
        } else {
            View.generateViewId()
        }
    }
}