package app.model;

/**
 * 
 * @author Koella
 */
public class MinMaxStruct extends StatStruct {
	public int min = 4, max = 5;
	public float minPerLvl = 1, maxPerLvl = 1;
	
	public MinMaxStruct() {};
	
	public MinMaxStruct(int min, float minPerLvl, int max, float maxPerLvl) {
		this.min = min;
		this.minPerLvl = minPerLvl;
		this.max = max;
		this.maxPerLvl = maxPerLvl;
	}
	
	@Override
	public String toString() {
		return String.format("%d/%.2f%s%d/%.2f", min, minPerLvl, STAT_SEPARATOR, max, maxPerLvl).replaceAll(",", ".");
	}

	@Override
	public String toStringWithoutPerLvl() {
		return String.format("%d%s%d", min, STAT_SEPARATOR, max).replaceAll(",", ".");
	}
}