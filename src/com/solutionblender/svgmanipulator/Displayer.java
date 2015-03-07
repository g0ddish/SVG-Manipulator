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
    private ArrayList<String> svgs;
   public void setup(){
       size(1200, 500);
        background(255);
       //svg1 = loadShape("Map.svg");

       File folder = new File(System.getProperty("user.dir"));
       File[] listOfFiles = folder.listFiles();
        svgs = new ArrayList<String>();
       for (int i = 0; i < listOfFiles.length; i++) {
           if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".svg")) {
               System.out.println("File " + listOfFiles[i].getAbsolutePath());
               svgs.add(listOfFiles[i].getAbsolutePath());
           } else if (listOfFiles[i].isDirectory()) {
               //System.out.println("Directory " + listOfFiles[i].getName());
           }
       }
        // ...
       svg1 = loadShape(svgs.get(1));
        leap = new LeapMotion(this).withGestures();
       leap.setGestureSwipeMinLength(400);
       //leap.setGestureSwipeMinVelocity(1000);

    }

   public void draw() {
       background(255);
       // ...


       // ========= HANDS =========

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
           if(scaleAmount < 2) {
               if (transAmountX > 1100) {
                   transAmountX = 1100;
               }
               if (transAmountX < 0 - 100) {
                   transAmountX = 0 - 100;
               }
               if (transAmountY > height - 100) {
                   transAmountY = height - 100;
               }
               if (transAmountY < 0 - 100) {
                   transAmountY = 0 - 100;
               }
           }
           //case statement instead

           if(leap.getFrontHand().getPosition().x < 200){
               transAmountX = transAmountX - 5;
               //System.out.println(leap.getLeftHand().getPosition().x);
           }

           if(leap.getFrontHand().getPosition().x > 1200){
               transAmountX = transAmountX + 5;
               //System.out.println(leap.getRightHand().getPosition().x);
           }
           if(leap.getFrontHand().getPosition().y > 150){
               transAmountY = transAmountY + 5;
               //System.out.println(leap.getFrontHand().getPosition().y);
           }
           if(leap.getFrontHand().getPosition().y < 400){
               transAmountY = transAmountY - 5;
               //System.out.println(leap.getFrontHand().getPosition().y);
           }

           if(leap.getFrontHand().getPosition().z > 60){
               if(delay > 60) {
                   delay = 0;
                   if (viewIndex == (svgs.size())) {
                       viewIndex = 0;
                   }
                   svg1 = loadShape(svgs.get(viewIndex));
                   viewIndex = viewIndex + 1;

                   System.out.println(leap.getFrontHand().getPosition().z);
               }
           }

       }

       delay = delay + 1;

    }
    private int viewIndex = 1;
    private float transAmountX;
    private float transAmountY;
    private float scaleAmount = 0f;
    private float delay = 0f;

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
