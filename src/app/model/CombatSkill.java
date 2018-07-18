package app.model;

public class CombatSkill {

	public enum Type {
		DAMAGE,
		HEAL,
		BUFF,
		DEBUFF
	}
	
	public String id;
	public Type type;
	public String launch, target;
	public AtkDmgCritStruct atkDmgCrit;
	
	public CombatSkill(String id) {
		this.id = id;
		this.type = Type.DAMAGE;
		this.launch = "4321";
		this.target = "1234";
		this.atkDmgCrit = new AtkDmgCritStruct();
	}
	
	public CombatSkill(String id, Type type, String launch, String target, AtkDmgCritStruct atkDmgCrit) {
		this.id = id;
		this.type = type;
		this.launch = launch;
		this.target = target;
		this.atkDmgCrit = atkDmgCrit;
	}
	
	@Override
	public String toString() {
		return String.format("%-35s (%-6s - %4s - %7s)", id, type.toString(), launch, target);
	}
	
}
