package ui.utils;

import java.awt.event.ItemEvent;

import javax.swing.JComboBox;

import app.utils.Globals;

/**
 * A String Combobox that lets the user add new items at runtime.
 * @author Koella
 */
@SuppressWarnings("serial")
public class AddItemCombobox extends JComboBox<String> {

	public AddItemCombobox(String addNewItemString, String inputDialogTitle, String inputDialogMessage) {
		super();
		setup(addNewItemString, inputDialogTitle, inputDialogMessage);
	}
	
	public AddItemCombobox(String[] initialItems, String addNewItemString, String inputDialogTitle, String inputDialogMessage) {
		super(initialItems);
		setup(addNewItemString, inputDialogTitle, inputDialogMessage);
	}
	
	private String previousItem = null;
	
	private void setup(String addNewItemString, String inputDialogTitle, String inputDialogMessage) {
		this.addItem(addNewItemString);
		this.setSelectedItem(null);
		
		this.addItemListener(ev -> {
			if (ev.getStateChange() == ItemEvent.SELECTED && ev.getItem().equals(addNewItemString)) {
				String userInput = Globals.displayInputDialog(inputDialogTitle, inputDialogMessage);
				if (userInput != null && userInput.length() > 0) {
					this.removeItemAt(this.getItemCount() - 1);
					this.addItem(userInput);
					this.addItem(addNewItemString);
					previousItem = userInput;
				}
				this.setSelectedItem(previousItem);
			}
			else {
				previousItem = ev.getItem().toString();
			}
		});
	}
	
}
