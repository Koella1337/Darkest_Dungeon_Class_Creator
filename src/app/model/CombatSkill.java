package app.model;

import java.io.PrintStream;

import app.utils.Strings;

public class CombatSkill {

	public enum Type {
		DAMAGE,
		HEAL,
		BUFF,
		DEBUFF
	}
	
	public String id;
	public Type type;
	public boolean isMelee, isAoe, isRandom;
	public String launch, target;
	public String effects;		//TODO: probably use List<Effect> instead
	
	public AtkDmgCritStruct atkDmgCrit;
	public MinMaxStruct heal;
	
	//extra effects
	public int 		per_battle_limit 		= 0; 		//0 for infinite
	public boolean 	generation_guaranteed 	= false;
	public boolean 	self_target_valid 		= true;
	public boolean 	ignore_protection 		= false;
	public boolean	ignore_guard 			= false;
	public boolean	ignore_stealth 			= false;
	public MinMaxStruct move 				= new MinMaxStruct(0, 0, 0, 0);	//min = back, max = forward movement
	
	public CombatSkill(String id) {
		this.id = id;
		this.type = Type.DAMAGE;
		this.isMelee = false;
		this.launch = "4321";
		this.target = "1234";
		this.atkDmgCrit = new AtkDmgCritStruct();
		this.heal = new MinMaxStruct();
		this.effects = "";
		
		//TODO: remove
		ignore_protection = true;
		ignore_guard = true;
		ignore_stealth = true;
		self_target_valid = false;
		if (id.endsWith("two")) {
			this.type = Type.HEAL;
			isAoe = true;
			effects = "\"test1\" \"test2\"";
			generation_guaranteed = true;
			move.min = 1;
			this.launch = "23";
		}
		else if (id.endsWith("three")) {
			this.type = Type.BUFF;
			isRandom = true;
			isMelee = true;
		}
		else if (id.endsWith("four")) {
			this.type = Type.DEBUFF;
			isAoe = true;
			isRandom = true;
			effects = "\"test1\"";
			per_battle_limit = 2;
			move.max = 3;
			this.target = "123";
		}
	}
	
	@Override
	public String toString() {
		return String.format("%-35s (%-6s - %4s - %6s)", id, type.toString(), launch, makeTargetString());
	}
	
	/**
	 * Prints the CombatSkill in Darkest Dungeon's format.<br>
	 * @param out - the PrintStream to use. If this is null then System.out is used.
	 */
	public void print(PrintStream out) {
		if (out == null) out = System.out;
		
		out.println("//" + id);
		for (int i = 0; i < 5; i++) {	//5 skill levels	
			out.printf("combat skill: .id %s .level %d ", Strings.wrapWithQuotationMarks(id), i);
			if (type.equals(Type.HEAL)) {
				out.printf(".heal %d %d ", calculateIntValue(heal.min, heal.minPerLvl, i), calculateIntValue(heal.max, heal.maxPerLvl, i));
			}
			else {
				String atkDmgCritFormat = ".type \"" + (isMelee ? "melee" : "ranged") + "\" .atk %d%% .dmg %d%%, .crit %d.%d%% ";
				if (type == Type.DAMAGE) {
					float critValue = calculateFloatValue(atkDmgCrit.crit, atkDmgCrit.critPerLvl, i);
					int critPreCommaValue = (int) critValue;
					int critPostCommaValue = (int) (critValue * 10) % 10;
					
					out.printf(atkDmgCritFormat, calculateIntValue(atkDmgCrit.atk, atkDmgCrit.atkPerLvl, i),
							calculateIntValue(atkDmgCrit.dmg, atkDmgCrit.dmgPerLvl, i),
							critPreCommaValue, critPostCommaValue);
				}
				else if (type == Type.BUFF) {
					out.printf(atkDmgCritFormat, 0, 0, 0, 0);
				}
				else if (type == Type.DEBUFF) {
					out.printf(atkDmgCritFormat, calculateIntValue(atkDmgCrit.atk, atkDmgCrit.atkPerLvl, i), -100, 0, 0);
				}
			}
			if (move.min > 0 || move.max > 0) 
				out.printf(".move %d %d ", move.min, move.max);
			if (per_battle_limit > 0) 
				out.printf(".per_battle_limit %d ", per_battle_limit);
			
			out.printf(".launch %s .target %s ", launch, makeTargetString());
			
			if (!self_target_valid && (type == Type.HEAL || type == Type.BUFF)) 
				out.print(".self_target_valid False ");
			
			if (type == Type.BUFF || type == Type.DEBUFF)
				out.print(".is_crit_valid False ");
			else
				out.print(".is_crit_valid True ");
			
			if (type == Type.DAMAGE || type == Type.DEBUFF) {
				if (ignore_protection && type == Type.DAMAGE) out.print(".ignore_protection True ");
				if (ignore_guard) out.print(".ignore_guard True ");
				if (ignore_stealth) out.print(".ignore_stealth True ");
			}
			
			if (effects != null && effects.length() > 0) 
				out.printf(".effect %s ", effects);
			if (generation_guaranteed && i == 0) 
				out.print(".generation_guaranteed True ");
			if (type != Type.DAMAGE) 
				out.print(".is_stall_invalidating False ");
			out.println();		//finish line
		}
	}
	
	/**
	 * Calculates the exact value (floored) of an Integer-based skill at a specific level.
	 */
	private int calculateIntValue(int base, float perLvl, int level) {
		return base + (int) Math.floor(level * perLvl);
	}
	
	/**
	 * Calculates the exact value rounded to 1 decimal place of a Float-based skill at a specific level.
	 */
	private float calculateFloatValue(float base, float perLvl, int level) {
		float rawValue = base + level * perLvl;
		float roundedValue = Math.round(rawValue * 10f);
		return roundedValue / 10f;
	}
	
	private String makeTargetString() {
		if (target == null || target.length() == 0) return " ";
		
		StringBuilder sb = new StringBuilder(6);
		if (isAoe) sb.append("~");
		if (isRandom && !isAoe) sb.append("?");
		if (type == Type.BUFF || type == Type.HEAL) {
			sb.append(new StringBuilder(target).reverse());
			sb.insert(0, "@");
		}
		else {
			sb.append(target);
		}
		return sb.toString();
	}
	
}
