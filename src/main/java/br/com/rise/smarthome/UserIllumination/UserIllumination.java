package br.com.rise.smarthome.UserIllumination;

import br.com.rise.smarthome.BaseComponents.BaseFeature;
import br.com.rise.smarthome.Devices.Led;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;

public class UserIllumination extends BaseFeature {

	private ArrayList<Led> leds;
	private static UserIllumination userIllumination = null;

	protected UserIllumination() {}

	public static UserIllumination getInstance(ArrayList<Led> leds) {
		if (userIllumination == null) {
			userIllumination = new UserIllumination();
			userIllumination.setName("User Illumination");
			userIllumination.setLeds(leds);
		}

		return userIllumination;
	}

	public static void destroy() {
		userIllumination = null;
	}

	@Override public synchronized void proceedActions(String[] args) {

		if (CollectionUtils.isNotEmpty(leds)) {
			leds.forEach(led -> {

				if (led.getPin() == Integer.parseInt(args[0])) {
					if (Integer.parseInt(args[1]) == 0) {
						led.deactivate();
					}

					if (Integer.parseInt(args[1]) == 1) {
						led.activate();
					}

					if (Integer.parseInt(args[1]) == -1) {
						led.switchState();
					}
				}

			});
		}

	}

	public ArrayList<Led> getLeds() {
		return leds;
	}

	public void setLeds(ArrayList<Led> leds) {
		this.leds = leds;
	}
}
