package br.com.rise.smarthome.Feature.AutomatedWindowControl;

import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.AutomaticWindow;
import br.com.rise.smarthome.Devices.TemperatureSensor;
import br.com.rise.smarthome.Feature.AdaptableFeature;
import br.com.rise.smarthome.Feature.UserAirConditionerControl.UserAirConditionerControl;

import java.util.ArrayList;

public class AutomatedWindowControl extends UserAirConditionerControl implements AdaptableFeature {

	private ArrayList<AutomaticWindow> automaticWindowsToAutomate;
	private TemperatureSensor temperatureSensor;
	private static AutomatedWindowControl automatedWindowControl = null;

	private AutomatedWindowControl(){}

	public static AutomatedWindowControl getInstance() {
		if(automatedWindowControl == null){
			automatedWindowControl = new AutomatedWindowControl();
			automatedWindowControl.setName("Automated Window Control");
			automatedWindowControl.setAutomaticWindowsToAutomate(new ArrayList<AutomaticWindow>());
			automatedWindowControl.setActive(false);
		}
		return automatedWindowControl;
	}

	public static void destroy() {
		automatedWindowControl = null;
	}

	@Override
	public void proceedActions() {

		if(isActive() && automaticWindowsToAutomate!= null && temperatureSensor!=null){
			for (AutomaticWindow automaticWindow : automaticWindowsToAutomate) {
				temperatureSensor.act(automaticWindow);
			}
		}

	}

	@Override public BaseUI getFeatureUI() {
		return new AutomatedWindowControlUI();
	}

	public ArrayList<AutomaticWindow> getAutomaticWindowsToAutomate() {
		return automaticWindowsToAutomate;
	}

	public void setAutomaticWindowsToAutomate(ArrayList<AutomaticWindow> automaticWindowsToAutomate) {
		this.automaticWindowsToAutomate = automaticWindowsToAutomate;
	}
	public TemperatureSensor getTemperatureSensor() {
		return temperatureSensor;
	}

	public void setTemperatureSensor(TemperatureSensor temperatureSensor) {
		this.temperatureSensor = temperatureSensor;
	}

}
