package ucai.cn.fulicenter.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import ucai.cn.fulicenter.R;

/**
 * Created by Administrator on 2016/9/29.
 *
 */
public class FocusView extends View{
    Paint mpaint;
    int mfocus;
    int mcount;
    int mnoamlaColor;
    int mfocusColor;
    int mradius;
    int mspace;

    public int getMfocus() {
        return mfocus;
    }

    public void setMfocus(int mfocus) {
        this.mfocus = mfocus;
        requestLayout();
        invalidate();
    }

    public int getMcount() {
        return mcount;
    }

    public void setMcount(int mcount) {
        this.mcount = mcount;
        requestLayout();
        invalidate();
    }

    public FocusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FocusView);
        mcount = typedArray.getInteger(R.styleable.FocusView_count_my,6);
        mfocus = typedArray.getInteger(R.styleable.FocusView_focus_my,1);
        mfocusColor = typedArray.getColor(R.styleable.FocusView_focusColor,0x7777);
        mnoamlaColor = typedArray.getColor(R.styleable.FocusView_noamlaColor,0xfff55);
        mradius = typedArray.getDimensionPixelOffset(R.styleable.FocusView_radius,5);
        mspace = typedArray.getDimensionPixelSize(R.styleable.FocusView_space_my,2);
        typedArray.recycle();
        mpaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mpaint.setAntiAlias(true);
        mpaint.setStyle(Paint.Style.FILL);
        int leftspace = getPaddingLeft() + (getWidth() - mcount * 2 * mradius - mspace * (mcount - 1)) / 2;

        for (int i = 0;i<mcount;i++){
            int X = leftspace + i * (2 * mradius + mspace);

            int color = i==mfocus?mfocusColor:mnoamlaColor;
            mpaint.setColor(color);
            canvas.drawCircle(X+mradius,mradius,mradius,mpaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthspec(widthMeasureSpec),hightspec(heightMeasureSpec));

    }
    public int widthspec(int widthMeasureSpec){
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int result = width;
        if (widthmode == MeasureSpec.EXACTLY){
           int lenghth =   getPaddingLeft() + getPaddingRight() + 2 * mradius * mcount + mspace * (mcount - 1);

            result = Math.min(lenghth,width);
        }
        return result;
    }
    public int hightspec(int heightMeasureSpec){
        int hightmode = MeasureSpec.getMode(heightMeasureSpec);
        int hight = MeasureSpec.getSize(heightMeasureSpec);
        int result = hight;
        if (hightmode == MeasureSpec.AT_MOST){
            int height = getPaddingTop() + getPaddingBottom() + mradius * 2;


            result = Math.min(height,hight);
        }
        return result;
    }

}
