package com.example.garci.mariogameapp;

public abstract class Sprite
{
    Model model;

    //Variables
    int x;
    int y;
    int w;
    int h;

    double vertVel;

    //abstract methods
    abstract void update();

    //Constructor
    Sprite(Model m)
    {
        model = m;
    }

    //AM I A Brick
    boolean isBrick()
    {
        return false;
    }

    //AM I A Mario
    boolean isMario()
    {
        return false;
    }


}