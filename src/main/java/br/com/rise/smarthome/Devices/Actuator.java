package br.com.rise.smarthome.Devices;

public abstract class Actuator extends Hardware {

	private int state;

	public Actuator(int pin, String name, boolean isAnalog) {
		super(pin, name, isAnalog);
		setState(0);
	}

	public void activate() {
		ArduinoControl.getInstance().activate(this);
		setState(1);
	}

	public void deactivate() {
		ArduinoControl.getInstance().deactivate(this);
		setState(0);
	}

	public void switchState() {
		if (getState() == 0) {
			activate();
		} else {
			deactivate();
		}
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
