package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import app.ClassRenamer;
import ui.utils.Globals;
import ui.utils.HelpfulTextfield;
import ui.utils.TutorialPanel;

/**
 * The main window for the user.
 * @author Koella
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private final ClassRenamer classRenamer;

	public MainWindow() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Darkest Dungeon Class Creator");
		
		classRenamer = new ClassRenamer();
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(Globals.WINDOW_SIZE);
		tabbedPane.setFocusable(false);
		
		tabbedPane.add("Rename existing class", createClassRenamingPanel());
		tabbedPane.add("Create basic \".info\" file", createInfoCreationPanel());
		tabbedPane.add("Create an effect", createEffectCreationPanel());
		
		this.add(tabbedPane);
	}
	
	@Override
	public void dispose() {
		classRenamer.shutdownThreadPool();
		super.dispose();
	}
	
	public void packAndShow() {
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private JPanel createClassRenamingPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		final JPanel topPanel = new JPanel(new GridLayout(0, 1));
		
		JLabel lblTarget = new JLabel("Target folder:");
		JTextField txtTarget = new HelpfulTextfield(Globals.WORKING_DIR, 20);
		
		JLabel lblOldName = new JLabel("Old Name:");
		JTextField txtOldName = new HelpfulTextfield("old class name", 20);
		JLabel lblNewName = new JLabel("New Name:");
		JTextField txtNewName = new HelpfulTextfield("your new name", 20);
		
		topPanel.add(lblTarget);
		topPanel.add(txtTarget);
		topPanel.add(lblOldName);
		topPanel.add(txtOldName);
		topPanel.add(lblNewName);
		topPanel.add(txtNewName);
		
		TutorialPanel tutorialPanel = new TutorialPanel("<html><body>"
				+ "Copy an existing class folder (\"SteamApps/common/DarkestDungeon/heroes/&lt;chosenclass&gt;\") to somewhere you want. "
				+ "Then set the target folder for this program to your copied folder either by moving this program into that folder and "
				+ "re-opening it or by copying the folder path into the text field.<br>"
				+ "Now fill in the \"Old Name\" and \"New Name\" textfields.<br>"
				+ "Example: Target = \"C:\\Desktop\\heroes\\crusader\", Old Name = \"crusader\", New Name = \"my_first_class\"<br>"
				+ "Then press Start and look at your files! :)<br>"
				+ "P.S.: Relative paths and forward-slashes also work."
				+ "</body></html>"
		);
		
		JButton btnStart = new JButton("Start!");
		btnStart.setFocusable(false);
		btnStart.addActionListener(ev -> {
			try {
				classRenamer.start(new File(txtTarget.getText().length() == 0 ? Globals.WORKING_DIR : txtTarget.getText())
						, txtOldName.getText(), txtNewName.getText());
			} catch (IllegalArgumentException e) {
				Globals.displayError("<html><body>One of the textfields is invalid!<br>"
						+ "Is the target path correct? (directory of hero to rename)<br>"
						+ "Are the names only lowercase letters with '_' in-between?"
						+ "</html></body>");
			}
		});
		
		panel.add(topPanel, BorderLayout.PAGE_START);
		panel.add(tutorialPanel, BorderLayout.CENTER);
		panel.add(btnStart, BorderLayout.PAGE_END);
		return panel;
	}
	
	private Component createInfoCreationPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		
		JLabel lblComingSoon = new JLabel("Coming soon!", SwingConstants.CENTER);
		lblComingSoon.setFont(new Font(lblComingSoon.getFont().getName(), Font.BOLD, 36));
		
		panel.add(lblComingSoon, BorderLayout.CENTER);
		return panel;
	}

	private Component createEffectCreationPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		
		JLabel lblComingSoon = new JLabel("Coming soon!", SwingConstants.CENTER);
		lblComingSoon.setFont(new Font(lblComingSoon.getFont().getName(), Font.BOLD, 36));
		
		panel.add(lblComingSoon, BorderLayout.CENTER);
		return panel;
	}
	
}
