package app.model;

/**
 * 
 * @author Koella
 */
public class AtkDmgCritStruct {
	public int atk = 90, dmg = 0;
	public float atkPerLvl = 5.5f, dmgPerLvl = 0;
	public float crit = 7.5f, critPerLvl = 1.25f;
	
	public AtkDmgCritStruct() {};
	
	public AtkDmgCritStruct(int atk, float atkPerLvl, int dmg, float dmgPerLvl, float crit, float critPerLvl) {
		this.atk = atk;
		this.atkPerLvl = atkPerLvl;
		this.dmg = dmg;
		this.dmgPerLvl = dmgPerLvl;
		this.crit = crit;
		this.critPerLvl = critPerLvl;
	}
}
