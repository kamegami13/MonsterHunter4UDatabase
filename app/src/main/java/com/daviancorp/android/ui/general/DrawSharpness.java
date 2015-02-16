package com.daviancorp.android.ui.general;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Draws a sharpness level by values
 *
 * Max sharpness value on the Japanese wiki is 40
 * Max sharpness value in existing 3U db is 50
 *
 * Max sharpness units combined should not exceed 50
 */
public class DrawSharpness extends View {

    // Error Tag
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

	int orangeColor = Color.rgb(255, 150, 0);
	int purpleColor = Color.rgb(120, 81, 169);

	Paint paint = new Paint();

    public DrawSharpness(Context context, AttributeSet attrs){
        super(context, attrs);
    }

	public void init(String sharpness) {

        int[] sharpness1 = new int[7];
        int[] sharpness2 = new int[7];

        String[] strSharpnessBoth;
        List<String> strSharpness1;
        List<String> strSharpness2;

        //separate both sets of sharpness
        strSharpnessBoth = sharpness.split(" ");

        //convert sharpness strings to arrays
        strSharpness1 = new ArrayList<>(Arrays.asList(strSharpnessBoth[0].split("\\.")));
        strSharpness2 = new ArrayList<>(Arrays.asList(strSharpnessBoth[1].split("\\.")));

        //add leading 0s to those with less than purple sharpness
        while(strSharpness1.size()<=7){
            strSharpness1.add("0");
        }
        while(strSharpness2.size()<=7){
            strSharpness2.add("0");
        }

        // Error handling logs error and passes empty sharpness bars
        for(int i = 0; i<7; i++){
            try{
                sharpness1[i] = Integer.parseInt(strSharpness1.get(i));
            } catch(Exception e){
                Log.v(TAG, "Error in sharpness " + sharpness);
                sharpness1 = new int[]{0, 0, 0, 0, 0, 0, 0};
                break;
            }
        }
        for(int i = 0; i<7; i++){
            try{
                sharpness2[i] = Integer.parseInt(strSharpness2.get(i));
            } catch(Exception e){
                Log.v(TAG, "Error in sharpness " + sharpness);
                sharpness2 = new int[]{0, 0, 0, 0, 0, 0, 0};
                break;
            }
        }

		mRed1 = sharpness1[0];
		mOrange1 = sharpness1[1];
		mYellow1 = sharpness1[2];
		mGreen1 = sharpness1[3];
		mBlue1 = sharpness1[4];
		mWhite1 = sharpness1[5];
		mPurple1 = sharpness1[6];

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
	public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int margins = (int) Math.floor(mheight/7);
        int scalefactor = (int) Math.floor((mwidth-(margins*2))/50);

        // Draw the background
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);
        canvas.drawRect(0, 0, mwidth, mheight, paint);

        // Draw top bar
        int bartop = margins;
        int barbottom = (int) Math.floor(mheight/2-(margins/2));
        drawBar(canvas, margins, scalefactor, bartop, barbottom,
                mRed1, mOrange1, mYellow1, mGreen1, mBlue1, mWhite1, mPurple1);

        // Draw bottom bar
        int bartop2 = (int) Math.floor(mheight/2+(margins/2));
        int barbottom2 = mheight-margins;
        drawBar(canvas, margins, scalefactor, bartop2, barbottom2,
                mRed2, mOrange2, mYellow2, mGreen2, mBlue2, mWhite2, mPurple2);

	}

    private void drawBar(Canvas canvas, int margins, int scalefactor, int bartop, int barbottom,
                         int ired, int iorange, int iyellow,
                         int igreen, int iblue, int iwhite, int ipurple){

        int start = margins;
        int end = start + ired*scalefactor;
        paint.setStrokeWidth(0);
        paint.setColor(Color.RED);
        canvas.drawRect(start, bartop, end, barbottom, paint);

        start = end;
        end = end + iorange*scalefactor;
        paint.setColor(orangeColor);
        canvas.drawRect(start, bartop, end, barbottom, paint);

        start = end;
        end = end + iyellow*scalefactor;
        paint.setColor(Color.YELLOW);
        canvas.drawRect(start, bartop, end, barbottom, paint);

        start = end;
        end = end + igreen*scalefactor;
        paint.setColor(Color.GREEN);
        canvas.drawRect(start, bartop, end, barbottom, paint);

        start = end;
        end = end + iblue*scalefactor;
        paint.setColor(Color.BLUE);
        canvas.drawRect(start, bartop, end, barbottom, paint);

        start = end;
        end = end + iwhite*scalefactor;
        paint.setColor(Color.WHITE);
        canvas.drawRect(start, bartop, end, barbottom, paint);

        start = end;
        end = end + ipurple*scalefactor;
        paint.setColor(purpleColor);
        canvas.drawRect(start, bartop, end, barbottom, paint);
    }

}