package com.solutionblender.svgmanipulator;

import com.leapmotion.leap.*;

/**
 * Created by Alex on 2/28/2015.
 */
public class SampleListener extends Listener  {

    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }

    public void onFrame(Controller controller) {
        System.out.println("Frame available");
    }
}
