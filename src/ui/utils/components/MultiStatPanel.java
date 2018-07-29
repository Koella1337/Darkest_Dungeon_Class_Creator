package ui.utils.components;

import java.awt.Component;
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
	
	/**
	 * Gets the JTextField from a statIndex.
	 * @param statIndex - starts at 0 and goes to the amount of PAIRS that have been supplied in the constructor.
	 * 						For example: {"min: ", "5/1", "max: ", "12/1"} would have statIndex 0 for "5/1" and 1 for "12/1".
	 * @return
	 */
	public JTextField getTextfieldFromIndex(int statIndex) {
		try {
			return (JTextField) this.getComponent(statIndex * 2 + 1);
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * @see <code>getTextfieldFromIndex(int statIndex)</code>
	 * @return the text of the JTextField or <b>null</b> if statIndex is invalid
	 */
	public String getStringValue(int statIndex) {
		JTextField field = getTextfieldFromIndex(statIndex);
		return field == null ? null : field.getText();
	}
	
	/** @see <code>getTextfieldFromIndex(int statIndex)</code> */
	public int getIntegerValue(int statIndex) {
		JTextField field = getTextfieldFromIndex(statIndex);
		return -1;		//TODO getIntegerValue
	}
	
	/** @see <code>getTextfieldFromIndex(int statIndex)</code> */
	public int[] getIntegerPerLevelValue(int statIndex) {
		JTextField field = getTextfieldFromIndex(statIndex);
		return null;	//TODO getIntegerPerLevelValue
	}
	
	/** @see <code>getTextfieldFromIndex(int statIndex)</code> */
	public float[] getFloatPerLevelValue(int statIndex) {
		JTextField field = getTextfieldFromIndex(statIndex);
		return null;	//TODO getFloatPerLevelValue
	}
	
	/** @see <code>getTextfieldFromIndex(int statIndex)</code> */
	public void setTooltipText(int statIndex, String text) {
		JTextField field = getTextfieldFromIndex(statIndex);
		if (field != null) field.setToolTipText(text);
	}
	
	/** @see <code>getTextfieldFromIndex(int statIndex)</code> */
	public void setStat(int statIndex, String value) {
		JTextField field = getTextfieldFromIndex(statIndex);
		if (field != null) field.setText(value);
	}
	
	public void setAllStats(String[] newValues) {
		if (newValues == null || newValues.length == 0) return;
		
		for (int i = 0; i < newValues.length; i++) {
			JTextField field = getTextfieldFromIndex(i);
			if (field != null) field.setText(newValues[i]);
		}
	}
	
	public void setComponentsEnabled(boolean areEnabled) {
		for (Component c : this.getComponents()) {
			c.setEnabled(areEnabled);
		}
	}
	

	
}
