package br.com.rise.smarthome.Devices;

public class AutomaticDoor extends Actuator {

	public AutomaticDoor(int pin, boolean isAnalog) {
		super(pin,"Automatic Door", isAnalog);
	}

	@Override
	public String toString() {
		return "Automatic Door [state=" + getState() + ", pin=" + getPin()+ "]";
	}

}