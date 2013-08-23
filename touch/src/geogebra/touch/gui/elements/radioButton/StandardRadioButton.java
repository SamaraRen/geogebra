package geogebra.touch.gui.elements.radioButton;

import geogebra.touch.gui.algebra.events.FastClickHandler;
import geogebra.touch.gui.elements.StandardButton;

public class StandardRadioButton extends StandardButton {

	private boolean activated = false;
	private final StandardRadioGroup group;

	public StandardRadioButton(String label, StandardRadioGroup group) {
		super(laf.getIcons().radioButtonInactive(), label);
		this.setStyleName("radioButton");

		this.group = group;
		this.group.addRadioButton(this);

		this.addFastClickHandler(new FastClickHandler() {

			@Override
			public void onClick() {
				handleClick();
			}
		});
	}

	void handleClick() {
		if (this.activated) {
			return;
		}
		this.group.deselectAll();
		this.setActive(true);
		this.group.fireRadioChanged(this);
	}

	@Override
	public void setActive(boolean value) {
		if (value) {
			this.group.deselectAll();
		}
		this.activated = value;
		if (value) {
			this.setIcon(laf.getIcons().radioButtonActive());
		} else {
			this.setIcon(laf.getIcons().radioButtonInactive());
		}
	}

	public boolean isActivated() {
		return this.activated;
	}

	@Override
	public void onHoldPressDownStyle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHoldPressOffStyle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisablePressStyle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnablePressStyle() {
		// TODO Auto-generated method stub

	}
}
