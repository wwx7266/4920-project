import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class Army {
	
	static public JPanel pl;
	private JButton armyButton;
	private String armyID;
	private UnitSummaryUI armyUnitDetail;
	
	private void setArmyID(String armyID) {
		this.armyID = armyID;
	}
	
	public JButton getArmyButton() {
		return armyButton;
	}
	
	Army(JPanel p, String armyID) {
		pl = p;
		setArmyID(armyID);
		armyButton = new JButton(armyID);
		armyUnitDetail = new UnitSummaryUI(pl);
		armyUnitDetail.setBounds(600, 600, 600, 600);
		
		armyButton.addActionListener(new armyListAction());
	}
	
	private class armyListAction implements ActionListener{
		public void actionPerformed(ActionEvent a){
			//System.out.println(armyID);
			pl.add(armyUnitDetail);
			((CardLayout) pl.getLayout()).next(pl);
		}
	}

}
