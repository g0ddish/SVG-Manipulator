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
        Frame frame = controller.frame();

        /*System.out.println("Frame id: " + frame.id()
                + ", timestamp: " + frame.timestamp()
                + ", hands: " + frame.hands().count()
                + ", fingers: " + frame.fingers().count()
                + ", tools: " + frame.tools().count()
                + ", gestures " + frame.gestures().count());

                     if(frame.finger(1).isExtended() || frame.finger(0).isExtended() || frame.finger(2).isExtended() || frame.finger(3).isExtended() || frame.finger(4).isExtended())
        {
            System.out.println("Don't point at me.");
        }
        */

       // frame.hand().
        FingerList extended = frame.fingers().extended();
        for(Finger pointer : extended){
            System.out.println(pointer.length());

        }


    }
}
