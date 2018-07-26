package app.model;

/**
 * 
 * @author Koella
 */
public class AtkDmgCritStruct extends StatStruct {
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
	
	@Override
	public String toString() {
		return String.format("%d/%.2f%s%d/%.2f%s%.1f/%.2f", atk, atkPerLvl, STAT_SEPARATOR, dmg, dmgPerLvl, 
				STAT_SEPARATOR, crit, critPerLvl).replaceAll(",", ".");
	}

	@Override
	public String toStringWithoutPerLvl() {
		return String.format("%d%s%d%s%.2f", atk, STAT_SEPARATOR, dmg, STAT_SEPARATOR, crit).replaceAll(",", ".");
	}
}
