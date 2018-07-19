package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import app.features.ClassRenamer;
import app.features.InfoFileCreator;
import app.utils.Globals;
import app.utils.Strings;
import ui.utils.AddItemCombobox;
import ui.utils.CombatSkillsPanel;
import ui.utils.FormFactory;
import ui.utils.HelpfulTextfield;
import ui.utils.MultiStatPanel;
import ui.utils.TutorialPanel;

/**
 * The main window for the user.
 * @author Koella
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private final JTabbedPane featureTabsPane;
	private final ClassRenamer classRenamer;
	private final InfoFileCreator infoFileCreator;
	
	public MainWindow() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Darkest Dungeon Class Creator");
		
		classRenamer = new ClassRenamer();
		infoFileCreator = new InfoFileCreator();
		
		featureTabsPane = new JTabbedPane(JTabbedPane.TOP);
		featureTabsPane.setPreferredSize(Globals.WINDOW_SIZE);
		featureTabsPane.setFocusable(false);
		
		featureTabsPane.add("Rename existing class", createClassRenamingPanel());
		featureTabsPane.add("Create \".info\" file", createInfoCreationPanel());
		featureTabsPane.add("Create an effect", createEffectCreationPanel());
		
		this.add(featureTabsPane);
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
				Globals.displayError(Strings.wrapWithHtml("One of the textfields is invalid!<br>"
						+ "Is the target path correct? (directory of hero to rename)<br>"
						+ "Are the names only lowercase letters with '_' in-between?"));
			}
		});
		topPanel.add(btnStart);
		
		KeyListener enterListener = Globals.createEnterListener(btnStart);
		txtTarget.addKeyListener(enterListener);
		txtOldName.addKeyListener(enterListener);
		txtNewName.addKeyListener(enterListener);
		
		TutorialPanel tutorialPanel = new TutorialPanel(Strings.getClassRenamingTutorial());
		
		panel.add(topPanel, BorderLayout.PAGE_START);
		panel.add(tutorialPanel, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel createInfoCreationPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		final JPanel topPanel = new JPanel(new GridLayout(0, 1));
		final int labelWidth = 90;
		
		//the first line asks for the class name and the crit effect
		JPanel firstLinePanel = new JPanel(new GridLayout(1, 0));
		JTextField txtClassName = new HelpfulTextfield("your_class_name", 20);
		firstLinePanel.add(txtClassName);
		firstLinePanel.add(new JLabel("Crit Effect: ", SwingConstants.TRAILING));
		JComboBox<String> cbxOnCritEffect = new AddItemCombobox(Strings.getDefaultCritEffects(), "add effect...", 
				"Add on-crit effect", "Your effect name: (don't forget the \"quotation marks\"!)");
		cbxOnCritEffect.setFocusable(false);
		firstLinePanel.add(cbxOnCritEffect);
		topPanel.add(FormFactory.createSimpleForm(firstLinePanel, "Class Name: ", labelWidth));
		
		MultiStatPanel weaponPanel = new MultiStatPanel(new String[] {
				"atk: ", "0/0", "min dmg: ", "6/1", "max dmg: ", "12/2", "crit: ", "4/1", "spd: ", "1/1"
		});
		topPanel.add(FormFactory.createSimpleForm(weaponPanel, "Weapon: ", labelWidth));
		
		MultiStatPanel armorPanel = new MultiStatPanel(new String[] {
				"def: ", "5/5", "prot: ", "0/0", "hp: ", "33/7", "spd: ", "0/0"
		});
		topPanel.add(FormFactory.createSimpleForm(armorPanel, "Armor: ", labelWidth));
		
		MultiStatPanel resistancesPanel1 = new MultiStatPanel(new String[] {
				"stun: ", "40", "poison: ", "30", "bleed: ", "30", "disease: ", "30"
		});
		topPanel.add(FormFactory.createSimpleForm(resistancesPanel1, "Resistances: ", labelWidth));
		
		MultiStatPanel resistancesPanel2 = new MultiStatPanel(new String[] {
				"move: ", "40", "debuff: ", "30", "death: ", "67", "trap: ", "10"
		});
		topPanel.add(FormFactory.createSimpleForm(resistancesPanel2, "Resistances: ", labelWidth));
		
		CombatSkillsPanel cbxCombatSkills = new CombatSkillsPanel(infoFileCreator);
		cbxCombatSkills.setFocusable(false);
		topPanel.add(FormFactory.createSimpleForm(cbxCombatSkills, "Combat Skills: ", labelWidth));
		
		panel.add(topPanel, BorderLayout.PAGE_START);
		return panel;
	}

	private JPanel createEffectCreationPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		
		JLabel lblComingSoon = new JLabel("Coming soon!", SwingConstants.CENTER);
		lblComingSoon.setFont(new Font(lblComingSoon.getFont().getName(), Font.BOLD, 36));
		
		panel.add(lblComingSoon, BorderLayout.CENTER);
		return panel;
	}
	
}
