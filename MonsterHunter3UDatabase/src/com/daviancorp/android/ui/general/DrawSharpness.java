package com.daviancorp.android.ui.general;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawSharpness extends View {

	private int mRed;
	private int mOrange;
	private int mYellow;
	private int mGreen;
	private int mBlue;
	private int mWhite;
	private int mPurple;

	int orangeColor = Color.rgb(255, 150, 0);
	int purpleColor = Color.rgb(120, 81, 169);

	Paint paint = new Paint();

	public DrawSharpness(Context context) {
		super(context);
	}

	public DrawSharpness(Context context, int red, int orange, int yellow,
			int green, int blue, int white, int purple) {
		super(context);
		mRed = red;
		mOrange = orange;
		mYellow = yellow;
		mGreen = green;
		mBlue = blue;
		mWhite = white;
		mPurple = purple;
	}

	@Override
	public void onDraw(Canvas canvas) {
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(4);
		canvas.drawRect(8, 8, 600, 52, paint);
		int start = 10;
		int end = start + mRed;
		paint.setStrokeWidth(0);
		paint.setColor(Color.RED);
		canvas.drawRect(start, 10, end, 50, paint);
		start = end;
		end = end + mOrange;
		paint.setColor(orangeColor);
		canvas.drawRect(start, 10, end, 50, paint);
		start = end;
		end = end + mYellow;
		paint.setColor(Color.YELLOW);
		canvas.drawRect(start, 10, end, 50, paint);
		start = end;
		end = end + mGreen;
		paint.setColor(Color.GREEN);
		canvas.drawRect(start, 10, end, 50, paint);
		start = end;
		end = end + mBlue;
		paint.setColor(Color.BLUE);
		canvas.drawRect(start, 10, end, 50, paint);
		start = end;
		end = end + mWhite;
		paint.setColor(Color.WHITE);
		canvas.drawRect(start, 10, end, 50, paint);
		start = end;
		end = end + mPurple;
		paint.setColor(purpleColor);
		canvas.drawRect(start, 10, end, 50, paint);
	}

}