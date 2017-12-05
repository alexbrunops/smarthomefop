package br.com.rise.smarthome.Devices;

public abstract class Hardware {

	private int pin;
	private String name;
	private boolean isAnalog;

	public Hardware(int pin, String name, boolean isAnalog) {
		this.pin = pin;
		this.name = name;
		this.isAnalog = isAnalog;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAnalog() {
		return isAnalog;
	}

	public void setAnalog(boolean analog) {
		isAnalog = analog;
	}

	@Override public String toString() {
		return "Hardware{" + "name='" + name + '\'' + '}';
	}

	@Override public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAnalog ? 1231 : 1237);
		result = prime * result + pin;
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Hardware)) {
			return false;
		}

		Hardware other = (Hardware) obj;
		if (isAnalog != other.isAnalog()) {
			return false;
		}

		if (pin != other.getPin()) {
			return false;
		}

		return true;
	}
}
