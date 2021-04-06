package com.viatom.tengdency22

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class TendencyView : View{
    private val sysPaint = Paint()
    private val sysShadowPaint = Paint()
    private val diaPaint = Paint()
    private val diaShadowPaint = Paint()
    private val wavePaint = Paint()
    private val gridPaint = Paint()
    private val CANVAS_W = getPixel(R.dimen.w)
    private val CANVAS_H = getPixel(R.dimen.h)
    private val cH=CANVAS_H.toFloat()
    private val cW=CANVAS_W.toFloat()
    private val diaW = getPixel(R.dimen.dia_w).toFloat()
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

        diaPaint.apply {
            color = getColor(R.color.diaColor)
            style = Paint.Style.FILL
        }
        diaShadowPaint.apply {
            color = getColor(R.color.diaShadowColor)
            style = Paint.Style.FILL
        }
        sysPaint.apply {
            color = getColor(R.color.sysColor)
            style = Paint.Style.FILL
            isAntiAlias=true
        }
        sysShadowPaint.apply {
            color = getColor(R.color.sysShadowColor)
            style = Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(getColor(R.color.white))
        drawSysList(canvas,3, arrayListOf(100,80,150))
        drawDia(canvas,50f,50f)
        drawSys(canvas,150f,50f)
        for(k in 0 until 2){
            canvas.drawLine(
                0f,
                (k + 1) * CANVAS_H.toFloat() / 3,
                CANVAS_W.toFloat(),
                (k + 1) * CANVAS_H.toFloat() / 3,
                gridPaint
            )
        }

        for(k in 0 until 6){
            canvas.drawLine(
                (k + 1) * CANVAS_W.toFloat() / 7,
                0f,
                (k + 1) * CANVAS_W.toFloat() / 7,
                CANVAS_H.toFloat(),
                gridPaint
            )
        }

        var wavePath = Path()
        wavePath.moveTo(
            0f,
            0f
        )
        wavePath.lineTo(0f, CANVAS_H.toFloat())
        wavePath.lineTo(CANVAS_W.toFloat(), CANVAS_H.toFloat())
        wavePath.lineTo(CANVAS_W.toFloat(), 0f)
        wavePath.lineTo(0f, 0f)
        canvas.drawPath(wavePath, wavePaint)
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

    fun drawDiaList(canvas: Canvas,i:Int,b1:ArrayList<Int>){
        val b=virL2RealL(b1)
        if(b.size==0)return
        val x=i*cW/7+cW/14
        val halfWidth = diaW / 2
        if(b.size!=1){
           b.sort()
            canvas.drawRect(RectF(x-halfWidth,b[0].toFloat(),x+halfWidth,b[b.size-1].toFloat()),diaShadowPaint)
        }

        for(k in b){
            drawDia(canvas,x,k.toFloat())
        }

    }


    fun drawSysList(canvas: Canvas,i:Int,b1:ArrayList<Int>){
        val b=virL2RealL(b1)
        if(b.size==0)return
        val x=i*cW/7+cW/14
        val halfWidth = diaW / 2
        if(b.size!=1){
            b.sort()
            canvas.drawRect(RectF(x-halfWidth,b[0].toFloat(),x+halfWidth,b[b.size-1].toFloat()),sysShadowPaint)
        }

        for(k in b){
            drawSys(canvas,x,k.toFloat())
        }

    }


    private fun drawDia(canvas: Canvas, x: Float, y: Float) {
        val halfWidth = diaW / 2
        val path = Path()
        path.moveTo(x, y + halfWidth) // Top
        path.lineTo(x - halfWidth, y) // Left
        path.lineTo(x, y - halfWidth) // Bottom
        path.lineTo(x + halfWidth, y) // Right
        path.lineTo(x, y + halfWidth) // Back to Top
        path.close()
        canvas.drawPath(path, diaPaint)
    }

    private fun drawSys(canvas: Canvas,  x: Float, y: Float) {
        val halfWidth = diaW / 2
        canvas.drawCircle(x,y,halfWidth,sysPaint)
    }

    fun vir2Real(f:Float):Float{
        return cH-(f-50f)/150f*cH
    }

    fun virL2RealL(a:ArrayList<Int>):ArrayList<Int>{
        val b= ArrayList<Int> ()
        for(k in a){
            b.add(vir2Real(k.toFloat()).toInt())
        }
        return b
    }
}