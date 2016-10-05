import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class SandBoxGUI extends JPanel {
    private JLabel jcomp1;
    private JLabel jcomp2;
    private JTextArea jcomp3;
    private JButton jcomp4;
    private JList jcomp5;
    private JTextArea jcomp6;
    private JTextArea jcomp7;
    private JTextArea jcomp8;

    public SandBoxGUI() {
        //construct preComponents
        String[] jcomp5Items = {"Item 1", "Item 2", "Item 3"};

        //construct components
        jcomp1 = new JLabel ("Turns Left");
        jcomp2 = new JLabel ("Turn Timer");
        jcomp3 = new JTextArea (5, 5);
        jcomp4 = new JButton ("End Turn");
        jcomp5 = new JList (jcomp5Items);
        jcomp6 = new JTextArea (5, 5);
        jcomp7 = new JTextArea (5, 5);
        jcomp8 = new JTextArea (5, 5);

        //adjust size and set layout
        setPreferredSize (new Dimension (726, 521));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);
        add (jcomp6);
        add (jcomp7);
        add (jcomp8);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (25, 40, 100, 25);
        jcomp2.setBounds (605, 40, 100, 25);
        jcomp3.setBounds (25, 250, 175, 250);
        jcomp4.setBounds (595, 435, 105, 60);
        jcomp5.setBounds (220, 425, 350, 70);
        jcomp6.setBounds (160, 15, 405, 70);
        jcomp7.setBounds (25, 100, 100, 140);
        jcomp8.setBounds (605, 90, 100, 140);
    }


    public static void main (String[] args) {
        JFrame frame = new JFrame ("SandBoxGUI");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new SandBoxGUI());
        frame.pack();
        frame.setVisible (true);
    }
}
