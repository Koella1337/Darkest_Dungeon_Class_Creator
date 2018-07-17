package app.utils;

public class CombatSkill {

	public enum Type {
		DAMAGE,
		HEAL,
		BUFF,
		DEBUFF
	}
	
	private final String id, launch, target;
	private final float atk, dmg, crit;
	
	public CombatSkill(String id, String launch, String target, float atk, float dmg, float crit) {
		super();
		this.id = id;
		this.launch = launch;
		this.target = target;
		this.atk = atk;
		this.dmg = dmg;
		this.crit = crit;
	}
	
}
