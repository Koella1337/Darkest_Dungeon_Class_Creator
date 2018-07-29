package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import app.features.ClassRenamer;
import app.features.InfoFileCreator;
import app.utils.Globals;
import app.utils.Strings;
import ui.utils.FormFactory;
import ui.utils.components.AddItemCombobox;
import ui.utils.components.CombatSkillsPanel;
import ui.utils.components.HelpfulTextfield;
import ui.utils.components.MultiStatPanel;
import ui.utils.components.TutorialPanel;

/**
 * The main window for the user.
 * @author Koella
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private final JTabbedPane featureTabsPane;
	private final List<Runnable> onDisposeCleanUpList = new ArrayList<>();
	
	private final ClassRenamer classRenamer;
	private final InfoFileCreator infoFileCreator;
	
	/**
	 * Constructs the main window for the Darkest Dungeon Class Creator application.<br>
	 * Call <code>createFeaturePanels()</code> after the constructor to fill the feature tabs.<br>
	 * To display the constructed window call <code>packAndShow()</code>.
	 */
	public MainWindow() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Darkest Dungeon Class Creator");
		
		classRenamer = new ClassRenamer();
		infoFileCreator = new InfoFileCreator();
		
		featureTabsPane = new JTabbedPane(JTabbedPane.TOP);
		featureTabsPane.setPreferredSize(Globals.MAIN_WINDOW_SIZE);
		featureTabsPane.setFocusable(false);
		
		this.add(featureTabsPane);
	}
	
	/**
	 * This needs to be called separately after the constructor since some features need a reference to this frame.
	 */
	public void createFeaturePanels() {
		featureTabsPane.add("Rename existing class", createClassRenamingPanel());
		featureTabsPane.add("Create \".info\" file", createInfoFileCreationPanel());
		featureTabsPane.add("Create \".art\" file", createArtFileCreationPanel());
		featureTabsPane.add("Create an effect", createEffectCreationPanel());
	}
	
	public void packAndShow() {
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	@Override
	public void dispose() {
		for (Runnable cleanUpMethod : onDisposeCleanUpList) {
			cleanUpMethod.run();
		}
		super.dispose();
	}
	
	private JPanel createClassRenamingPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		final JPanel topPanel = new JPanel(new GridLayout(0, 1));
		final int labelWidth = 90;
		
		JTextField txtTarget = new JTextField(Globals.WORKING_DIR);
		topPanel.add(FormFactory.createOneInputForm("Target folder: ", txtTarget, labelWidth));
		
		JTextField txtOldName = new HelpfulTextfield("old_class_name");
		topPanel.add(FormFactory.createOneInputForm("Old Name: ", txtOldName, labelWidth));
		
		JTextField txtNewName = new HelpfulTextfield("your_new_class");
		topPanel.add(FormFactory.createOneInputForm("New Name: ", txtNewName, labelWidth));
		
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
		onDisposeCleanUpList.add(() -> {
			classRenamer.shutdownThreadPool();
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
	
	private JPanel createInfoFileCreationPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		final JPanel topPanel = new JPanel(new GridLayout(0, 1));
		final int labelWidth = 90;
		
		//the first line asks for the class name and the crit effect
		JTextField txtClassName = new HelpfulTextfield("your_class_name", 20);
		JComboBox<String> cbxOnCritEffect = new AddItemCombobox(Strings.getDefaultCritEffects(), "add effect...", 
				"Add on-crit effect", "Your effect name: (don't forget the \"quotation marks\"!)");
		cbxOnCritEffect.setFocusable(false);
		topPanel.add(FormFactory.createTwoInputForm("Class Name: ", txtClassName, "Crit Effect: ", cbxOnCritEffect, labelWidth));
		
		MultiStatPanel weaponPanel = new MultiStatPanel(new String[] {
				"atk: ", "0/0", "min dmg: ", "6/1", "max dmg: ", "12/2", "crit: ", "4/1", "spd: ", "1/1"
		});
		topPanel.add(FormFactory.createOneInputForm("Weapon: ", weaponPanel, labelWidth));
		
		MultiStatPanel armorPanel = new MultiStatPanel(new String[] {
				"def: ", "5/5", "prot: ", "0/0", "hp: ", "33/7", "spd: ", "0/0"
		});
		topPanel.add(FormFactory.createOneInputForm("Armor: ", armorPanel, labelWidth));
		
		MultiStatPanel resistancesPanel1 = new MultiStatPanel(new String[] {
				"stun: ", "40", "poison: ", "30", "bleed: ", "30", "disease: ", "30"
		});
		topPanel.add(FormFactory.createOneInputForm("Resistances: ", resistancesPanel1, labelWidth));
		
		MultiStatPanel resistancesPanel2 = new MultiStatPanel(new String[] {
				"move: ", "40", "debuff: ", "30", "death: ", "67", "trap: ", "10"
		});
		topPanel.add(FormFactory.createOneInputForm("Resistances: ", resistancesPanel2, labelWidth));
		
		//TODO: move back/forward, other extras
		
		CombatSkillsPanel combatSkillsPanel = new CombatSkillsPanel(infoFileCreator);
		combatSkillsPanel.setFocusable(false);
		topPanel.add(FormFactory.createOneInputForm("Combat Skills: ", combatSkillsPanel, labelWidth));
		onDisposeCleanUpList.add(() -> {
			combatSkillsPanel.disposeDialogs();
		});
		
		panel.add(topPanel, BorderLayout.PAGE_START);
		return panel;
	}
	
	private JPanel createArtFileCreationPanel() {
		//TODO: make art file creation feature
		return createComingSoonPanel();
	}

	private JPanel createEffectCreationPanel() {
		//TODO: make effect creation feature
		return createComingSoonPanel();
	}
	
	/**
	 * This panel displays a large "COMING SOON" label for upcoming features.
	 */
	private JPanel createComingSoonPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		
		JLabel lblComingSoon = new JLabel("Coming soon!", SwingConstants.CENTER);
		lblComingSoon.setFont(new Font(lblComingSoon.getFont().getName(), Font.BOLD, 36));
		
		panel.add(lblComingSoon, BorderLayout.CENTER);
		return panel;
	}
	
}
