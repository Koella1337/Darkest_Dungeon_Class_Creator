package ui.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import app.features.InfoFileCreator;
import app.model.CombatSkill;
import app.utils.Globals;
import ui.EditCombatSkillDialog;

@SuppressWarnings("serial")
public class CombatSkillsPanel extends JPanel{
	
	private final Map<CombatSkill, JDialog> skillToDialogMap;
	private final JComboBox<CombatSkill> cbxCombatSkills;
	
	/**
	 * Disposes of all JDialogs held by the panel.
	 */
	public void disposeDialogs() {
		for (JDialog dialog : skillToDialogMap.values()) {
			dialog.dispose();
		}
	}

	public CombatSkillsPanel(InfoFileCreator infoFileCreator) {
		super(new BorderLayout(5, 0));
		skillToDialogMap = new HashMap<>();
		for (CombatSkill skill : infoFileCreator.getAllSkills()) {
			skillToDialogMap.put(skill, new EditCombatSkillDialog(Globals.MAIN_WINDOW, skill));
		}
		
		cbxCombatSkills = new JComboBox<>(infoFileCreator.getAllSkills());
		cbxCombatSkills.setFocusable(false);
		cbxCombatSkills.setFont(Globals.FONT_CONSOLAS);
		
		JButton btnEdit = new JButton("Edit...");
		btnEdit.setFocusable(false);
		btnEdit.setPreferredSize(new Dimension(170, 0));
		btnEdit.addActionListener(ev -> {
			editCombatSkill((CombatSkill) cbxCombatSkills.getSelectedItem());
		});
		
		this.add(cbxCombatSkills, BorderLayout.CENTER);
		this.add(btnEdit, BorderLayout.LINE_END);
	}
	
	private void editCombatSkill(CombatSkill skill) {
		if (skill == null) return;
		skillToDialogMap.get(skill).setVisible(true);
	}
	
}
