package app.model;

import java.io.PrintStream;

import app.utils.Strings;

public class Effect {

	/**
	 * An expandable List of effects that a Combat Skill could utilize.
	 */
	public static Effect[] getDefaultEffects () {
		return new Effect[] {
				new Effect("Unholy Killer 1", true), 		new Effect("Man Killer 1", true), 	new Effect("Beast Killer 1", true),
				new Effect("Eldritch Killer 1", true)
		};
	}
	
	private final String name;
	private boolean scalesWithLevel;
	
	public Effect(String name, boolean scaleWithLevel) {
		this.name = name;
		setScalesWithLevel(scaleWithLevel);
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getScalesWithLevel() {
		return scalesWithLevel;
	}

	/**
	 * Sets whether this effect scales with the Combat Skill's level.<br>
	 * Can only be set to true if the effect's name ends with "1".
	 * @param scalesWithLevel
	 */
	public void setScalesWithLevel(boolean scalesWithLevel) {
		this.scalesWithLevel = scalesWithLevel && (name.endsWith("1") || name.endsWith("1\""));
	}

	/**
	 * Simply returns the name of this effect.<br>
	 * Therefore this method is identical to <code>getName()</code>
	 */
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Effect))
			return false;
		return name.equals(((Effect) obj).name);
	}
	
	/**
	 * Prints the effect by wrapping it with quotation marks and, if scalesWithLevel is true, scaling it.<br>
	 * If out is null then System.out is used.
	 * @param level - the level to scale this effect to if scalesWithLevel is true.
	 */
	public void print(PrintStream out, int level) {
		if (out == null) out = System.out;
		
		String printedName = name;
		if (scalesWithLevel) {
			printedName = printedName.replaceAll("\"", "");						//remove quotation marks
			printedName = printedName.substring(0, printedName.length() - 2);	//remove "1" at the end
			printedName += level;												//add scaled level at the end
		}
		out.print(Strings.wrapWithQuotationMarks(printedName));
	}
}
