package br.com.rise.smarthome.Devices;

public class PresenceSensor extends Sensor {

    public PresenceSensor(int pin, boolean isAnalog) { super(pin, "Presence Sensor", isAnalog); }

    @Override
    protected int[] activationValues() {
        int[] activated = { 0 };
        return activated;
    }
}
