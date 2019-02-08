package com.example.garci.mariogameapp;

public class Brick extends Sprite
{

    //Constructor
    Brick(Model m, int xx, int yy)
    {
        super(m);

        x = xx;
        y = yy;
        w = 150;
        h = 144;

        vertVel = 0;

    }

    boolean isBrick()
    {
        return true;
    }


    void update()
    {

    }
}
