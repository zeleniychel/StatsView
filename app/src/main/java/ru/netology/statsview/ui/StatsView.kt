package ru.netology.statsview.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.withStyledAttributes
import ru.netology.statsview.R
import ru.netology.statsview.utils.AndroidUtils
import kotlin.math.min
import kotlin.random.Random

class StatsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(
    context,
    attributeSet,
    defStyleAttr,
    defStyleRes
) {
    private var radius = 0F
    private var center = PointF()
    private var oval = RectF()

    private var textSize = AndroidUtils.dp(context, 20).toFloat()
    private var lineWidth = AndroidUtils.dp(context, 5)
    private var colors = emptyList<Int>()

    private var progress = 0F
    private var valueAnimator: ValueAnimator? = null


    private fun generateRandomColor() =
        Random.nextInt(0xFF0000000.toInt(), 0xFFFFFFFF.toInt())

    init {
        context.withStyledAttributes(attributeSet, R.styleable.StatsView) {
            textSize = getDimension(R.styleable.StatsView_textSize, textSize)
            lineWidth = getDimension(R.styleable.StatsView_lineWidth, lineWidth.toFloat()).toInt()
            colors = listOf(
                getColor(R.styleable.StatsView_color1, generateRandomColor()),
                getColor(R.styleable.StatsView_color2, generateRandomColor()),
                getColor(R.styleable.StatsView_color3, generateRandomColor()),
                getColor(R.styleable.StatsView_color4, generateRandomColor())
            )
        }
    }

    data class ArcData(val progressStart: Float, val progressEnd: Float)
    private var arcData: List<ArcData> = emptyList()
    var data: List<Float> = emptyList()
        set(value) {
            field = value
            var cumulativeProgress = 0f
            arcData = value.map { datum ->
                val arcDatum = ArcData(cumulativeProgress, cumulativeProgress + datum)
                cumulativeProgress += datum
                arcDatum
            }
            update()
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = lineWidth.toFloat()
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    private val pointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = lineWidth.toFloat()
        strokeCap = Paint.Cap.ROUND
        color = colors[0]
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = min(w, h) / 2F - lineWidth
        center = PointF(w / 2F, h / 2F)
        oval = RectF(
            center.x - radius,
            center.y - radius,
            center.x + radius,
            center.y + radius
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (data.isEmpty())
            return
        var startAngle = -90F
        arcData.forEachIndexed { index, datum ->
            val sweepAngleTotal = data[index] / data.sum() * 360F
            val progressWithinArc = (progress - datum.progressStart).coerceAtLeast(0F)
                .coerceAtMost(datum.progressEnd - datum.progressStart)
            val sweepAngle = sweepAngleTotal*(progressWithinArc/data[index])
            paint.color = colors.getOrElse(index) { generateRandomColor() }
            if (progressWithinArc > 0) {
                canvas.drawArc(oval,startAngle,sweepAngle,false,paint)
            }
            startAngle += sweepAngleTotal
        }
        canvas.drawPoint(center.x, center.y - radius, pointPaint)
    }

    private fun update() {
        valueAnimator?.let {
            it.removeAllListeners()
            it.cancel()
        }
        progress = 0F
        valueAnimator = ValueAnimator.ofFloat(0F, data.sum()).apply {
            addUpdateListener { anim ->
                progress = anim.animatedValue as Float
                invalidate()
            }
            duration = 1500 * data.size.toLong()
            interpolator = LinearInterpolator()
        }.also {
            it.start()
        }
    }
}