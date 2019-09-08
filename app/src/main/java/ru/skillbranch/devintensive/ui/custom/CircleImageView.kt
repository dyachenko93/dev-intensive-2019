package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.*
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
    private var avatarSize: Int = 0
    private var rect: Rect = Rect()
    private var pathR: Path = Path()
    private var paintText = DrawableText()
    private var paintBorder = Paint(Paint.ANTI_ALIAS_FLAG)
    private var borderWidth: Float = DEFAULT_BORDER_WIDTH
    private var borderColor: Int = DEFAULT_BORDER_COLOR
    private var bgColor: Int = DEFAULT_BACKGROUND_COLOR
    private var initials: String? = null
    private val bgColors = arrayOf(
        "#7BC862",
        "#E17076",
        "#FAA774",
        "#6EC9CB",
        "#65AADD",
        "#A695E7",
        "#EE7AAE"
    )
    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_BORDER_WIDTH = 2f
        private const val DEFAULT_BACKGROUND_COLOR = Color.MAGENTA
    }

//    private var border_color = DEFAULT_BORDER_COLOR
//    private var border_width = dpToPix(DEFAULT_BORDER_WIDTH)
//
//    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
//    private val path = Path()

    init {
        if(attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, borderColor)
            borderWidth = a.getDimension(R.styleable.CircleImageView_cv_borderWidth, borderWidth)
            a.recycle()
        }
    }

    @Dimension
    fun getBorderWidth(): Int = pixToDp(borderWidth).toInt()

    fun setBorderWidth(@Dimension dp: Int) {
        borderWidth = dpToPix(dp.toFloat())
        invalidate()
    }

    fun getBorderColor(): Int = borderColor

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
        invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = resources.getColor(colorId, context.theme)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val x = width / 2f
        val y = height / 2f
        val radius = min(x, y)

        pathR.addCircle(x, y, radius, Path.Direction.CW)
        canvas?.clipPath(pathR)

        super.onDraw(canvas)
        drawCircle(canvas, x, y, radius)
    }

    private fun drawCircle(canvas: Canvas?, x: Float, y: Float, radius: Float) {
        paintBorder.style = Paint.Style.STROKE
        paintBorder.color = borderColor
        paintBorder.strokeWidth = borderWidth

        canvas?.drawCircle(x, y, radius, paintBorder)
    }

    private fun dpToPix(dp: Float): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    )

    private fun pixToDp(px: Float): Float = px / Resources.getSystem().displayMetrics.density

    fun setInitials(initials: String) {
        paintText.text = initials
        paintText.backgroundColor = hexToInt(bgColors[(Math.random()*bgColors.size).toInt()])
        this.setImageDrawable(paintText)
    }

    private fun hexToInt(s: String): Int {
        return -s.substring(1).toInt(16)
    }

}