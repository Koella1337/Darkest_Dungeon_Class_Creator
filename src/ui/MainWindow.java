package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * The main window for the user.
 * @author Koella1337
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	public MainWindow() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Darkest Dungeon Class Creator");
		
		JTabbedPane mainPanel = new JTabbedPane(JTabbedPane.TOP);
		mainPanel.setPreferredSize(new Dimension(600,400));
		mainPanel.setFocusable(false);
		
		mainPanel.add("Rename existing class", createClassRenamingPanel());
		mainPanel.add("Create basic \".info\" file", createInfoCreationPanel());
		mainPanel.add("Create an effect", createEffectCreationPanel());
		
		this.add(mainPanel);
	}
	
	public void packAndShow() {
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private JPanel createClassRenamingPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		
		
		return panel;
	}
	
	private Component createInfoCreationPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JLabel lblComingSoon = new JLabel("Coming soon!", SwingConstants.CENTER);
		lblComingSoon.setFont(new Font(lblComingSoon.getFont().getName(), Font.BOLD, 36));
		
		panel.add(lblComingSoon, BorderLayout.CENTER);
		return panel;
	}

	private Component createEffectCreationPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JLabel lblComingSoon = new JLabel("Coming soon!", SwingConstants.CENTER);
		lblComingSoon.setFont(new Font(lblComingSoon.getFont().getName(), Font.BOLD, 36));
		
		panel.add(lblComingSoon, BorderLayout.CENTER);
		return panel;
	}
	
}
