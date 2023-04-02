package org.k4ng.aircraftwar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class AircraftWar extends View
{
    private Context ctx;
    private String TAG = "K4NG";

    private Boolean isDown = false;

    // 屏幕宽高
    private int sw;
    private int sh;

    // 步长值
    private int step = 0;
    private int A_step = 0;
    private int B_step = -sh;

    // 飞机坐标
    private float x = 0;
    private float y = 0;

    // 飞机图片宽高
    private int aw = 0;
    private int ah = 0;

    public AircraftWar(Context context)
    {
        super(context);
        this.ctx = context;
        InitScreenWS();
        InitSpeed(1);
        PlayMusic(ctx);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "DOWN");
                isDown = false;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "MOVE");
                x = event.getX() - (aw / 2);
                y = event.getY() - ah;
                isDown = true;
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "UP");
                isDown = false;
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        String tmp = String.format("A_step: %d, B_step: %d, sh: %d", A_step, B_step, sh);
        // Log.i(TAG, tmp);

        // 绘制背景
        DrawBackGround(canvas);

        // 绘制飞机
        DrawAircraft(canvas);

        // 卷屏A
        A_step += step;
        if(A_step >= sh)
            A_step = 0;

        // 卷屏B
        B_step += step;
        if(B_step >= 0)
            B_step = -sh;

        postInvalidate();
    }


    private void InitScreenWS()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)ctx).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        sw = metrics.widthPixels;
        sh = metrics.heightPixels;
        Log.i(TAG, String.format("widthPixels:%d heightPixels:%d", sw, sh));
    }

    private void InitSpeed(int leve)
    {
        switch(leve)
        {
            case 1:
                step = sh / 40;
                break;

            case 2:
                step = sh / 30;
                break;

            case 3:
                step = sh / 20;
                break;

            case 4:
                step = sh / 10;
                break;

            default:
                break;
        }
    }


    // 背景
    private void DrawBackGround(Canvas canvas)
    {
        Paint paint = new Paint();
        Bitmap bitmapA = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.background);
        int bg_w = bitmapA.getWidth();
        int bg_h = bitmapA.getHeight();
        Rect src = new Rect(0, 0, bg_w, bg_h);
        Rect dstA = new Rect(0, A_step, sw, sh + A_step);
        canvas.drawBitmap(bitmapA, src, dstA, paint);

        Bitmap bitmapB = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.background);
        Rect dstB = new Rect(0, A_step - sh, sw, A_step);
        canvas.drawBitmap(bitmapB, src, dstB, paint);
    }

    // 飞机
    private void DrawAircraft(Canvas canvas)
    {
        Paint paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.me1);
        aw = bitmap.getWidth();
        ah = bitmap.getHeight();
        canvas.drawBitmap(bitmap, x, y, paint);
    }

    private void PlayMusic(Context ctx)
    {
        MediaPlayer mediaPlayer = MediaPlayer.create(ctx, R.raw.bg_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
}
