package app.utils;

public class Strings {

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
		return Globals.wrapWithHtml(
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
