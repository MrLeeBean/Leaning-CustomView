package com.xk.customview.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xuekai on 2017/2/17.
 */

public class BezierView extends View {

    private PointF dataA;
    private PointF dataB;
    private PointF dataC;
    private PointF dataD;
    private PointF controlA;
    private PointF controlB;
    private PointF controlC;
    private PointF controlD;
    private PointF controlE;
    private PointF controlF;
    private PointF controlG;
    private PointF controlH;
    private int width;
    private int height;
    private Paint paint;

    private final float C = 0.551915024494f;     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置

    private Paint mPaint;
    private int mCenterX, mCenterY;

    private PointF mCenter = new PointF(0, 0);
    private float mCircleRadius = 200;                  // 圆的半径
    private float mDifference = 100 * C;        // 圆形的控制点与数据点的差值


    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        dataA = new PointF();
        dataB = new PointF();
        dataC = new PointF();
        dataD = new PointF();
        controlA = new PointF();
        controlB = new PointF();
        controlC = new PointF();
        controlD = new PointF();
        controlE = new PointF();
        controlF = new PointF();
        controlG = new PointF();
        controlH = new PointF();
        paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        dataA.set(0, 100);
        dataB.set(100, 0);
        dataC.set(0, -100);
        dataD.set(-100, 0);
        controlA.set(0 + mDifference, 100);
        controlB.set(100, 0 + mDifference);
        controlC.set(100, 0 - mDifference);
        controlD.set(0 + mDifference, -100);

        controlE.set(0, -100 + mDifference);

        controlF.set(-100, 0 - mDifference);

        controlG.set(-100, 0 + mDifference);
        controlH.set(0 - mDifference, 100);


        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCoordinate(canvas);
        canvas.save();
        canvas.translate(width / 2, height / 2);

        Path path = new Path();
        path.moveTo(dataA.x, dataA.y);
//        path.cubicTo(controlA.x,controlA.y,controlB.x,controlB.y,dataB.x,dataB.y);


        path.cubicTo(controlA.x, controlA.y, controlB.x, controlB.y, dataA.x, dataA.y);
        path.cubicTo(controlC.x, controlC.y, controlD.x, controlD.y, dataC.x, dataC.y);
        path.cubicTo(controlE.x, controlE.y, controlF.x, controlF.y, dataD.x, dataA.y);
        path.cubicTo(controlG.x, controlG.y, controlH.x, controlH.y, dataA.x, dataA.y);

        canvas.drawPath(path, paint);
        paint.setColor(Color.BLUE);


        canvas.restore();
    }

    private void drawCoordinate(Canvas canvas) {
        canvas.save();
        paint.setColor(0xff000000);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawLine(-getWidth() / 2 + 5, 0, getWidth() / 2 - 5, 0, paint);
        canvas.drawLine(0, -getHeight() / 2 + 5, 0, getHeight() / 2 - 5, paint);
        paint.setColor(Color.RED);           // 绘制合并后的路径
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                controlA.set(event.getX() - width / 2, event.getY() - height / 2);
//                if (event.getX()<width/2) {
//                    controlA.set(event.getX()-width/2,event.getY()-height/2);
//
//                }else{
//                    controlB.set(event.getX()-width/2,event.getY()-height/2);
//
//                }
                postInvalidate();
                break;
        }
        return true;
    }


}
