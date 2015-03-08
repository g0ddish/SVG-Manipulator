package com.solutionblender.svgmanipulator;

import de.voidplus.leapmotion.*;
import de.voidplus.leapmotion.SwipeGesture;
import de.voidplus.leapmotion.Finger;
import de.voidplus.leapmotion.Hand;
import processing.core.*;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Alex on 3/1/2015.
 */
public class Displayer extends PApplet {
    
   public LeapMotion leap;
    private PShape svg1;
    private PImage image;
    private ArrayList<String> svgs;
    private ArrayList<PShape> pshapes;
    private ArrayList<String> img;

   public void setup(){
       //frameRate(15);
       size(Math.round(sizeX), Math.round(sizeY), P2D);
        background(255);
       //svg1 = loadShape("Map.svg");

       File folder = new File(System.getProperty("user.dir"));
       File[] listOfFiles = folder.listFiles();
        svgs = new ArrayList<String>();
        pshapes = new ArrayList<PShape>();
       img = new ArrayList<String>();

       for (int i = 0; i < listOfFiles.length; i++) {
           if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".svg")) {
               System.out.println("File " + listOfFiles[i].getAbsolutePath());
               svgs.add(listOfFiles[i].getAbsolutePath());
               PShape fuckery = loadShape(listOfFiles[i].getAbsolutePath());
               if(fuckery != null){
                   pshapes.add(fuckery);
               }


           } else if (listOfFiles[i].isDirectory()) {
               //System.out.println("Directory " + listOfFiles[i].getName());
           } else if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".jpg")){
              //System.out.println("File " + listOfFiles[i].getAbsolutePath());
               img.add(listOfFiles[i].getAbsolutePath());

           }
       }
        // ...
       svg1 = pshapes.get(1);

       image = loadImage(img.get(1));
       leap = new LeapMotion(this).withGestures();
       leap.setGestureSwipeMinLength(400);
       //leap.setGestureSwipeMinVelocity(1000);

    }

   public void draw() {
       background(255);
       // ...
;


       // ========= HANDS =========
pushMatrix();
       shapeMode(CORNER);
       for (Hand hand : leap.getHands()) {
           hand.draw();

       }

       if (scaleAmount < 0.5) {            scaleAmount = 0.5f;        }
     // shapeMode(CENTER);
        translate(transAmountX, transAmountY);
        scale(scaleAmount);

       shape(svg1,0,0);



       if (leap.getHands().size() == 2){
          checkHandDistanceZoomOut(leap.getRightHand(), leap.getLeftHand());
           checkHandDistanceZoomIn(leap.getRightHand(), leap.getLeftHand());


    }

       if (leap.getHands().size() == 1){
           //System.out.println(scaleAmount);
           if (transAmountX > sizeX) {
               transAmountX = sizeX;
           }
           if (transAmountY > sizeY) {
               transAmountY = sizeY;
           }
           if (transAmountX < 0 - svg1.getWidth()*scaleAmount) {
               transAmountX = 0 - svg1.getWidth()*scaleAmount;
           }
           if (transAmountY < 0 - svg1.getHeight()*scaleAmount) {
               transAmountY = 0 - svg1.getHeight()*scaleAmount;
           }

            Float frontHandPosX = leap.getFrontHand().getPosition().x;
            Float frontHandPosY = leap.getFrontHand().getPosition().y;

           if(frontHandPosX != null && frontHandPosX < 200){
               transAmountX = transAmountX - 5;
               //System.out.println(leap.getLeftHand().getPosition().x);
           }

           if(frontHandPosX != null && frontHandPosX > 1200){
               transAmountX = transAmountX + 5;
               //System.out.println(leap.getRightHand().getPosition().x);
           }
           if(frontHandPosY != null && frontHandPosY > 250){
               transAmountY = transAmountY + 5; // moves up
               //System.out.println(leap.getFrontHand().getPosition().y);
           }
           if(frontHandPosY != null && frontHandPosY < 500){
               transAmountY = transAmountY - 5; // moves down
               //System.out.println(leap.getFrontHand().getPosition().y);
           }

           //Switching SVGs when touching forward

           if(leap.getFrontHand().getPosition().z > 60){
               if(delay > 60) {
                   delay = 0;
                   if (viewIndex == (pshapes.size())) {
                       viewIndex = 0;
                   }
                   svg1 = pshapes.get(viewIndex);
                   image = loadImage(img.get(viewIndex));
                   viewIndex = viewIndex + 1;

                   //System.out.println(leap.getFrontHand().getPosition().z);
               }
           }

       }


       popMatrix();


       delay = delay + 1;
       pushMatrix();

       //MiniMap

       //translate(0,0);
       //shapeMode(CENTER);
       //svg1Width = (svg1.getWidth()/1200)*100;
       //svg1Height = (svg1.getHeight()/800)*100;
       //svg1Width = map(svg1.getWidth(), 0, 100, 0, 216);
       //svg1Height = map(svg1.getHeight(), 0, 50, 0, 144);
        //scale(0.5f);
       image(image, sizeX - 216, sizeY-144);
       //System.out.println(scaleAmount);
       stroke(1);
       noFill();
       rect(sizeX-216, sizeY-144, 216, 144);


       precentX=transAmountX/sizeX;
       precentY=transAmountY/sizeY;
       miniX = map(precentX, 0, 1, sizeX-216, sizeX);
       miniY = map(precentY, 0, 1, sizeY-144, sizeY);
       //miniX = constrain(miniX, sizeX-200,sizeX);
       //scale(scaleAmount);
       noFill();
       //rectMode(CENTER);
       miniScaleX = map(scaleAmount, 0, 30, 216, 0);
       miniScaleY = map(scaleAmount, 0, 30, 144, 0);
       rect(miniX, miniY, miniScaleX, miniScaleY);

       //rectMode(CORNER);
       popMatrix();

    }
    private int viewIndex = 1;
    private float transAmountX=300;
    private float transAmountY;
    private float scaleAmount = 3f;
    private float delay = 0f;
    private float sizeX = 1200f;
    private float sizeY = 800f;
    private float precentX=0f;
    private float precentY=0f;
    private float miniX, miniY, miniScaleX, miniScaleY, svg1Width, svg1Height = 0f;

    private void checkHandDistanceZoomOut(Hand right, Hand left){
      //  System.out.println(right.getPosition().x - left.getPosition().x);
      float distance =  right.getPosition().x - left.getPosition().x;
        if(distance > 700 && distance < 800){
            scaleAmount = scaleAmount - 0.009f;
            //System.out.println("Correct distance apart! Commence Zooming out speed 1!");
        }else if(distance > 800 && distance < 900){
            scaleAmount = scaleAmount - 0.02f;
            //System.out.println("Correct distance apart! Commence Zooming out speed 2!");

        }else if(distance > 900){
            scaleAmount = scaleAmount - 0.05f;
            //System.out.println("Correct distance apart! Commence Zooming out speed 3!");

        }

    }
    private void checkHandDistanceZoomIn(Hand right, Hand left){
    //    System.out.println(right.getPosition().x - left.getPosition().x);
        float distance =  right.getPosition().x - left.getPosition().x;

        if(distance > 500 && distance < 600){
            scaleAmount = scaleAmount + 0.01f;
            //System.out.println("Correct distance apart! Commence Zooming in speed 1!");
        }else if(distance > 400 && distance < 500){
            scaleAmount = scaleAmount + 0.05f;
            //System.out.println("Correct distance apart! Commence Zooming in speed 2!");

        }else if(distance < 400){
            scaleAmount = scaleAmount + 0.10f;
            //System.out.println("Correct distance apart! Commence Zooming in speed 3!");

        }

    }



    // ----- SWIPE GESTURE -----

  public  void leapOnSwipeGesture(SwipeGesture g, int state){
        int     id                  = g.getId();
        Finger  finger              = g.getFinger();
        PVector position            = g.getPosition();
        PVector position_start      = g.getStartPosition();
        PVector direction           = g.getDirection();
        float   speed               = g.getSpeed();
        long    duration            = g.getDuration();
        float   duration_seconds    = g.getDurationInSeconds();

        switch(state){
            case 1: // Start
                break;
            case 2: // Update
                break;
            case 3: // Stop

                /*if(viewIndex == (svgs.size())){
                    viewIndex = 0;
                }
                svg1 = loadShape(svgs.get(viewIndex));
                viewIndex = viewIndex + 1;
*/
                println("SwipeGesture: "+id);


                break;
        }
    }

    // ----- CIRCLE GESTURE -----

    void leapOnCircleGesture(CircleGesture g, int state) {
        int id = g.getId();
        Finger finger = g.getFinger();
        PVector position_center = g.getCenter();
        float radius = g.getRadius();
        float progress = g.getProgress();
        long duration = g.getDuration();
        float duration_seconds = g.getDurationInSeconds();
        int direction = g.getDirection();

        switch (state) {
            case 1:  // Start
                break;
            case 2: // Update
                break;
            case 3: // Stop
                println("CircleGesture: " + id);

                break;
        }

        switch (direction) {
            case 0: // Anticlockwise/Left gesture
                break;
            case 1: // Clockwise/Right gesture
                break;
        }
    }


    void leapOnInit(){
        // println("Leap Motion Init");
    }
    void leapOnConnect(){
        // println("Leap Motion Connect");
    }
    void leapOnFrame(){
        // println("Leap Motion Frame");
    }
    void leapOnDisconnect(){
        // println("Leap Motion Disconnect");
    }
    void leapOnExit(){
        // println("Leap Motion Exit");
    }
}
