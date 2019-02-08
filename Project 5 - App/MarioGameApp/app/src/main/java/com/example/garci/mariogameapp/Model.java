package com.example.garci.mariogameapp;

import java.util.ArrayList;

public class Model
{
    //Array List of Sprites
    ArrayList<Sprite> sprites;

    //Scrolling
    int scrollPos = 0;

    //Instances
    Mario mario;
    Brick brick;
    Brick brick2;
    Brick brick3;
    Brick brick4;
    Brick brick5;
    Brick brick6;
    Brick brick7;
    Brick brick8;

    //Constructor
    Model()
    {
        mario = new Mario(this);
        brick7 = new Brick(this, 650, 1100);
        brick6 = new Brick(this, 800, 1000);
        brick5 = new Brick(this, 950, 900);
        brick4 = new Brick(this, 1100, 800);
        brick = new Brick(this, 1250, 700);
        brick2 = new Brick(this, 1400, 600);
        brick3 = new Brick(this, 2000, 900);
        brick8 = new Brick(this, 2150, 900);


        sprites = new ArrayList<Sprite>();
        sprites.add(mario);
        sprites.add(brick);
        sprites.add(brick2);
        sprites.add(brick3);
        sprites.add(brick4);
        sprites.add(brick5);
        sprites.add(brick6);
        sprites.add(brick7);
        sprites.add(brick8);

    }

    void update()
    {

        for(int i = 0; i < sprites.size(); i++)
        {
            Sprite s = sprites.get(i);
            s.update();

            //Collision Detection
            if(mario.doesCollide(s.x, s.y, s.w, s.h))
            {
                //if mario collides with a brick
                if(s.isBrick())
                {
                    //makes mario not collide with brick
                    mario.getOut(s.x, s.y, s.w, s.h);
                }
            }

        }

    }
}