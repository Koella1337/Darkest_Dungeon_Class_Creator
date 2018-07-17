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
import javax.swing.border.EmptyBorder;

import app.ClassRenamer;
import app.InfoFileCreator;
import ui.utils.FormFactory;
import ui.utils.Globals;
import ui.utils.HelpfulTextfield;
import ui.utils.TutorialPanel;

/**
 * The main window for the user.
 * @author Koella
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private final JTabbedPane featureTabs;
	private final ClassRenamer classRenamer;
	private final InfoFileCreator infoFileCreator;
	
	public MainWindow() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Darkest Dungeon Class Creator");
		
		classRenamer = new ClassRenamer();
		infoFileCreator = new InfoFileCreator();
		
		featureTabs = new JTabbedPane(JTabbedPane.TOP);
		featureTabs.setPreferredSize(Globals.WINDOW_SIZE);
		featureTabs.setFocusable(false);
		
		featureTabs.add("Rename existing class", createClassRenamingPanel());
		featureTabs.add("Create \".info\" file", createInfoCreationPanel());
		featureTabs.add("Create an effect", createEffectCreationPanel());
		
		this.add(featureTabs);
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
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		final JPanel topPanel = new JPanel(new GridLayout(0, 1));
		final int labelWidth = 90;
		
		JTextField txtTarget = new JTextField(Globals.WORKING_DIR);
		topPanel.add(FormFactory.createSimpleForm(txtTarget, "Target folder: ", labelWidth));
		
		JTextField txtOldName = new HelpfulTextfield("old_class_name");
		topPanel.add(FormFactory.createSimpleForm(txtOldName, "Old Name: ", labelWidth));
		
		JTextField txtNewName = new HelpfulTextfield("your_new_class");
		topPanel.add(FormFactory.createSimpleForm(txtNewName, "New Name: ", labelWidth));
		
		JButton btnStart = new JButton("Start!");
		btnStart.setFocusable(false);
		btnStart.addActionListener(ev -> {
			try {
				classRenamer.start(new File(txtTarget.getText()), txtOldName.getText(), txtNewName.getText());
			} catch (IllegalArgumentException e) {
				Globals.displayError(Globals.wrapWithHtml("One of the textfields is invalid!<br>"
						+ "Is the target path correct? (directory of hero to rename)<br>"
						+ "Are the names only lowercase letters with '_' in-between?"));
			}
		});
		topPanel.add(btnStart);
		
		TutorialPanel tutorialPanel = new TutorialPanel(Globals.wrapWithHtml(
				"Copy an existing class folder (\"SteamApps/common/DarkestDungeon/heroes/&lt;chosenclass&gt;\") to somewhere you want. "
				+ "Then set the target folder for this program to your copied folder either by moving this program into the folder and "
				+ "re-opening it or by copying the folder path into the \"Target folder\" text field.<br><br>"
				+ "Now fill in the \"Old Name\" and \"New Name\" textfields.<br>"
				+ "These names can only contain lowercase letters with '_' in-between.<br>"
				+ "--- Example:<br>"
				+ "--- Target folder: \"C:\\my_mod\\heroes\\crusader\",<br>"
				+ "--- Old Name: \"crusader\",<br>"
				+ "--- New Name: \"my_first_class\"<br>"
				+ "Then press Start and look at your files! :)<br><br>"
				+ "P.S.: Relative paths and forward-slashes also work."
		));
		
		panel.add(topPanel, BorderLayout.PAGE_START);
		panel.add(tutorialPanel, BorderLayout.CENTER);
		return panel;
	}
	
	private Component createInfoCreationPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		final JPanel topPanel = new JPanel(new GridLayout(0, 1));
		
		JTextField txtClassName = new HelpfulTextfield("your_class_name");
		topPanel.add(FormFactory.createSimpleForm(txtClassName, "Class Name: ", 0));
		
		
		
		panel.add(topPanel, BorderLayout.PAGE_START);
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
