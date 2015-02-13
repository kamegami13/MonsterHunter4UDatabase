package com.daviancorp.android.ui.general;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/*
 * Currently unused
 * 
 * Draws a sharpness level by values
 *
 * Max sharpness value on the Japanese wiki is 40
 * Max sharpness value in existing 3U db is 50
 *
 * Max sharpness units combined should not exceed 50
 */
public class DrawSharpness extends View {

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

        for(int i = 0; i<7; i++){
            sharpness1[i] = Integer.parseInt(strSharpness1.get(i));
        }
        for(int i = 0; i<7; i++){
            sharpness2[i] = Integer.parseInt(strSharpness2.get(i));
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
	public void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //DRAW TOP BAR-------------------------------------
        int scalefactor = 8;
        int bartop = 5;
        int barbottom = 20;

		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(4);
		canvas.drawRect(0, 0, 450, 50, paint);

		int start = 5;
		int end = start + mRed1*scalefactor;
		paint.setStrokeWidth(0);
		paint.setColor(Color.RED);
		canvas.drawRect(start, bartop, end, barbottom, paint);

		start = end;
		end = end + mOrange1*scalefactor;
		paint.setColor(orangeColor);
		canvas.drawRect(start, bartop, end, barbottom, paint);

		start = end;
		end = end + mYellow1*scalefactor;
		paint.setColor(Color.YELLOW);
		canvas.drawRect(start, bartop, end, barbottom, paint);

		start = end;
		end = end + mGreen1*scalefactor;
		paint.setColor(Color.GREEN);
		canvas.drawRect(start, bartop, end, barbottom, paint);

		start = end;
		end = end + mBlue1*scalefactor;
		paint.setColor(Color.BLUE);
		canvas.drawRect(start, bartop, end, barbottom, paint);

		start = end;
		end = end + mWhite1*scalefactor;
		paint.setColor(Color.WHITE);
		canvas.drawRect(start, bartop, end, barbottom, paint);

		start = end;
		end = end + mPurple1*scalefactor;
		paint.setColor(purpleColor);
		canvas.drawRect(start, bartop, end, barbottom, paint);

        //DRAW BOTTOM BAR-------------------------------------
        int bartop2 = 30;
        int barbottom2 = 45;

        start = 5;
        end = start + mRed2*scalefactor;
        paint.setStrokeWidth(0);
        paint.setColor(Color.RED);
        canvas.drawRect(start, bartop2, end, barbottom2, paint);

        start = end;
        end = end + mOrange2*scalefactor;
        paint.setColor(orangeColor);
        canvas.drawRect(start, bartop2, end, barbottom2, paint);

        start = end;
        end = end + mYellow2*scalefactor;
        paint.setColor(Color.YELLOW);
        canvas.drawRect(start, bartop2, end, barbottom2, paint);

        start = end;
        end = end + mGreen2*scalefactor;
        paint.setColor(Color.GREEN);
        canvas.drawRect(start, bartop2, end, barbottom2, paint);

        start = end;
        end = end + mBlue2*scalefactor;
        paint.setColor(Color.BLUE);
        canvas.drawRect(start, bartop2, end, barbottom2, paint);

        start = end;
        end = end + mWhite2*scalefactor;
        paint.setColor(Color.WHITE);
        canvas.drawRect(start, bartop2, end, barbottom2, paint);

        start = end;
        end = end + mPurple2*scalefactor;
        paint.setColor(purpleColor);
        canvas.drawRect(start, bartop2, end, barbottom2, paint);
	}

}