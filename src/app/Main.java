package app;

import app.utils.Globals;
import ui.MainWindow;

/**
 * Simple entry point for the application.
 * @author Koella
 */
public class Main {

	public static void main(String[] args) {
		Globals.MAIN_WINDOW = new MainWindow();
		Globals.MAIN_WINDOW.createFeaturePanels();
		Globals.MAIN_WINDOW.packAndShow();
	}
	
}
