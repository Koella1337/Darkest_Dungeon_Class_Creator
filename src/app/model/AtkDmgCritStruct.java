package app.model;

/**
 * 
 * @author Koella
 */
public class AtkDmgCritStruct {
	public int atk = 90, atkPerLvl = 5, dmg = 0, dmgPerLvl = 0;
	public float crit = 1.0f, critPerLvl = 1.0f;
	
	public AtkDmgCritStruct() {};
	
	public AtkDmgCritStruct(int atk, int atkPerLvl, int dmg, int dmgPerLvl, float crit, float critPerLvl) {
		this.atk = atk;
		this.atkPerLvl = atkPerLvl;
		this.dmg = dmg;
		this.dmgPerLvl = dmgPerLvl;
		this.crit = crit;
		this.critPerLvl = critPerLvl;
	}
}
