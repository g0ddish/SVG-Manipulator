package com.solutionblender.svgmanipulator;

import javax.swing.*;
import com.leapmotion.leap.*;
import java.awt.event.*;

/**
 * Created by Alex on 2/28/2015.
 */
public class MainMenu {

    private static Controller controller;
    private static SampleListener listener;
    public static void main(String[] args) {
        JFrame frame = new JFrame("MainMenu");
        frame.setContentPane(new MainMenu().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        listener = new SampleListener();
        controller = new Controller();

        // Have the sample listener receive events from the controller
        controller.addListener(listener);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controller.removeListener(listener);
            }
        });
    }

    private JPanel MainPanel;
    private JLabel HeadLabel;
}
