package ui.utils;

import java.awt.Dimension;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class Globals {

	public static final String WORKING_DIR = Paths.get("").toAbsolutePath().toString();
	public static final Dimension WINDOW_SIZE = new Dimension(650, 400);
	
	/**
	 * Shows the user a GUI error warning.
	 */
	public static void displayError(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}
