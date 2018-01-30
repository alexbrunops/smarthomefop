package br.com.rise.smarthome.Feature.LockDoors;

import br.com.rise.smarthome.BaseComponents.BaseFeature;
import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.AutomaticDoor;

import java.util.ArrayList;

public class LockDoors extends BaseFeature {

	private ArrayList<AutomaticDoor> automaticDoors;

	private static LockDoors lockDoors = null;

	protected LockDoors(){}

	public static LockDoors getInstance(ArrayList<AutomaticDoor> automaticDoors) {
		if(lockDoors == null){
			lockDoors = new LockDoors();
			lockDoors.setName("Lock Doors");
			lockDoors.setautomaticDoors(automaticDoors);
		}
		return lockDoors;
	}

	public static void destroy() {
		lockDoors = null;
	}

	@Override
	public void proceedActions(String[] args) {
		// [0] - 0 Lock all doors; 1 Unlock all doors
		for (AutomaticDoor automaticDoor : automaticDoors) {
			if (args[0].equals("0")) {
				automaticDoor.deactivate();
			} else if (args[0].equals("1")) {
				automaticDoor.activate();
			}
		}
	}

	@Override public BaseUI getFeatureUI() {
		return new LockDoorsUI();
	}

	public ArrayList<AutomaticDoor> getautomaticDoors() {
		return automaticDoors;
	}

	public void setautomaticDoors(ArrayList<AutomaticDoor> automaticDoors) {
		this.automaticDoors = automaticDoors;
	}

}
