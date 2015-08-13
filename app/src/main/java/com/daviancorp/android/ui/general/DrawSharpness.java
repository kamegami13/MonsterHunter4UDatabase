package com.daviancorp.android.ui.general;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/*
 * Draws a sharpness level by values
 *
 * Max sharpness units combined should not exceed the value of int maxsharpness
 */
public class DrawSharpness extends View {

    private static final String TAG = "DrawSharpness";

    private int mRed1;
    private int mOrange1;
    private int mYellow1;
    private int mGreen1;
    private int mBlue1;
    private int mWhite1;
    private int mPurple1;
    private int mRed2;
    private int mOrange2;
    private int mYellow2;
    private int mGreen2;
    private int mBlue2;
    private int mWhite2;
    private int mPurple2;

    private int mheight;
    private int mwidth;

    private final int maxsharpness = 70;

    int orangeColor = Color.rgb(255, 150, 0);
    int purpleColor = Color.rgb(120, 81, 169);

    Paint paint = new Paint();

    public DrawSharpness(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(int[] sharpness1, int[] sharpness2) {

        // Assign sharpness array 1
        mRed1 = sharpness1[0];
        mOrange1 = sharpness1[1];
        mYellow1 = sharpness1[2];
        mGreen1 = sharpness1[3];
        mBlue1 = sharpness1[4];
        mWhite1 = sharpness1[5];
        mPurple1 = sharpness1[6];

        // Assign sharpness array 2
        mRed2 = sharpness2[0];
        mOrange2 = sharpness2[1];
        mYellow2 = sharpness2[2];
        mGreen2 = sharpness2[3];
        mBlue2 = sharpness2[4];
        mWhite2 = sharpness2[5];
        mPurple2 = sharpness2[6];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width, height;

        // Width should be no greater than 500px
        width = Math.min(500, MeasureSpec.getSize(widthMeasureSpec));
        // Height should be no greater than 50px
        height = Math.min(50, MeasureSpec.getSize(heightMeasureSpec));

        mwidth = width;
        mheight = height;

        setMeasuredDimension(width, height);
    }

    @Override
    public void requestLayout() {
        /*
         * Do nothing here
         */
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Margins are defined by height/7. 7 can be changed.
        int margins = (int) Math.floor(mheight / 7);
        // Scale factor is used to multiply sharpness values to make sure full sharpness fills the bar
        // Must be a float to retain accuracy until pixel conversion
        float scalefactor = (float) (mwidth - (margins * 2)) / maxsharpness;
        // specify the width of each bar
        int barwidth = (int) (scalefactor * maxsharpness) + (margins * 2);

        // Draw the background
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);
        canvas.drawRect(0, 0, barwidth, mheight, paint);

        // Draw top bar
        int bartop = margins;
        int barbottom = (int) Math.floor(mheight / 2 - (margins / 2));
        drawBar(canvas, margins, scalefactor, bartop, barbottom,
                mRed1, mOrange1, mYellow1, mGreen1, mBlue1, mWhite1, mPurple1);

        // Draw bottom bar
        int bartop2 = (int) Math.floor(mheight / 2 + (margins / 2));
        int barbottom2 = mheight - margins;
        drawBar(canvas, margins, scalefactor, bartop2, barbottom2,
                mRed2, mOrange2, mYellow2, mGreen2, mBlue2, mWhite2, mPurple2);

    }

    private void drawBar(Canvas canvas, int margins, float scalefactor, int bartop, int barbottom,
                         int ired, int iorange, int iyellow,
                         int igreen, int iblue, int iwhite, int ipurple) {

        // Run through the bar and accumulate sharpness
        int start = margins;
        int end = start + (int) (ired * scalefactor);
        paint.setStrokeWidth(0);
        paint.setColor(Color.RED);
        canvas.drawRect(start, bartop, end, barbottom, paint);

        start = end;
        end = end + (int) (iorange * scalefactor);
        paint.setColor(orangeColor);
        canvas.drawRect(start, bartop, end, barbottom, paint);

        start = end;
        end = end + (int) (iyellow * scalefactor);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(start, bartop, end, barbottom, paint);

        start = end;
        end = end + (int) (igreen * scalefactor);
        paint.setColor(Color.GREEN);
        canvas.drawRect(start, bartop, end, barbottom, paint);

        start = end;
        end = end + (int) (iblue * scalefactor);
        paint.setColor(Color.BLUE);
        canvas.drawRect(start, bartop, end, barbottom, paint);

        start = end;
        end = end + (int) (iwhite * scalefactor);
        paint.setColor(Color.WHITE);
        canvas.drawRect(start, bartop, end, barbottom, paint);

        start = end;
        end = end + (int) (ipurple * scalefactor);
        paint.setColor(purpleColor);
        canvas.drawRect(start, bartop, end, barbottom, paint);
    }

}