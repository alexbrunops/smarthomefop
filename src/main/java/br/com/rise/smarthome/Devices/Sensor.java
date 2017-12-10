package br.com.rise.smarthome.Devices;

import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;

public abstract class Sensor extends Hardware {

	public Sensor(int pin, String name, boolean isAnalog) {
		super(pin, name, isAnalog);
	}

	protected abstract int[] activationValues();

	public final void act(Actuator actuator) {

		if (isInRange(activationValues(), getPinValue())) {
			actuator.activate();
			return;
		} else {
			actuator.deactivate();
			return;
		}

	}

	private boolean isInRange(int[] array, int value) {
		for (int i = 0; i < array.length; i++) {
			if(array[i] == value){
				return true;
			}
		}

		return false;
	}

	public int getPinValue() {
		return ArduinoControl.getInstance().getPinValue(getPin(), isAnalog());
	}

	@Override public String toString() {
		return getName() + "[pin=" + getPin() + "]";
	}
}
