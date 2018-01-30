package br.com.rise.smarthome.Devices;

public class AirConditioner extends Actuator {

	public AirConditioner(int pin, boolean isAnalog) {
		super(pin, "Air Conditioner", isAnalog);
	}

	@Override
	public String toString() {
		return "AirConditioner [state=" + getState() + ", pin=" + getPin()+ "]";
	}

}
