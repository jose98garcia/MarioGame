package com.example.garci.mariogameapp;

import android.util.Log;
import android.view.MotionEvent;

public class GameController implements Runnable
{
    volatile boolean playing;
    Thread gameThread = null;
    Model model;
    GameView view;

    boolean right;
    boolean left;

    GameController(Model m, GameView v)
    {
        model = m;
        view = v;
        view.setController(this);
        playing = true;

        right = false;
        left = false;
    }

    void update()
    {
        model.mario.rememberPos();

    }

    @Override
    public void run()
    {
        while(playing)
        {
            //long time = System.currentTimeMillis();
            this.update();
            model.update();
            view.update();

            try {
                Thread.sleep(20);
            } catch(Exception e) {
                Log.e("Error:", "sleeping");
                System.exit(1);
            }
        }
    }

    void onTouchEvent(MotionEvent motionEvent)
    {
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        model.mario.rememberPos();


        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: // Player touched the screen

                //mario scrolls right touch
                if(x > 540 && y > 1000)
                {
                    model.mario.rightScroll = true;
                    model.mario.recently_leftScroll = false;
                }

                //mario scrolls left touch
                else if (x < 540 & y > 1000)
                {
                    model.mario.leftScroll = true;
                    model.mario.recently_rightScroll = false;
                }

                //mario jumps touch
                else if (y < 1000)
                {
                    model.mario.jump();
                }



                break;

            case MotionEvent.ACTION_UP: // Player withdrew finger

                break;
        }
    }

    // Shut down the game thread.
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
            System.exit(1);
        }

    }

    // Restart the game thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}