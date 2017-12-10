package br.com.rise.smarthome.Devices;

public class Led extends Actuator {

	public Led(int pin, boolean isAnalog) {
		super(pin, "Led", isAnalog);
	}

	@Override public String toString() {
		return "Led{[ state=" + getState() + ", pin=" + getPin() + " ]}";
	}
}
