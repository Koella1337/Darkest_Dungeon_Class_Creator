package app.utils;

/**
 * Utility class used for various validations in the application.
 * @author Koella
 */
public class Validations {

	/**
	 * Checks whether the specified Class/Skill/Effect name is valid.<br>
	 * It is valid if it only contains lowercase letters with '_' in-between.
	 */
	public static boolean isNameValid(String name) {
		return name.matches("([a-z]+(_[a-z]+)?)+");
	}
	
}
