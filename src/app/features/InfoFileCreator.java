package app.features;

import app.model.CombatSkill;

public class InfoFileCreator {
	
	private final CombatSkill[] skills;

	public InfoFileCreator() {
		skills = new CombatSkill[] {
			new CombatSkill("skill_one"), new CombatSkill("skill_two"), new CombatSkill("skill_three"), new CombatSkill("skill_four"),
			new CombatSkill("skill_five"), new CombatSkill("skill_six"), new CombatSkill("skill_seven")
		};
	}
	
	public CombatSkill[] getAllSkills() {
		return skills;
	}
	
	/**
	 * Returns the skill with the specified index.<br>
	 * If the index is out of range then <b>null</b> is returned.
	 */
	public CombatSkill getSkill(int index) {
		if (index < 0 || index >= skills.length) return null;
		return skills[index];
	}
	
}
