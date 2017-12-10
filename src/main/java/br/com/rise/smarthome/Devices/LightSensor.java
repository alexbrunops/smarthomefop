package br.com.rise.smarthome.Devices;

public class LightSensor extends Sensor {

	public LightSensor(int pin, boolean isAnalog) {
		super(pin, "Light Sensor", isAnalog);
	}

	@Override protected int[] activationValues() {
		int[] a = new int[100];
		int i = 0;

		while (i < 100) {
			a[i] = i;
			i++;
		}

		return a;

	}

}
