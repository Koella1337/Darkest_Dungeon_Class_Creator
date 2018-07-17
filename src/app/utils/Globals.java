package app.utils;

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
	
}
