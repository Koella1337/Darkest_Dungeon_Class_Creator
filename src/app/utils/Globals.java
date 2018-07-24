package app.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import ui.MainWindow;

public class Globals {

	public static final String WORKING_DIR = Paths.get("").toAbsolutePath().toString();
	public static final Font FONT_CONSOLAS = new Font("Consolas", Font.PLAIN, 12);;
	
	public static MainWindow MAIN_WINDOW;
	public static final Dimension MAIN_WINDOW_SIZE = new Dimension(800, 450);
	public static final Dimension EDIT_COMBATSKILL_DIALOG_SIZE = new Dimension(700, 400);
	
	/**
	 * Shows the user a GUI error warning.
	 */
	public static void displayError(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Asks the user for an Input.
	 */
	public static String displayInputDialog(String title, String msg) {
		return JOptionPane.showInputDialog(null, msg, title, JOptionPane.QUESTION_MESSAGE);
	}
	
	/**
	 * Creates a new KeyListener that will press the specified button when Enter is released.
	 */
	public static KeyListener createEnterListener(JButton buttonToClick) {
		return new KeyListener() {
			@Override
			public void keyReleased(KeyEvent ev) {
				if (ev.getKeyCode() == KeyEvent.VK_ENTER) {
					buttonToClick.doClick();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		};
	}
	
}
