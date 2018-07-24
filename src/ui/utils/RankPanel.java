package ui.utils;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 * A JPanel that has 4 JCheckboxes: one for each "rank" (aka position in combat).<br>
 * The getRanks() method returns a String that represents all selected ranks.
 * 
 * @author Koella
 */
@SuppressWarnings("serial")
public class RankPanel extends JPanel {

	private final boolean displayAsTargetRanks;
	private final JCheckBox rank1, rank2, rank3, rank4;
	
	/**
	 * Constructs the rank panel with it's 4 checkboxes.
	 * @param displayAsTargetRanks :<br>if this is false then ranks are displayed and<br>
	 * 									evaluated as "4321" instead of "1234".
	 */
	public RankPanel(boolean displayAsTargetRanks) {
		super(new GridLayout(1, 0));		
		this.displayAsTargetRanks = displayAsTargetRanks;
		rank1 = new JCheckBox(displayAsTargetRanks ? "1" : "4");
		rank2 = new JCheckBox(displayAsTargetRanks ? "2" : "3");
		rank3 = new JCheckBox(displayAsTargetRanks ? "3" : "2");
		rank4 = new JCheckBox(displayAsTargetRanks ? "4" : "1");
		rank1.setFocusable(false);
		rank2.setFocusable(false);
		rank3.setFocusable(false);
		rank4.setFocusable(false);
		this.add(rank1);
		this.add(rank2);
		this.add(rank3);
		this.add(rank4);
	}
	
	/**
	 * Returns the selected ranks (always in the order "1234", even if displayAsTargetRanks is false).
	 * @return
	 */
	public String getRanks() {
		String ranks = "";
		
		for (int i = displayAsTargetRanks ? 1 : 4; i >= 1 && i <= 4; i += displayAsTargetRanks ? 1 : -1) {
			try {
				JCheckBox curRank = (JCheckBox) this.getClass().getDeclaredField("rank" + i).get(this);
				if (curRank.isSelected()) {
					ranks += curRank.getText();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ranks;
	}
	
	/**
	 * Sets the selected JCheckBoxes according to a specified ranks String.
	 * @param ranks - the ranks string to derive the selected ranks from.
	 * @throws IllegalArgumentException if ranks is null, has an invalid length or contains an invalid rank.
	 */
	public void setRanks(String ranks) throws IllegalArgumentException {
		if (ranks == null || ranks.length() > 6) 
			throw new IllegalArgumentException("Ranks String invalid");
		if (ranks.matches("[~?@]+.*")) {
			ranks = ranks.substring(ranks.indexOf('~') + 1);
			ranks = ranks.substring(ranks.indexOf('?') + 1);
			ranks = ranks.substring(ranks.indexOf('@') + 1);
		}
		
		//reset ranks
		rank1.setSelected(false);
		rank2.setSelected(false);
		rank3.setSelected(false);
		rank4.setSelected(false);
		
		//set ranks according to String
		for (char c : ranks.toCharArray()) {
			try {
				int rank = Character.getNumericValue(c);
				if (rank < 1 || rank > 4) throw new IllegalArgumentException("Rank \"" + rank + "\" in Ranks String invalid");
				if (!displayAsTargetRanks) rank = 5 - rank;
				
				JCheckBox curRank = (JCheckBox) this.getClass().getDeclaredField("rank" + rank).get(this);
				curRank.setSelected(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
