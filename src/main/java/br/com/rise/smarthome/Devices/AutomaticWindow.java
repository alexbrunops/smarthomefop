package br.com.rise.smarthome.Devices;

public class AutomaticWindow extends Actuator {

	public AutomaticWindow(int pin, boolean isAnalog) {
		super(pin,"Automatic Window", isAnalog);
	}
	@Override
	public String toString() {
		return "Window [state=" + getState() + ", pin=" + getPin()+ "]";
	}

}
