import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class CustomUnitGUI extends JPanel {
    private JButton cancelButton;
    private JButton confirmButton;
    private JLabel unitTypeLabel;
    private JLabel attackLabel;
    private JLabel defenseLabel;
    private JLabel hpLabel;
    private JComboBox unitTypeComboBox;
    private JSlider hpSlider;
    private JSlider atkSlider;
    private JSlider defSlider;

    private JFrame thisFrame;
    
	private Object obj;
    
    public CustomUnitGUI(JFrame frame, Object obj) {
    	this.thisFrame = frame;
    	this.obj = obj;
        //construct preComponents
        String[] unitTypeComboBoxItems = {"Swordsman", "Pikeman", "Knight", "Archer"};

        //construct components
        cancelButton = new JButton ("Cancel");
        confirmButton = new JButton ("Confirm");
        unitTypeLabel = new JLabel ("Unit Type:");
        attackLabel = new JLabel ("Attack:");
        defenseLabel = new JLabel ("Defense:");
        hpLabel = new JLabel ("HP:");
        unitTypeComboBox = new JComboBox (unitTypeComboBoxItems);
        hpSlider = new JSlider (10, 60);
        atkSlider = new JSlider (1, 15);
        defSlider = new JSlider (1, 15);

        //set components properties
        hpSlider.setOrientation (JSlider.HORIZONTAL);
        hpSlider.setMinorTickSpacing (2);
        hpSlider.setMajorTickSpacing (10);
        hpSlider.setPaintTicks (true);
        hpSlider.setPaintLabels (true);
        atkSlider.setOrientation (JSlider.HORIZONTAL);
        atkSlider.setMinorTickSpacing (1);
        atkSlider.setMajorTickSpacing (2);
        atkSlider.setPaintTicks (true);
        atkSlider.setPaintLabels (true);
        defSlider.setOrientation (JSlider.HORIZONTAL);
        defSlider.setMinorTickSpacing (1);
        defSlider.setMajorTickSpacing (2);
        defSlider.setPaintTicks (true);
        defSlider.setPaintLabels (true);

        //JButton action listeners
        confirmButton.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) {
        		  Unit temp = new Unit(unitTypeComboBox.getSelectedIndex(),hpSlider.getValue(), atkSlider.getValue(), defSlider.getValue());
        		  temp.setOwner("Player");
        		  addUnit(temp);
        		  printLog("UNIT CREATED: " + temp.getTypeName() + " - HP: " + temp.getMaxHP() + " ATK: " + temp.getAttack() + " DEF: " + temp.getDefence() + "\n" );
        		  thisFrame.dispose();
        	  }
        });
        
        cancelButton.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) {
      		  thisFrame.dispose();
      	  } 
        });
        
        //adjust size and set layout
        setPreferredSize (new Dimension (345, 319));
        setLayout (null);

        //add components
        add (cancelButton);
        add (confirmButton);
        add (unitTypeLabel);
        add (attackLabel);
        add (defenseLabel);
        add (hpLabel);
        add (unitTypeComboBox);
        add (hpSlider);
        add (atkSlider);
        add (defSlider);

        //set component bounds (only needed by Absolute Positioning)
        cancelButton.setBounds (40, 265, 100, 30);
        confirmButton.setBounds (200, 265, 100, 30);
        unitTypeLabel.setBounds (40, 35, 100, 25);
        attackLabel.setBounds (40, 130, 100, 25);
        defenseLabel.setBounds (40, 180, 100, 25);
        hpLabel.setBounds (40, 80, 100, 25);
        unitTypeComboBox.setBounds (150, 35, 165, 25);
        hpSlider.setBounds (145, 80, 175, 45);
        atkSlider.setBounds (145, 135, 175, 45);
        defSlider.setBounds (145, 190, 175, 45);
    }
    
    private void addUnit(Unit unit){
		((SandBoxGUI) obj).addCustomUnit(unit);	
    }
	// ONLY FOR USE WITH SANDBOX GUI
	private void printLog(String text){
		System.out.print(text);
		((SandBoxGUI) obj).addTextToLog(text);
	}
}
