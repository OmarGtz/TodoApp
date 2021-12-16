package com.example.todoapp.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.example.todoapp.R
import com.example.todoapp.utils.themeBackground
import com.example.todoapp.utils.themeColor
import com.example.todoapp.utils.themeTextAppearance

/**
 * PrefixTextView
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
class PrefixTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = R.attr.prefixTextViewStyle
): ConstraintLayout(context, attrs, defStyleRes) {

    var prefixText: String? = null
    set(value) {
        if (value != null) {
            field = value
            prefixTextView.text = value
        }
    }

    var prefixContent: String? = null
    set(value) {
        if (value != null) {
            field = value
            contentTextView.text = value
        }
    }

    var showDivider: Boolean = true
    set(value) {
        field = value
        dividerView.visibility = if (value) View.VISIBLE else View.GONE
    }

    var prefixIcon: Drawable? = null
    set(value) {
        if (value != null) {
            field = value
            prefixImageView.setImageDrawable(value)
        }
    }

    private var contentTextAppearance: Int = 0
    set(value) {
        if (value != 0) {
            field = value
            contentTextView.setTextAppearance(value)
        }
    }

    private var contentTextColor: Int = 0
    set(value)  {
        if (value != 0) {
            field = value
            contentTextView.setTextColor(value)
        }
    }

    private val contentTextView: TextView
    private val prefixTextView: TextView
    private val prefixImageView: ImageView
    private val dividerView: View

    init {
        LayoutInflater.from(context).inflate(R.layout.component_prefix_textview, this, true).apply {
            contentTextView = findViewById(R.id.value_text)
            prefixTextView = findViewById(R.id.prefix_text)
            prefixImageView = findViewById(R.id.prefix_icon)
            dividerView = findViewById(R.id.divider)
        }
//        val prefixText = typeArray.getString(R.styleable.PrefixTextView_prefixText)
//        typeArray.recycle()
        context.withStyledAttributes(attrs, R.styleable.PrefixTextView, defStyleRes) {
            prefixText = getString(R.styleable.PrefixTextView_prefixText)
            prefixContent = getString(R.styleable.PrefixTextView_prefixContent)
            prefixIcon = getDrawable(R.styleable.PrefixTextView_prefixIcon)
            contentTextAppearance = getResourceId(R.styleable.PrefixTextView_prefixContentTextAppearance,
            context.themeTextAppearance(R.attr.textAppearanceBody1))
            contentTextColor = getColor(R.styleable.PrefixTextView_prefixContentTextColor,
            context.themeColor(android.R.attr.textColorPrimary))
            showDivider = getBoolean(R.styleable.PrefixTextView_showDivider, true)
        }
    }

    fun addActionClickListener(action: (String) -> Unit) {
        context.themeBackground(R.attr.selectableItemBackground).let {
            background = it
        }
        setOnClickListener {
            action(prefixText ?: "")
        }
    }

}