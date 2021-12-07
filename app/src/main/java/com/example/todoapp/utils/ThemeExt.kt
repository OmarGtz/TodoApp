package com.example.todoapp.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.res.use
/**
 * ThemeExt
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
@ColorInt
fun Context.themeColor(
    @AttrRes themeAttrId: Int
): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}

fun Context.themeTextAppearance(
    @AttrRes themeAttrId: Int
): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getResourceId(0, -1)
    }
}

fun Context.themeBackground(
    @AttrRes themeAttrId: Int
): Drawable? {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getDrawable(0)
    }
}


