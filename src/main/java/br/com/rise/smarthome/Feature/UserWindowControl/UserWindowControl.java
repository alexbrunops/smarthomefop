package br.com.rise.smarthome.Feature.UserWindowControl;

import br.com.rise.smarthome.BaseComponents.BaseFeature;
import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.AutomaticWindow;

import java.util.ArrayList;

public class UserWindowControl extends BaseFeature {

	private ArrayList<AutomaticWindow> automaticWindows;
	private static UserWindowControl userWindowControl = null;

	public UserWindowControl(){}

	public static UserWindowControl getInstance(ArrayList<AutomaticWindow> automaticWindows) {
		if(userWindowControl == null){
			userWindowControl = new UserWindowControl();
			userWindowControl.setName("User Window Control");
			userWindowControl.setAutomaticWindows(automaticWindows);
		}
		return userWindowControl;
	}

	public static void destroy() {
		userWindowControl = null;
	}

	@Override public void proceedActions(String[] args) {
		// [0] Led pin
		// [1] action: 1 on; 0 off;
		for (AutomaticWindow automaticWindow : automaticWindows) {
			if (automaticWindow.getPin() == Integer.parseInt(args[0])) {
				if (Integer.parseInt(args[1]) == 1) {
					automaticWindow.activate();
				} else if (Integer.parseInt(args[1]) == 0 ) {
					automaticWindow.deactivate();
				}
			}
		}
	}

	@Override public BaseUI getFeatureUI() {
		return new UserWindowControlUI();
	}

	public ArrayList<AutomaticWindow> getAutomaticWindows() {
		return automaticWindows;
	}

	public void setAutomaticWindows(ArrayList<AutomaticWindow> automaticWindows) {
		this.automaticWindows = automaticWindows;
	}
}
