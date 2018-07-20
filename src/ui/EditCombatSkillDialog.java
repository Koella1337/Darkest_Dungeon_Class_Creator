package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ItemEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import app.model.CombatSkill;
import app.utils.Globals;
import app.utils.Strings;
import ui.utils.FormFactory;

/**
 * A JDialog that lets the user edit a Combat Skill.
 * @author Koella
 */
@SuppressWarnings("serial")
public class EditCombatSkillDialog extends JDialog {
	
	private final JTextField txtID;
	private final JComboBox<CombatSkill.Type> cbxType;
	
	private final CombatSkill skill;
	private final JButton btnConfirm, btnReset, btnHide;

	public EditCombatSkillDialog(Window owner, CombatSkill skillToEdit) {
		super(owner, String.format("Edit Combat Skill \"%s\"", skillToEdit.id) , ModalityType.MODELESS);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		

		txtID = new JTextField();
		
		cbxType = new JComboBox<>(CombatSkill.Type.values());
		cbxType.setFocusable(false);
		cbxType.addItemListener(ev -> {
			if (ev.getStateChange() == ItemEvent.SELECTED) {
				disableIrrelevantComponents();
			}
		});
		
		
		this.skill = skillToEdit;
		btnConfirm = createConfirmButton();
		btnReset = createResetButton();
		btnHide = createHideButton();
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
		
		final JPanel topPanel = new JPanel(new GridLayout(0, 1));
		final int labelWidth = 90;
		
		topPanel.add(FormFactory.createTwoInputForm("Skill Name: ", txtID, "Type: ", cbxType, labelWidth));
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 0, 5, 0));
		buttonPanel.add(btnConfirm);
		buttonPanel.add(btnReset);
		buttonPanel.add(btnHide);
		
		btnReset.doClick();
		panel.add(topPanel, BorderLayout.PAGE_START);
		panel.add(buttonPanel, BorderLayout.PAGE_END);
		return panel;
	}
	
	private JButton createConfirmButton() {
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(ev -> {
			//TODO: verify and then actually set the skill's variables with values from the input components
		});
		btnConfirm.setToolTipText(Strings.wrapWithHtml(
				"When pressing this button your changes will be verified and,<br>"
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
			
			
		});
		btnReset.setToolTipText(Strings.wrapWithHtml(
				"ATTENTION! This gets rid of every change you haven't confirmed yet!<br>"
				+ "Pressing this button will reset all the fields to the Combat Skill's saved values."
		));
		btnReset.setFocusable(false);
		return btnReset;
	}
	
	private JButton createHideButton() {
		JButton btnHide = new JButton("Hide");
		btnHide.addActionListener(ev -> {
			this.setVisible(false);
		});
		btnHide.setToolTipText(Strings.wrapWithHtml(
				"Hides the window without actually changing the Combat Skill's values.<br>"
				+ "Make sure to press Confirm first if you want your changes to stick!"
		));
		btnHide.setFocusable(false);
		return btnHide;
	}

	/**
	 * Disables components that will not change the printing of the skill based on the skills "more important" variables.<br>
	 * For example: When a skill is AOE then the checkbox for "Random Target" will be disabled.
	 */
	private void disableIrrelevantComponents(/*TODO: maybe add param. "JComponent importantComp" */) {
		if (cbxType.getSelectedItem() == CombatSkill.Type.HEAL) {
			txtID.setEnabled(false);
		}
		else {
			txtID.setEnabled(true);
		}
		
	}

}
