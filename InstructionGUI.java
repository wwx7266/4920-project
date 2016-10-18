import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class InstructionGUI extends JPanel {
    private JButton jcomp1;
    private JList jcomp2;
    private JLabel jcomp3;

    public InstructionGUI() {
        //construct preComponents
        String[] jcomp2Items = {"Item 1", "Item 2", "Item 3"};

        //construct components
        jcomp1 = new JButton ("Back");
        jcomp2 = new JList (jcomp2Items);
        jcomp3 = new JLabel ("Friends List");

        //adjust size and set layout
        setPreferredSize (new Dimension (376, 790));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (140, 735, 100, 20);
        jcomp2.setBounds (50, 100, 275, 600);
        jcomp3.setBounds (145, 45, 100, 25);
    }


    public static void main (String[] args) {
        JFrame frame = new JFrame ("Friends List");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new InstructionGUI());
        frame.pack();
        frame.setVisible (true);
    }
}
