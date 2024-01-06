package ru.netology.statsview.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import ru.netology.statsview.R
import ru.netology.statsview.utils.AndroidUtils

class StatsViewText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(
    context, attributeSet, defStyleAttr, defStyleRes
) {
    private var center = PointF()
    private var textSize = AndroidUtils.dp(context, 20).toFloat()

    init {
        context.withStyledAttributes(attributeSet, R.styleable.StatsView) {
            textSize = getDimension(R.styleable.StatsView_textSize, textSize)
        }
    }

    var data: List<Float> = emptyList()
        set(value) {
            field = value
            invalidate()
        }
    private val textPrint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = this@StatsViewText.textSize
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        center = PointF(w / 2F, h / 2F)
    }

    override fun onDraw(canvas: Canvas) {
        if (data.isEmpty()) return
        val total = data.map { (it / data.sum()) * 100 }
        canvas.drawText(
            "%.2f%%".format(total.sum()),
            center.x,
            center.y + textPrint.textSize / 4,
            textPrint
        )
    }
}