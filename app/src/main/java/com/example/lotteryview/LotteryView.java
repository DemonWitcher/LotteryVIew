package com.example.lotteryview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class LotteryView extends View {

    private Paint mContentPaint;
    private PorterDuffXfermode mContentMode;
    private Bitmap mBitmapSrc, mBitmapDst;
    private Path mTouchPath;
    private int mColorBg;
    private Paint mPathPaint;

    public LotteryView(Context context) {
        super(context);
        init();
    }

    public LotteryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LotteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mContentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mContentMode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        mColorBg = Color.GRAY;

        mTouchPath = new Path();

        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setStrokeWidth(50);
        mPathPaint.setStrokeJoin(Paint.Join.ROUND);
        mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        mPathPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmapDst == null || mBitmapSrc == null) {
            return;
        }

        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);

        canvas.drawBitmap(mBitmapDst, 0, 0, mContentPaint);
        mContentPaint.setXfermode(mContentMode);
        canvas.drawBitmap(mBitmapSrc, 0, 0, mContentPaint);
        mContentPaint.setXfermode(null);

        canvas.restoreToCount(sc);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        resetBitmap();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mTouchPath.moveTo(event.getX(), event.getY());
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                mTouchPath.lineTo(event.getX(), event.getY());
                resetPathBitmap();
                invalidate();
            }
            break;
            case MotionEvent.ACTION_UP: {
            }
            case MotionEvent.ACTION_CANCEL: {
            }
            break;
            default:
        }
        return true;
    }

    private Bitmap makeSrc() {
        int width = getWidth();
        int height = getHeight();

        if (width == 0 || height == 0) {
            return null;
        }
        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        c.drawPath(mTouchPath, mPathPaint);
        return bm;
    }

    private Bitmap makeDst() {
        int width = getWidth();
        int height = getHeight();

        if (width == 0 || height == 0) {
            return null;
        }

        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(mColorBg);
        c.drawRect(0, 0, width, height, paint);
        return bm;
    }

    private void resetPathBitmap() {
        mBitmapSrc = makeSrc();
        invalidate();
    }

    private void resetBitmap() {
        mBitmapSrc = makeSrc();
        mBitmapDst = makeDst();
        invalidate();
    }

    public void reset(){
        mTouchPath.reset();
        resetPathBitmap();
    }
}
