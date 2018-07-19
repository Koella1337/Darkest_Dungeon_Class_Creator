package app.model;

/**
 * 
 * @author Koella
 */
public class MinMaxStruct {
	public int min = 4, max = 5;
	public float minPerLvl = 1, maxPerLvl = 1;
	
	public MinMaxStruct() {};
	
	public MinMaxStruct(int min, float minPerLvl, int max, float maxPerLvl) {
		this.min = min;
		this.minPerLvl = minPerLvl;
		this.max = max;
		this.maxPerLvl = maxPerLvl;
	}
}