package io.auxo.bezier;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zhaoweiwei@qk365.com
 */
public class BezierView extends View {

    private int mColor = Color.WHITE;
    private Paint mPaint;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BezierView);

            mControlPointMaxOffset = ta.getDimensionPixelSize(R.styleable.BezierView_controlPointMaxOffset, mControlPointMaxOffset);
            mStartPointMaxOffset = ta.getDimensionPixelSize(R.styleable.BezierView_startPointMaxOffset, mStartPointMaxOffset);

            mColor = ta.getColor(R.styleable.BezierView_color, mColor);
        }

        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path mPath = new Path();
        mPath.moveTo(0, mStartPointY);
        mPath.quadTo(getMeasuredWidth() / 2, mControlPointY, getMeasuredWidth(), mStartPointY);
        mPath.lineTo(getMeasuredWidth(), 0);
        mPath.lineTo(0, 0);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    // 开始点位置Y
    private int mStartPointY = 0;
    // 控制点位置Y
    private int mControlPointY = 0;

    // 控制点相对于开始点的最大偏移
    private int mControlPointMaxOffset = 200;
    // 开始点相对于锚点View底部的偏移
    private int mStartPointMaxOffset = 100;

    private int mAnchorId = 0;
    private int mAppBarMaxOffset = 0;

    public void setUpWithAppBarLayout(AppBarLayout layout, @IdRes int anchor) {
        mAnchorId = anchor;
        layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mAppBarMaxOffset = appBarLayout.getMeasuredHeight() - appBarLayout.findViewById(mAnchorId).getMeasuredHeight();
                double offsetPercent = mAppBarMaxOffset == 0 ? 0 : (1 + (double) verticalOffset / mAppBarMaxOffset);
                mStartPointY = (int) (appBarLayout.getMeasuredHeight() + verticalOffset + mStartPointMaxOffset * offsetPercent);
                mControlPointY = (int) (mStartPointY + mControlPointMaxOffset * offsetPercent);
                invalidate();
            }
        });
    }
}
