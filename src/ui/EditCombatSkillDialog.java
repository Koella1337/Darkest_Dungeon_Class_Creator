package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import app.model.CombatSkill;
import app.model.Effect;
import app.model.StatStruct;
import app.utils.Globals;
import app.utils.Strings;
import ui.utils.EffectListModel;
import ui.utils.FormFactory;
import ui.utils.components.HelpfulTextfield;
import ui.utils.components.MultiStatPanel;
import ui.utils.components.RankPanel;

/**
 * A JDialog that lets the user edit a Combat Skill.
 * @author Koella
 */
@SuppressWarnings("serial")
public class EditCombatSkillDialog extends JDialog {
	
	public static final String TITLE_FORMAT = "Edit Combat Skill \"%s\"";
	
	private final CombatSkill skill;
	private final JButton btnConfirm, btnReset;
	
	private final JTextField txtID;
	private final JComboBox<CombatSkill.Type> cbxType;
	private final JRadioButton rbRanged, rbMelee;
	private final JCheckBox chkAoe, chkRandom, chkGeneration, chkSelfTarget, chkIgnoreProt, chkIgnoreGuard, chkIgnoreStealth;
	private final JSpinner spinPerBattleLimit;
	private final RankPanel rpTarget, rpLaunch;
	private final MultiStatPanel mstatAtkDmgCrit, mstatHeal, mstatMove;
	private final DefaultListModel<Effect> effectListModel;

	public EditCombatSkillDialog(CombatSkill skillToEdit) {
		super(null, ModalityType.MODELESS);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.skill = skillToEdit;
		
		// --- Initialize all Components ---		
		btnConfirm = createConfirmButton();
		btnReset = createResetButton();

		txtID = new JTextField();
		
		chkRandom = new JCheckBox("Random");
		chkRandom.setFocusable(false);
		
		chkAoe = new JCheckBox("AOE");
		chkAoe.setFocusable(false);
		chkAoe.addItemListener(ev -> {
			chkRandom.setEnabled(!chkAoe.isSelected());
		});
		
		chkSelfTarget = new JCheckBox("Self-target");
		chkSelfTarget.setFocusable(false);
		
		chkGeneration = new JCheckBox("Generation guaranteed");
		chkGeneration.setFocusable(false);
		
		chkIgnoreProt = new JCheckBox("Ignore Protection");
		chkIgnoreProt.setFocusable(false);
		
		chkIgnoreGuard = new JCheckBox("Ignore Guard");
		chkIgnoreGuard.setFocusable(false);
		
		chkIgnoreStealth = new JCheckBox("Ignore Stealth");
		chkIgnoreStealth.setFocusable(false);
		
		rbRanged = new JRadioButton("Ranged Skill");
		rbRanged.setFocusable(false);
		
		rbMelee = new JRadioButton("Melee Skill");
		rbMelee.setFocusable(false);
		
		SpinnerNumberModel perBattleLimitModel = new SpinnerNumberModel();
		perBattleLimitModel.setMinimum(0);
		perBattleLimitModel.setMaximum(20);
		spinPerBattleLimit = new JSpinner(perBattleLimitModel);
		spinPerBattleLimit.setToolTipText(Strings.wrapWithHtml(
				"The maximum amount of times this skill can be used in a battle.<br>"
				+ "Must be a number between 0 and 20. If it is 0 then the skill is not limited."
		));
		
		rpTarget = new RankPanel(true);
		rpLaunch = new RankPanel(false);
		
		mstatAtkDmgCrit = new MultiStatPanel(new String[] {
				"atk: ", "90/5", "dmg:", "100/0", "crit: ", "1/1"
		});
		mstatHeal = new MultiStatPanel(new String[] {
				"min: ", "4/1", "max:", "5/1"
		});
		mstatMove = new MultiStatPanel(new String[] {
				"back: ", "0", "forward:", "0"
		});
		
		cbxType = new JComboBox<>(CombatSkill.Type.values());
		cbxType.setFocusable(false);
		cbxType.setSelectedItem(null);
		cbxType.addItemListener(ev -> {
			if (ev.getStateChange() == ItemEvent.SELECTED) {
				this.disableComponentsByType((CombatSkill.Type) ev.getItem());
			}
		});
		
		effectListModel = new DefaultListModel<Effect>() {	//disallow duplicate entries
			@Override
			public void add(int index, Effect element) {
				if (!this.contains(element))
					super.add(index, element);
			}
			@Override
			public void addElement(Effect element) {
				if (!this.contains(element))
					super.addElement(element);
			}
		};
		
		// --- Finalize ---
		this.add(createMainPanel());
		this.pack();
	}
	
	@Override
	public void setVisible(boolean b) {
		if (b) this.setLocationRelativeTo(Globals.MAIN_WINDOW);
		super.setVisible(b);
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(Globals.EDIT_COMBATSKILL_DIALOG_SIZE);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// --- Create Top Panel ---
		JPanel topPanel = createTopPanel();

		// --- Create Center Panel ---
		JList<Effect> effectList = new JList<>(effectListModel);
		effectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		effectList.setCellRenderer(new ListCellRenderer<Effect>() {
			private final JPanel panel = new JPanel(new BorderLayout());
			private final JLabel label = new JLabel();
			private final JCheckBox checkBox = new JCheckBox("Scales?");
			{
				checkBox.setHorizontalTextPosition(SwingConstants.LEADING);
				checkBox.setEnabled(false);
				panel.add(label, BorderLayout.CENTER);
				panel.add(checkBox, BorderLayout.LINE_END);
			}
			@Override
			public Component getListCellRendererComponent(JList<? extends Effect> list, Effect value, int index,
					boolean isSelected, boolean cellHasFocus) {
				label.setText(value.getName());
				checkBox.setSelected(value.getScalesWithLevel());
				if (isSelected) {
					checkBox.setBackground(effectList.getSelectionBackground());
					panel.setBackground(effectList.getSelectionBackground());
				}
				else {
					checkBox.setBackground(effectList.getBackground());
					panel.setBackground(effectList.getBackground());
				}
				return panel;
			}
		});
		
		JViewport effectListHeader = new JViewport();
		effectListHeader.setView(new JLabel("Effects"));
		JScrollPane effectListScrollPane = new JScrollPane(effectList);
		effectListScrollPane.setColumnHeader(effectListHeader);
		
		// --- Create Right Panel ---
		JPanel rightPanel = new JPanel(new GridLayout(0, 1));
		
		JCheckBox chkIsSkillScaling = new JCheckBox("Effect scales: ");
		chkIsSkillScaling.setHorizontalTextPosition(SwingConstants.LEADING);
		chkIsSkillScaling.setFocusable(false);
		
		JComboBox<Effect> cbxDefaultEffects = new JComboBox<>(Effect.getDefaultEffects());
		cbxDefaultEffects.setFocusable(false);
		cbxDefaultEffects.addItemListener(ev -> {
			if (ev.getStateChange() == ItemEvent.SELECTED) {
				Effect effect = (Effect) ev.getItem();
				effect.setScalesWithLevel(chkIsSkillScaling.isSelected());
				effectListModel.addElement(effect);
				effectList.repaint();
			}
		});
		chkIsSkillScaling.addItemListener(ev -> {	//reset combobox selection on skill scaling checkbox change
			cbxDefaultEffects.setSelectedItem(null);
		});
		chkIsSkillScaling.setSelected(true);
		
		JTextField txtNewEffect = new HelpfulTextfield("New effect name...");
		txtNewEffect.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent ev) {
				if (ev.getKeyCode() == KeyEvent.VK_ENTER) {
					String effectName = txtNewEffect.getText();
					if (effectName != null && effectName.length() > 0)
						effectListModel.addElement(new Effect(effectName, chkIsSkillScaling.isSelected()));
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
		rightPanel.add(chkIsSkillScaling);
		rightPanel.add(cbxDefaultEffects);
		rightPanel.add(new JLabel("Select an Effect to add it to the List."));
		rightPanel.add(txtNewEffect);
		rightPanel.add(new JLabel("Type a name and press Enter to add it to the List."));
		
		// --- Create Bottom Panel ---
		JPanel buttonPanel = new JPanel(new GridLayout(1, 0, 5, 0));
		buttonPanel.add(btnConfirm);
		buttonPanel.add(btnReset);
		
		// --- Finalize ---
		btnReset.doClick();
		panel.add(topPanel, BorderLayout.PAGE_START);
		panel.add(effectListScrollPane, BorderLayout.CENTER);
		panel.add(rightPanel, BorderLayout.LINE_END);
		panel.add(buttonPanel, BorderLayout.PAGE_END);
		return panel;
	}
	
	private JPanel createTopPanel() {
		final JPanel topPanel = new JPanel() {
			@Override
			public Component add(Component comp) {	//create vertical gap after add
				Component ret = super.add(comp);
				super.add(Box.createRigidArea(new Dimension(0, 2)));
				return ret;
			}
		};
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
		final int labelWidth = 90;
		
		topPanel.add(FormFactory.createTwoInputForm("Skill Name: ", txtID, "Type: ", cbxType, labelWidth));
		topPanel.add(FormFactory.createOneInputForm("Target Mod: ", createTargetModifierPanel(), labelWidth));
		topPanel.add(FormFactory.createTwoInputForm("Target Ranks: ", rpTarget, "Launch Ranks: ", rpLaunch, labelWidth));
		
		topPanel.add(FormFactory.createOneInputForm("Damage: ", mstatAtkDmgCrit, labelWidth));
		topPanel.add(FormFactory.createOneInputForm("Heal: ", mstatHeal, labelWidth));
		topPanel.add(FormFactory.createOneInputForm("Move: ", mstatMove, labelWidth));
		
		topPanel.add(FormFactory.createOneInputForm("Extras: ", FormFactory.createLinePanel(new JComponent[] {
				appendLabel(spinPerBattleLimit, " Per-Battle-Limit"), chkGeneration, chkSelfTarget
		}), labelWidth));
		topPanel.add(FormFactory.createOneInputForm("Extras: ", FormFactory.createLinePanel(new JComponent[] {
				chkIgnoreProt, chkIgnoreGuard, chkIgnoreStealth
		}), labelWidth));
		return topPanel;
	}
	
	
	
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------- COMPONENT - CREATING METHODS -----------------------------------
	// ----------------------------------------------------------------------------------------------------
	
	private JButton createConfirmButton() {
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(ev -> {
			//TODO: validate and then actually set the skill's variables with values from the input components
			skill.print(null);
			
			//TODO: uncomment
//			this.setTitle(String.format(TITLE_FORMAT, skill.id));
//			this.setVisible(false);
		});
		btnConfirm.setToolTipText(Strings.wrapWithHtml(
				"When pressing this button your changes will be validated and,<br>"
				+ "if they are valid, saved to the Combat Skill's values."
		));
		btnConfirm.setFocusable(false);
		return btnConfirm;
	}
	
	private JButton createResetButton() {
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(ev -> {
			this.setTitle(String.format(TITLE_FORMAT, skill.id));
			//reset all input components back to the skill's variables
			txtID.setText(skill.id);
			cbxType.setSelectedItem(skill.type);
			
			chkRandom.setSelected(skill.isRandom);
			chkAoe.setSelected(skill.isAoe);
			chkGeneration.setSelected(skill.generation_guaranteed);
			chkSelfTarget.setSelected(skill.self_target_valid);
			chkIgnoreProt.setSelected(skill.ignore_protection);
			chkIgnoreGuard.setSelected(skill.ignore_guard);
			chkIgnoreStealth.setSelected(skill.ignore_stealth);
			
			spinPerBattleLimit.setValue(skill.per_battle_limit);
			
			rbMelee.setSelected(skill.isMelee);
			rbRanged.setSelected(!skill.isMelee);
			
			rpTarget.setRanks(skill.target);
			rpLaunch.setRanks(skill.launch);
			
			mstatAtkDmgCrit.setAllStats(skill.atkDmgCrit.toString().split(StatStruct.STAT_SEPARATOR));
			mstatHeal.setAllStats(skill.heal.toString().split(StatStruct.STAT_SEPARATOR));
			mstatMove.setAllStats(skill.move.toStringWithoutPerLvl().split(StatStruct.STAT_SEPARATOR));
			
			effectListModel.clear();
			for (Effect effect : skill.effects.toArray(new Effect[0])) {
				//cloned effect list so that UI changes are not reflected in the skill before "Confirm" is pressed
				effectListModel.addElement(effect);
			}
		});
		btnReset.setToolTipText(Strings.wrapWithHtml(
				"ATTENTION! This gets rid of every change you haven't confirmed yet!<br>"
				+ "Pressing this button will reset all the fields to the Combat Skill's saved values."
		));
		btnReset.setFocusable(false);
		return btnReset;
	}
	
	private JPanel createTargetModifierPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		
		ButtonGroup rangedVsMelee = new ButtonGroup();
		rangedVsMelee.add(rbRanged);
		rangedVsMelee.add(rbMelee);
		
		panel.add(chkAoe);
		panel.add(chkRandom);
		panel.add(Box.createHorizontalGlue());
		panel.add(rbRanged);
		panel.add(rbMelee);
		return panel;
	}
	
	/**
	 * Appends a label to the specified component and returns a panel containing the two.
	 * @return a JPanel with the specified component in front and a label after it.
	 */
	private JPanel appendLabel(JComponent component, String labelText) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(component);
		panel.add(new JLabel(labelText));
		return panel;
	}
	
	

	// ----------------------------------------------------------------------------------------------------
	/* ----------------------------------- COMPONENT - DISABLING METHODS -----------------------------------
	 * The following methods disable components that won't change the printing of the skill.
	 * Each method is based on a specific "more important" variable that has an impact on other variables.
	 * For example: When a skill is AOE then the checkbox for "Random Target" will be disabled.           */
	// ----------------------------------------------------------------------------------------------------
	
	private void disableComponentsByType(CombatSkill.Type type) {
		boolean healEnabled = true, damageEnabled = true, selfTargetEnabled = true;
		
		switch (type) {
			case HEAL:
				damageEnabled = false;
				break;
			case BUFF:
				damageEnabled = false;
				healEnabled = false;
				break;
			case DEBUFF:
				damageEnabled = false;
				healEnabled = false;
				selfTargetEnabled = false;
				break;
			case DAMAGE: 
				healEnabled = false;
				selfTargetEnabled = false;
				break;
		}
		
		mstatAtkDmgCrit.setComponentsEnabled(damageEnabled);
		mstatHeal.setComponentsEnabled(healEnabled);
		chkSelfTarget.setEnabled(selfTargetEnabled);
	}

}
