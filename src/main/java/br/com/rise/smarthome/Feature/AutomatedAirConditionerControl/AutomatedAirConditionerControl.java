package br.com.rise.smarthome.Feature.AutomatedAirConditionerControl;

import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.AirConditioner;
import br.com.rise.smarthome.Devices.TemperatureSensor;
import br.com.rise.smarthome.Feature.AdaptableFeature;
import br.com.rise.smarthome.Feature.AlternativeFeature;
import br.com.rise.smarthome.Feature.AutomatedWindowControl.AutomatedWindowControl;
import br.com.rise.smarthome.Feature.UserAirConditionerControl.UserAirConditionerControl;

import java.util.ArrayList;

@AlternativeFeature(alternatives = AutomatedWindowControl.class)
public class AutomatedAirConditionerControl extends UserAirConditionerControl implements AdaptableFeature {

	private ArrayList<AirConditioner> airConditionersToAutomate;
	private TemperatureSensor temperatureSensor;
	private static AutomatedAirConditionerControl automatedAirConditionerControl = null;

	private AutomatedAirConditionerControl(){}

	public static AutomatedAirConditionerControl getInstance() {
		if(automatedAirConditionerControl == null){
			automatedAirConditionerControl = new AutomatedAirConditionerControl();
			automatedAirConditionerControl.setName("Automated Air Conditioner Control");
			automatedAirConditionerControl.setAirConditionersToAutomate(new ArrayList<AirConditioner>());
			automatedAirConditionerControl.setActive(false);
		}
		return automatedAirConditionerControl;
	}

	public static void destroy() {
		automatedAirConditionerControl = null;
	}

	@Override
	public void proceedActions() {

		if (isActive() && airConditionersToAutomate != null && temperatureSensor != null) {
			for (AirConditioner airConditioner : airConditionersToAutomate) {
				temperatureSensor.act(airConditioner);
			}
		}
	}

	@Override public BaseUI getFeatureUI() {
		return new AutomatedAirConditionerControlUI();
	}

	public ArrayList<AirConditioner> getAirConditionersToAutomate() {
		return airConditionersToAutomate;
	}

	public void setAirConditionersToAutomate(ArrayList<AirConditioner> airConditionersToAutomate) {
		this.airConditionersToAutomate = airConditionersToAutomate;
	}
	public TemperatureSensor getTemperatureSensor() {
		return temperatureSensor;
	}

	public void setTemperatureSensor(TemperatureSensor temperatureSensor) {
		this.temperatureSensor = temperatureSensor;
	}

}
