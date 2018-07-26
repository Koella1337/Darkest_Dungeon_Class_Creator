package ui.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 * @author Koella
 */
public class FormFactory {

	/**
	 * Creates a single-line form out of an input component by adding a label in front of it.<br>
	 * When labelWidth is <= 0 then the label will only take the space it needs.
	 * 
	 * @return a panel containing the form.
	 */
	public static JPanel createOneInputForm(String labelText, JComponent inputComponent, int labelWidth) {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel(labelText, SwingConstants.TRAILING);
		if (labelWidth > 0)
			label.setPreferredSize(new Dimension(labelWidth, 0));
		
		panel.add(label, BorderLayout.LINE_START);
		panel.add(inputComponent, BorderLayout.CENTER);
		return panel;
	}
	
	/**
	 * Creates a single-line form with two input components and two respective labels.<br>
	 * When firstLabelWidth is <= 0 then the first label will only take the space it needs.
	 */
	public static JPanel createTwoInputForm(String labelText1, JComponent inputComp1, 
											String labelText2, JComponent inputComp2, int firstLabelWidth) 
	{
		JPanel firstLinePanel = new JPanel(new GridLayout(1, 0));
		firstLinePanel.add(inputComp1);
		firstLinePanel.add(new JLabel(labelText2, SwingConstants.TRAILING));
		firstLinePanel.add(inputComp2);
		return createOneInputForm(labelText1, firstLinePanel, firstLabelWidth);
	}
	
	/**
	 * Creates a panel that puts all of the components in the specified array into a single line.
	 * @param components - the specified components to be put into the panel.
	 * @return a panel with a BoxLayout using only a single row that all components have been added to
	 */
	public static JPanel createLinePanel(JComponent[] components) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		for (JComponent c : components) {
			panel.add(c);
			panel.add(Box.createHorizontalGlue());		//create spacing in-between components
		}
		panel.remove(panel.getComponentCount() - 1);	//remove last glue
		return panel;
	}
	
}
