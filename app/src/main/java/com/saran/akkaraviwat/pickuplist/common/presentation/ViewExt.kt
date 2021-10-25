package com.saran.akkaraviwat.pickuplist.common.presentation

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.TypedValue
import android.view.View
import kotlin.math.roundToInt

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.goneUnless(visible: Boolean) = if (visible) visible() else gone()

fun View.invisibleUnless(visible: Boolean) = if (visible) visible() else invisible()

