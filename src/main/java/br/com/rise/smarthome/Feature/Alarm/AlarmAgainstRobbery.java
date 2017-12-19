package br.com.rise.smarthome.Feature.Alarm;

import br.com.rise.smarthome.BaseComponents.BaseFeature;
import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.Alarm;
import br.com.rise.smarthome.Feature.OptionalFeature;

import java.util.ArrayList;

@OptionalFeature
public class AlarmAgainstRobbery extends BaseFeature {

	private ArrayList<Alarm> alarms;
	private AlarmAgainstRobberyThread alarmAgainstRobberyThread;
	private static AlarmAgainstRobbery alarmAgainstRobbery = null;

	public AlarmAgainstRobbery() { }

	public static AlarmAgainstRobbery getInstance(ArrayList<Alarm> alarms) {
		if (alarmAgainstRobbery == null) {
			alarmAgainstRobbery = new AlarmAgainstRobbery();
			alarmAgainstRobbery.setName("Alarm");
			alarmAgainstRobbery.setAlarms(alarms);
		}

		return alarmAgainstRobbery;
	}

	public static void destroy() {
		alarmAgainstRobbery = null;
	}

	@Override public BaseUI getFeatureUI() {
		return new AlarmAgainstRobberyUI();
	}

	@Override public void proceedActions(String[] args) {
		// [0] - 1 to activate; 0 to deactive

		if (args[1].equals("1")) {
			if(alarmAgainstRobberyThread==null || !alarmAgainstRobberyThread.isAlive()){
				alarmAgainstRobberyThread = new AlarmAgainstRobberyThread(alarms);
				alarmAgainstRobberyThread.run();
			}
		} else if (args[0].equals("0")){
			if(alarmAgainstRobberyThread!=null || alarmAgainstRobberyThread.isAlive()){
				alarmAgainstRobberyThread.finishAction();
			}
		}
	}

	public ArrayList<Alarm> getAlarms() {
		return alarms;
	}

	public void setAlarms(ArrayList<Alarm> alarms) {
		this.alarms = alarms;
	}

	private class AlarmAgainstRobberyThread extends Thread {

		private ArrayList<Alarm> alarms;
		private boolean shouldInterrupt = false;

		public AlarmAgainstRobberyThread(ArrayList<Alarm> alarms) {
			this.alarms = alarms;
		}

		@Override public void run() {
			while(!shouldInterrupt) {
				for (Alarm alarm : alarms) {
					alarm.activate();
				}
			}
		}

		protected void finishAction() {
			shouldInterrupt = true;
		}

	}

}
