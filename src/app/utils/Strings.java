package app.utils;

public class Strings {
	
	//String Utility functions
	
	/**
	 * Wraps a String with the &lt;html&gt; and &lt;body&gt; tags.
	 */
	public static String wrapWithHtml(String str) {
		StringBuilder sb = new StringBuilder(str.length() + 26);
		sb.append("<html><body>");		//12 chars
		sb.append(str);
		sb.append("</body></html>");	//14 chars
		return sb.toString();
	}
	
	public static String wrapWithQuotationMarks(String str) {
		StringBuilder sb = new StringBuilder(str.length() + 2);
		if (!str.startsWith("\"")) sb.append("\"");
		sb.append(str);
		if (!str.endsWith("\"")) sb.append("\"");
		return sb.toString();
	}
	
	
	
	//String "constants"

	public static String[] getDefaultCritEffects() {
		return new String[] {
				"\"on_crit_prot\"", 			"\"on_crit_def\"",				"\"on_crit_acc\"",
				"\"on_crit_dmg\"", 				"\"on_crit_dmgmarked\"", 		"\"on_crit_dmgbleeding\"",
				"\"on_crit_bleedchance\"",		"\"on_crit_blightchance\"", 	"\"on_crit_healdone\"",
				"\"on_crit_stresshealdone\"",	"\"on_crit_stressresist\"",		"\"on_crit_speed\""
		};
	}
	
	
	// ------------------ TUTORIALS ------------------
	
	public static String getClassRenamingTutorial() {
		return wrapWithHtml(
			"Copy an existing class folder (\"SteamApps/common/DarkestDungeon/heroes/&lt;chosenclass&gt;\") to somewhere you want. "
			+ "Then set the target folder for this program to your copied folder either by moving this program into the folder and "
			+ "re-opening it or by copying the folder path into the \"Target folder\" text field.<br><br>"
			+ "Now fill in the \"Old Name\" and \"New Name\" textfields.<br>"
			+ "These names can only contain lowercase letters with '_' in-between.<br>"
			+ "--- Example:<br>"
			+ "--- Target folder: \"C:\\my_mod\\heroes\\crusader\",<br>"
			+ "--- Old Name: \"crusader\",<br>"
			+ "--- New Name: \"my_first_class\"<br>"
			+ "Then press Start and look at your files! :)<br><br>"
			+ "P.S.: Relative paths and forward-slashes also work."
		);
	}
	
}
