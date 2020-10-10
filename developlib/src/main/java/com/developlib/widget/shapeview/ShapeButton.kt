package com.developlib.widget.shapeview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import com.developlib.R
import kotlin.math.ceil

//带shape的button 以后再也不用 写shape文件了
class ShapeButton @JvmOverloads constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int = 0) : AppCompatButton(context, attributeSet, defStyleAttr) {

    private companion object {
        const val TOP_LEFT = 1
        const val TOP_RIGHT = 2
        const val BOTTOM_RIGHT = 4
        const val BOTTOM_LEFT = 8
    }

    /*
    shape模式
    矩形（rectangle）
    椭圆形(oval)
    线形(line)
    环形(ring)
    */
    private var shapeMode = 0

    //填充颜色
    private var fillColor = 0

    //按下的颜色
    private var pressedColor = 0

    //描边颜色
    private var strokeColor = 0

    //描边宽度
    private var strokeWidth = 0

    //圆角半径
    private var shapeCornerRadius = 0

    //圆角位置
    private var cornerPosition = 0

    //点击动效果
    private var activeEnable = false

    //起始颜色
    private var startColor = 0

    //结束颜色
    private var endColor = 0

    /*渐变方向
    0-GradientDrawable.Orientation.TOP_BOTTOM
    1-GradientDrawable.Orientation.LEFT_RIGHT
    */
    private var gradientOrientation = 0

    /**
     * drawable位置
     * -1-null、0-left、1-top、2-right、3-bottom
     */
    private var drawablePosition = -1

    /**
     * 普通shape样式
     */
    private val normalGradientDrawable: GradientDrawable by lazy { GradientDrawable() }


    // button内容总宽度
    private var contentWidth = 0f

    // button内容总高度
    private var contentHeight = 0f

    //水波纹效果
    private var rippleDrawable: RippleDrawable

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.ShapeButton).apply {
            shapeMode = getInt(R.styleable.ShapeButton_shapeMode, 0)
            fillColor = getColor(R.styleable.ShapeButton_shapeFillColor, 0xFFFFFFFF.toInt())
            pressedColor = getColor(R.styleable.ShapeButton_shapePressedColor, 0xFF666666.toInt())
            strokeColor = getColor(R.styleable.ShapeButton_shapeStrokeColor, 0)
            strokeWidth = getDimensionPixelSize(R.styleable.ShapeButton_shapeStrokeWidth, 0)
            shapeCornerRadius = getDimensionPixelSize(R.styleable.ShapeButton_shapeCornerRadius, 0)
            cornerPosition = getInt(R.styleable.ShapeButton_shapeCornerPosition, -1)
            activeEnable = getBoolean(R.styleable.ShapeButton_shapeButtonActiveEnable, false)
            drawablePosition = getInt(R.styleable.ShapeButton_shapeButtonDrawablePosition, -1)
            startColor = getColor(R.styleable.ShapeButton_shapeStartColor, 0xFFFFFFFF.toInt())
            endColor = getColor(R.styleable.ShapeButton_shapeEndColor, 0xFFFFFFFF.toInt())
            gradientOrientation = getColor(R.styleable.ShapeButton_shapeOrientation, 0)
            recycle()
            rippleDrawable = RippleDrawable(ColorStateList.valueOf(pressedColor), normalGradientDrawable, null)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        with(normalGradientDrawable) {
            // 渐变色
            if (startColor != 0xFFFFFFFF.toInt() && endColor != 0xFFFFFFFF.toInt()) {
                colors = intArrayOf(startColor, endColor)
                when (gradientOrientation) {
                    0 -> orientation = GradientDrawable.Orientation.TOP_BOTTOM
                    1 -> orientation = GradientDrawable.Orientation.LEFT_RIGHT
                }
            } else {
                setColor(fillColor)
            }
            when (shapeMode) {
                0 -> shape = GradientDrawable.RECTANGLE
                1 -> shape = GradientDrawable.OVAL
                2 -> shape = GradientDrawable.LINE
                3 -> shape = GradientDrawable.RING
            }
            // 统一设置圆角半径
            if (cornerPosition == -1) {
                cornerRadius = shapeCornerRadius.toFloat()
            } else { // 根据圆角位置设置圆角半径
                cornerRadii = getCornerRadiusByPosition()
            }
            // 默认的透明边框不绘制,否则会导致没有阴影
            if (strokeColor != 0) {
                setStroke(strokeWidth, strokeColor)
            }
            // 是否开启点击动效
            background = if (activeEnable) {
                rippleDrawable
            } else {
                normalGradientDrawable
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        // 如果xml中配置了drawable则设置padding让文字移动到边缘与drawable靠在一起
        // button中配置的drawable默认贴着边缘
        if (drawablePosition > -1) {
            val drawable: Drawable? = compoundDrawables[drawablePosition]
            drawable?.let {
                // 图片间距
                val drawablePadding = compoundDrawablePadding
                when (drawablePosition) {
                    // 左右drawable
                    0, 2 -> {
                        // 图片宽度
                        val drawableWidth = it.intrinsicWidth
                        // 获取文字宽度
                        val textWidth = paint.measureText(text.toString())
                        // 内容总宽度
                        contentWidth = textWidth + drawableWidth + drawablePadding
                        val rightPadding = (width - contentWidth).toInt()
                        // 图片和文字全部靠在左侧
                        setPadding(0, 0, rightPadding, 0)
                    }
                    // 上下drawable
                    1, 3 -> {
                        // 图片高度
                        val drawableHeight = it.intrinsicHeight
                        // 获取文字高度
                        val fm = paint.fontMetrics
                        // 单行高度
                        val singeLineHeight =
                            ceil(fm.descent.toDouble() - fm.ascent.toDouble()).toFloat()
                        // 总的行间距
                        val totalLineSpaceHeight = (lineCount - 1) * lineSpacingExtra
                        val textHeight = singeLineHeight * lineCount + totalLineSpaceHeight
                        // 内容总高度
                        contentHeight = textHeight + drawableHeight + drawablePadding
                        // 图片和文字全部靠在上侧
                        val bottomPadding = (height - contentHeight).toInt()
                        setPadding(0, 0, 0, bottomPadding)
                    }
                }
            }
        }
        // 内容居中
        gravity = Gravity.CENTER
        // 可点击
        if (activeEnable) {
            isClickable = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        // 让图片和文字居中
        when {
            contentWidth > 0 && (drawablePosition == 0 || drawablePosition == 2) -> canvas.translate(
                (width - contentWidth) / 2,
                0f
            )
            contentHeight > 0 && (drawablePosition == 1 || drawablePosition == 3) -> canvas.translate(
                0f,
                (height - contentHeight) / 2
            )
        }
        super.onDraw(canvas)
    }


    /**
     * 根据圆角位置获取圆角半径
     */
    private fun getCornerRadiusByPosition(): FloatArray {
        val result = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
        val cornerRadius = shapeCornerRadius.toFloat()
        if (containsFlag(
                cornerPosition,
                TOP_LEFT
            )
        ) {
            result[0] = cornerRadius
            result[1] = cornerRadius
        }
        if (containsFlag(
                cornerPosition,
                TOP_RIGHT
            )
        ) {
            result[2] = cornerRadius
            result[3] = cornerRadius
        }
        if (containsFlag(
                cornerPosition,
                BOTTOM_RIGHT
            )
        ) {
            result[4] = cornerRadius
            result[5] = cornerRadius
        }
        if (containsFlag(
                cornerPosition,
                BOTTOM_LEFT
            )
        ) {
            result[6] = cornerRadius
            result[7] = cornerRadius
        }
        return result
    }

    /**
     * 是否包含对应flag
     * 按位或
     */
    private fun containsFlag(flagSet: Int, flag: Int): Boolean {
        return flagSet or flag == flagSet
    }
}