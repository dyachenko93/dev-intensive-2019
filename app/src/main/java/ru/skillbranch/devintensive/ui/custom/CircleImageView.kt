package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import ru.skillbranch.devintensive.R
import kotlin.math.min

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_BORDER_WIDTH = 2f
    }

    private var border_color = DEFAULT_BORDER_COLOR
    private var border_width = dpToPix(DEFAULT_BORDER_WIDTH)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    init {
        if(attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            border_color = a.getColor(R.styleable.CircleImageView_cv_borderColor, border_color)
            border_width = a.getDimension(R.styleable.CircleImageView_cv_borderWidth, border_width)
            a.recycle()
        }
    }

    @Dimension
    fun getBorderWidth(): Int = pixToDp(border_width).toInt()

    fun setBorderWidth(@Dimension dp: Int) {
        border_width = dpToPix(dp.toFloat())
        invalidate()
    }

    fun getBorderColor(): Int = border_color

    fun setBorderColor(hex: String) {
        border_color = Color.parseColor(hex)
        invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        border_color = resources.getColor(colorId, context.theme)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val x = width / 2f
        val y = height / 2f
        val radius = min(x, y)

        path.addCircle(x, y, radius, Path.Direction.CW)
        canvas?.clipPath(path)

        super.onDraw(canvas)
        drawCircle(canvas, x, y, radius)
    }

    private fun drawCircle(canvas: Canvas?, x: Float, y: Float, radius: Float) {
        paint.style = Paint.Style.STROKE
        paint.color = border_color
        paint.strokeWidth = border_width

        canvas?.drawCircle(x, y, radius, paint)
    }

    private fun dpToPix(dp: Float): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    )

    private fun pixToDp(px: Float): Float = px / Resources.getSystem().displayMetrics.density

}