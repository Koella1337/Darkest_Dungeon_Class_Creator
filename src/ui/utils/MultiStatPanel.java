package ui.utils;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import app.utils.Globals;

/**
 * A panel which provides multiple textfield inputs for the user.
 * @author Koella
 */
@SuppressWarnings("serial")
public class MultiStatPanel extends JPanel {

	/**
	 * The stats array should be filled with stat-descriptions at even numbers and stat-defaults at odd numbers.<br>
	 * <br>
	 * Example: the panel "min: 5/1    max: 12/1" is achieved by supplying:<br>
	 * <code>new String[] {"min: ", "5/1", "max: ", "12/1"};</code>
	 * @param stats
	 */
	public MultiStatPanel(String... stats) {
		super(new GridLayout(1, 0));
		if (stats == null || stats.length <= 0 || stats.length % 2 != 0)
			throw new IllegalArgumentException("Invalid \"stats\" argument... maybe uneven amount of Strings?");
		
		for (int i = 0; i < stats.length; i += 2) {
			this.add(new JLabel(stats[i], SwingConstants.CENTER));
			JTextField txtStatInput = new JTextField(stats[i+1]);
			txtStatInput.setFont(Globals.FONT_CONSOLAS);
			this.add(txtStatInput);
		}
	}
	
	public String getStringValue(int statIndex) {
		statIndex *= 2;
		JTextField textField = (JTextField) this.getComponent(statIndex);
		return textField.getText();
	}
	
	public int getIntegerValue(int statIndex) {
		statIndex *= 2;
		JTextField textField = (JTextField) this.getComponent(statIndex);
		return -1;		//TODO
	}
	
	public int[] getIntegerPerLevelValue(int statIndex) {
		statIndex *= 2;
		JTextField textField = (JTextField) this.getComponent(statIndex);
		return null;	//TODO
	}
	
	public float[] getFloatPerLevelValue(int statIndex) {
		statIndex *= 2;
		JTextField textField = (JTextField) this.getComponent(statIndex);
		return null;	//TODO
	}
	
}
