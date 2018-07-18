package app.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Globals {

	public static final String WORKING_DIR = Paths.get("").toAbsolutePath().toString();
	public static final Dimension WINDOW_SIZE = new Dimension(800, 450);
	public static final Font FONT_CONSOLAS = new Font("Consolas", Font.PLAIN, 12);;
	
	/**
	 * Checks whether the specified Class/Skill/Effect name is valid.<br>
	 * It is valid if it only contains lowercase letters with '_' in-between.
	 */
	public static boolean isNameValid(String name) {
		return name.matches("([a-z]+(_[a-z]+)?)+");
	}
	
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
	 * Wraps a String with the &lt;html&gt; and &lt;body&gt; tags.
	 */
	public static String wrapWithHtml(String str) {
		StringBuilder sb = new StringBuilder(str.length() + 26);
		sb.append("<html><body>");		//12 chars
		sb.append(str);
		sb.append("</body></html>");	//14 chars
		return sb.toString();
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
