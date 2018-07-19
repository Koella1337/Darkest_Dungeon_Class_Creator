package ui.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import app.features.InfoFileCreator;
import app.model.CombatSkill;
import app.utils.Globals;

@SuppressWarnings("serial")
public class CombatSkillsPanel extends JPanel{
	
	private final CombatSkill[] combatSkills;
	private final JComboBox<CombatSkill> cbxCombatSkills;

	public CombatSkillsPanel(InfoFileCreator infoFileCreator) {
		super(new BorderLayout(5, 0));
		combatSkills = infoFileCreator.getAllSkills();
		
		cbxCombatSkills = new JComboBox<>(combatSkills);
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
		if (index < 0 || index >= combatSkills.length) return;
		
		combatSkills[index].print(null);
	}
	
}
