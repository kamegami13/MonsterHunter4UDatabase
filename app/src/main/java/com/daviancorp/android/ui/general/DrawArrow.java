package com.daviancorp.android.ui.general;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.daviancorp.android.mh4udatabase.R;

/**
 * Created by Mark on 2/23/2015.
 */
public class DrawArrow extends View {

    private Paint arrowPaint;
    private Path arrowPath;
    private int arrowColor = R.color.text_color;
    private float density;
    private int diameter = 2, diameter_calc, radius_calc;

    public DrawArrow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public DrawArrow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public DrawArrow(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {

        //Getting density "dp"
        density = getContext().getResources().getDisplayMetrics().scaledDensity;
        //Calculating actual diameter
        diameter_calc = (int) density * diameter;
        radius_calc = diameter/2;

        //Creating paint
        arrowPaint = new Paint();
        arrowPaint.setAntiAlias(true);
        arrowPaint.setColor(arrowColor);

        //Initialize path
        arrowPath = new Path();

        this.setWillNotDraw(false);
    }

    private int startX,startY, currentX, currentY;

    protected void onDraw(Canvas c) {

        startX = c.getWidth()/2;
        startY = c.getHeight()/2;

        c.rotate(-45, startX, startY);

        arrowPath.reset();
        currentX = startX;
        currentY = startY;
        //Move to right end side center of the canvas
        arrowPath.moveTo(currentX,currentY);
        //Lets move up
        currentY = radius_calc;
        arrowPath.lineTo(currentX, currentY);
        //Now draw circle
        currentX-=radius_calc;
        arrowPath.addCircle(currentX, radius_calc, radius_calc, Path.Direction.CCW);
        currentX-=radius_calc;

        arrowPath.lineTo(currentX,currentY);
        // Go to inner side center point
        currentX = startX - diameter_calc;
        currentY = startY - diameter_calc;
        arrowPath.lineTo(currentX,currentY);
        // Go left
        currentX = startX - startY + radius_calc;
        arrowPath.lineTo(currentX, currentY);
        //Draw circle
        currentY+=radius_calc;
        c.drawCircle(currentX, currentY, radius_calc, arrowPaint);
        currentY+=radius_calc;
        arrowPath.lineTo(currentX, currentY);
        //Go to start
        arrowPath.lineTo(startX, startY);

        c.drawPath(arrowPath, arrowPaint);
    }
}
