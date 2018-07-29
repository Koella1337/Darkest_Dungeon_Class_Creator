package ui.utils.components;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * A JTextfield that displays instructions to the User when it is empty and not in focus.
 * @author Koella
 */
@SuppressWarnings("serial")
public class HelpfulTextfield extends JTextField {

	private final String instructions;
	private int fontStyle = Font.PLAIN;
	
	/**
	 * Creates a new HelpfulTextfield that displays the instructionString when the field is empty and not in focus.
	 * 
	 * @param instructionString - the string to be displayed to the user, for example telling him the purpose of this field
	 */
	public HelpfulTextfield(String instructionString) {
		super();
		this.instructions = instructionString;
		setup();
	}
	
	/**
	 * Creates a new HelpfulTextfield that displays the instructionString when the field is empty and not in focus.
	 * 
	 * @param instructionString - the string to be displayed to the user, for example telling him the purpose of this field
	 * @param columns - used to calculate the preferred width of the textfield
	 */
	public HelpfulTextfield(String instructionString, int columns) {
		super(columns);
		this.instructions = instructionString;
		setup();
	}
	
	private void setup() {
		reset();
		
		this.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (HelpfulTextfield.super.getText().equals("")) {
					reset();
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if (HelpfulTextfield.super.getText().equals(instructions)) {
					reset();
				}
			}
		});
	}
	
	@Override
	public String getText() {
		return super.getText().equals(instructions) ? "" : super.getText();
	}

	/**
	 * Returns the instructions given to the user when the Textfield is empty and not in focus.
	 * 
	 * @return the instructions string
	 */
	public String getInstructions() {
		return instructions;
	}
	
	/**
	 * Resets the Textfield to the instructions String and the Font to Italic.<br>
	 * If the Textfield is currently focussed then it is reset to an empty String and the fontStyle of this component.
	 */
	public void reset() {
		if (hasFocus()) {
			setText("");
			super.setFont(new Font(getFont().getName(), fontStyle, getFont().getSize()));
		}
		else {
			setText(instructions);
			super.setFont(new Font(getFont().getName(), Font.ITALIC, getFont().getSize()));
		}
	}

	public int getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}
	
	@Override
	public void setFont(Font f) {
		setFontStyle(f.getStyle());
		super.setFont(f);
	}
	
}
