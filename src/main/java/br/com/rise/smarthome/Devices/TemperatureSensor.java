package br.com.rise.smarthome.Devices;

public class TemperatureSensor extends Sensor {

	public TemperatureSensor(int pin, boolean isAnalog) {
		super(pin, "Temperature Sensor", isAnalog);
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