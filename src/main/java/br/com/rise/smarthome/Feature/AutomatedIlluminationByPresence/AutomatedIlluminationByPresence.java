package br.com.rise.smarthome.Feature.AutomatedIlluminationByPresence;

import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.Led;
import br.com.rise.smarthome.Devices.PresenceSensor;
import br.com.rise.smarthome.Feature.AdaptableFeature;
import br.com.rise.smarthome.Feature.UserIllumination.UserIllumination;

import java.util.ArrayList;

public class AutomatedIlluminationByPresence extends UserIllumination implements AdaptableFeature {

	private ArrayList<Led> ledsToAutomate;
	private PresenceSensor presenceSensor;
	private static AutomatedIlluminationByPresence automatedIlluminationByPresence = null;

	private AutomatedIlluminationByPresence() {
	}

	public static AutomatedIlluminationByPresence getInstance() {

		if (automatedIlluminationByPresence == null) {
			automatedIlluminationByPresence = new AutomatedIlluminationByPresence();
			automatedIlluminationByPresence.setName("Automated Illumination By Presence");
			automatedIlluminationByPresence.setLedsToAutomate(new ArrayList<Led>());
			automatedIlluminationByPresence.setActive(false);
		}

		return automatedIlluminationByPresence;

	}

	public static void destroy() {
		automatedIlluminationByPresence = null;
	}

	@Override public void proceedActions() {

		if (isActive() && ledsToAutomate != null && presenceSensor != null) {
			for (Led led: ledsToAutomate) {
				presenceSensor.act(led);
			}
		}

	}

	@Override public BaseUI getFeatureUI() {
		return new AutomatedIlluminationByPresenceUI();
	}

	public ArrayList<Led> getLedsToAutomate() {
		return ledsToAutomate;
	}

	public void setLedsToAutomate(ArrayList<Led> ledsToAutomate) {
		this.ledsToAutomate = ledsToAutomate;
	}

	public PresenceSensor getPresenceSensor() {
		return presenceSensor;
	}

	public void setPresenceSensor(PresenceSensor presenceSensor) {
		this.presenceSensor = presenceSensor;
	}
}
