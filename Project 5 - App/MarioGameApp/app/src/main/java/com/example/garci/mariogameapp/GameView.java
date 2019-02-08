package com.example.garci.mariogameapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView
{
    SurfaceHolder ourHolder;
    Canvas canvas;
    Paint paint;
    Model model;
    GameController controller;


    Bitmap[] marioImages = new Bitmap[5];
    Bitmap[] resize = new Bitmap[5];
    Bitmap[] marioLeftImages = new Bitmap[5];
    Bitmap[] resizeL = new Bitmap[5];
    Bitmap brick;
    Bitmap resizeBrick;
    Bitmap backgroundImg;
    Bitmap resizeBackground;

    public GameView(Context context, Model m)
    {
        super(context);
        model = m;

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        paint = new Paint();

        // Loads the right mario images
        marioImages[0] = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.mario1);
        marioImages[1] = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.mario2);
        marioImages[2] = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.mario3);
        marioImages[3] = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.mario4);
        marioImages[4] = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.mario5);

        // Loads the left mario images
        marioLeftImages[0] = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.mariol1);
        marioLeftImages[1] = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.mariol2);
        marioLeftImages[2] = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.mariol3);
        marioLeftImages[3] = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.mariol4);
        marioLeftImages[4] = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.mariol5);

        // Loads brick image
        brick = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.brick);

        // Loads the background image
        backgroundImg =  BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.wallpaper2);

        // Resizes Right Mario Images
        for(int i = 0; i < 5; i++)
        {
            resize[i] = Bitmap.createScaledBitmap(marioImages[i], model.mario.w, model.mario.h, false);
        }

        // Resizes Left Mario Images
        for(int i = 0; i < 5; i++)
        {
            resizeL[i] = Bitmap.createScaledBitmap(marioLeftImages[i], model.mario.w, model.mario.h, false);
        }


        //Resizes Brick
        resizeBrick = Bitmap.createScaledBitmap(brick, model.brick.w, model.brick.h, false);

        //Resizes Background
        resizeBackground = Bitmap.createScaledBitmap(backgroundImg, 3500, 1600, false);

    }

    void setController(GameController c)
    {
        controller = c;
    }

    public void update()
    {
        if (!ourHolder.getSurface().isValid())
            return;
        canvas = ourHolder.lockCanvas();

        //Background Image
        canvas.drawBitmap(resizeBackground, -200 - model.scrollPos/4, 0, paint);

        //Draws Ground Line
        paint.setColor(Color.BLACK);
        canvas.drawLine(0,1600,2000,1600,paint);

        //-----------------------------------------------------------------------------------------//
        //DRAWS CONTROLLER QUADRANTS

        canvas.drawLine(540, 1000, 540, 2000, paint); //draws horiz line
        paint.setTextSize(45);
        canvas.drawText("Left", 25 , 1075, paint);

        canvas.drawLine(0, 1000, 2000, 1000, paint); //draws vert line
        canvas.drawText("Right", 565, 1075, paint);

        canvas.drawText("Jump", 25, 65, paint);

        //------------------------------------------------------------------------------------------//


        // Draw Animated Mario
        int marioFrame = ((int)Math.abs(model.mario.x) / 20) % 5;

        //draws right scrolling mario
        if(model.mario.recently_rightScroll)
        {
            canvas.drawBitmap(resize[marioFrame], model.mario.x - model.scrollPos , model.mario.y, paint);
        }

        //draws left scrolling mario
        else if (model.mario.recently_leftScroll)
        {
            canvas.drawBitmap(resizeL[marioFrame], model.mario.x - model.scrollPos, model.mario.y, paint);
        }


        //Draws Bricks
        for(int i = 0; i < model.sprites.size(); i++)
        {
            Sprite b = model.sprites.get(i);

            if(b.isBrick())
            {
                canvas.drawBitmap(resizeBrick, b.x - model.scrollPos, b.y, paint);
            }
        }

        ourHolder.unlockCanvasAndPost(canvas);
    }

    // The SurfaceView class (which GameView extends) already
    // implements onTouchListener, so we override this method
    // and pass the event to the controller.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        controller.onTouchEvent(motionEvent);
        return true;
    }
}