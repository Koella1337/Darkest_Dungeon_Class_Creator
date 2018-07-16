package ui.utils;

import java.awt.Dimension;

public class Globals {

	public static final String WORKING_DIR = System.getProperty("user.dir");
	public static final Dimension WINDOW_SIZE = new Dimension(650, 400);
	
	/**
	 * Shows the user a GUI error warning.
	 */
	public static void displayError(String msg) {
		//TODO
		System.err.println(msg);
	}
	
}
