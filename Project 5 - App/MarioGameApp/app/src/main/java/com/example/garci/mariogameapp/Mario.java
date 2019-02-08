package com.example.garci.mariogameapp;

public class Mario extends Sprite
{

    //previous positions
    int prevX;
    int prevY;


    int frameSinceOnGround;

    boolean rightScroll = true;
    boolean recently_rightScroll = true;

    boolean leftScroll = false;
    boolean recently_leftScroll = false;

    //Remember position
    void rememberPos()
    {
        prevX = x;
        prevY = y;
    }

    //Constructor
    Mario(Model m)
    {
        super(m);

        frameSinceOnGround = 0;
        vertVel = 0;
        w = 150;
        h = 237;


    }

    void update()
    {
        //scroll position goes with mario
        model.scrollPos = x - 475;

        if(rightScroll)
        {
            x += 15;
            rightScroll = false;
            recently_rightScroll = true;
        }

        else if(leftScroll)
        {
            x -= 15;
            leftScroll = false;
            recently_leftScroll = true;
        }

        //gravity
        vertVel += 3.14;
        y += vertVel;

        //Makes Ground Stop Mario From Falling
        if(y > 1180)
        {
            y = 1180;
            vertVel = 0.0;
            frameSinceOnGround = 0;

        }

        frameSinceOnGround++;


    }

    //Mario Jumps
    void jump()
    {
        if(frameSinceOnGround < 4)
        {
            vertVel += -25;
        }
    }


    //What AM I?
    boolean isMario()
    {
        return true;
    }

    //Does Mario Collide
    boolean doesCollide(int xx, int yy, int ww, int hh)
    {
        //detects collision
        if(x + w <= xx)
            return false;
        else if(x >= xx + ww)
            return false;
        else if (y + h <= yy)
            return false;
        else if (y >= yy + hh)
            return false;

        return true;
    }

    //Gets Mario out of collison
    void getOut(int xx, int yy, int ww, int hh)
    {
        if(x + w >= xx && prevX + w <= xx) //if coming in from left
        {
            x = xx - w;
            //System.out.println("Working -- Left Side");
        }

        else if(x < xx + ww && prevX >= xx + ww) //coming in from right
        {
            x = xx + ww;
            //System.out.println ("Working -- Right Side");
        }


        else if(y + h > yy && prevY + h <= yy) //coming in from above
        {
            y = yy - h;
            vertVel = 0;
            frameSinceOnGround = 0;
            //System.out.println ("Working -- Top Side");
        }

        else if(y < yy + hh && prevY >= yy + hh) //coming in from below
        {
            y = yy + hh;
            vertVel = 0;
            //System.out.println("Working -- Bottom Side");
        }
    }

}
