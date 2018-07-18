package ui.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import app.model.CombatSkill;
import app.utils.Globals;

@SuppressWarnings("serial")
public class CombatSkillsPanel extends JPanel{

	public CombatSkillsPanel() {
		super(new BorderLayout(5, 0));
		
		JComboBox<CombatSkill> cbxCombatSkills = new JComboBox<>(new CombatSkill[] {
				new CombatSkill("skill_one"), new CombatSkill("skill_two"), new CombatSkill("skill_three"), new CombatSkill("skill_four"),
				new CombatSkill("skill_five"), new CombatSkill("skill_six"), new CombatSkill("skill_seven")
		});
		cbxCombatSkills.setFocusable(false);
		cbxCombatSkills.setFont(Globals.FONT_CONSOLAS);
		
		JButton btnEdit = new JButton("Edit...");
		btnEdit.setFocusable(false);
		btnEdit.setPreferredSize(new Dimension(170, 0));
		btnEdit.addActionListener(ev -> {
			editCombatSkill(cbxCombatSkills.getSelectedIndex());
		});
		
		this.add(cbxCombatSkills, BorderLayout.CENTER);
		this.add(btnEdit, BorderLayout.LINE_END);
	}
	
	private void editCombatSkill(int index) {
		if (index < 0 || index > 6) return;
		
		System.err.println("Sel: " + index);
	}
	
}
