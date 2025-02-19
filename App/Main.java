package ro.tuc;

import GUI.View;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new View("Queues management application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}