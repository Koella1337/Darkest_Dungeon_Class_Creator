package ui.utils;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TutorialPanel extends JPanel {
	
	public TutorialPanel(String tutorial) {
		super(new BorderLayout());
		JPanel showTutorialPanel = new JPanel();
		
		JLabel lblShowTutorial = new JLabel("Show tutorial?");
		JLabel lblTutorial = new JLabel(tutorial);
		lblTutorial.setVisible(false);
		
		JCheckBox checkShowTutorial = new JCheckBox();
		checkShowTutorial.addActionListener(ev -> {
			lblTutorial.setVisible(checkShowTutorial.getModel().isSelected());
		});
		
		showTutorialPanel.add(lblShowTutorial);
		showTutorialPanel.add(checkShowTutorial);
		this.add(showTutorialPanel, BorderLayout.NORTH);
		this.add(lblTutorial, BorderLayout.CENTER);
	}
	
}
