package com.developlib.widget.shapeview

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import com.developlib.R

/**
 * 支持shape背景的ConstraintLayout
 */
class ShapeLinearLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    private companion object {
        const val TOP_LEFT = 1
        const val TOP_RIGHT = 2
        const val BOTTOM_RIGHT = 4
        const val BOTTOM_LEFT = 8
    }

    /**
     * shape模式
     * 矩形（rectangle）、椭圆形(oval)、线形(line)、环形(ring)
     */
    private var shapeMode = 0

    /**
     * 填充颜色
     */
    private var fillColor = 0

    /**
     * 描边颜色
     */
    private var strokeColor = 0

    /**
     * 描边宽度
     */
    private var strokeWidth = 0

    /**
     * 圆角半径
     */
    private var shapeCornerRadius = 0

    /**
     * 圆角位置
     * topLeft、topRight、bottomRight、bottomLeft
     */
    private var cornerPosition = -1

    /**
     * 起始颜色
     */
    private var startColor = 0

    /**
     * 结束颜色
     */
    private var endColor = 0

    /**
     * 渐变方向
     * 0-GradientDrawable.Orientation.TOP_BOTTOM
     * 1-GradientDrawable.Orientation.LEFT_RIGHT
     */
    private var gradientOrientation = 0

    /**
     * 阴影大小
     */
    private var elevationSize = -1

    /**
     * shape样式
     */
    private val gradientDrawableShape: GradientDrawable by lazy {
        GradientDrawable()
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ShapeLinearLayout).apply {
            shapeMode = getInt(R.styleable.ShapeLinearLayout_shapeMode, 0)
            fillColor = getColor(R.styleable.ShapeLinearLayout_shapeFillColor, 0xFFFFFFFF.toInt())
            strokeColor = getColor(R.styleable.ShapeLinearLayout_shapeStrokeColor, 0)
            strokeWidth = getDimensionPixelSize(R.styleable.ShapeLinearLayout_shapeStrokeWidth, 0)
            shapeCornerRadius = getDimensionPixelSize(R.styleable.ShapeLinearLayout_shapeCornerRadius, 0)
            cornerPosition = getInt(R.styleable.ShapeLinearLayout_shapeCornerPosition, -1)
            startColor = getColor(R.styleable.ShapeLinearLayout_shapeStartColor, 0xFFFFFFFF.toInt())
            endColor = getColor(R.styleable.ShapeLinearLayout_shapeEndColor, 0xFFFFFFFF.toInt())
            gradientOrientation = getColor(R.styleable.ShapeLinearLayout_shapeOrientation, 0)
            elevationSize = getDimensionPixelSize(R.styleable.ShapeLinearLayout_shapeElevation, -1)
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 初始化shape
        with(gradientDrawableShape) {
            // 渐变色
            if (startColor != 0xFFFFFFFF.toInt() && endColor != 0xFFFFFFFF.toInt()) {
                colors = intArrayOf(startColor, endColor)
                when (gradientOrientation) {
                    0 -> orientation = GradientDrawable.Orientation.TOP_BOTTOM
                    1 -> orientation = GradientDrawable.Orientation.LEFT_RIGHT
                }
            }
            // 填充色
            else {
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
            }
            // 根据圆角位置设置圆角半径
            else {
                cornerRadii = getCornerRadiusByPosition()
            }
            // 默认的透明边框不绘制
            if (strokeColor != 0) {
                setStroke(strokeWidth, strokeColor)
            }
        }

        // 设置背景
        background = gradientDrawableShape
        if (elevationSize != -1) {
            elevation = elevationSize.toFloat()
        }
    }

    /**
     * 根据圆角位置获取圆角半径
     */
    private fun getCornerRadiusByPosition(): FloatArray {
        val result = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
        val cornerRadius = shapeCornerRadius.toFloat()
        if (containsFlag(cornerPosition, TOP_LEFT)) {
            result[0] = cornerRadius
            result[1] = cornerRadius
        }
        if (containsFlag(cornerPosition, TOP_RIGHT)) {
            result[2] = cornerRadius
            result[3] = cornerRadius
        }
        if (containsFlag(cornerPosition, BOTTOM_RIGHT)) {
            result[4] = cornerRadius
            result[5] = cornerRadius
        }
        if (containsFlag(cornerPosition, BOTTOM_LEFT)) {
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