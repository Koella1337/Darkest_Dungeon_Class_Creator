package ui.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import app.model.AtkDmgCritStruct;

/**
 * 
 * @author Koella
 */
public class FormFactory {

	/**
	 * Creates a simple form out of an input component by adding a label in front of it.<br>
	 * When labelWidth is <= 0 then the label will only take the space it needs.
	 * 
	 * @return a panel containing the form.
	 */
	public static JPanel createSimpleForm(JComponent inputComponent, String labelText, int labelWidth) {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel(labelText, SwingConstants.TRAILING);
		if (labelWidth > 0)
			label.setPreferredSize(new Dimension(labelWidth, 0));
		
		panel.add(label, BorderLayout.LINE_START);
		panel.add(inputComponent, BorderLayout.CENTER);
		return panel;
	}
	
	
	public static JPanel createAtkDmgCritForm(AtkDmgCritStruct struct) {
		
		
		
		return null;
	}
	
}
