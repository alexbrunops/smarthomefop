package br.com.rise.smarthome.Feature.UserAirConditionerControl;

import br.com.rise.smarthome.BaseComponents.BaseFeature;
import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.AirConditioner;
import br.com.rise.smarthome.Feature.AlternativeFeature;
import br.com.rise.smarthome.Feature.UserWindowControl.UserWindowControl;

import java.util.ArrayList;

@AlternativeFeature(alternatives = UserWindowControl.class)
public class UserAirConditionerControl extends BaseFeature {

	private ArrayList<AirConditioner> airConditioners;

	private static UserAirConditionerControl userAirConditionerControl = null;

	public UserAirConditionerControl(){}

	public static UserAirConditionerControl getInstance(ArrayList<AirConditioner> airConditioners) {
		if (userAirConditionerControl == null) {
			userAirConditionerControl = new UserAirConditionerControl();
			userAirConditionerControl.setName("User Air Conditioner Control");
			userAirConditionerControl.setAirConditioners(airConditioners);
		}

		return userAirConditionerControl;
	}

	public static void destroy() {
		userAirConditionerControl = null;
	}

	@Override public void proceedActions(String[] args) {
		// [0] Led pin
		// [1] action: 1 on; 0 off;
		for (AirConditioner airConditioner : airConditioners) {

			if (airConditioner.getPin() == Integer.parseInt(args[0])) {
				if (Integer.parseInt(args[1]) == 1) {
					airConditioner.activate();
				} else if (Integer.parseInt(args[1]) == 0 ) {
					airConditioner.deactivate();
				}
			}
		}

	}

	@Override public BaseUI getFeatureUI() {
		return new UserAirConditionerControlUI();
	}

	public ArrayList<AirConditioner> getAirConditioners() {
		return airConditioners;
	}

	public void setAirConditioners(ArrayList<AirConditioner> airConditioners) {
		this.airConditioners = airConditioners;
	}

}
