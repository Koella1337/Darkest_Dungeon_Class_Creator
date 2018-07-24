package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ItemEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import app.model.CombatSkill;
import app.utils.Globals;
import app.utils.Strings;
import ui.utils.FormFactory;
import ui.utils.RankPanel;

/**
 * A JDialog that lets the user edit a Combat Skill.
 * @author Koella
 */
@SuppressWarnings("serial")
public class EditCombatSkillDialog extends JDialog {
	
	private final CombatSkill skill;
	private final JButton btnConfirm, btnReset;
	
	private final JTextField txtID;
	private final JComboBox<CombatSkill.Type> cbxType;
	private final JRadioButton rbRanged, rbMelee;
	private final JCheckBox checkAoe, checkRandom;
	private final RankPanel rankPanelTarget, rankPanelLaunch;

	public EditCombatSkillDialog(Window owner, CombatSkill skillToEdit) {
		super(owner, String.format("Edit Combat Skill \"%s\"", skillToEdit.id) , ModalityType.MODELESS);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.skill = skillToEdit;
		
		btnConfirm = createConfirmButton();
		btnReset = createResetButton();

		txtID = new JTextField();
		
		checkRandom = new JCheckBox("Random");
		checkRandom.setFocusable(false);
		
		checkAoe = new JCheckBox("AOE");
		checkAoe.setFocusable(false);
		checkAoe.addItemListener(ev -> {
			checkRandom.setEnabled(!checkAoe.isSelected());
		});
		
		rbRanged = new JRadioButton("Ranged Skill");
		rbRanged.setFocusable(false);
		
		rbMelee = new JRadioButton("Melee Skill");
		rbMelee.setFocusable(false);
		
		rankPanelTarget = new RankPanel(true);
		rankPanelLaunch = new RankPanel(false);
		
		cbxType = new JComboBox<>(CombatSkill.Type.values());
		cbxType.setFocusable(false);
		cbxType.setSelectedItem(null);
		cbxType.addItemListener(ev -> {
			if (ev.getStateChange() == ItemEvent.SELECTED) {
				this.disableComponentsByType((CombatSkill.Type) ev.getItem());
			}
		});
		
		this.add(createMainPanel());
		this.pack();
	}
	
	@Override
	public void setVisible(boolean b) {
		if (b) this.setLocationRelativeTo(this.getOwner());
		super.setVisible(b);
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(Globals.EDIT_COMBATSKILL_DIALOG_SIZE);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		final JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
		final int labelWidth = 90;
		
		topPanel.add(FormFactory.createTwoInputForm("Skill Name: ", txtID, "Type: ", cbxType, labelWidth));
		topPanel.add(FormFactory.createOneInputForm("Target Mod: ", createTargetModifierPanel(), labelWidth));
		topPanel.add(FormFactory.createTwoInputForm("Target Ranks: ", rankPanelTarget, "Launch Ranks: ", rankPanelLaunch, labelWidth));
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 0, 5, 0));
		buttonPanel.add(btnConfirm);
		buttonPanel.add(btnReset);
		
		btnReset.doClick();
		panel.add(topPanel, BorderLayout.PAGE_START);
		panel.add(buttonPanel, BorderLayout.PAGE_END);
		return panel;
	}
	
	// ----------------------------------- COMPONENT - CREATING METHODS -----------------------------------
	
	private JButton createConfirmButton() {
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(ev -> {
			//TODO: verify and then actually set the skill's variables with values from the input components
			System.out.println("Target:" + rankPanelTarget.getRanks());
			System.out.println("Launch:" + rankPanelLaunch.getRanks());
			
			//TODO: uncomment this.setVisible(false);
		});
		btnConfirm.setToolTipText(Strings.wrapWithHtml(
				"When pressing this button your changes will be validated and,<br>"
				+ "if they are valid, saved to the Combat Skill's values."
		));
		btnConfirm.setFocusable(false);
		return btnConfirm;
	}
	
	private JButton createResetButton() {
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(ev -> {
			//reset all input components back to the skill's variables
			txtID.setText(skill.id);
			cbxType.setSelectedItem(skill.type);
			checkAoe.setSelected(skill.isAoe);
			checkRandom.setSelected(skill.isRandom);
			rbMelee.setSelected(skill.isMelee);
			rbRanged.setSelected(!skill.isMelee);
			rankPanelTarget.setRanks(skill.target);
			rankPanelLaunch.setRanks(skill.launch);
		});
		btnReset.setToolTipText(Strings.wrapWithHtml(
				"ATTENTION! This gets rid of every change you haven't confirmed yet!<br>"
				+ "Pressing this button will reset all the fields to the Combat Skill's saved values."
		));
		btnReset.setFocusable(false);
		return btnReset;
	}
	
	private JPanel createTargetModifierPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		
		ButtonGroup rangedVsMelee = new ButtonGroup();
		rangedVsMelee.add(rbRanged);
		rangedVsMelee.add(rbMelee);
		
		panel.add(checkAoe);
		panel.add(checkRandom);
		panel.add(Box.createHorizontalGlue());
		panel.add(rbRanged);
		panel.add(rbMelee);
		return panel;
	}
	

	/* ----------------------------------- COMPONENT - DISABLING METHODS -----------------------------------
	 * The following methods disable components that won't change the printing of the skill.
	 * Each method is based on a specific "more important" variable that has an impact on other variables.
	 * For example: When a skill is AOE then the checkbox for "Random Target" will be disabled.           */
	
	private void disableComponentsByType(CombatSkill.Type type) {
		txtID.setEnabled(true);
		switch (type) {
			case DAMAGE: 
				txtID.setEnabled(false);
				break;
			case HEAL:
				
				break;
			case BUFF:
				
				break;
			case DEBUFF:
				
				break;
		}
		
	}

}
