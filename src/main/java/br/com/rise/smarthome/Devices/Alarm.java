package br.com.rise.smarthome.Devices;

public class Alarm extends Actuator {

	public Alarm(int pin, boolean isAnalog) {
		super(pin, "Alarm", isAnalog);
	}

	@Override public String toString() {
		return "Alarm [state=" + getState() + ", pin=" + getPin()+ "]";
	}
}
