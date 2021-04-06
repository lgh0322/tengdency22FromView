package com.viatom.tengdency22

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class TendencyView : View{
    private val wavePaint = Paint()
    private val gridPaint = Paint()
    private val CANVAS_W = getPixel(R.dimen.w)
    private val CANVAS_H = getPixel(R.dimen.h)
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }
    private fun init() {
        wavePaint.apply {
            color = getColor(R.color.myBlue)
            style = Paint.Style.STROKE
            strokeWidth = getPixel(R.dimen.grid_w).toFloat()*2
        }

        gridPaint.apply {
            color = getColor(R.color.myGray)
            style = Paint.Style.STROKE
            strokeWidth = getPixel(R.dimen.grid_w).toFloat()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(getColor(R.color.white))

        for(k in 0 until 2){
            canvas.drawLine(0f,(k+1)*CANVAS_H.toFloat()/3,CANVAS_W.toFloat(),(k+1)*CANVAS_H.toFloat()/3,gridPaint)
        }

        for(k in 0 until 6){
            canvas.drawLine((k+1)*CANVAS_W.toFloat()/7,0f,(k+1)*CANVAS_W.toFloat()/7,CANVAS_H.toFloat(),gridPaint)
        }

        var wavePath = Path()
        wavePath.moveTo(
           0f,
           0f
        )
        wavePath.lineTo(0f,CANVAS_H.toFloat())
        wavePath.lineTo(CANVAS_W.toFloat(),CANVAS_H.toFloat())
        wavePath.lineTo(CANVAS_W.toFloat(),0f)
        wavePath.lineTo(0f,0f)
        canvas.drawPath(wavePath,wavePaint)
    }
    private fun getColor(resource_id: Int): Int {
        return ContextCompat.getColor(context, resource_id)
    }
    private fun getPixel(resource_id: Int): Int {
        return resources.getDimensionPixelSize(resource_id)
    }
    override fun onMeasure(width: Int, height: Int) {
        setMeasuredDimension(CANVAS_W, CANVAS_H)
    }
}