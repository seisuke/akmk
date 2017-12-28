package com.github.seisuke.akmk

import android.app.Activity
import android.content.Context
import android.support.annotation.FloatRange
import android.support.constraint.ConstraintLayout
import android.support.constraint.Group
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

open class _ConstraintLayout(ctx: Context): ConstraintLayout(ctx) {

    val matchConstraint: Int = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT

    inline fun <T: View> T.lparams(
            source: ConstraintLayout.LayoutParams?,
            init: ConstraintLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ConstraintLayout.LayoutParams(source!!)
        layoutParams.init()
        layoutParams.validate()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T: View> T.lparams(
            source: ConstraintLayout.LayoutParams?
    ): T {
        val layoutParams = ConstraintLayout.LayoutParams(source!!)
        layoutParams.validate()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            c: Context?,
            attrs: AttributeSet?,
            init: ConstraintLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ConstraintLayout.LayoutParams(c!!, attrs!!)
        layoutParams.init()
        layoutParams.validate()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T: View> T.lparams(
            c: Context?,
            attrs: AttributeSet?
    ): T {
        val layoutParams = ConstraintLayout.LayoutParams(c!!, attrs!!)
        layoutParams.validate()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            init: ConstraintLayout.LayoutParams.() -> Unit
    ): T {



        val layoutParams = ConstraintLayout.LayoutParams(width, height)
        layoutParams.init()
        layoutParams.validate()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T: View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = ConstraintLayout.LayoutParams(width, height)
        layoutParams.validate()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            source: ViewGroup.LayoutParams?,
            init: ConstraintLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ConstraintLayout.LayoutParams(source!!)
        layoutParams.init()
        layoutParams.validate()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T: View> T.lparams(
            source: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = ConstraintLayout.LayoutParams(source!!)
        layoutParams.validate()
        this@lparams.layoutParams = layoutParams
        return this
    }


    fun ConstraintLayout.LayoutParams.circularPosition(view: View, radius: Int, @FloatRange(from = 0.0, to = 360.0) angle: Float) {
        circleConstraint = view.id
        circleRadius = radius
        circleAngle = angle
    }
}

@PublishedApi
internal object `$$Anko$Factories$ConstraintLayoutView` {
    val GUIDELINE = { ctx: Context -> android.support.constraint.Guideline(ctx) }
}

fun ViewManager.guideline(): android.support.constraint.Guideline = guideline() {}
inline fun ViewManager.guideline(init: (@AnkoViewDslMarker android.support.constraint.Guideline).() -> Unit): android.support.constraint.Guideline {
    return ankoView(`$$Anko$Factories$ConstraintLayoutView`.GUIDELINE, theme = 0) { init() }
}

fun _ConstraintLayout.group(vararg views: View): Group = ankoView(::Group, theme = 0) {
    referencedIds = views.map(View::getId).toIntArray()
}

@PublishedApi
internal object `$$Anko$Factories$ConstraintLayoutViewGroup` {
    val CONSTRAINT_LAYOUT = { ctx: Context -> _ConstraintLayout(ctx) }
}

fun ViewManager.constraintLayout(): android.support.constraint.ConstraintLayout = constraintLayout() {}
inline fun ViewManager.constraintLayout(init: (@AnkoViewDslMarker _ConstraintLayout).() -> Unit): android.support.constraint.ConstraintLayout {
    return ankoView(`$$Anko$Factories$ConstraintLayoutViewGroup`.CONSTRAINT_LAYOUT, theme = 0) { init() }
}

fun ViewManager.themedConstraintLayout(theme: Int = 0): android.support.constraint.ConstraintLayout = themedConstraintLayout(theme) {}
inline fun ViewManager.themedConstraintLayout(theme: Int = 0, init: (@AnkoViewDslMarker _ConstraintLayout).() -> Unit): android.support.constraint.ConstraintLayout {
    return ankoView(`$$Anko$Factories$ConstraintLayoutViewGroup`.CONSTRAINT_LAYOUT, theme) { init() }
}

fun Context.constraintLayout(): android.support.constraint.ConstraintLayout = constraintLayout() {}
inline fun Context.constraintLayout(init: (@AnkoViewDslMarker _ConstraintLayout).() -> Unit): android.support.constraint.ConstraintLayout {
    return ankoView(`$$Anko$Factories$ConstraintLayoutViewGroup`.CONSTRAINT_LAYOUT, theme = 0) { init() }
}

fun Context.themedConstraintLayout(theme: Int = 0): android.support.constraint.ConstraintLayout = themedConstraintLayout(theme) {}
inline fun Context.themedConstraintLayout(theme: Int = 0, init: (@AnkoViewDslMarker _ConstraintLayout).() -> Unit): android.support.constraint.ConstraintLayout {
    return ankoView(`$$Anko$Factories$ConstraintLayoutViewGroup`.CONSTRAINT_LAYOUT, theme) { init() }
}

fun Activity.constraintLayout(): android.support.constraint.ConstraintLayout = constraintLayout() {}
inline fun Activity.constraintLayout(init: (@AnkoViewDslMarker _ConstraintLayout).() -> Unit): android.support.constraint.ConstraintLayout {
    return ankoView(`$$Anko$Factories$ConstraintLayoutViewGroup`.CONSTRAINT_LAYOUT, theme = 0) { init() }
}

fun Activity.themedConstraintLayout(theme: Int = 0): android.support.constraint.ConstraintLayout = themedConstraintLayout(theme) {}
inline fun Activity.themedConstraintLayout(theme: Int = 0, init: (@AnkoViewDslMarker _ConstraintLayout).() -> Unit): android.support.constraint.ConstraintLayout {
    return ankoView(`$$Anko$Factories$ConstraintLayoutViewGroup`.CONSTRAINT_LAYOUT, theme) { init() }
}
