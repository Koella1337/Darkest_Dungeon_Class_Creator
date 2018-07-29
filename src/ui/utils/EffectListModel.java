package ui.utils;

import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;

/**
 * A special form of the <code>DefaultListModel&lt;String&gt;</code>.<br>
 * It does not allow duplicate entries and holds a Map which indicates whether<br>
 * the contained effects scale with the Combat Skill's level.
 * @author Koella
 */
@SuppressWarnings("serial")
public class EffectListModel extends DefaultListModel<String> {

	private final Map<String, Boolean> effectScalingMap = new HashMap<>();
	
	/**
	 * @return <b>true</b> if the specified effect scales,<br>
	 * <b>false</b> if it doesn't scale or isn't in the list.
	 */
	public boolean isEffectScaling(String effect) {
		Boolean ret = effectScalingMap.get(effect);
		return ret == null ? false : ret.booleanValue();
	}
	
	/**
	 * Adds the option to make an effect scale with level.
	 * @param element - the effect to add to the end of the list.
	 * @param scalesWithLevel - whether the effect scales. Only works if the effect ends with '1'.<br>
	 * 							If it does and this is true, then the '1' will be scaled up to '5' when printing.
	 */
	public void addElement(String element, boolean scalesWithLevel) {
		effectScalingMap.put(element, (element.endsWith("1") || element.endsWith("1\"")) && scalesWithLevel);
		if (!this.contains(element)) {
			super.addElement(element);
		}
	}
	
	@Override
	public void addElement(String element) {
		this.addElement(element, false);
	}
	
	@Override
	public void add(int index, String element) {
		if (!this.contains(element)) {
			super.add(index, element);
			effectScalingMap.put(element, false);
		}
	}
	
	@Override
	public boolean removeElement(Object obj) {
		effectScalingMap.remove(obj);
		return super.removeElement(obj);
	}
	
	@Override
	public String remove(int index) {
		String ret = super.remove(index);
		effectScalingMap.remove(ret);
		return ret;
	}
	
	@Override
	public void clear() {
		effectScalingMap.clear();
		super.clear();
	}
	
}
