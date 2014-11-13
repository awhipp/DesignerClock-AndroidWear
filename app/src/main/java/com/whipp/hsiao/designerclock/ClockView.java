package com.whipp.hsiao.designerclock;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ClockView extends View{
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int hours;
    private int mins;
    private int secs;
    private int color;

    private int x = 320/2;
    private int y = 320/2;

    final int r = 150;

    int line_color = Color.BLACK;
    int[] colors = {
            Color.rgb(1,0,101),
            Color.rgb(14,1,107),
            Color.rgb(29,3,114),
            Color.rgb(45,4,121),
            Color.rgb(63,5,127),
            Color.rgb(82,6,134),
            Color.rgb(103,8,141),
            Color.rgb(124,10,147),
            Color.rgb(147,11,154),
            Color.rgb(161,13,150),
            Color.rgb(167,15,138),
            Color.rgb(174,17,125),
            Color.rgb(181,19,111),
            Color.rgb(188,22,96),
            Color.rgb(194,24,81),
            Color.rgb(201,26,64),
            Color.rgb(208,29,46),
            Color.rgb(214,36,32),
            Color.rgb(221,62,35),
            Color.rgb(228,88,38),
            Color.rgb(234,115,41),
            Color.rgb(241,143,44),
            Color.rgb(248,172,47),
            Color.rgb(255,202,50)
    };

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void updateView(int hours, int mins, int secs, boolean is_AM){
        if(is_AM) {
            this.color = colors[hours * 2 + mins / 30];
        }else{
            this.color = colors[23-((hours%12)*2 + mins / 30)];
        }
        if(hours > 9 && hours < 15)
            this.line_color = Color.BLACK;
        else
            this.line_color = Color.WHITE;
        this.hours = hours;
        this.mins = mins;
        this.secs = secs;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /* #FF9933 to #000099 */
        paint.setColor(this.color);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(0, 0, 320, 320, paint);

        float sec=(float)secs;
        float min=(float)mins;
        float hour=(float)hours+mins/60.0f;
        paint.setColor(this.line_color);
        paint.setStrokeWidth(2.5f);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(320/2,320/2,r,paint);
        /* Hour Hand */
        canvas.drawLine(x, y, (float)(x+(r/2-15)*Math.cos(Math.toRadians((hour / 12.0f * 360.0f)-90f))), (float)(y+(r/2-10)*Math.sin(Math.toRadians((hour / 12.0f * 360.0f)-90f))), paint);
        canvas.save();
        /* Minute */
        canvas.drawLine(x, y, (float)(x+(r/5*4)*Math.cos(Math.toRadians((min / 60.0f * 360.0f)-90f))), (float)(y+(r/5*4)*Math.sin(Math.toRadians((min / 60.0f * 360.0f)-90f))), paint);
        canvas.save();
        /* Second */
        paint.setStrokeWidth(1f);
        canvas.drawLine(x, y, (float)(x+(r/4*3)*Math.cos(Math.toRadians((sec / 60.0f * 360.0f)-90f))), (float)(y+(r/4*3)*Math.sin(Math.toRadians((sec / 60.0f * 360.0f)-90f))), paint);

    }
}
