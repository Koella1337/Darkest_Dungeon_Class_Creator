package app.model;

/**
 * An abstract class for all "Struct" classes that simply hold one or more stat/statPerLvl variables.
 * @author Koella
 */
public abstract class StatStruct {

	public static final String STAT_SEPARATOR = " --- ";
	
	/**
	 * Makes a String that holds all stat variables in the stat/statPerLvl format.<br>
	 * Each stat/statPerLvl pair is separated by the StatStruct.STAT_SEPARATOR String.<br>
	 * Float values have 2 decimal places which are separated by a '.' (instead of a ',').
	 */
	@Override
	public abstract String toString();
	
	/**
	 * Makes a String that holds all stats, but without their respective perLvl values.<br>
	 * Each stat is separated by the StatStruct.STAT_SEPARATOR String.<br>
	 * Float values have 2 decimal places which are separated by a '.' (instead of a ',').
	 */
	public abstract String toStringWithoutPerLvl();
	
}
